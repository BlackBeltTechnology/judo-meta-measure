package hu.blackbelt.judo.meta.measure.runtime;

import hu.blackbelt.epsilon.runtime.execution.api.Log;
import hu.blackbelt.epsilon.runtime.execution.exceptions.EvlScriptExecutionException;
import hu.blackbelt.epsilon.runtime.execution.impl.BufferedSlf4jLogger;
import hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeEach;

import java.util.Collection;

import static hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport.measureModelResourceSupportBuilder;

@Slf4j
public class MeasureValidationTest {

    MeasureModelResourceSupport measureModelSupport;

    private final String createdSourceModelName = "urn:Measure.model";

    private MeasureModel measureModel;
    @BeforeEach
    void setUp() {

        measureModelSupport = measureModelResourceSupportBuilder()
                .uri(URI.createFileURI(createdSourceModelName))
                .build();

        measureModel = MeasureModel.buildMeasureModel()
        		.measureModelResourceSupport(measureModelSupport)
                .name("test")
                .build();
    }


    private void runEpsilon (Collection<String> expectedErrors, Collection<String> expectedWarnings) throws Exception {
        try (Log bufferedLog = new BufferedSlf4jLogger(log)) {
            MeasureEpsilonValidator.validateMeasure(bufferedLog,
            		measureModel,
                    MeasureEpsilonValidator.calculateMeasureValidationScriptURI(),
                    expectedErrors,
                    expectedWarnings);
        } catch (EvlScriptExecutionException ex) {
            log.error("EVL failed", ex);
            log.error("\u001B[31m - expected errors: {}\u001B[0m", expectedErrors);
            log.error("\u001B[31m - unexpected errors: {}\u001B[0m", ex.getUnexpectedErrors());
            log.error("\u001B[31m - errors not found: {}\u001B[0m", ex.getErrorsNotFound());
            log.error("\u001B[33m - expected warnings: {}\u001B[0m", expectedWarnings);
            log.error("\u001B[33m - unexpected warnings: {}\u001B[0m", ex.getUnexpectedWarnings());
            log.error("\u001B[33m - warnings not found: {}\u001B[0m", ex.getWarningsNotFound());
            throw ex;
        }
    }
}
