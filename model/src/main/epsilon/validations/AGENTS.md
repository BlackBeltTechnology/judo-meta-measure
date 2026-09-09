# `model/src/main/epsilon/validations` — EVL constraint source for the measure metamodel

| File | Purpose |
|---|---|
| `measure-plugin-validation.evl` | Eclipse-plugin entry point. Imports `measure.evl`, adds a `pre` block binding `measureUtils = new Native("hu.blackbelt.judo.meta.measure.runtime.MeasureUtils")(MEASURE.resource.resourceSet, false)`. Needed because the plugin run has no Java-side injection step; the runtime path injects `measureUtils` itself. Rules added here stay invisible to the runtime validator. |
| `measure.evl` | Authoritative rules over model alias `MEASURE`. On `Measure`: `constraint NoCircularReferencesOfMeasureWithTerms` (worklist walk of `terms.baseMeasure`, `DerivedMeasure` only), `constraint MeasureSymbolIsUnique`, `critique BaseUnitShouldBeDefined` (needs `rateDividend == rateDivisor`), `critique MeasureNameIsUnique` (case-insensitive). On `Unit`: `UnitSymbolIsUniqueInMeasure`, `UnitNameIsUnique`, `UnitSymbolIsUnique`. `constraint`→error, `critique`→warning. |
