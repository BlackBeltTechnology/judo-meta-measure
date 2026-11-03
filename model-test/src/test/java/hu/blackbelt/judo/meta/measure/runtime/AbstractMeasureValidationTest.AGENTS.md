# `AbstractMeasureValidationTest.java`

Shared fixture that routes a single test body to two validation engines, so every
subclass case runs once against EVL and once against the Java (Zeta) validator.

## Exports

- `protected MeasureModelResourceSupport measureModelSupport`
- `protected MeasureModel measureModel`
- `protected ValidatorType validatorType` — defaults `ValidatorType.EVL`
- `protected void initModel()` / `initModel(String modelName)`
- `protected void runValidation()`, `runValidation(Collection<String> expectedErrors)`,
  `runValidation(Collection<String> expectedErrors, Collection<String> expectedWarnings)`

## Contracts

- `@BeforeEach setUp` nulls `measureModelSupport` and `measureModel`. A subclass MUST
  assign `validatorType` and then call `initModel()` inside the test body; skipping
  `initModel()` dereferences null.
- `initModel()` uses model URI `urn:Measure.model` (`DEFAULT_MODEL_NAME`) and model
  name `test`. `initModel(String)` overrides the URI only, never the name.
- `runValidation` dispatches on `validatorType` and throws
  `IllegalStateException("Unknown validator type: ...")` for any unhandled constant.
- EVL path calls `MeasureEpsilonValidator.validateMeasure` with EMPTY expected lists and
  does its own comparison: it groups `EvlScriptExecutionException.getUnexpectedErrors()`
  and `getUnexpectedWarnings()` into count-by-constraint maps, splitting Epsilon's
  `ConstraintName|Message` at the first `|`. Comparison is by multiset count, so a
  constraint expected twice must actually fire twice.
- A `null` expected collection means "do not check that category"; an EMPTY collection
  means "must be empty" and a non-empty actual set fails.
- If EVL throws nothing while errors or warnings were expected, the fixture calls
  `fail(...)` explicitly.
- Java path delegates expectation matching to `MeasureValidator.validateMeasure(log,
  measureModel.getResourceSet(), expectedErrors, expectedWarnings)`; the fixture logs and
  rethrows `MeasureValidationException` rather than translating it.
- EVL runs inside a try-with-resources `BufferedSlf4jLogger`; the Java path uses the raw
  `log`, so the two engines do not produce identical log output.
