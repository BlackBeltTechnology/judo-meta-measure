package hu.blackbelt.judo.meta.measure.validation.rules;

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

import hu.blackbelt.judo.meta.measure.Measure;
import hu.blackbelt.judo.meta.measure.Unit;
import hu.blackbelt.judo.meta.measure.runtime.MeasureUtils;
import hu.blackbelt.judo.meta.measure.validation.MeasureConstraints;
import hu.blackbelt.judo.zeta.annotation.Constraint;
import hu.blackbelt.judo.zeta.annotation.Critique;
import hu.blackbelt.judo.zeta.annotation.Guard;
import hu.blackbelt.judo.zeta.annotation.ValidationContext;
import hu.blackbelt.judo.zeta.validation.core.ValidationResult;
import hu.blackbelt.judo.zeta.validation.core.ValidationRule;
import org.eclipse.emf.ecore.EObject;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Validation rules for Unit elements.
 */
@ValidationContext(Unit.class)
public class UnitValidations {

    /**
     * Helper method to get all Units from the resource set.
     */
    private Collection<Unit> getAllUnits(
            hu.blackbelt.judo.zeta.validation.core.ValidationContext ctx
    ) {
        MeasureUtils measureUtils = new MeasureUtils(ctx.getResourceSet());
        return measureUtils.all(ctx.getResourceSet(), Unit.class)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to get the containing Measure for a Unit.
     */
    private Measure getContainingMeasure(Unit unit) {
        EObject container = unit.eContainer();
        if (container instanceof Measure) {
            return (Measure) container;
        }
        return null;
    }

    // ==========================================================================
    // Constraints (ERROR severity)
    // ==========================================================================

    /**
     * Constraint: UnitSymbolIsUniqueInMeasure
     * Check: Unit symbol must be unique within its containing measure.
     */
    @Constraint(
            name = MeasureConstraints.UNIT_SYMBOL_IS_UNIQUE_IN_MEASURE,
            message = "Unit symbol is not unique within measure"
    )
    @Guard(method = MeasureConstraints.GUARD_UNIT_SYMBOL_IS_UNIQUE_IN_MEASURE)
    public ValidationRule unitSymbolIsUniqueInMeasure() {
        return (element, ctx) -> {
            Unit self = (Unit) element;
            Measure containingMeasure = getContainingMeasure(self);

            if (containingMeasure == null) {
                return ValidationResult.pass();
            }

            boolean hasDuplicate = containingMeasure.getUnits().stream()
                    .filter(u -> u != self)
                    .anyMatch(u ->
                            u.getSymbol() != null &&
                            u.getSymbol().equals(self.getSymbol())
                    );

            if (hasDuplicate) {
                return ValidationResult.fail(
                        "Unit symbol is not unique within measure: " + self.getSymbol() +
                        " (unit: " + self.getName() + ", measure: " + containingMeasure.getName() + ")"
                );
            }

            return ValidationResult.pass();
        };
    }

    /**
     * Guard for UnitSymbolIsUniqueInMeasure.
     * Only applies when symbol is non-empty.
     */
    public boolean guardUnitSymbolIsUniqueInMeasure(
            EObject element,
            hu.blackbelt.judo.zeta.validation.core.ValidationContext ctx
    ) {
        Unit self = (Unit) element;
        return self.getSymbol() != null && !self.getSymbol().isEmpty();
    }

    // ==========================================================================
    // Critiques (WARNING severity)
    // ==========================================================================

    /**
     * Critique: UnitNameIsUnique
     * Check: Unit name should be unique within its containing measure.
     */
    @Critique(
            name = MeasureConstraints.UNIT_NAME_IS_UNIQUE,
            message = "There are two or more units of the same name within measure"
    )
    public ValidationRule unitNameIsUnique() {
        return (element, ctx) -> {
            Unit self = (Unit) element;
            Measure containingMeasure = getContainingMeasure(self);

            if (containingMeasure == null) {
                return ValidationResult.pass();
            }

            boolean hasDuplicate = containingMeasure.getUnits().stream()
                    .filter(u -> u != self)
                    .anyMatch(u ->
                            u.getName() != null &&
                            self.getName() != null &&
                            u.getName().equalsIgnoreCase(self.getName())
                    );

            if (hasDuplicate) {
                return ValidationResult.fail(
                        "There are two or more units of the same name within measure: " +
                        self.getName() + " (measure: " + containingMeasure.getName() + ")"
                );
            }

            return ValidationResult.pass();
        };
    }

    /**
     * Critique: UnitSymbolIsUnique
     * Check: Unit symbol should be unique across all units globally.
     */
    @Critique(
            name = MeasureConstraints.UNIT_SYMBOL_IS_UNIQUE,
            message = "Unit symbol is not unique globally"
    )
    public ValidationRule unitSymbolIsUnique() {
        return (element, ctx) -> {
            Unit self = (Unit) element;

            // Guard: only check if symbol is defined
            if (self.getSymbol() == null || self.getSymbol().isEmpty()) {
                return ValidationResult.pass();
            }

            boolean hasDuplicate = getAllUnits(ctx).stream()
                    .filter(u -> u != self)
                    .anyMatch(u ->
                            u.getSymbol() != null &&
                            u.getSymbol().equals(self.getSymbol())
                    );

            if (hasDuplicate) {
                return ValidationResult.fail(
                        "Unit symbol is not unique globally: " + self.getSymbol() +
                        " (unit: " + self.getName() + ")"
                );
            }

            return ValidationResult.pass();
        };
    }
}
