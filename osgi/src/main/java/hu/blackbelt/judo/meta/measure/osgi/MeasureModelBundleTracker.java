package hu.blackbelt.judo.meta.measure.osgi;

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
@Designate(ocd = MeasureModelBundleTracker.TrackerConfig.class)
public class MeasureModelBundleTracker {

    public static final String MEASURE_MODELS = "Measure-Models";

    @ObjectClassDefinition(name="Measure Model Bundle Tracker")
    public @interface TrackerConfig {
        @AttributeDefinition(
                name = "Tags",
                description = "Which tags are on the loaded model when there is no one defined in bundle"
        )
        String tags() default "";
    }

    @Reference
    BundleTrackerManager bundleTrackerManager;

    Map<String, ServiceRegistration<MeasureModel>> measureModelRegistrations = new ConcurrentHashMap<>();

    Map<String, MeasureModel> measureModels = new HashMap<>();

    TrackerConfig config;

    @Activate
    public void activate(final ComponentContext componentContext, final TrackerConfig trackerConfig) {
        this.config = trackerConfig;
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
                                                .inputStream(trackedBundle.getEntry(params.get("file")).openStream())
                                                .name(params.get(MeasureModel.NAME))
                                                .version(trackedBundle.getVersion().toString())
                                                .checksum(Optional.ofNullable(params.get(MeasureModel.CHECKSUM)).orElse("notset"))
                                                .tags(Stream.of(ofNullable(params.get(MeasureModel.TAGS)).orElse(config.tags()).split(",")).collect(Collectors.toSet()))
                                                .acceptedMetaVersionRange(Optional.of(versionRange.toString()).orElse("[0,99)")));

                                log.info("Registering Measure model: " + measureModel);

                                ServiceRegistration<MeasureModel> modelServiceRegistration = bundleContext.registerService(MeasureModel.class, measureModel, measureModel.toDictionary());
                                measureModels.put(key, measureModel);
                                measureModelRegistrations.put(key, modelServiceRegistration);

                            } catch (IOException | MeasureModel.MeasureValidationException e) {
                                log.error("Could not load Psm model: " + params.get(MeasureModel.NAME) + " from bundle: " + trackedBundle.getBundleId(), e);
                            }
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
