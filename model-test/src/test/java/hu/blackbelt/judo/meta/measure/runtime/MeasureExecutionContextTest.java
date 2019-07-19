package hu.blackbelt.judo.meta.measure.runtime;

import com.google.common.collect.ImmutableList;
import hu.blackbelt.judo.meta.measure.runtime.MeasureModel;
import hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport.measureModelResourceSupportBuilder;
import static hu.blackbelt.judo.meta.measure.util.builder.MeasureBuilders.*;

class ExecutionContextTest {

    @Test
    @DisplayName("Create Measure model with builder pattern")
    void testMeasureReflectiveCreated() throws Exception {


        String createdSourceModelName = "urn:measure.judo-meta-measure";

        MeasureModelResourceSupport measureModelSupport = measureModelResourceSupportBuilder().build();
        Resource measureResource = measureModelSupport.getResourceSet().createResource(
                URI.createFileURI(createdSourceModelName));

        // Build model here
    }
}