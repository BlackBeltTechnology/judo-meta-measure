# Tasks: Add Zeta-Based Java Validation Framework

## 1. Build Configuration

- [ ] 1.1 Add `judo-zeta-version` property to parent pom.xml (SNAPSHOT version)
- [ ] 1.2 Add Zeta dependencies to model/pom.xml:
  - `hu.blackbelt.judo.zeta:hu.blackbelt.judo.zeta.zeta-annotations`
  - `hu.blackbelt.judo.zeta:hu.blackbelt.judo.zeta.validation-core`
  - `hu.blackbelt.judo.zeta:hu.blackbelt.judo.zeta.zeta-common`
- [ ] 1.3 Add Zeta dependencies to model-test/pom.xml (test scope)
- [ ] 1.4 Update any existing Zeta references to use `${judo-zeta-version}` property

## 2. Core Framework

- [ ] 2.1 Create `validation/` package in model module
- [ ] 2.2 Create `MeasureConstraints.java` - Constants for all constraint names
- [ ] 2.3 Create `MeasureValidator.java` entry point class
  - Static `validateMeasure(Logger, MeasureModel)` method
  - Support for expected errors/warnings (for testing)
  - Support for parallel execution flag

## 3. Validation Rules - Scan and Convert EVL

### 3.1 Analyze EVL Files
- [ ] 3.1.1 Scan `model/src/main/epsilon/validations/measure.evl` for all constraints
- [ ] 3.1.2 Scan `model/src/main/epsilon/validations/measure-plugin-validation.evl`
- [ ] 3.1.3 Document all constraint names, guards, and satisfies dependencies

### 3.2 Measure Validations
- [ ] 3.2.1 Create `rules/MeasureValidations.java`
  - `@ValidationContext(Measure.class)`
  - All measure-specific constraints and critiques

### 3.3 Unit Validations
- [ ] 3.3.1 Create `rules/UnitValidations.java`
  - `@ValidationContext(Unit.class)`
  - Unit name uniqueness
  - Unit symbol uniqueness

### 3.4 Duration Unit Validations
- [ ] 3.4.1 Create `rules/DurationUnitValidations.java`
  - `@ValidationContext(DurationUnit.class)`
  - Rate validation rules (microsecond, millisecond, second, minute, hour, day, week)

### 3.5 MeasuredType Validations
- [ ] 3.5.1 Create `rules/MeasuredTypeValidations.java`
  - `@ValidationContext(MeasuredType.class)`
  - Scale validation

## 4. Test Infrastructure

- [ ] 4.1 Create `ValidatorType.java` enum (EVL, JAVA)
- [ ] 4.2 Create `AbstractMeasureValidationTest.java` base class
  - `initModel()` method
  - `runValidation(expectedErrors, expectedWarnings)` method
  - Support for both EVL and Java validators
- [ ] 4.3 Modify `MeasureValidationTest.java` to use parameterized tests
  - Convert existing tests to `@ParameterizedTest` with `@EnumSource(ValidatorType.class)`
  - Ensure all tests pass for both EVL and JAVA

## 5. Performance Testing

- [ ] 5.1 Analyze rackinspect model characteristics
  - Read `/Users/robson/Project/rackinspect/application/model/target/generated-resources/model/rackinspect-measure.model`
  - Extract model structure patterns
- [ ] 5.2 Create `MeasureValidationPerformanceTest.java`
  - Generate 10,000 element model with rackinspect-like characteristics
  - Compare EVL vs Java Sequential vs Java Parallel
  - Document performance results

## 6. Documentation

### 6.1 Convert AsciiDoc to Markdown
- [ ] 6.1.1 Convert `README.adoc` to `README.md`
- [ ] 6.1.2 Convert `CONTRIBUTING.adoc` to `CONTRIBUTING.md`
- [ ] 6.1.3 Convert `.github/CIFLOW.adoc` to `.github/CIFLOW.md`
- [ ] 6.1.4 Convert any PlantUML diagrams to Mermaid format
- [ ] 6.1.5 Skip files under `pages/` directory (if any exist)

### 6.2 Update Documentation Content
- [ ] 6.2.1 Update AGENTS.md with Java validation information
- [ ] 6.2.2 Create `docs/validation/README.md` - Validation overview
- [ ] 6.2.3 Create `docs/validation/java-validation-framework.md` - Detailed documentation
- [ ] 6.2.4 Add references to Zeta documentation (don't copy Zeta docs)
- [ ] 6.2.5 Document all implemented validation rules

## 7. Final Verification

- [ ] 7.1 Run all tests with `mvn clean test`
- [ ] 7.2 Verify EVL and Java validators produce identical results
- [ ] 7.3 Review constraint name constants are used consistently
- [ ] 7.4 Verify performance test shows expected improvement
- [ ] 7.5 Run build to ensure OSGi bundle exports are correct

## Implementation Notes

### Constraint Name Constants Pattern
```java
public final class MeasureConstraints {
    // Constraints (ERROR severity)
    public static final String NO_CIRCULAR_REFERENCES = "NoCircularReferencesOfMeasureWithTerms";
    public static final String MEASURE_SYMBOL_IS_UNIQUE = "MeasureSymbolIsUnique";

    // Critiques (WARNING severity)
    public static final String BASE_UNIT_SHOULD_BE_DEFINED = "BaseUnitShouldBeDefined";
    public static final String MEASURE_NAME_IS_UNIQUE = "MeasureNameIsUnique";

    private MeasureConstraints() {} // Prevent instantiation
}
```

### Validation Class Pattern
```java
@ValidationContext(Measure.class)
public class MeasureValidations {

    @Constraint(
        name = MeasureConstraints.NO_CIRCULAR_REFERENCES,
        message = "Circular reference detected in measure {element.name}"
    )
    public ValidationRule noCircularReferences() {
        return (element, ctx) -> {
            Measure measure = (Measure) element;
            // validation logic
            return ValidationResult.pass();
        };
    }

    @Critique(
        name = MeasureConstraints.BASE_UNIT_SHOULD_BE_DEFINED,
        message = "Measure {element.name} should have a base unit defined"
    )
    public ValidationRule baseUnitShouldBeDefined() {
        return (element, ctx) -> {
            Measure measure = (Measure) element;
            // validation logic
            return ValidationResult.pass();
        };
    }
}
```

### Test Pattern
```java
public class MeasureValidationTest extends AbstractMeasureValidationTest {

    @ParameterizedTest(name = "testNoCircularReferences [{0}]")
    @EnumSource(ValidatorType.class)
    void testNoCircularReferences(ValidatorType type) throws Exception {
        this.validatorType = type;
        initModel();

        // Create model with circular reference
        // ...

        runValidation(
            List.of(MeasureConstraints.NO_CIRCULAR_REFERENCES),
            null // No expected warnings
        );
    }
}
```
