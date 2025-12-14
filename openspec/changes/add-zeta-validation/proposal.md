# Change: Add Zeta-Based Java Validation Framework

## Why

The current Measure model validation relies solely on Epsilon Validation Language (EVL), which has limitations:
- No IDE support (debugging, refactoring, code navigation)
- Performance overhead compared to native Java
- Limited integration with standard Java tooling
- Harder to maintain and extend

Following the successful pattern from judo-meta-esm, we will add a native Java validation framework using the Zeta validation library, enabling dual validation (EVL + Java) with identical results.

## What Changes

### Core Framework Integration
- Add Zeta validation framework dependencies (annotations, validation-core, common)
- Create `MeasureValidator` entry point class in model module
- Create `ValidatorType` enum for test parameterization
- Use constants for all constraint names, validation result names, guard method names

### Validation Rules (Java)
- Convert all EVL constraints to Java validation classes using Zeta annotations
- Create validation rule classes organized by domain:
  - `MeasureValidations.java` - Measure-specific rules
  - `UnitValidations.java` - Unit validation rules
  - `DurationUnitValidations.java` - Duration unit rate validation
  - `MeasuredTypeValidations.java` - MeasuredType validation

### Test Infrastructure
- Create `AbstractMeasureValidationTest` base class with dual validation support
- Modify existing tests to run as parameterized tests (EVL + Java)
- Add performance comparison tests (10,000 elements)
- Ensure test parity between EVL and Java validators

### Documentation Updates
- Convert AsciiDoc files to Markdown (except pages directory)
- Convert PlantUML diagrams to Mermaid
- Update docs to reference Zeta documentation
- Add validation method documentation

### Build Configuration
- Add `judo-zeta-version` property to pom.xml (SNAPSHOT version)
- Update all Zeta references to use the property

## Impact

- **Affected modules:** model, model-test, osgi
- **Affected specs:** measure-validation (new capability)
- **Backward compatible:** Yes - EVL validation remains functional
- **Performance:** Expected 5-10x improvement for Java validation
