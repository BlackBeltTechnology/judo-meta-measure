# measure-testing Specification

## Purpose

Provides unit and integration test coverage for the measure metamodel, verifying model creation, XMI ID management, Epsilon validation execution, and OSGi bundle deployment.

## Architecture

Tests are split across two modules:

- **model-test/** contains JUnit 5 unit tests: `MeasureExecutionContextTest` (model resource creation), `MeasureUtilsTest` (XMI ID validation), and `MeasureValidationTest` (Epsilon validation integration)
- **osgi-itest/** contains Pax Exam integration tests: `MeasureModelLoadITest` (Karaf bundle loading and validation) with `KarafFeatureProvider` utility for container configuration

Tests use SLF4J/Logback for logging (configured via `logback-test.xml` at the project root).

## Requirements

### Requirement: Model Resource Creation

The test suite SHALL verify that a MeasureModel can be constructed programmatically using the builder pattern.

#### Scenario: Create model with resource support

- **GIVEN** a URI "urn:measure.judo-meta-measure"
- **WHEN** `measureModelResourceSupportBuilder()` is used to construct a MeasureModelResourceSupport
- **THEN** the model is created successfully with a valid ResourceSet

### Requirement: XMI ID Uniqueness Validation

The test suite SHALL verify that `MeasureUtils.validateUniqueXmiids()` correctly detects duplicate and null-ResourceSet conditions.

#### Scenario: Null ResourceSet detection

- **GIVEN** two BaseMeasure objects added to a model
- **GIVEN** MeasureUtils has no ResourceSet assigned
- **WHEN** `validateUniqueXmiids()` is called
- **THEN** IllegalStateException is thrown with message containing "Model's ResourceSet is unknown (null)"

#### Scenario: Duplicate ID detection

- **GIVEN** two BaseMeasure objects with the same XMI ID "ID"
- **WHEN** `validateUniqueXmiids()` is called
- **THEN** IllegalStateException is thrown with message containing "There are non-unique xmiid-s"

#### Scenario: Unique IDs accepted

- **GIVEN** two BaseMeasure objects with distinct IDs "ID" and "ID2"
- **WHEN** `validateUniqueXmiids()` is called
- **THEN** no exception is thrown

### Requirement: Epsilon Validation Integration

The test suite SHALL verify that Epsilon EVL scripts can be executed against a MeasureModel and that expected vs unexpected errors are properly distinguished.

#### Scenario: Run validation with no expected errors

- **GIVEN** a MeasureModel built with `MeasureModelResourceSupport`
- **WHEN** `MeasureEpsilonValidator.validateMeasure()` is called with the calculated script URI
- **THEN** validation completes (passes if no constraints are violated)

#### Scenario: Unexpected validation errors reported

- **GIVEN** a model that violates a constraint
- **WHEN** validation is run and the error is not in `expectedErrors`
- **THEN** `EvlScriptExecutionException` is thrown with details about unexpected errors

### Requirement: OSGi Integration Test

The test suite SHALL verify that the measure OSGi bundle loads correctly in a Karaf container and that bundle-tracked models can be validated.

#### Scenario: End-to-end Karaf test

- **GIVEN** a Pax Exam Karaf container configured with measure dependencies
- **GIVEN** a dynamically created test bundle with a `Measure-Models` header and embedded XMI model
- **WHEN** the container starts and the test bundle is provisioned
- **THEN** `MeasureModel` is injected via OSGi service lookup
- **THEN** `MeasureEpsilonValidator.validateMeasure()` executes successfully against the injected model
