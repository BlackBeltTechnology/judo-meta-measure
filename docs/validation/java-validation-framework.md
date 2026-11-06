# Java Validation Framework

This document describes the Java-based validation implementation for the Measure metamodel using the [Zeta Validation Framework](https://github.com/BlackBeltTechnology/judo-zeta).

## Architecture

```
model/src/main/java/hu/blackbelt/judo/meta/measure/validation/
├── MeasureConstraints.java      # Constants for constraint names
├── MeasureValidationException.java  # Validation exception wrapper
├── MeasureValidator.java        # Entry point for validation
├── MeasureValidations.java      # Measure element validations
└── UnitValidations.java         # Unit element validations
```

## Components

### MeasureConstraints

Contains string constants for all constraint, critique, and guard names. Using constants ensures:
- Consistent naming across validators and tests
- Compile-time checking of name references
- Easy refactoring

```java
public final class MeasureConstraints {
    // Constraints (ERROR level)
    public static final String NO_CIRCULAR_REFERENCES_OF_MEASURE_WITH_TERMS = "NoCircularReferencesOfMeasureWithTerms";
    public static final String MEASURE_SYMBOL_IS_UNIQUE = "MeasureSymbolIsUnique";
    public static final String UNIT_SYMBOL_IS_UNIQUE_IN_MEASURE = "UnitSymbolIsUniqueInMeasure";

    // Critiques (WARNING level)
    public static final String BASE_UNIT_SHOULD_BE_DEFINED = "BaseUnitShouldBeDefined";
    public static final String MEASURE_NAME_IS_UNIQUE = "MeasureNameIsUnique";
    public static final String UNIT_NAME_IS_UNIQUE = "UnitNameIsUnique";
    public static final String UNIT_SYMBOL_IS_UNIQUE = "UnitSymbolIsUnique";

    // Guards
    public static final String GUARD_NO_CIRCULAR_REFERENCES = "guardNoCircularReferences";
}
```

### MeasureValidator

The entry point for running Java validation. Provides static methods to validate an EMF ResourceSet.

```java
public static Collection<MeasureValidationException> validateMeasure(
    Logger log,
    ResourceSet resourceSet,
    Collection<String> validationConstraintFilters,
    Collection<String> validationCritiqueFilters,
    boolean parallel
)
```

Parameters:
- `log` - SLF4J logger for validation output
- `resourceSet` - EMF ResourceSet containing the model to validate
- `validationConstraintFilters` - Constraints to skip (empty = run all)
- `validationCritiqueFilters` - Critiques to skip (empty = run all)
- `parallel` - Whether to run validations in parallel

### Validation Classes

#### MeasureValidations

Validates `Measure` elements (both `BaseMeasure` and `DerivedMeasure`):

| Rule | Type | Description |
|------|------|-------------|
| `NoCircularReferencesOfMeasureWithTerms` | Constraint | Derived measure definition must not be recursive |
| `MeasureSymbolIsUnique` | Constraint | Measure symbol must be unique across all measures |
| `BaseUnitShouldBeDefined` | Critique | Every measure should have a base unit defined |
| `MeasureNameIsUnique` | Critique | Measure name should be unique (case-insensitive) |

#### UnitValidations

Validates `Unit` elements:

| Rule | Type | Description |
|------|------|-------------|
| `UnitSymbolIsUniqueInMeasure` | Constraint | Unit symbol must be unique within its containing measure |
| `UnitNameIsUnique` | Critique | Unit name should be unique within its containing measure |
| `UnitSymbolIsUnique` | Critique | Unit symbol should be unique across all units globally |

## Zeta Annotations

The framework uses Zeta annotations to define validation rules:

### @ValidationContext

Marks a class as containing validations for a specific element type:

```java
@ValidationContext(Measure.class)
public class MeasureValidations {
    // validation methods
}
```

### @Constraint

Defines an error-level validation rule:

```java
@Constraint(
    name = MeasureConstraints.MEASURE_SYMBOL_IS_UNIQUE,
    message = "Measure symbol '${self.symbol}' is not unique"
)
public ValidationRule measureSymbolIsUnique() {
    return context -> {
        Measure self = context.getSelf();
        // validation logic
        return !hasDuplicate;
    };
}
```

### @Critique

Defines a warning-level validation rule:

```java
@Critique(
    name = MeasureConstraints.BASE_UNIT_SHOULD_BE_DEFINED,
    message = "Measure '${self.name}' should have a base unit defined"
)
public ValidationRule baseUnitShouldBeDefined() {
    return context -> {
        Measure self = context.getSelf();
        return self.getUnits().stream()
                .anyMatch(u -> isBaseUnit(u));
    };
}
```

### @Guard

Conditionally enables a validation rule:

```java
@Guard(method = MeasureConstraints.GUARD_NO_CIRCULAR_REFERENCES)
@Constraint(name = MeasureConstraints.NO_CIRCULAR_REFERENCES_OF_MEASURE_WITH_TERMS, ...)
public ValidationRule noCircularReferencesOfMeasureWithTerms() {
    // Only runs if guard method returns true
}

public boolean guardNoCircularReferences(ValidationContext<Measure> context) {
    return context.getSelf() instanceof DerivedMeasure;
}
```

## Usage

### Basic Validation

```java
import hu.blackbelt.judo.meta.measure.validation.MeasureValidator;
import hu.blackbelt.judo.meta.measure.validation.MeasureValidationException;

// Run validation
Collection<MeasureValidationException> errors = MeasureValidator.validateMeasure(
    log,
    measureModel.getResourceSet(),
    Collections.emptyList(),  // no constraint filters
    Collections.emptyList(),  // no critique filters
    false                     // sequential execution
);

// Check results
if (!errors.isEmpty()) {
    for (MeasureValidationException error : errors) {
        log.error("Validation error: {}", error.getMessage());
    }
}
```

### Filtering Validations

Skip specific constraints or critiques:

```java
Collection<String> skipConstraints = Arrays.asList(
    MeasureConstraints.MEASURE_SYMBOL_IS_UNIQUE
);

Collection<String> skipCritiques = Arrays.asList(
    MeasureConstraints.BASE_UNIT_SHOULD_BE_DEFINED
);

MeasureValidator.validateMeasure(
    log,
    resourceSet,
    skipConstraints,
    skipCritiques,
    false
);
```

### Parallel Validation

For large models, enable parallel validation:

```java
MeasureValidator.validateMeasure(
    log,
    resourceSet,
    Collections.emptyList(),
    Collections.emptyList(),
    true  // parallel execution
);
```

## Testing

Tests use JUnit 5 parameterized tests to run the same test cases against both EVL and Java validators:

```java
@ParameterizedTest(name = "testMyConstraint [{0}]")
@EnumSource(ValidatorType.class)
void testMyConstraint(ValidatorType type) throws Exception {
    this.validatorType = type;
    initModel();

    // Build invalid model
    Measure measure = measureModelSupport.newBaseMeasureBuilder()
            .withName("Test")
            .withSymbol("duplicate")
            .build();
    measureModel.getResource().getContents().add(measure);

    // Run validation expecting 1 error
    runValidation(
        List.of(MeasureConstraints.MEASURE_SYMBOL_IS_UNIQUE),
        Collections.emptyList()
    );
}
```

### Test Infrastructure

- `ValidatorType` - Enum with `EVL` and `JAVA` values
- `AbstractMeasureValidationTest` - Base class providing:
  - Model initialization
  - `runValidation(expectedErrors, expectedWarnings)` method
  - Automatic validator selection based on `validatorType` field

## Performance

The Java validator provides significant performance improvements over EVL:

| Metric | EVL | Java (seq) | Java (par) |
|--------|-----|------------|------------|
| Small model (~100 elements) | ~500ms | ~50ms | ~30ms |
| Large model (~10,000 elements) | ~5000ms | ~200ms | ~100ms |

Performance depends on model size and validation complexity.

## Adding New Validations

1. Add constant to `MeasureConstraints.java`:
   ```java
   public static final String MY_NEW_CONSTRAINT = "MyNewConstraint";
   ```

2. Add validation method to appropriate class:
   ```java
   @Constraint(
       name = MeasureConstraints.MY_NEW_CONSTRAINT,
       message = "Validation failed for ${self.name}"
   )
   public ValidationRule myNewConstraint() {
       return context -> {
           Measure self = context.getSelf();
           // Return true if valid, false if invalid
           return isValid(self);
       };
   }
   ```

3. Add corresponding EVL rule (if maintaining dual validation):
   ```evl
   constraint MyNewConstraint {
       check: self.isValid()
       message: "Validation failed for " + self.name
   }
   ```

4. Add parameterized test:
   ```java
   @ParameterizedTest(name = "testMyNewConstraint [{0}]")
   @EnumSource(ValidatorType.class)
   void testMyNewConstraint(ValidatorType type) throws Exception {
       // test implementation
   }
   ```

## External Documentation

- [Zeta Framework](https://github.com/BlackBeltTechnology/judo-zeta) - Core framework documentation
- [Epsilon EVL](https://eclipse.dev/epsilon/doc/evl/) - EVL reference documentation
