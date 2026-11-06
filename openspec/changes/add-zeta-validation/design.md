# Design: Add Zeta-Based Java Validation Framework

## Context

The judo-meta-measure project currently uses Epsilon Validation Language (EVL) for model validation. While EVL is powerful, it lacks IDE integration and has performance overhead. The judo-meta-esm project has successfully implemented a dual validation approach using the Zeta framework, and we will follow the same pattern.

### Stakeholders
- Model developers using the Measure metamodel
- Runtime applications consuming validation results
- CI/CD pipelines running validation tests

### Constraints
- EVL validation must continue to work (dual validation)
- Test results must be identical between EVL and Java validators
- No changes to the Zeta framework itself (consume only)
- Performance tests must demonstrate Java advantage

## Goals / Non-Goals

### Goals
- Implement Java-based validation using Zeta annotations
- Achieve test parity with EVL validation
- Enable IDE debugging and refactoring of validation rules
- Improve validation performance (target: 5-10x faster than EVL)
- Create parameterized tests running both validators
- Document all validation rules

### Non-Goals
- Removing EVL validation (keep both running)
- Modifying the Zeta framework
- Copying Zeta framework code into this project
- Changing validation rule semantics (must match EVL exactly)

## Decisions

### Decision 1: Use Zeta Framework from External Dependency

**What:** Import Zeta validation framework as Maven dependency, not copy code.

**Why:**
- Avoids code duplication and maintenance burden
- Enables framework updates via version management
- Follows established pattern from judo-meta-esm

**Alternatives considered:**
- Copy Zeta code: Rejected - maintenance burden, divergence risk
- Create custom framework: Rejected - unnecessary duplication

### Decision 2: Validation Classes in model Module

**What:** Place Java validation classes in `model/src/main/java/hu/blackbelt/judo/meta/measure/validation/`

**Why:**
- Keeps validation close to the model definition
- Follows judo-meta-esm pattern
- Enables direct access to model classes without additional dependencies

### Decision 3: Parameterized Tests for Dual Validation

**What:** Use JUnit 5 `@ParameterizedTest` with `@EnumSource(ValidatorType.class)` to run same test against both validators.

**Why:**
- Ensures test parity automatically
- Single test case covers both validators
- Clear indication of which validator failed

**Pattern:**
```java
@ParameterizedTest(name = "testConstraintName [{0}]")
@EnumSource(ValidatorType.class)
void testConstraintName(ValidatorType type) throws Exception {
    this.validatorType = type;
    initModel();
    // ... build model ...
    runValidation(expectedErrors, expectedWarnings);
}
```

### Decision 4: Constants for All Validation Names

**What:** Define string constants for constraint names, critique names, guard method names.

**Why:**
- Avoids typos in constraint name references
- Enables IDE refactoring
- Documents all constraints in one place

**Pattern:**
```java
public final class MeasureConstraints {
    public static final String NO_CIRCULAR_REFERENCES = "NoCircularReferencesOfMeasureWithTerms";
    public static final String BASE_UNIT_SHOULD_BE_DEFINED = "BaseUnitShouldBeDefined";
    // ...
}
```

### Decision 5: Performance Test with Generated Model

**What:** Create performance test that generates 10,000 model elements and compares EVL vs Java validation time.

**Why:**
- Quantifies performance benefit
- Uses characteristics similar to real-world models (based on rackinspect model structure)
- Validates parallel execution benefits

## Architecture

### Package Structure
```
model/src/main/java/hu/blackbelt/judo/meta/measure/
├── runtime/                         # Existing runtime classes
│   ├── MeasureModel.java
│   ├── MeasureUtils.java
│   └── MeasureEpsilonValidator.java
└── validation/                      # New validation package
    ├── MeasureValidator.java        # Entry point
    ├── MeasureConstraints.java      # Constraint name constants
    └── rules/
        ├── MeasureValidations.java      # @ValidationContext(Measure.class)
        ├── UnitValidations.java         # @ValidationContext(Unit.class)
        ├── DurationUnitValidations.java # @ValidationContext(DurationUnit.class)
        └── MeasuredTypeValidations.java # @ValidationContext(MeasuredType.class)
```

### Test Structure
```
model-test/src/test/java/hu/blackbelt/judo/meta/measure/runtime/
├── ValidatorType.java                     # EVL/JAVA enum
├── AbstractMeasureValidationTest.java     # Base class with runValidation()
├── MeasureValidationTest.java             # Parameterized tests
└── MeasureValidationPerformanceTest.java  # Performance comparison
```

## Risks / Trade-offs

### Risk: Validation Result Mismatch
**Risk:** Java validation produces different results than EVL.
**Mitigation:** Parameterized tests catch any discrepancy immediately.

### Risk: Framework Version Incompatibility
**Risk:** Zeta framework changes break validation.
**Mitigation:** Use specific SNAPSHOT version, test on update.

### Trade-off: Increased Test Time
**Trade-off:** Running both validators doubles test execution time.
**Acceptable:** Performance benefit in production outweighs test time increase.

## Migration Plan

### Phase 1: Infrastructure
1. Add Zeta dependencies to pom.xml
2. Create validation package structure
3. Create test infrastructure (ValidatorType, AbstractMeasureValidationTest)

### Phase 2: Validation Rules
4. Implement MeasureValidations (scan EVL, convert to Java)
5. Implement UnitValidations
6. Implement DurationUnitValidations
7. Implement MeasuredTypeValidations

### Phase 3: Testing
8. Modify existing tests to parameterized format
9. Add performance tests
10. Verify test parity

### Phase 4: Documentation
11. Convert AsciiDoc to Markdown
12. Convert PlantUML to Mermaid
13. Update AGENTS.md
14. Add validation documentation

### Rollback
- Remove validation package
- Revert pom.xml changes
- Revert test changes

## Open Questions

1. **Rackinspect model characteristics:** What specific structures from rackinspect should be replicated in performance tests? (Will analyze model files during implementation)

2. **Existing EVL rules:** The current EVL files appear minimal. Need to verify if there are additional rules in related files.
