# Measure Validation Capability

## ADDED Requirements

### Requirement: Java-Based Validation Framework
The system SHALL provide a native Java validation framework using Zeta annotations as an alternative to EVL validation.

#### Scenario: Validation entry point
- **WHEN** a MeasureModel is submitted for validation
- **THEN** the MeasureValidator SHALL validate all model elements using registered validation rules

#### Scenario: Expected errors and warnings
- **WHEN** expected errors and warnings collections are provided to the validator
- **THEN** the validator SHALL compare actual results against expected results and report discrepancies

#### Scenario: Parallel execution
- **WHEN** parallel execution is enabled
- **THEN** the validator SHALL process model elements concurrently for improved performance

### Requirement: Dual Validation Support
The system SHALL support running both EVL and Java validators with identical results.

#### Scenario: Test parity
- **WHEN** the same test case is run against both EVL and Java validators
- **THEN** both validators SHALL produce identical error and warning sets

#### Scenario: Parameterized tests
- **WHEN** a validation test is defined
- **THEN** it SHALL run automatically against both EVL and JAVA validator types

### Requirement: Constraint Name Constants
The system SHALL use string constants for all constraint names, guard method names, and critique names.

#### Scenario: Constraint name reference
- **WHEN** a constraint name is referenced in code (validation rules, tests, or comparisons)
- **THEN** it SHALL use a constant from MeasureConstraints class

#### Scenario: Guard method reference
- **WHEN** a guard method name is referenced in @Guard annotation
- **THEN** it SHALL use a constant rather than a string literal

### Requirement: Measure Validation Rules
The system SHALL implement Java validation rules equivalent to all EVL constraints for Measure elements.

#### Scenario: Circular reference detection
- **WHEN** a Measure has terms that create a circular reference
- **THEN** the NoCircularReferencesOfMeasureWithTerms constraint SHALL fail

#### Scenario: Base unit recommendation
- **WHEN** a Measure does not have a base unit defined
- **THEN** the BaseUnitShouldBeDefined critique SHALL produce a warning

#### Scenario: Measure name uniqueness
- **WHEN** multiple Measures have the same name
- **THEN** the MeasureNameIsUnique critique SHALL produce a warning

#### Scenario: Measure symbol uniqueness
- **WHEN** multiple Measures have the same symbol
- **THEN** the MeasureSymbolIsUnique constraint SHALL fail

### Requirement: Unit Validation Rules
The system SHALL implement Java validation rules equivalent to all EVL constraints for Unit elements.

#### Scenario: Unit name uniqueness
- **WHEN** multiple Units have the same name
- **THEN** the UnitNameIsUnique critique SHALL produce a warning

#### Scenario: Unit symbol uniqueness globally
- **WHEN** multiple Units have the same symbol
- **THEN** the UnitSymbolIsUnique critique SHALL produce a warning

#### Scenario: Unit symbol uniqueness in measure
- **WHEN** multiple Units within the same Measure have the same symbol
- **THEN** the UnitSymbolIsUniqueInMeasure constraint SHALL fail

### Requirement: Duration Unit Rate Validation
The system SHALL validate duration unit rate conversions for all temporal units.

#### Scenario: Microsecond rate validation
- **WHEN** a DurationUnit defines microsecond rates
- **THEN** the MicrosecondRateIsValid constraint SHALL validate the conversion factor

#### Scenario: Millisecond rate validation
- **WHEN** a DurationUnit defines millisecond rates
- **THEN** the MillisecondRateIsValid constraint SHALL validate the conversion factor

#### Scenario: Second rate validation
- **WHEN** a DurationUnit defines second rates
- **THEN** the SecondRateIsValid constraint SHALL validate the conversion factor

#### Scenario: Minute rate validation
- **WHEN** a DurationUnit defines minute rates
- **THEN** the MinuteRateIsValid constraint SHALL validate the conversion factor

#### Scenario: Hour rate validation
- **WHEN** a DurationUnit defines hour rates
- **THEN** the HourRateIsValid constraint SHALL validate the conversion factor

#### Scenario: Day rate validation
- **WHEN** a DurationUnit defines day rates
- **THEN** the DayRateIsValid constraint SHALL validate the conversion factor

#### Scenario: Week rate validation
- **WHEN** a DurationUnit defines week rates
- **THEN** the WeekRateIsValid constraint SHALL validate the conversion factor

### Requirement: MeasuredType Validation Rules
The system SHALL implement Java validation rules equivalent to all EVL constraints for MeasuredType elements.

#### Scenario: Scale validation
- **WHEN** a MeasuredType has an invalid scale value
- **THEN** the ValidScale constraint SHALL fail

### Requirement: Performance Testing
The system SHALL include performance tests comparing EVL and Java validation execution time.

#### Scenario: Large model validation
- **WHEN** a model with 10,000 elements is validated
- **THEN** performance metrics SHALL be recorded for both EVL and Java validators

#### Scenario: Model characteristics
- **WHEN** generating test models
- **THEN** the model structure SHALL reflect characteristics similar to real-world usage (e.g., rackinspect model patterns)

### Requirement: Documentation
The system SHALL provide comprehensive documentation for the validation framework.

#### Scenario: Validation overview
- **WHEN** a developer needs to understand the validation system
- **THEN** a README.md in docs/validation/ SHALL provide an overview

#### Scenario: Implementation guide
- **WHEN** a developer needs to add or modify validation rules
- **THEN** a java-validation-framework.md SHALL provide implementation guidance

#### Scenario: Zeta reference
- **WHEN** a developer needs Zeta framework details
- **THEN** documentation SHALL reference Zeta documentation without duplicating it
