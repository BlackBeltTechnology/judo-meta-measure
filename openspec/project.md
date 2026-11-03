# Project Context

## Purpose

**Judo Measure Meta** is an Eclipse/Tycho-based metamodel project that:
- Defines a comprehensive Measure metamodel via EMF/Ecore for SI units and measurements
- Generates Java code from the model using MWE2 workflows
- Provides both Eclipse UI and OSGi standalone runtime
- Supports the broader JUDO ecosystem for measurement definitions
- Distributes via both Maven Central and Eclipse P2 repositories

Based on https://en.wikipedia.org/wiki/International_System_of_Units[SI Standard] model extended with descriptions.

## Tech Stack

### Core Technologies
- **Java 21** - Primary language
- **Eclipse Modeling Framework (EMF)** 2.38.0+ - Metamodel foundation
- **Ecore** - Model definition language
- **MWE2** (Model Workflow Engine) 2.13.0 - Code generation workflows
- **Epsilon** 2.8.0 - Model validation (EVL) and object language (EOL)
- **Tycho** 4.0.13 - Eclipse plugin build

### Runtime
- **Apache Karaf** 4.4.7 - OSGi container
- **Apache Felix** 6.0.0 - OSGi bundle plugin

### Build & Testing
- **Maven** 3.9.4+ with wrapper
- **JUnit 5** - Unit testing
- **Pax Exam** 4.13.5 - OSGi integration testing

## Project Conventions

### Code Style
- Java 21 language features (records, pattern matching, sealed classes where applicable)
- Use Lombok for boilerplate reduction (`@Getter`, `@Setter`, `@Builder`, `@Slf4j`)
- EMF-generated code follows GenModel conventions
- Immutable objects preferred for validation results and cache keys
- Functional interfaces for validation rules and guards

### Architecture Patterns
- **EMF/Ecore patterns** for metamodel definition and manipulation
- **Annotation-based configuration** for validation rules (Zeta framework)
- **Functional interfaces** for validation logic (lambdas supported)
- **Registry pattern** for scanning and discovering validators
- **Dual validation** - Both EVL and Java validators run with same test cases

### Testing Strategy
- Unit tests in `model-test/` module using JUnit 5
- Parameterized tests run same cases against both EVL and Java validators
- Expected errors/warnings passed to validator for assertion
- Model fixtures created using EMF builders
- OSGi integration tests via Pax Exam in `osgi-itest/`

### Git Workflow
- **Main Branch:** `develop`
- **Versioning:** SNAPSHOT-based development (currently 1.0.2-SNAPSHOT)
- Feature branches for significant changes
- OpenSpec proposals for architectural changes

## Domain Context

### Measure Metamodel
The Measure metamodel defines:
- **Measure** - A physical quantity (e.g., Length, Mass, Time)
- **Unit** - A unit of measurement (e.g., meter, kilogram, second)
- **DurationUnit** - Time-based units with specific rate conversions
- **DerivedMeasure** - Measures composed from base measures via terms

### Key Validation Concepts
- **Constraint**: Error-level rule that must pass
- **Critique**: Warning-level rule (advisory)
- **Guard**: Condition that determines if rule should evaluate
- **Satisfies**: Dependency on another constraint's result (cached)
- **Context**: EClass type the rule applies to

## Important Constraints

### Build Constraints
- Tycho build requires Eclipse plugin structure
- OSGi bundle manifests must be maintained
- P2 update site structure for Eclipse distribution

### Validation Constraints
- EVL rules must remain functional (dual validation)
- Test parity: Java tests must mirror EVL tests exactly
- Cache results for `satisfies()` calls to avoid re-evaluation

## External Dependencies

### Eclipse Platform
- EMF Runtime 2.38.0+

### Zeta Validation Framework
- `hu.blackbelt.judo.zeta:hu.blackbelt.judo.zeta.validation-core` - Validation annotations and runtime
- `hu.blackbelt.judo.zeta:hu.blackbelt.judo.zeta.zeta-annotations` - Annotation definitions
- `hu.blackbelt.judo.zeta:hu.blackbelt.judo.zeta.zeta-common` - Common utilities

### Epsilon Runtime
- EVL (Epsilon Validation Language) for model validation
- EOL (Epsilon Object Language) for helper operations
- EMC (Epsilon Model Connectivity) for EMF integration

## Module Overview

| Module | Purpose |
|--------|---------|
| `model/` | Core Measure metamodel, EMF code, validation rules |
| `model-test/` | Unit tests for metamodel and validation |
| `osgi/` | OSGi bundle repackaging |
| `osgi-itest/` | OSGi integration tests |
| `feature/` | Eclipse feature packaging |
| `site/` | P2 update site |

## Active Changes

See `openspec/changes/` for in-progress proposals:
- `add-zeta-validation/` - Native Java validation framework using Zeta
