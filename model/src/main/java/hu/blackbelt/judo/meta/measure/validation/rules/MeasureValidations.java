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

import hu.blackbelt.judo.meta.measure.BaseMeasure;
import hu.blackbelt.judo.meta.measure.BaseMeasureTerm;
import hu.blackbelt.judo.meta.measure.DerivedMeasure;
import hu.blackbelt.judo.meta.measure.Measure;
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
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validation rules for Measure elements.
 */
@ValidationContext(Measure.class)
public class MeasureValidations {

    /**
     * Helper method to get all Measures from the resource set.
     */
    private Collection<Measure> getAllMeasures(
            hu.blackbelt.judo.zeta.validation.core.ValidationContext ctx
    ) {
        MeasureUtils measureUtils = new MeasureUtils(ctx.getResourceSet());
        return measureUtils.all(ctx.getResourceSet(), Measure.class)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to collect all measures referenced transitively through terms.
     * Used to detect circular references.
     */
    private Set<Measure> getAllTermMeasures(Measure measure) {
        Set<Measure> result = new HashSet<>();
        collectTermMeasures(measure, result, new HashSet<>());
        return result;
    }

    private void collectTermMeasures(Measure measure, Set<Measure> collected, Set<Measure> visited) {
        if (visited.contains(measure)) {
            return;
        }
        visited.add(measure);

        if (measure instanceof DerivedMeasure) {
            DerivedMeasure derived = (DerivedMeasure) measure;
            if (derived.getTerms() != null) {
                for (BaseMeasureTerm term : derived.getTerms()) {
                    BaseMeasure baseMeasure = term.getBaseMeasure();
                    if (baseMeasure != null) {
                        collected.add(baseMeasure);
                        collectTermMeasures(baseMeasure, collected, visited);
                    }
                }
            }
        }
    }

    // ==========================================================================
    // Constraints (ERROR severity)
    // ==========================================================================

    /**
     * Constraint: NoCircularReferencesOfMeasureWithTerms
     * Check: Derived measure definition must not be recursive.
     */
    @Constraint(
            name = MeasureConstraints.NO_CIRCULAR_REFERENCES_OF_MEASURE_WITH_TERMS,
            message = "Derived measure definition is recursive"
    )
    @Guard(method = MeasureConstraints.GUARD_NO_CIRCULAR_REFERENCES)
    public ValidationRule noCircularReferencesOfMeasureWithTerms() {
        return (element, ctx) -> {
            Measure self = (Measure) element;

            if (getAllTermMeasures(self).contains(self)) {
                return ValidationResult.fail(
                        "Derived measure definition " + self.getName() + " is recursive"
                );
            }

            return ValidationResult.pass();
        };
    }

    /**
     * Guard for NoCircularReferencesOfMeasureWithTerms.
     * Only applies to DerivedMeasure with terms.
     */
    public boolean guardNoCircularReferencesOfMeasureWithTerms(
            EObject element,
            hu.blackbelt.judo.zeta.validation.core.ValidationContext ctx
    ) {
        if (!(element instanceof DerivedMeasure)) {
            return false;
        }
        DerivedMeasure self = (DerivedMeasure) element;
        return self.getTerms() != null && !self.getTerms().isEmpty();
    }

    /**
     * Constraint: MeasureSymbolIsUnique
     * Check: Measure symbol must be unique across all measures.
     */
    @Constraint(
            name = MeasureConstraints.MEASURE_SYMBOL_IS_UNIQUE,
            message = "Measure symbol is not unique"
    )
    @Guard(method = MeasureConstraints.GUARD_MEASURE_SYMBOL_IS_UNIQUE)
    public ValidationRule measureSymbolIsUnique() {
        return (element, ctx) -> {
            Measure self = (Measure) element;

            boolean hasDuplicate = getAllMeasures(ctx).stream()
                    .filter(m -> m != self)
                    .anyMatch(m ->
                            m.getSymbol() != null &&
                            m.getSymbol().equals(self.getSymbol())
                    );

            if (hasDuplicate) {
                return ValidationResult.fail(
                        "Measure symbol is not unique: " + self.getSymbol() +
                        " (measure: " + self.getName() + ")"
                );
            }

            return ValidationResult.pass();
        };
    }

    /**
     * Guard for MeasureSymbolIsUnique.
     * Only applies when symbol is non-empty.
     */
    public boolean guardMeasureSymbolIsUnique(
            EObject element,
            hu.blackbelt.judo.zeta.validation.core.ValidationContext ctx
    ) {
        Measure self = (Measure) element;
        return self.getSymbol() != null && !self.getSymbol().isEmpty();
    }

    // ==========================================================================
    // Critiques (WARNING severity)
    // ==========================================================================

    /**
     * Critique: BaseUnitShouldBeDefined
     * Check: Every measure should have a base unit (where rateDividend == rateDivisor).
     */
    @Critique(
            name = MeasureConstraints.BASE_UNIT_SHOULD_BE_DEFINED,
            message = "No base unit is defined for measure"
    )
    public ValidationRule baseUnitShouldBeDefined() {
        return (element, ctx) -> {
            Measure self = (Measure) element;

            boolean hasBaseUnit = self.getUnits().stream()
                    .anyMatch(u ->
                            u.getRateDividend() != null &&
                            u.getRateDivisor() != null &&
                            u.getRateDividend().compareTo(u.getRateDivisor()) == 0
                    );

            if (!hasBaseUnit) {
                return ValidationResult.fail(
                        "No base unit is defined for measure: " + self.getName()
                );
            }

            return ValidationResult.pass();
        };
    }

    /**
     * Critique: MeasureNameIsUnique
     * Check: Measure name should be unique (case-insensitive).
     */
    @Critique(
            name = MeasureConstraints.MEASURE_NAME_IS_UNIQUE,
            message = "There are two or more measures of the same name"
    )
    public ValidationRule measureNameIsUnique() {
        return (element, ctx) -> {
            Measure self = (Measure) element;

            boolean hasDuplicate = getAllMeasures(ctx).stream()
                    .filter(m -> m != self)
                    .anyMatch(m ->
                            m.getName() != null &&
                            self.getName() != null &&
                            m.getName().equalsIgnoreCase(self.getName())
                    );

            if (hasDuplicate) {
                return ValidationResult.fail(
                        "There are two or more measures of the same name: " + self.getName()
                );
            }

            return ValidationResult.pass();
        };
    }
}
