package hu.blackbelt.judo.meta.measure.osgi;

import hu.blackbelt.epsilon.runtime.osgi.BundleURIHandler;
import hu.blackbelt.judo.meta.measure.Measure;
import hu.blackbelt.judo.meta.measure.runtime.MeasureModel;
import hu.blackbelt.osgi.utils.osgi.api.BundleCallback;
import hu.blackbelt.osgi.utils.osgi.api.BundleTrackerManager;
import hu.blackbelt.osgi.utils.osgi.api.BundleUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.VersionRange;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

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
                    if (params.containsKey(MeasureModel.META_VERSION_RANGE)) {
                        VersionRange versionRange = new VersionRange(params.get(MeasureModel.META_VERSION_RANGE).replaceAll("\"", ""));
                        if (versionRange.includes(bundleContext.getBundle().getVersion())) {
                            // Unpack model
                            try {
                                        MeasureModel measureModel = MeasureModel.loadMeasureModel(
                                        MeasureModel.LoadArguments.measureLoadArgumentsBuilder()
                                                .uriHandler(new BundleURIHandler(trackedBundle.getSymbolicName(), "", trackedBundle))
                                                .uri(URI.createURI(trackedBundle.getSymbolicName() + ":" + params.get("file")))
                                                .name(params.get(MeasureModel.NAME))
                                                .version(trackedBundle.getVersion().toString())
                                                .checksum(Optional.ofNullable(params.get(MeasureModel.CHECKSUM)).orElse("notset"))
                                                .acceptedMetaVersionRange(Optional.of(versionRange.toString()).orElse("[0,99)")));

                                log.info("Registering Measure model: " + measureModel);

                                ServiceRegistration<MeasureModel> modelServiceRegistration = bundleContext.registerService(MeasureModel.class, measureModel, measureModel.toDictionary());
                                measureModels.put(key, measureModel);
                                measureModelRegistrations.put(key, modelServiceRegistration);

                            } catch (IOException e) {
                                log.error("Could not load Measure model: " + params.get(MeasureModel.NAME) + " from bundle: " + trackedBundle.getBundleId());
                            } catch (MeasureModel.MeasureValidationException e) {
                                log.error("Could not load Measure model: " + params.get(MeasureModel.NAME) + " from bundle: " + trackedBundle.getBundleId(), e);
                            }                        }
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
