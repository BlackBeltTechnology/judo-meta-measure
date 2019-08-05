package hu.blackbelt.judo.meta.measure.runtime;

import hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport.measureModelResourceSupportBuilder;

class MeasureExecutionContextTest {

    @Test
    @DisplayName("Create Measure model with builder pattern")
    void testMeasureReflectiveCreated() throws Exception {

        String createdSourceModelName = "urn:measure.judo-meta-measure";

        MeasureModelResourceSupport measureModelSupport = measureModelResourceSupportBuilder()
                .uri(URI.createURI(createdSourceModelName))
                .build();

        // Build model here
    }
}