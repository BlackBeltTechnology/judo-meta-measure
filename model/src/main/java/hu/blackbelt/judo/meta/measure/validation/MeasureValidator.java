package hu.blackbelt.judo.meta.measure.validation;

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

import hu.blackbelt.judo.meta.measure.runtime.MeasureUtils;
import hu.blackbelt.judo.meta.measure.validation.rules.MeasureValidations;
import hu.blackbelt.judo.meta.measure.validation.rules.UnitValidations;
import hu.blackbelt.judo.zeta.common.ExtensionMethodRegistry;
import hu.blackbelt.judo.zeta.common.ModelProvider;
import hu.blackbelt.judo.zeta.validation.core.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.slf4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Entry point for Java-based Measure model validation using Zeta framework.
 *
 * <p>This validator provides a native Java alternative to EVL (Epsilon Validation Language)
 * validation with better IDE integration, debugging support, and performance.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * MeasureValidator.validateMeasure(log, measureModel);
 * }
 * </pre>
 */
public class MeasureValidator {

    /**
     * Validate Measure model using Java validation rules.
     *
     * @param log the logger
     * @param resourceSet the resource set containing the model
     * @throws MeasureValidationException if validation fails
     */
    public static void validateMeasure(Logger log, ResourceSet resourceSet)
            throws MeasureValidationException {
        validateMeasure(log, resourceSet, null, null, false);
    }

    /**
     * Validate Measure model with expected errors and warnings (for testing).
     *
     * @param log the logger
     * @param resourceSet the resource set containing the model
     * @param expectedErrors expected error constraint names
     * @param expectedWarnings expected warning constraint names
     * @throws MeasureValidationException if validation fails
     */
    public static void validateMeasure(
            Logger log,
            ResourceSet resourceSet,
            Collection<String> expectedErrors,
            Collection<String> expectedWarnings
    ) throws MeasureValidationException {
        validateMeasure(log, resourceSet, expectedErrors, expectedWarnings, false);
    }

    /**
     * Validate Measure model with all options.
     *
     * @param log the logger
     * @param resourceSet the resource set containing the model
     * @param expectedErrors expected error constraint names
     * @param expectedWarnings expected warning constraint names
     * @param parallel use parallel execution
     * @throws MeasureValidationException if validation fails
     */
    public static void validateMeasure(
            Logger log,
            ResourceSet resourceSet,
            Collection<String> expectedErrors,
            Collection<String> expectedWarnings,
            boolean parallel
    ) throws MeasureValidationException {
        log.info("Starting Java-based Measure validation...");

        // Create validation infrastructure
        ValidationRegistry registry = new ValidationRegistry();

        // Create model provider that wraps MeasureUtils
        MeasureUtils measureUtils = new MeasureUtils(resourceSet);
        ModelProvider modelProvider = new ModelProvider() {
            @Override
            public <T extends EObject> Collection<T> getAllContents(ResourceSet rs, Class<T> type) {
                return measureUtils.all(rs, type).collect(Collectors.toList());
            }
        };

        // Create extension method registry (empty for now, can be extended later)
        ExtensionMethodRegistry extensionRegistry = new ExtensionMethodRegistry();

        // Create validation context
        ValidationContext context = new ValidationContext(
                modelProvider,
                resourceSet,
                extensionRegistry
        );

        // Register validation rule classes
        try {
            registry.register(MeasureValidations.class);
            registry.register(UnitValidations.class);
            log.debug("Registered measure validation rules");
        } catch (Exception e) {
            log.error("Failed to register measure validations", e);
            throw new RuntimeException("Failed to register measure validations", e);
        }

        // Set the registry on the context for satisfies() evaluation
        context.setValidationRegistry(registry);

        // Create executor
        ValidationExecutor executor = new ValidationExecutor(
                registry,
                context,
                parallel
        );

        try {
            // Collect all elements to validate
            List<EObject> allElements = new ArrayList<>();
            resourceSet.getResources().forEach(resource -> {
                Iterator<EObject> it = resource.getAllContents();
                while (it.hasNext()) {
                    allElements.add(it.next());
                }
            });

            log.info("Validating {} elements...", allElements.size());

            // Execute validation
            List<ValidationResult> failures = executor.validate(allElements);

            // Separate errors and warnings
            List<ValidationResult> errors = failures.stream()
                    .filter(r -> r.getSeverity() == Severity.ERROR)
                    .collect(Collectors.toList());

            List<ValidationResult> warnings = failures.stream()
                    .filter(r -> r.getSeverity() == Severity.WARNING)
                    .collect(Collectors.toList());

            log.info("Validation complete: {} errors, {} warnings", errors.size(), warnings.size());

            // Check against expected results and determine if there are unexpected issues
            boolean hasUnexpected = false;
            if (expectedErrors != null || expectedWarnings != null) {
                hasUnexpected = checkExpectedResults(
                        log,
                        errors,
                        warnings,
                        expectedErrors,
                        expectedWarnings
                );
            }

            // Throw exception if there are unexpected failures or errors when none expected
            if (!errors.isEmpty()) {
                logFailures(log, errors, "ERRORS");
                if (expectedErrors == null || hasUnexpected) {
                    throw new MeasureValidationException("Measure model validation failed with " + errors.size() + " errors");
                }
            }

            if (!warnings.isEmpty()) {
                logFailures(log, warnings, "WARNINGS");
            }
        } finally {
            executor.shutdown();
        }
    }

    private static boolean checkExpectedResults(
            Logger log,
            List<ValidationResult> errors,
            List<ValidationResult> warnings,
            Collection<String> expectedErrors,
            Collection<String> expectedWarnings
    ) {
        // Count actual errors and warnings by constraint name
        Map<String, Long> actualErrorCounts = errors.stream()
                .map(ValidationResult::getConstraintName)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        Map<String, Long> actualWarningCounts = warnings.stream()
                .map(ValidationResult::getConstraintName)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        // Count expected errors and warnings
        Map<String, Long> expectedErrorCounts = expectedErrors != null
                ? expectedErrors.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                : Collections.emptyMap();
        Map<String, Long> expectedWarningCounts = expectedWarnings != null
                ? expectedWarnings.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                : Collections.emptyMap();

        boolean errorCountsMatch = actualErrorCounts.equals(expectedErrorCounts);
        boolean warningCountsMatch = expectedWarnings == null || actualWarningCounts.equals(expectedWarningCounts);

        boolean hasUnexpected = !errorCountsMatch || !warningCountsMatch;

        if (hasUnexpected) {
            log.error("Validation result mismatch:");
            log.error("  Actual error counts: {}", actualErrorCounts);
            log.error("  Expected error counts: {}", expectedErrorCounts);
            if (expectedWarnings != null) {
                log.error("  Actual warning counts: {}", actualWarningCounts);
                log.error("  Expected warning counts: {}", expectedWarningCounts);
            }
        } else {
            log.info("Validation results match expected:");
            log.info("  Error counts: {}", actualErrorCounts);
            log.info("  Warning counts: {}", actualWarningCounts);
        }

        return hasUnexpected;
    }

    private static void logFailures(
            Logger log,
            List<ValidationResult> failures,
            String label
    ) {
        log.info("{}: {}", label, failures.size());
        for (ValidationResult failure : failures) {
            log.info("  [{}] {}: {}",
                    failure.getSeverity(),
                    failure.getConstraintName(),
                    failure.getMessage()
            );
        }
    }
}
