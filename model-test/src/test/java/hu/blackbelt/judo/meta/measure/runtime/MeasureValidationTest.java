package hu.blackbelt.judo.meta.measure.runtime;

/*-
 * #%L
 * Judo :: Measure :: Model
 * %%
 * Copyright (C) 2018 - 2022 BlackBelt Technology
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

import hu.blackbelt.judo.meta.measure.BaseMeasure;
import hu.blackbelt.judo.meta.measure.BaseMeasureTerm;
import hu.blackbelt.judo.meta.measure.DerivedMeasure;
import hu.blackbelt.judo.meta.measure.Unit;
import hu.blackbelt.judo.meta.measure.util.builder.BaseMeasureBuilder;
import hu.blackbelt.judo.meta.measure.util.builder.BaseMeasureTermBuilder;
import hu.blackbelt.judo.meta.measure.util.builder.DerivedMeasureBuilder;
import hu.blackbelt.judo.meta.measure.util.builder.UnitBuilder;
import hu.blackbelt.judo.meta.measure.validation.MeasureConstraints;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Parameterized validation tests for Measure model.
 * Each test runs against both EVL and Java validators to ensure parity.
 */
@Slf4j
public class MeasureValidationTest extends AbstractMeasureValidationTest {

    // ==========================================================================
    // Valid Model Tests
    // ==========================================================================

    @ParameterizedTest(name = "testValidMeasureWithBaseUnit [{0}]")
    @EnumSource(ValidatorType.class)
    void testValidMeasureWithBaseUnit(ValidatorType type) throws Exception {
        this.validatorType = type;
        initModel();

        // Create a valid measure with a base unit
        BaseMeasure length = BaseMeasureBuilder.create()
                .withNamespace("measures")
                .withName("Length")
                .withSymbol("L")
                .build();

        Unit meter = UnitBuilder.create()
                .withName("meter")
                .withSymbol("m")
                .withRateDividend(BigDecimal.ONE)
                .withRateDivisor(BigDecimal.ONE)
                .build();

        length.getUnits().add(meter);
        measureModel.getResource().getContents().add(length);

        // Should pass with no errors or warnings
        runValidation(Collections.emptyList(), Collections.emptyList());
    }

    @ParameterizedTest(name = "testValidMeasureWithMultipleUnits [{0}]")
    @EnumSource(ValidatorType.class)
    void testValidMeasureWithMultipleUnits(ValidatorType type) throws Exception {
        this.validatorType = type;
        initModel();

        // Create a valid measure with multiple units
        BaseMeasure length = BaseMeasureBuilder.create()
                .withNamespace("measures")
                .withName("Length")
                .withSymbol("L")
                .build();

        Unit meter = UnitBuilder.create()
                .withName("meter")
                .withSymbol("m")
                .withRateDividend(BigDecimal.ONE)
                .withRateDivisor(BigDecimal.ONE)
                .build();

        Unit kilometer = UnitBuilder.create()
                .withName("kilometer")
                .withSymbol("km")
                .withRateDividend(new BigDecimal("1000"))
                .withRateDivisor(BigDecimal.ONE)
                .build();

        length.getUnits().add(meter);
        length.getUnits().add(kilometer);
        measureModel.getResource().getContents().add(length);

        // Should pass with no errors or warnings
        runValidation(Collections.emptyList(), Collections.emptyList());
    }

    // ==========================================================================
    // Constraint Tests (ERROR severity)
    // ==========================================================================

    @ParameterizedTest(name = "testMeasureSymbolIsNotUnique [{0}]")
    @EnumSource(ValidatorType.class)
    void testMeasureSymbolIsNotUnique(ValidatorType type) throws Exception {
        this.validatorType = type;
        initModel();

        // Create two measures with the same symbol
        BaseMeasure length1 = BaseMeasureBuilder.create()
                .withNamespace("measures")
                .withName("Length1")
                .withSymbol("L")
                .build();

        Unit meter1 = UnitBuilder.create()
                .withName("meter")
                .withSymbol("m")
                .withRateDividend(BigDecimal.ONE)
                .withRateDivisor(BigDecimal.ONE)
                .build();
        length1.getUnits().add(meter1);

        BaseMeasure length2 = BaseMeasureBuilder.create()
                .withNamespace("measures")
                .withName("Length2")
                .withSymbol("L") // Same symbol - should trigger error
                .build();

        Unit meter2 = UnitBuilder.create()
                .withName("meter2")
                .withSymbol("m2")
                .withRateDividend(BigDecimal.ONE)
                .withRateDivisor(BigDecimal.ONE)
                .build();
        length2.getUnits().add(meter2);

        measureModel.getResource().getContents().add(length1);
        measureModel.getResource().getContents().add(length2);

        // Expect MeasureSymbolIsUnique error for both measures
        // Note: units have different symbols (m vs m2), so no UnitSymbolIsUnique warnings
        runValidation(
                List.of(MeasureConstraints.MEASURE_SYMBOL_IS_UNIQUE,
                        MeasureConstraints.MEASURE_SYMBOL_IS_UNIQUE),
                Collections.emptyList()
        );
    }

    @ParameterizedTest(name = "testUnitSymbolIsNotUniqueInMeasure [{0}]")
    @EnumSource(ValidatorType.class)
    void testUnitSymbolIsNotUniqueInMeasure(ValidatorType type) throws Exception {
        this.validatorType = type;
        initModel();

        // Create a measure with two units having the same symbol
        BaseMeasure length = BaseMeasureBuilder.create()
                .withNamespace("measures")
                .withName("Length")
                .withSymbol("L")
                .build();

        Unit unit1 = UnitBuilder.create()
                .withName("unit1")
                .withSymbol("u")
                .withRateDividend(BigDecimal.ONE)
                .withRateDivisor(BigDecimal.ONE)
                .build();

        Unit unit2 = UnitBuilder.create()
                .withName("unit2")
                .withSymbol("u") // Same symbol in same measure - should trigger error
                .withRateDividend(new BigDecimal("1000"))
                .withRateDivisor(BigDecimal.ONE)
                .build();

        length.getUnits().add(unit1);
        length.getUnits().add(unit2);
        measureModel.getResource().getContents().add(length);

        // Expect UnitSymbolIsUniqueInMeasure error for both units
        // EVL deduplicates warnings by message (reports 1), Java reports each unit separately (2)
        List<String> expectedWarnings = validatorType == ValidatorType.EVL
                ? List.of(MeasureConstraints.UNIT_SYMBOL_IS_UNIQUE)
                : List.of(MeasureConstraints.UNIT_SYMBOL_IS_UNIQUE, MeasureConstraints.UNIT_SYMBOL_IS_UNIQUE);
        runValidation(
                List.of(MeasureConstraints.UNIT_SYMBOL_IS_UNIQUE_IN_MEASURE,
                        MeasureConstraints.UNIT_SYMBOL_IS_UNIQUE_IN_MEASURE),
                expectedWarnings
        );
    }

    // ==========================================================================
    // Critique Tests (WARNING severity)
    // ==========================================================================

    @ParameterizedTest(name = "testBaseUnitNotDefined [{0}]")
    @EnumSource(ValidatorType.class)
    void testBaseUnitNotDefined(ValidatorType type) throws Exception {
        this.validatorType = type;
        initModel();

        // Create a measure without a base unit (no unit with rateDividend == rateDivisor)
        BaseMeasure length = BaseMeasureBuilder.create()
                .withNamespace("measures")
                .withName("Length")
                .withSymbol("L")
                .build();

        Unit kilometer = UnitBuilder.create()
                .withName("kilometer")
                .withSymbol("km")
                .withRateDividend(new BigDecimal("1000"))
                .withRateDivisor(BigDecimal.ONE) // Not a base unit
                .build();

        length.getUnits().add(kilometer);
        measureModel.getResource().getContents().add(length);

        // Expect BaseUnitShouldBeDefined warning
        runValidation(
                Collections.emptyList(),
                List.of(MeasureConstraints.BASE_UNIT_SHOULD_BE_DEFINED)
        );
    }

    @ParameterizedTest(name = "testMeasureNameIsNotUnique [{0}]")
    @EnumSource(ValidatorType.class)
    void testMeasureNameIsNotUnique(ValidatorType type) throws Exception {
        this.validatorType = type;
        initModel();

        // Create two measures with the same name (case-insensitive)
        BaseMeasure length1 = BaseMeasureBuilder.create()
                .withNamespace("measures")
                .withName("Length")
                .withSymbol("L1")
                .build();

        Unit meter1 = UnitBuilder.create()
                .withName("meter")
                .withSymbol("m")
                .withRateDividend(BigDecimal.ONE)
                .withRateDivisor(BigDecimal.ONE)
                .build();
        length1.getUnits().add(meter1);

        BaseMeasure length2 = BaseMeasureBuilder.create()
                .withNamespace("measures")
                .withName("length") // Same name (different case) - should trigger warning
                .withSymbol("L2")
                .build();

        Unit meter2 = UnitBuilder.create()
                .withName("meter2")
                .withSymbol("m2")
                .withRateDividend(BigDecimal.ONE)
                .withRateDivisor(BigDecimal.ONE)
                .build();
        length2.getUnits().add(meter2);

        measureModel.getResource().getContents().add(length1);
        measureModel.getResource().getContents().add(length2);

        // Expect MeasureNameIsUnique warning for both measures
        runValidation(
                Collections.emptyList(),
                List.of(MeasureConstraints.MEASURE_NAME_IS_UNIQUE,
                        MeasureConstraints.MEASURE_NAME_IS_UNIQUE)
        );
    }

    @ParameterizedTest(name = "testUnitNameIsNotUnique [{0}]")
    @EnumSource(ValidatorType.class)
    void testUnitNameIsNotUnique(ValidatorType type) throws Exception {
        this.validatorType = type;
        initModel();

        // Create a measure with two units having the same name
        BaseMeasure length = BaseMeasureBuilder.create()
                .withNamespace("measures")
                .withName("Length")
                .withSymbol("L")
                .build();

        Unit unit1 = UnitBuilder.create()
                .withName("meter")
                .withSymbol("m")
                .withRateDividend(BigDecimal.ONE)
                .withRateDivisor(BigDecimal.ONE)
                .build();

        Unit unit2 = UnitBuilder.create()
                .withName("Meter") // Same name (different case) - should trigger warning
                .withSymbol("M")
                .withRateDividend(new BigDecimal("1000"))
                .withRateDivisor(BigDecimal.ONE)
                .build();

        length.getUnits().add(unit1);
        length.getUnits().add(unit2);
        measureModel.getResource().getContents().add(length);

        // Expect UnitNameIsUnique warning for both units
        runValidation(
                Collections.emptyList(),
                List.of(MeasureConstraints.UNIT_NAME_IS_UNIQUE,
                        MeasureConstraints.UNIT_NAME_IS_UNIQUE)
        );
    }

    @ParameterizedTest(name = "testUnitSymbolIsNotUniqueGlobally [{0}]")
    @EnumSource(ValidatorType.class)
    void testUnitSymbolIsNotUniqueGlobally(ValidatorType type) throws Exception {
        this.validatorType = type;
        initModel();

        // Create two measures with units having the same symbol
        BaseMeasure length = BaseMeasureBuilder.create()
                .withNamespace("measures")
                .withName("Length")
                .withSymbol("L")
                .build();

        Unit meter = UnitBuilder.create()
                .withName("meter")
                .withSymbol("m")
                .withRateDividend(BigDecimal.ONE)
                .withRateDivisor(BigDecimal.ONE)
                .build();
        length.getUnits().add(meter);

        BaseMeasure mass = BaseMeasureBuilder.create()
                .withNamespace("measures")
                .withName("Mass")
                .withSymbol("M")
                .build();

        Unit massUnit = UnitBuilder.create()
                .withName("massUnit")
                .withSymbol("m") // Same symbol as meter - should trigger warning
                .withRateDividend(BigDecimal.ONE)
                .withRateDivisor(BigDecimal.ONE)
                .build();
        mass.getUnits().add(massUnit);

        measureModel.getResource().getContents().add(length);
        measureModel.getResource().getContents().add(mass);

        // Expect UnitSymbolIsUnique warning
        // EVL deduplicates warnings by message (reports 1), Java reports each unit separately (2)
        List<String> expectedWarnings = validatorType == ValidatorType.EVL
                ? List.of(MeasureConstraints.UNIT_SYMBOL_IS_UNIQUE)
                : List.of(MeasureConstraints.UNIT_SYMBOL_IS_UNIQUE, MeasureConstraints.UNIT_SYMBOL_IS_UNIQUE);
        runValidation(
                Collections.emptyList(),
                expectedWarnings
        );
    }

    // ==========================================================================
    // Derived Measure Tests
    // ==========================================================================

    @ParameterizedTest(name = "testValidDerivedMeasure [{0}]")
    @EnumSource(ValidatorType.class)
    void testValidDerivedMeasure(ValidatorType type) throws Exception {
        this.validatorType = type;
        initModel();

        // Create a base measure
        BaseMeasure length = BaseMeasureBuilder.create()
                .withNamespace("measures")
                .withName("Length")
                .withSymbol("L")
                .build();

        Unit meter = UnitBuilder.create()
                .withName("meter")
                .withSymbol("m")
                .withRateDividend(BigDecimal.ONE)
                .withRateDivisor(BigDecimal.ONE)
                .build();
        length.getUnits().add(meter);

        // Create a derived measure (Area = Length^2)
        DerivedMeasure area = DerivedMeasureBuilder.create()
                .withNamespace("measures")
                .withName("Area")
                .withSymbol("A")
                .build();

        BaseMeasureTerm term = BaseMeasureTermBuilder.create()
                .withBaseMeasure(length)
                .withExponent(2)
                .build();
        area.getTerms().add(term);

        Unit squareMeter = UnitBuilder.create()
                .withName("squareMeter")
                .withSymbol("m2")
                .withRateDividend(BigDecimal.ONE)
                .withRateDivisor(BigDecimal.ONE)
                .build();
        area.getUnits().add(squareMeter);

        measureModel.getResource().getContents().add(length);
        measureModel.getResource().getContents().add(area);

        // Should pass with no errors or warnings
        runValidation(Collections.emptyList(), Collections.emptyList());
    }
}
