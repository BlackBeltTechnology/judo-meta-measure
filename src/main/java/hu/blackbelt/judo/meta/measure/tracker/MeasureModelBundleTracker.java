package hu.blackbelt.judo.meta.measure.tracker;

import hu.blackbelt.judo.meta.measure.MeasureMetaModel;
import hu.blackbelt.judo.meta.measure.MeasureModelInfo;
import hu.blackbelt.osgi.utils.osgi.api.BundleCallback;
import hu.blackbelt.osgi.utils.osgi.api.BundleTrackerManager;
import hu.blackbelt.osgi.utils.osgi.api.BundleUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@Component(immediate = true)
@Slf4j
public class MeasureModelBundleTracker {

    public static final String MEASURE_MODELS = "Measure-Models";

    @Reference
    BundleTrackerManager bundleTrackerManager;

    @Reference
    MeasureMetaModel measureMetaModel;

    Map<String, ServiceRegistration<MeasureModelInfo>> measureRegistrations = new ConcurrentHashMap<>();
    Map<String, MeasureModelInfo> measureModels = new HashMap<>();

    @Activate
    public void activate(final ComponentContext componentContext) {
        bundleTrackerManager.registerBundleCallback(this.getClass().getName(),
                new MeasureRegisterCallback(componentContext.getBundleContext()),
                new MeasureUnregisterCallback(componentContext.getBundleContext()),
                new MeasureBundlePredicate());
    }

    @Deactivate
    public void deactivate(final ComponentContext componentContext) {
        bundleTrackerManager.unregisterBundleCallback(this.getClass().getName());
    }

    private static class MeasureBundlePredicate implements Predicate<Bundle> {
        @Override
        public boolean test(Bundle trackedBundle) {
            return  BundleUtil.hasHeader(trackedBundle, MEASURE_MODELS);
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
                if (params.containsKey(MeasureModelInfo.META_VERSION)) {
                    VersionRange versionRange = new VersionRange(params.get(MeasureModelInfo.META_VERSION).replaceAll("\"", ""));
                    if (versionRange.includes(bundleContext.getBundle().getVersion())) {

                        // Unpack model
                        try {
                            String key = trackedBundle.getBundleId() + "-" + params.get(MeasureModelInfo.NAME);

                            File file = BundleUtil.copyBundleFileToPersistentStorage(trackedBundle, key + ".model", params.get(MeasureModelInfo.FILE));

                            MeasureModelInfo measureModelInfo = new MeasureModelInfo(
                                    file,
                                    params.get(MeasureModelInfo.NAME),
                                    new Version(params.get(MeasureModelInfo.VERSION)),
                                    URI.createURI(file.getAbsolutePath()),
                                    params.get(MeasureModelInfo.CHECKSUM),
                                    versionRange);

                            log.info("Registering model: " + measureModelInfo);

                            ServiceRegistration<MeasureModelInfo> modelServiceRegistration = bundleContext.registerService(MeasureModelInfo.class, measureModelInfo, measureModelInfo.toDictionary());
                            measureModels.put(key, measureModelInfo);
                            measureRegistrations.put(key, modelServiceRegistration);


                        } catch (IOException e) {
                            log.error("Could not load model: " + params.get(MeasureModelInfo.NAME) + " from bundle: " + trackedBundle.getBundleId());
                        }
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
        BundleContext bundleContext;

        public MeasureUnregisterCallback(BundleContext bundleContext) {
            this.bundleContext = bundleContext;
        }

        @Override
        public void accept(Bundle trackedBundle) {
            List<Map<String, String>> entries = BundleUtil.getHeaderEntries(trackedBundle, MEASURE_MODELS);
            for (Map<String, String> params : entries) {
                VersionRange versionRange = new VersionRange(params.get(MeasureModelInfo.META_VERSION).replaceAll("\"", ""));
                if (params.containsKey(MeasureModelInfo.META_VERSION)) {
                    if (versionRange.includes(bundleContext.getBundle().getVersion())) {
                        String key = trackedBundle.getBundleId() + "-" + params.get(MeasureModelInfo.NAME);
                        ServiceRegistration<MeasureModelInfo> modelServiceRegistration = measureRegistrations.get(key);

                        if (modelServiceRegistration != null) {
                            log.info("Unregistering moodel: " + measureModels.get(key));
                            modelServiceRegistration.unregister();
                            measureRegistrations.remove(key);
                            measureModels.remove(key);
                        }
                    }
                }
            }
        }

        @Override
        public Thread process(Bundle bundle) {
            return null;
        }
    }
}
