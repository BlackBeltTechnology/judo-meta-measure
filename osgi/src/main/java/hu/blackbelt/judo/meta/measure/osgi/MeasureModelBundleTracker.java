package hu.blackbelt.judo.meta.measure.osgi;

/*-
 * #%L
 * Judo :: Measure :: Model
 * %%
 * Copyright (C) 2018 - 2022 BlackBelt Technology
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */

import hu.blackbelt.judo.meta.measure.runtime.MeasureModel;
import hu.blackbelt.osgi.utils.osgi.api.BundleCallback;
import hu.blackbelt.osgi.utils.osgi.api.BundleTrackerManager;
import hu.blackbelt.osgi.utils.osgi.api.BundleUtil;
import lombok.extern.slf4j.Slf4j;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.VersionRange;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

@Component(immediate = true)
@Slf4j
public class MeasureModelBundleTracker {

    public static final String MEASURE_MODELS = "Measure-Models";

    @Reference
    BundleTrackerManager bundleTrackerManager;

    Map<String, ServiceRegistration<MeasureModel>> measureModelRegistrations = new ConcurrentHashMap<>();

    Map<String, MeasureModel> measureModels = new HashMap<>();

    @Activate
    public void activate(final ComponentContext componentContext) {
        bundleTrackerManager.registerBundleCallback(this.getClass().getName(),
                new MeasureRegisterCallback(componentContext.getBundleContext()),
                new MeasureUnregisterCallback(),
                new MeasureBundlePredicate());
    }

    @Deactivate
    public void deactivate(final ComponentContext componentContext) {
        bundleTrackerManager.unregisterBundleCallback(this.getClass().getName());
    }

    private static class MeasureBundlePredicate implements Predicate<Bundle> {
        @Override
        public boolean test(Bundle trackedBundle) {
            return BundleUtil.hasHeader(trackedBundle, MEASURE_MODELS);
        }
    }

    private class MeasureRegisterCallback implements BundleCallback {

        BundleContext bundleContext;

        public MeasureRegisterCallback(BundleContext bundleContext) {
            this.bundleContext = bundleContext;
        }


        @Override
        public void accept(Bundle trackedBundle) {
            List<Map<String, String>> entries = BundleUtil.getHeaderEntries(trackedBundle, MEASURE_MODELS);


            for (Map<String, String> params : entries) {
                String key = params.get(MeasureModel.NAME);
                if (measureModelRegistrations.containsKey(key)) {
                    log.error("Measure model already loaded: " + key);
                } else {
                    // Unpack model
                    try {
                                MeasureModel measureModel = MeasureModel.loadMeasureModel(
                                MeasureModel.LoadArguments.measureLoadArgumentsBuilder()
                                        .inputStream(trackedBundle.getEntry(params.get("file")).openStream())
                                        .name(params.get(MeasureModel.NAME))
                                        .version(trackedBundle.getVersion().toString()));

                        log.info("Registering Measure model: " + measureModel);

                        ServiceRegistration<MeasureModel> modelServiceRegistration = bundleContext.registerService(MeasureModel.class, measureModel, measureModel.toDictionary());
                        measureModels.put(key, measureModel);
                        measureModelRegistrations.put(key, modelServiceRegistration);

                    } catch (IOException | MeasureModel.MeasureValidationException e) {
                        log.error("Could not load Measure model: " + params.get(MeasureModel.NAME) + " from bundle: " + trackedBundle.getBundleId(), e);
                    }
                }
            }
        }

        @Override
        public Thread process(Bundle bundle) {
            return null;
        }
    }

    private class MeasureUnregisterCallback implements BundleCallback {

        @Override
        public void accept(Bundle trackedBundle) {
            List<Map<String, String>> entries = BundleUtil.getHeaderEntries(trackedBundle, MEASURE_MODELS);
            for (Map<String, String> params : entries) {
                String key = params.get(MeasureModel.NAME);

                if (measureModels.containsKey(key)) {
                    ServiceRegistration<MeasureModel> modelServiceRegistration = measureModelRegistrations.get(key);

                    if (modelServiceRegistration != null) {
                        log.info("Unregistering Measure model: " + measureModels.get(key));
                        modelServiceRegistration.unregister();
                        measureModelRegistrations.remove(key);
                        measureModels.remove(key);
                    }
                } else {
                    log.error("Measure Model is not registered: " + key);
                }
            }
        }

        @Override
        public Thread process(Bundle bundle) {
            return null;
        }
    }

}
