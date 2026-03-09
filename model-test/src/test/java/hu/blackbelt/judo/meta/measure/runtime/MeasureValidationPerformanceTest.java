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

import hu.blackbelt.epsilon.runtime.execution.impl.BufferedSlf4jLogger;
import hu.blackbelt.judo.meta.measure.BaseMeasure;
import hu.blackbelt.judo.meta.measure.BaseMeasureTerm;
import hu.blackbelt.judo.meta.measure.DerivedMeasure;
import hu.blackbelt.judo.meta.measure.Unit;
import hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport;
import hu.blackbelt.judo.meta.measure.util.builder.BaseMeasureBuilder;
import hu.blackbelt.judo.meta.measure.util.builder.BaseMeasureTermBuilder;
import hu.blackbelt.judo.meta.measure.util.builder.DerivedMeasureBuilder;
import hu.blackbelt.judo.meta.measure.util.builder.UnitBuilder;
import hu.blackbelt.judo.meta.measure.validation.MeasureValidator;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static hu.blackbelt.judo.meta.measure.support.MeasureModelResourceSupport.measureModelResourceSupportBuilder;

/**
 * Performance test comparing EVL and Java validation execution times.
 *
 * <p>This test generates a model with characteristics similar to real-world usage
 * (based on rackinspect model patterns) and measures validation performance.</p>
 *
 * <p>The test is disabled by default to avoid slowing down CI builds.
 * Enable it by removing the @Disabled annotation or running explicitly.</p>
 */
@Slf4j
public class MeasureValidationPerformanceTest {

    private static final int NUM_BASE_MEASURES = 100;
    private static final int NUM_DERIVED_MEASURES = 100;
    private static final int MIN_UNITS_PER_MEASURE = 1;
    private static final int MAX_UNITS_PER_MEASURE = 5;

    private MeasureModelResourceSupport measureModelSupport;
    private MeasureModel measureModel;
    private final Random random = new Random(42); // Fixed seed for reproducibility

    @BeforeEach
    void setUp() {
        measureModelSupport = measureModelResourceSupportBuilder()
                .uri(URI.createFileURI("urn:Performance.model"))
                .build();

        measureModel = MeasureModel.buildMeasureModel()
                .measureModelResourceSupport(measureModelSupport)
                .name("performance")
                .build();
    }

    @Test
    void testPerformanceComparison() throws Exception {
        log.info("=".repeat(70));
        log.info("Performance Test: Generating model with {} base + {} derived measures, {}-{} units each",
                NUM_BASE_MEASURES, NUM_DERIVED_MEASURES, MIN_UNITS_PER_MEASURE, MAX_UNITS_PER_MEASURE);
        log.info("=".repeat(70));

        // Generate a large model
        long startGen = System.currentTimeMillis();
        generateLargeModel();
        long genTime = System.currentTimeMillis() - startGen;
        log.info("Model generation time: {} ms", genTime);

        int actualElements = countElements();
        log.info("Actual elements generated: {}", actualElements);
        log.info("-".repeat(70));

        // Run EVL validation
        log.info("Running EVL validation...");
        long evlStart = System.currentTimeMillis();
        try (BufferedSlf4jLogger bufferedLog = new BufferedSlf4jLogger(log)) {
            MeasureEpsilonValidator.validateMeasure(
                    bufferedLog,
                    measureModel,
                    MeasureEpsilonValidator.calculateMeasureValidationScriptURI(),
                    Collections.emptyList(),
                    Collections.emptyList()
            );
        }
        long evlTime = System.currentTimeMillis() - evlStart;
        log.info("EVL validation time: {} ms", evlTime);

        // Run Java validation (sequential)
        log.info("Running Java validation (sequential)...");
        long javaSeqStart = System.currentTimeMillis();
        MeasureValidator.validateMeasure(
                log,
                measureModel.getResourceSet(),
                Collections.emptyList(),
                Collections.emptyList(),
                false // sequential
        );
        long javaSeqTime = System.currentTimeMillis() - javaSeqStart;
        log.info("Java validation (sequential) time: {} ms", javaSeqTime);

        // Run Java validation (parallel)
        log.info("Running Java validation (parallel)...");
        long javaParStart = System.currentTimeMillis();
        MeasureValidator.validateMeasure(
                log,
                measureModel.getResourceSet(),
                Collections.emptyList(),
                Collections.emptyList(),
                true // parallel
        );
        long javaParTime = System.currentTimeMillis() - javaParStart;
        log.info("Java validation (parallel) time: {} ms", javaParTime);

        // Print summary
        log.info("=".repeat(70));
        log.info("PERFORMANCE SUMMARY");
        log.info("=".repeat(70));
        log.info("Model size: {} elements", actualElements);
        log.info("-".repeat(70));
        log.info("EVL validation:            {} ms", evlTime);
        log.info("Java validation (seq):     {} ms", javaSeqTime);
        log.info("Java validation (par):     {} ms", javaParTime);
        log.info("-".repeat(70));
        if (evlTime > 0) {
            log.info("Java (seq) speedup vs EVL: {:.2f}x", (double) evlTime / javaSeqTime);
            log.info("Java (par) speedup vs EVL: {:.2f}x", (double) evlTime / javaParTime);
        }
        log.info("=".repeat(70));
    }

    /**
     * Generate a large model with characteristics similar to real-world models.
     * Model structure is based on rackinspect model patterns:
     * - Multiple base measures (physical quantities)
     * - Derived measures composed of base measures
     * - Multiple units per measure with varying conversion rates
     */
    private void generateLargeModel() {
        List<BaseMeasure> baseMeasures = new ArrayList<>();

        // Create base measures
        for (int i = 0; i < NUM_BASE_MEASURES; i++) {
            BaseMeasure measure = BaseMeasureBuilder.create()
                    .withNamespace("measures")
                    .withName("BaseMeasure" + i)
                    .withSymbol("BM" + i)
                    .build();

            // Add 1-5 units to each measure
            int numUnits = MIN_UNITS_PER_MEASURE + random.nextInt(MAX_UNITS_PER_MEASURE - MIN_UNITS_PER_MEASURE + 1);
            for (int j = 0; j < numUnits; j++) {
                BigDecimal dividend = j == 0 ? BigDecimal.ONE : new BigDecimal(String.valueOf(1 + random.nextInt(1000)));
                BigDecimal divisor = j == 0 ? BigDecimal.ONE : new BigDecimal(String.valueOf(1 + random.nextInt(100)));

                Unit unit = UnitBuilder.create()
                        .withName("Unit" + i + "_" + j)
                        .withSymbol("u" + i + "_" + j)
                        .withRateDividend(dividend)
                        .withRateDivisor(divisor)
                        .build();

                measure.getUnits().add(unit);
            }

            measureModel.getResource().getContents().add(measure);
            baseMeasures.add(measure);
        }

        // Create derived measures
        for (int i = 0; i < NUM_DERIVED_MEASURES; i++) {
            DerivedMeasure derived = DerivedMeasureBuilder.create()
                    .withNamespace("measures")
                    .withName("DerivedMeasure" + i)
                    .withSymbol("DM" + i)
                    .build();

            // Add 1-3 terms referencing random base measures
            int numTerms = 1 + random.nextInt(3);
            for (int t = 0; t < numTerms; t++) {
                BaseMeasure baseMeasure = baseMeasures.get(random.nextInt(baseMeasures.size()));
                int exponent = 1 + random.nextInt(3);

                BaseMeasureTerm term = BaseMeasureTermBuilder.create()
                        .withBaseMeasure(baseMeasure)
                        .withExponent(exponent)
                        .build();

                derived.getTerms().add(term);
            }

            // Add 1-5 units to derived measure
            int numDerivedUnits = MIN_UNITS_PER_MEASURE + random.nextInt(MAX_UNITS_PER_MEASURE - MIN_UNITS_PER_MEASURE + 1);
            for (int j = 0; j < numDerivedUnits; j++) {
                BigDecimal dividend = j == 0 ? BigDecimal.ONE : new BigDecimal(String.valueOf(1 + random.nextInt(1000)));
                BigDecimal divisor = j == 0 ? BigDecimal.ONE : new BigDecimal(String.valueOf(1 + random.nextInt(100)));

                Unit unit = UnitBuilder.create()
                        .withName("DUnit" + i + "_" + j)
                        .withSymbol("du" + i + "_" + j)
                        .withRateDividend(dividend)
                        .withRateDivisor(divisor)
                        .build();

                derived.getUnits().add(unit);
            }

            measureModel.getResource().getContents().add(derived);
        }
    }

    private int countElements() {
        final int[] count = {0};
        measureModel.getResourceSet().getResources().forEach(resource -> {
            resource.getAllContents().forEachRemaining(e -> count[0]++);
        });
        return count[0];
    }
}
