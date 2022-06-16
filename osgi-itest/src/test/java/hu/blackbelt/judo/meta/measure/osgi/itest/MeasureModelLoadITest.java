package hu.blackbelt.judo.meta.measure.osgi.itest;

import hu.blackbelt.epsilon.runtime.execution.api.Log;
import hu.blackbelt.epsilon.runtime.execution.impl.BufferedSlf4jLogger;
import hu.blackbelt.judo.meta.measure.runtime.MeasureModel;
import hu.blackbelt.judo.meta.measure.runtime.MeasureModel.MeasureValidationException;
import hu.blackbelt.judo.meta.measure.runtime.MeasureModel.SaveArguments;
import hu.blackbelt.osgi.utils.osgi.api.BundleTrackerManager;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import javax.inject.Inject;
import java.io.*;

import static hu.blackbelt.judo.meta.measure.osgi.itest.KarafFeatureProvider.karafConfig;
import static hu.blackbelt.judo.meta.measure.runtime.MeasureEpsilonValidator.calculateMeasureValidationScriptURI;
import static hu.blackbelt.judo.meta.measure.runtime.MeasureEpsilonValidator.validateMeasure;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.OptionUtils.combine;
import static org.ops4j.pax.tinybundles.core.TinyBundles.bundle;
import static org.ops4j.pax.tinybundles.core.TinyBundles.withBnd;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
@Slf4j
public class MeasureModelLoadITest {

    private static final String DEMO = "northwind-measure";

    @Inject
    protected BundleTrackerManager bundleTrackerManager;

    @Inject
    BundleContext bundleContext;

    @Inject
    MeasureModel measureModel;

    @Configuration
    public Option[] config() throws IOException, MeasureValidationException {

        return combine(karafConfig(this.getClass()),
                mavenBundle(maven()
                        .groupId("hu.blackbelt.judo.meta")
                        .artifactId("hu.blackbelt.judo.meta.measure.osgi")
                        .versionAsInProject()),
                getProvisonModelBundle());
    }

    public Option getProvisonModelBundle() throws IOException, MeasureValidationException {
        return provision(
                getMeasureModelBundle()
        );
    }

    private InputStream getMeasureModelBundle() throws IOException, MeasureValidationException {
    	MeasureModel measureModel = MeasureModel.buildMeasureModel()
    			.name(DEMO)
    			.uri(URI.createFileURI("test.model"))
    			.build();
    	
    	ByteArrayOutputStream os = new ByteArrayOutputStream();
    	
    	measureModel.saveMeasureModel(SaveArguments.measureSaveArgumentsBuilder().outputStream(os));
    	
        return bundle()
                .add( "model/" + DEMO + ".judo-meta-measure",
                		new ByteArrayInputStream(os.toByteArray()))
                .set( Constants.BUNDLE_MANIFESTVERSION, "2")
                .set( Constants.BUNDLE_SYMBOLICNAME, DEMO + "-measure" )
                //set( Constants.IMPORT_PACKAGE, "meta/psm;version=\"" + getConfiguration(META_PSM_IMPORT_RANGE) +"\"")
                .set( "Measure-Models", "file=model/" + DEMO + ".judo-meta-measure;version=1.0.0;name=" + DEMO + ";checksum=notset;meta-version-range=\"[1.0.0,2)\"")
                .build( withBnd());
    }

    @Test
    public void testModelValidation() throws Exception {
        try (Log bufferedLog = new BufferedSlf4jLogger(log)) {
            validateMeasure(bufferedLog, measureModel, calculateMeasureValidationScriptURI());
        }
    }
}
