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

/**
 * Constants for all validation constraint names, critique names, and guard method names.
 *
 * <p>Using constants ensures consistency between validation rules and tests,
 * and enables IDE refactoring support.</p>
 */
public final class MeasureConstraints {

    private MeasureConstraints() {
        // Prevent instantiation
    }

    // ==========================================================================
    // Measure Constraints (ERROR severity)
    // ==========================================================================

    /**
     * Constraint: Derived measure definition must not be recursive.
     */
    public static final String NO_CIRCULAR_REFERENCES_OF_MEASURE_WITH_TERMS =
            "NoCircularReferencesOfMeasureWithTerms";

    /**
     * Constraint: Measure symbol must be unique across all measures.
     */
    public static final String MEASURE_SYMBOL_IS_UNIQUE = "MeasureSymbolIsUnique";

    // ==========================================================================
    // Measure Critiques (WARNING severity)
    // ==========================================================================

    /**
     * Critique: Every measure should have a base unit defined.
     */
    public static final String BASE_UNIT_SHOULD_BE_DEFINED = "BaseUnitShouldBeDefined";

    /**
     * Critique: Measure name should be unique (case-insensitive).
     */
    public static final String MEASURE_NAME_IS_UNIQUE = "MeasureNameIsUnique";

    // ==========================================================================
    // Unit Constraints (ERROR severity)
    // ==========================================================================

    /**
     * Constraint: Unit symbol must be unique within its containing measure.
     */
    public static final String UNIT_SYMBOL_IS_UNIQUE_IN_MEASURE = "UnitSymbolIsUniqueInMeasure";

    // ==========================================================================
    // Unit Critiques (WARNING severity)
    // ==========================================================================

    /**
     * Critique: Unit name should be unique within its containing measure.
     */
    public static final String UNIT_NAME_IS_UNIQUE = "UnitNameIsUnique";

    /**
     * Critique: Unit symbol should be unique across all units globally.
     */
    public static final String UNIT_SYMBOL_IS_UNIQUE = "UnitSymbolIsUnique";

    // ==========================================================================
    // Guard Method Names
    // ==========================================================================

    /**
     * Guard for NoCircularReferencesOfMeasureWithTerms - only applies to DerivedMeasure.
     */
    public static final String GUARD_NO_CIRCULAR_REFERENCES =
            "guardNoCircularReferencesOfMeasureWithTerms";

    /**
     * Guard for MeasureSymbolIsUnique - only applies when symbol is non-empty.
     */
    public static final String GUARD_MEASURE_SYMBOL_IS_UNIQUE = "guardMeasureSymbolIsUnique";

    /**
     * Guard for UnitSymbolIsUniqueInMeasure - only applies when symbol is non-empty.
     */
    public static final String GUARD_UNIT_SYMBOL_IS_UNIQUE_IN_MEASURE =
            "guardUnitSymbolIsUniqueInMeasure";

    /**
     * Guard for UnitSymbolIsUnique - only applies when symbol is non-empty.
     */
    public static final String GUARD_UNIT_SYMBOL_IS_UNIQUE = "guardUnitSymbolIsUnique";
}
