package hu.blackbelt.judo.meta.measure.runtime;

/*-
 * #%L
 * Judo :: Measure :: Model
 * %%
 * Copyright (C) 2018 - 2024 BlackBelt Technology
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

import hu.blackbelt.epsilon.runtime.execution.exceptions.EvlScriptExecutionException;
import hu.blackbelt.epsilon.runtime.execution.impl.BufferedSlf4jLogger;
import hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport;
import hu.blackbelt.judo.meta.measure.validation.MeasureValidationException;
import hu.blackbelt.judo.meta.measure.validation.MeasureValidator;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;
import java.util.stream.Collectors;

import static hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport.measureModelResourceSupportBuilder;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Abstract base class for Measure validation tests.
 * Provides infrastructure for dual validation testing (EVL + Java).
 *
 * <p>Subclasses should use {@code @ParameterizedTest} with {@code @EnumSource(ValidatorType.class)}
 * to run the same tests against both EVL and Java validators.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * @ParameterizedTest(name = "testMyConstraint [{0}]")
 * @EnumSource(ValidatorType.class)
 * void testMyConstraint(ValidatorType type) throws Exception {
 *     this.validatorType = type;
 *     initModel();
 *     // ... build model ...
 *     runValidation(List.of("ExpectedError"), null);
 * }
 * }
 * </pre>
 */
@Slf4j
public abstract class AbstractMeasureValidationTest {

    protected MeasureModelResourceSupport measureModelSupport;
    protected MeasureModel measureModel;
    protected ValidatorType validatorType = ValidatorType.EVL;

    private static final String DEFAULT_MODEL_NAME = "urn:Measure.model";

    /**
     * Sets up the model infrastructure before each test.
     * Called automatically by JUnit, but initModel() must be called in each test
     * after setting the validatorType.
     */
    @BeforeEach
    void setUp() {
        // Reset to default state - actual initialization happens in initModel()
        measureModelSupport = null;
        measureModel = null;
    }

    /**
     * Initializes the model. Must be called at the beginning of each test
     * after setting the validatorType.
     */
    protected void initModel() {
        initModel(DEFAULT_MODEL_NAME);
    }

    /**
     * Initializes the model with a custom model name.
     *
     * @param modelName the URI name for the model
     */
    protected void initModel(String modelName) {
        measureModelSupport = measureModelResourceSupportBuilder()
                .uri(URI.createFileURI(modelName))
                .build();

        measureModel = MeasureModel.buildMeasureModel()
                .measureModelResourceSupport(measureModelSupport)
                .name("test")
                .build();
    }

    /**
     * Run validation with no expected errors or warnings.
     * Expects validation to pass.
     */
    protected void runValidation() throws Exception {
        runValidation(Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Run validation with expected errors only.
     *
     * @param expectedErrors list of expected error constraint names
     */
    protected void runValidation(Collection<String> expectedErrors) throws Exception {
        runValidation(expectedErrors, null);
    }

    /**
     * Run validation with expected errors and warnings.
     *
     * @param expectedErrors list of expected error constraint names (null = don't check)
     * @param expectedWarnings list of expected warning constraint names (null = don't check)
     */
    protected void runValidation(Collection<String> expectedErrors, Collection<String> expectedWarnings)
            throws Exception {
        switch (validatorType) {
            case EVL:
                runEvlValidation(expectedErrors, expectedWarnings);
                break;
            case JAVA:
                runJavaValidation(expectedErrors, expectedWarnings);
                break;
            default:
                throw new IllegalStateException("Unknown validator type: " + validatorType);
        }
    }

    /**
     * Run EVL (Epsilon) validation.
     * Uses custom comparison logic to handle duplicate constraint names properly.
     */
    private void runEvlValidation(Collection<String> expectedErrors, Collection<String> expectedWarnings)
            throws Exception {
        // Count expected errors/warnings by constraint name
        Map<String, Long> expectedErrorCounts = expectedErrors != null
                ? expectedErrors.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                : Collections.emptyMap();
        Map<String, Long> expectedWarningCounts = expectedWarnings != null
                ? expectedWarnings.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                : Collections.emptyMap();

        try (BufferedSlf4jLogger bufferedLog = new BufferedSlf4jLogger(log)) {
            // Run validation with empty expected lists - we'll do our own comparison
            MeasureEpsilonValidator.validateMeasure(
                    bufferedLog,
                    measureModel,
                    MeasureEpsilonValidator.calculateMeasureValidationScriptURI(),
                    Collections.emptyList(),
                    Collections.emptyList()
            );
            // If we get here, no errors or warnings were found
            if (!expectedErrorCounts.isEmpty() || !expectedWarningCounts.isEmpty()) {
                fail("Expected validation failures but none occurred. Expected errors: " + expectedErrors + ", warnings: " + expectedWarnings);
            }
        } catch (EvlScriptExecutionException ex) {
            // Extract actual errors and warnings from exception
            // Epsilon returns format: "ConstraintName|Message", we need just the constraint name
            Map<String, Long> actualErrorCounts = ex.getUnexpectedErrors().stream()
                    .map(e -> e.contains("|") ? e.substring(0, e.indexOf("|")) : e)
                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
            Map<String, Long> actualWarningCounts = ex.getUnexpectedWarnings().stream()
                    .map(w -> w.contains("|") ? w.substring(0, w.indexOf("|")) : w)
                    .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

            log.info("EVL validation results:");
            log.info("  Actual errors: {}", actualErrorCounts);
            log.info("  Actual warnings: {}", actualWarningCounts);
            log.info("  Expected errors: {}", expectedErrorCounts);
            log.info("  Expected warnings: {}", expectedWarningCounts);

            // Compare error counts
            if (expectedErrors != null) {
                assertEquals(expectedErrorCounts, actualErrorCounts,
                        "Error counts do not match. Expected: " + expectedErrorCounts + ", Actual: " + actualErrorCounts);
            }

            // Compare warning counts
            if (expectedWarnings != null) {
                assertEquals(expectedWarningCounts, actualWarningCounts,
                        "Warning counts do not match. Expected: " + expectedWarningCounts + ", Actual: " + actualWarningCounts);
            }

            // If we expected no errors/warnings but got some, fail
            if ((expectedErrors == null || expectedErrors.isEmpty()) && !actualErrorCounts.isEmpty()) {
                fail("Unexpected errors found: " + actualErrorCounts);
            }
            if ((expectedWarnings == null || expectedWarnings.isEmpty()) && !actualWarningCounts.isEmpty()) {
                fail("Unexpected warnings found: " + actualWarningCounts);
            }
        }
    }

    /**
     * Run Java (Zeta) validation.
     * The MeasureValidator handles expected error/warning comparison internally.
     * It only throws if there's a mismatch or unexpected errors.
     */
    private void runJavaValidation(Collection<String> expectedErrors, Collection<String> expectedWarnings)
            throws Exception {
        try {
            // Run validation - it will throw if:
            // 1. There are unexpected errors (errors found but none expected)
            // 2. Expected counts don't match actual counts
            MeasureValidator.validateMeasure(
                    log,
                    measureModel.getResourceSet(),
                    expectedErrors,
                    expectedWarnings
            );
            // If we get here, validation passed or expected errors matched
            log.info("Java validation completed successfully");
        } catch (MeasureValidationException ex) {
            // Validation found mismatched or unexpected errors
            log.error("Java validation failed: {}", ex.getMessage());
            throw ex;
        }
    }
}
