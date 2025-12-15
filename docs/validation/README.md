# Measure Model Validation

This project supports dual validation using both EVL (Epsilon Validation Language) and a native Java validation framework.

## Overview

| Aspect | EVL Validation | Java Validation |
|--------|---------------|-----------------|
| Location | `model/src/main/epsilon/validations/` | `model/src/main/java/.../validation/` |
| Performance | Slower (script interpretation) | Faster (native execution) |
| IDE Support | Limited | Full (debugging, refactoring) |
| Framework | Epsilon | Zeta |

## Validation Rules

### Measure Validations

| Rule Name | Type | Description |
|-----------|------|-------------|
| `NoCircularReferencesOfMeasureWithTerms` | Constraint (ERROR) | Derived measure definition must not be recursive |
| `MeasureSymbolIsUnique` | Constraint (ERROR) | Measure symbol must be unique across all measures |
| `BaseUnitShouldBeDefined` | Critique (WARNING) | Every measure should have a base unit defined |
| `MeasureNameIsUnique` | Critique (WARNING) | Measure name should be unique (case-insensitive) |

### Unit Validations

| Rule Name | Type | Description |
|-----------|------|-------------|
| `UnitSymbolIsUniqueInMeasure` | Constraint (ERROR) | Unit symbol must be unique within its containing measure |
| `UnitNameIsUnique` | Critique (WARNING) | Unit name should be unique within its containing measure |
| `UnitSymbolIsUnique` | Critique (WARNING) | Unit symbol should be unique across all units globally |

## Testing

Both EVL and Java validators are tested together using parameterized tests. Each test case runs against both validators to ensure parity.

```java
@ParameterizedTest(name = "testMyConstraint [{0}]")
@EnumSource(ValidatorType.class)
void testMyConstraint(ValidatorType type) throws Exception {
    this.validatorType = type;
    initModel();
    // ... build model ...
    runValidation(expectedErrors, expectedWarnings);
}
```

## Documentation

- [Java Validation Framework](java-validation-framework.md) - Detailed Java validation documentation
- [Zeta Framework](https://github.com/BlackBeltTechnology/judo-zeta) - External Zeta documentation
