package hu.blackbelt.judo.meta.measure.runtime;

import hu.blackbelt.judo.meta.measure.runtime.MeasureModel;
import hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import static hu.blackbelt.judo.meta.measure.runtime.MeasureModel.LoadArguments.measureLoadArgumentsBuilder;

public class MeasureModelLoaderTest {

    static Logger log = LoggerFactory.getLogger(MeasureModelLoaderTest.class);
	
    @Test
    @DisplayName("Load Measure Model")
    void loadMeasureModel() throws IOException, MeasureModel.MeasureValidationException {
        ResourceSet measureResourceSet = MeasureModelResourceSupport.createMeasureResourceSet();

        MeasureModel measureModel = MeasureModel.loadMeasureModel(measureLoadArgumentsBuilder()
                .resourceSet(measureResourceSet)
                .uri(URI.createFileURI(new File("src/test/model/test.measure").getAbsolutePath()))
                .name("test")
                .build());

        for (Iterator<EObject> i = measureModel.getResourceSet().getResource(measureModel.getUri(), false).getAllContents(); i.hasNext(); ) {
            log.info(i.next().toString());
        }
    }
}