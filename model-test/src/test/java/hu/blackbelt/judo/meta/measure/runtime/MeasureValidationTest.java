package hu.blackbelt.judo.meta.measure.runtime;

import hu.blackbelt.epsilon.runtime.execution.ExecutionContext;
import hu.blackbelt.epsilon.runtime.execution.api.Log;
import hu.blackbelt.epsilon.runtime.execution.exceptions.EvlScriptExecutionException;
import hu.blackbelt.epsilon.runtime.execution.impl.Slf4jLog;
import hu.blackbelt.judo.meta.measure.runtime.MeasureUtils;
import hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport;

import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Collection;

import static hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport.measureModelResourceSupportBuilder;


public class MeasureValidationTest {

    MeasureModelResourceSupport measureModelSupport;
    
    private static final Logger logger = LoggerFactory.getLogger(MeasureValidationTest.class);
    private final String createdSourceModelName = "urn:Measure.model";

    private Log log = new Slf4jLog();
    private MeasureModel measureModel;
    @BeforeEach
    void setUp() {

        measureModelSupport = measureModelResourceSupportBuilder()
                .uri(URI.createFileURI(createdSourceModelName))
                .build();
        
        measureModel = MeasureModel.buildMeasureModel()
        		.measureModelResourceSupport(measureModelSupport)
                .uri(URI.createURI(createdSourceModelName))
                .name("test")
                .build();
    }


    private void runEpsilon (Collection<String> expectedErrors, Collection<String> expectedWarnings) throws Exception {
        try {
            MeasureEpsilonValidator.validateMeasure(log,
            		measureModel,
                    MeasureEpsilonValidator.calculateMeasureValidationScriptURI(),
                    expectedErrors,
                    expectedWarnings);
        } catch (EvlScriptExecutionException ex) {
            logger.error("EVL failed", ex);
            logger.error("\u001B[31m - expected errors: {}\u001B[0m", expectedErrors);
            logger.error("\u001B[31m - unexpected errors: {}\u001B[0m", ex.getUnexpectedErrors());
            logger.error("\u001B[31m - errors not found: {}\u001B[0m", ex.getErrorsNotFound());
            logger.error("\u001B[33m - expected warnings: {}\u001B[0m", expectedWarnings);
            logger.error("\u001B[33m - unexpected warnings: {}\u001B[0m", ex.getUnexpectedWarnings());
            logger.error("\u001B[33m - warnings not found: {}\u001B[0m", ex.getWarningsNotFound());
            throw ex;
        }
    }
}
