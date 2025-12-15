<!-- OPENSPEC:START -->
# OpenSpec Instructions

These instructions are for AI assistants working in this project.

Always open `@/openspec/AGENTS.md` when the request:
- Mentions planning or proposals (words like proposal, spec, change, plan)
- Introduces new capabilities, breaking changes, architecture shifts, or big performance/security work
- Sounds ambiguous and you need the authoritative spec before coding

Use `@/openspec/AGENTS.md` to learn:
- How to create and apply change proposals
- Spec format and conventions
- Project structure and guidelines

Keep this managed block so 'openspec update' can refresh the instructions.

<!-- OPENSPEC:END -->

# Judo Measure Meta - Project Documentation

## Project Overview

**Repository:** BlackBeltTechnology/judo-meta-measure
**License:** Eclipse Public License 2.0 (EPL-2.0)
**Java Version:** 21
**Build System:** Maven 3.9.4+ with Tycho (Eclipse build tooling)

This is an Eclipse/Tycho-based metamodel project that:
1. **Defines** a comprehensive Measure metamodel via EMF/Ecore for SI units
2. **Generates** Java code from the model using MWE2 workflows
3. **Provides** both Eclipse UI and OSGi standalone runtime
4. **Distributes** via both Maven Central and Eclipse P2 repositories

Based on https://en.wikipedia.org/wiki/International_System_of_Units[SI Standard] model extended with descriptions.

## Directory Structure

```
judo-meta-measure/
├── model/                          # Core Measure metamodel (Ecore)
├── model-test/                     # Unit tests for metamodel
├── osgi/                           # OSGi bundle repackaging
├── osgi-itest/                     # OSGi integration tests (Pax Exam)
├── feature/                        # Eclipse feature (model)
├── site/                           # Eclipse P2 update site
└── openspec/                       # OpenSpec change management
```

## Core Modules

### Model Definition Layer

| Module | Type | Purpose |
|--------|------|---------|
| `model/` | eclipse-plugin | Core Measure metamodel via Ecore (`measure.ecore`). Generates EMF code, builders, helpers. Contains Epsilon validation rules. |
| `model-test/` | test | Unit tests for Measure metamodel using JUnit 5 and Epsilon runtime |

### Runtime/OSGi Layer

| Module | Type | Purpose |
|--------|------|---------|
| `osgi/` | bundle | Repackages model for OSGi environments using Apache Felix Bundle Plugin |
| `osgi-itest/` | test | Pax Exam integration tests for Karaf container (4.4.7) |

### Distribution Layer

| Module | Type | Purpose |
|--------|------|---------|
| `feature/` | eclipse-feature | Bundles model and plugins |
| `site/` | eclipse-repository | P2 update site for Eclipse distribution |

## Measure Metamodel Structure

The core metamodel (`model/model/measure.ecore`) defines:

| Element | Purpose |
|---------|---------|
| `Measure` | A physical quantity (e.g., Length, Mass, Time) |
| `Unit` | A unit of measurement (e.g., meter, kilogram, second) |
| `DurationUnit` | Time-based units with specific rate conversions |
| `DerivedMeasure` | Measures composed from base measures via terms |
| `MeasureTerm` | Component of a derived measure with exponent |
| `MeasuredType` | Type definition with measure and scale |

**Validation Rules (Dual Validation):**
- **EVL (Epsilon):** Located in `model/src/main/epsilon/validations/` using Epsilon Validation Language
- **Java Validation Framework:** Located in `model/src/main/java/hu/blackbelt/judo/meta/measure/validation/` using [Zeta](https://github.com/BlackBeltTechnology/judo-zeta) framework

Both validators run in parallel to ensure parity. See [Validation Documentation](docs/validation/README.md) for details.

## Technology Stack

### Core Technologies
- **Eclipse Modeling Framework (EMF)** 2.38.0+ - Metamodel foundation
- **Ecore** - Model definition language
- **MWE2** (Model Workflow Engine) 2.13.0 - Code generation workflows
- **Epsilon** 2.8.0 - Model validation and transformation
- **Tycho** 4.0.13 - Eclipse plugin build

### Runtime
- **Apache Karaf** 4.4.7 - OSGi container
- **Apache Felix** 6.0.0 - OSGi bundle plugin
- **Pax Exam** 4.13.5 - OSGi testing

### Build & Quality
- **Maven** 3.9.4+ with wrapper
- **JaCoCo** 0.8.12 - Code coverage
- **SonarQube** 3.9.1 - Code quality
- **Lombok** 1.18.34 - Annotation processing

## Build Commands

```bash
# Standard build
mvn clean install
# or with wrapper
./mvnw clean install

# Memory requirements (configured in .mvn/jvm.config)
# -Xms1024m -Xmx2048m
```

### Maven Profiles

| Profile | Purpose |
|---------|---------|
| `modules` | Includes all submodules (default) |
| `sign-artifacts` | GPG signing for release |
| `release-central` | Maven Central deployment |
| `release-judong` | Internal Judo repository |

## Code Generation Flow

1. **MWE2 Workflow** (`model/src/workflow/generateModel.mwe2`)
   - Generates EMF code from `measure.ecore`
   - Produces GenModel-based Java classes
   - Generates builders and helpers

2. **Model Compilation**
   - Tycho compiles eclipse-plugin modules
   - OSGi bundle compilation with Felix

3. **Feature/Site Building**
   - P2 metadata generation
   - Feature packaging
   - Update site assembly

## Key Configuration Files

| File | Purpose |
|------|---------|
| `pom.xml` | Parent POM with module definitions and plugin management |
| `.mvn/jvm.config` | JVM arguments for Maven build |
| `.mvn/extensions.xml` | Maven extensions |
| `model/model/measure.ecore` | Core metamodel definition |
| `model/model/measure.genmodel` | EMF code generation model |

## Development Environment

**Required:**
- Java 21 JDK
- Maven 3.9.4+
- Eclipse IDE with:
  - m2e (Maven integration)
  - Epsilon plugin
  - Modeling tools

## Git Workflow

- **Main Branch:** `develop`
- **Versioning:** SNAPSHOT-based development (currently 1.0.2-SNAPSHOT)
- **Version Placeholder:** `$VERSION_PLACEHOLDER$` in model metadata
- **Release Process:** CI/CD with Maven Central and P2 deployment

## Important Notes

1. **Understand EMF/Ecore patterns** before modifying model code
2. **Respect Tycho build constraints** when modifying Eclipse plugins
3. **Validation rules** - Dual validation with EVL and Java:
   - **EVL (Epsilon):** `model/src/main/epsilon/validations/`
   - **Java (Zeta):** `model/src/main/java/hu/blackbelt/judo/meta/measure/validation/`
   - Tests run both validators via `@EnumSource(ValidatorType.class)`
4. **Use OpenSpec for significant changes** - See `openspec/AGENTS.md` for proposal workflow

## Related Documentation

- `README.md` - Project overview
- `CONTRIBUTING.md` - Contribution guidelines
- `.github/CIFLOW.md` - CI/CD workflow documentation
- `docs/validation/README.md` - Validation overview
- `docs/validation/java-validation-framework.md` - Java validation details
- `openspec/AGENTS.md` - OpenSpec workflow for spec-driven development
- `openspec/project.md` - Project conventions for OpenSpec

## Active Changes

See `openspec/changes/` for in-progress proposals:
- `add-zeta-validation/` - Native Java validation framework using Zeta (dual validation)
