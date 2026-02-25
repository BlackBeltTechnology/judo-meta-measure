# measure-validation Specification

## Purpose

Provides Epsilon EVL (Epsilon Validation Language) based validation of measure models, with support for script location resolution across different runtime environments (JAR, OSGi bundle, filesystem).

## Architecture

`MeasureEpsilonValidator` is the entry point, offering static `validateMeasure()` methods that accept a `MeasureModel`, logger, script root URI, and optional expected error/warning collections. It builds an Epsilon `ExecutionContext`, injects a `MeasureUtils` instance, and executes the `measure.evl` script. Validation scripts live in `model/src/main/epsilon/validations/`. The `measure-plugin-validation.evl` wrapper imports `measure.evl` and instantiates `MeasureUtils` natively for use within Eclipse plugin context.

## Requirements

### Requirement: Epsilon Validation Execution

MeasureEpsilonValidator SHALL execute EVL validation scripts against a MeasureModel and report errors and warnings.

#### Scenario: Validate a well-formed model

- **GIVEN** a MeasureModel containing valid measures and units
- **WHEN** `MeasureEpsilonValidator.validateMeasure(log, model, scriptRoot)` is called
- **THEN** validation completes without throwing EvlScriptExecutionException

#### Scenario: Validate with expected errors

- **GIVEN** a MeasureModel that violates a constraint defined in measure.evl
- **WHEN** `validateMeasure()` is called with the expected error message in `expectedErrors`
- **THEN** validation completes successfully (expected errors are not treated as failures)

#### Scenario: Validate with unexpected errors

- **GIVEN** a MeasureModel that violates a constraint not listed in expectedErrors
- **WHEN** `validateMeasure()` is called
- **THEN** an `EvlScriptExecutionException` is thrown containing the unexpected error details

### Requirement: Script Location Resolution

`calculateMeasureValidationScriptURI()` SHALL resolve the correct URI for validation scripts regardless of whether the code runs from a JAR, an OSGi bundle, or the filesystem.

#### Scenario: Resolve from JAR

- **GIVEN** the validator class is loaded from a JAR file
- **WHEN** `calculateMeasureValidationScriptURI()` is called
- **THEN** it returns a `jar:` URI pointing to the `/validations/` directory inside the JAR

#### Scenario: Resolve from OSGi bundle

- **GIVEN** the validator class is loaded from an OSGi bundle
- **WHEN** `calculateMeasureValidationScriptURI()` is called
- **THEN** it returns a URI derived from the bundle location with `/validations/` appended

#### Scenario: Resolve from filesystem

- **GIVEN** the validator class is loaded from a filesystem directory
- **WHEN** `calculateMeasureValidationScriptURI()` is called
- **THEN** it returns a file URI pointing to the `validations/` subdirectory

### Requirement: Validation Context Lifecycle

The validator SHALL properly manage the Epsilon ExecutionContext lifecycle, committing and closing it in a finally block regardless of validation outcome.

#### Scenario: Context cleanup on success

- **GIVEN** a successful validation run
- **WHEN** validation completes
- **THEN** the ExecutionContext is committed and closed

#### Scenario: Context cleanup on failure

- **GIVEN** a validation run that throws an exception
- **WHEN** the exception propagates
- **THEN** the ExecutionContext is still committed and closed via the finally block
