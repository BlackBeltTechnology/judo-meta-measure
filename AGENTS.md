# judo-meta-measure - Project Documentation

## Project Overview


**Repository:** BlackBeltTechnology/judo-meta-measure
**License:** Eclipse Public License 2.0 (EPL-2.0)
**Java Version:** 21
**Build System:** Maven 3.9.4+ with Tycho 4.0.13 (Eclipse plugin builds)

1. Defines an EMF/Ecore metamodel for the SI (International System of Units) standard, representing base measures, derived measures, units, and conversion rates
2. Generates Java classes, builders, helpers, and runtime model support from the Ecore definition via MWE2 workflows
3. Packages the metamodel as both an Eclipse plugin (via Tycho/P2) and a standard OSGi bundle (via Felix Maven Bundle Plugin) for use in JUDO transformation pipelines
4. Provides Epsilon EVL-based model validation and an OSGi bundle tracker for auto-discovery of measure models at runtime
5. Part of the [judo-community](https://github.com/BlackBeltTechnology/judo-community) ecosystem

## Code Instructions

1. First think through the problem, read the codebase for relevant files.
2. Before you make any major changes, check in with me and I will verify the plan.
3. Please every step of the way just give me a high level explanation of what changes you made.
4. Make every task and code change you do as simple as possible. We want to avoid making any massive or complex changes. Every change should impact as little code as possible. Everything is about simplicity.
5. Maintain a documentation file that describes how the architecture of the app works inside and out.
6. Never speculate about code you have not opened. If the user references a specific file, you MUST read the file before answering. Make sure to investigate and read relevant files BEFORE answering questions about the codebase. Never make any claims about code before investigating unless you are certain of the correct answer - give grounded and hallucination-free answers.
7. For implementation use TDD (Test-Driven Development): write or update tests first to define the expected behaviour, verify they fail, then write the minimal implementation to make them pass.
8. Use DRY (Don't Repeat Yourself): extract reusable logic into separate classes, utilities, or components. If the same pattern appears in multiple places, refactor it into a shared helper.

## Directory Structure

```
judo-meta-measure/
├── model/                  # Core metamodel (eclipse-plugin)
│   ├── model/              # measure.ecore + measure.genmodel
│   ├── src/main/java/      # Hand-written code (MeasureUtils, validator, activator)
│   ├── src/main/epsilon/   # EVL validation scripts
│   ├── src/workflow/        # MWE2 code generation workflow
│   ├── src-gen/            # EMF-generated Java (DO NOT EDIT)
│   └── META-INF/           # OSGi manifest
├── model-test/             # JUnit 5 unit tests
├── osgi/                   # OSGi bundle wrapper
│   └── src/main/java/      # MeasureModelBundleTracker
├── osgi-itest/             # Pax Exam / Karaf integration tests
├── feature/                # Eclipse feature definition
├── site/                   # P2 update site
├── .github/workflows/      # CI/CD pipelines
└── .mvn/                   # Maven wrapper + extensions
```

## Core Modules

### Model Layer

| Module | Type | Purpose |
|--------|------|---------|
| `model/` | eclipse-plugin | Ecore metamodel definition (`measure.ecore`), EMF-generated Java classes in `src-gen/`, hand-written utilities in `src/main/java/`, Epsilon validation scripts in `src/main/epsilon/validations/`, and MWE2 code generation workflow |

### Test Layer

| Module | Type | Purpose |
|--------|------|---------|
| `model-test/` | jar | JUnit 5 tests for model resource creation (`MeasureExecutionContextTest`), XMI ID uniqueness validation (`MeasureUtilsTest`), and Epsilon constraint execution (`MeasureValidationTest`) |
| `osgi-itest/` | jar | Pax Exam integration tests deploying the OSGi bundle into a Karaf 4.4.7 container, verifying bundle loading and model validation via `MeasureModelLoadITest` |

### Packaging Layer

| Module | Type | Purpose |
|--------|------|---------|
| `osgi/` | bundle | Repackages the model as a standard OSGi bundle using Felix Maven Bundle Plugin. Exports all `hu.blackbelt.judo.meta.measure.*` packages. Includes `MeasureModelBundleTracker` for auto-discovery of measure models via the `Measure-Models` manifest header |
| `feature/` | eclipse-feature | Eclipse feature descriptor for plugin installation via P2 |
| `site/` | eclipse-repository | P2 update site repository for Eclipse marketplace distribution |

## Technology Stack

### Core Technologies

- **EMF/Ecore 2.38.0** — metamodel definition and Java code generation
- **Tycho 4.0.13** — Maven plugin for building Eclipse plugins, features, and P2 sites
- **Epsilon Runtime 2.8.0** — model validation via EVL (Epsilon Validation Language) scripts
- **MWE2 2.13.0** — model workflow engine for code generation (EcoreGenerator → Helpers → Builders → RuntimeModel)
- **OSGi 7.0.0** — modular runtime with bundle tracking and service registration
- **Lombok 1.18.34** — annotation processing (non-Eclipse modules only; Tycho incompatible)

### Build & Quality

- **Maven 3.9.4+** with Maven wrapper (`mvnw`)
- **JUnit 5.9.1** for unit tests, **Pax Exam 4.13.5** with **Karaf 4.4.7** for OSGi integration tests
- **JaCoCo 0.8.12** for code coverage
- **SonarQube** via sonar-maven-plugin 3.9.1 for quality metrics
- **Flatten Maven Plugin 1.3.0** for CI-friendly `${revision}` version resolution

## Build Commands

```bash
# Full build with tests
mvn clean install

# Build without tests
mvn clean install -DskipTests

# Run unit tests only
mvn clean test

# Run a single test class
mvn -pl model-test -Dtest=MeasureValidationTest clean test

# Run full verification including OSGi integration tests
mvn clean verify

# Update Eclipse site category versions
mvn clean install -P update-category-versions -f site/pom.xml
```

> **Note:** Maven wrapper is available: `./mvnw clean install`

### Maven Profiles

| Profile | Purpose |
|---------|---------|
| `modules` | Activates all submodule builds (active by default unless `-DskipModules=true`) |
| `sign-artifacts` | GPG-signs artifacts using sign-maven-plugin |
| `release-dummy` | Deploys to local `/tmp/` filesystem for testing |
| `release-judong` | Deploys to Judo NG Nexus (`nexus.judo.technology`) |
| `release-central` | Deploys to Maven Central via Sonatype OSSRH |
| `generate-github-asciidoc-diagrams` | Generates PNG diagrams from AsciiDoc via AsciidoctorJ |
| `update-source-code-license` | Updates EPL-2.0 license headers in source files |

## Key Configuration Files

| File | Purpose |
|------|---------|
| `pom.xml` | Parent POM: properties, dependency management, build plugins, profiles |
| `model/model/measure.ecore` | Ecore metamodel definition (source of truth for all generated code) |
| `model/model/measure.genmodel` | EMF code generation configuration |
| `model/src/workflow/generateModel.mwe2` | MWE2 workflow orchestrating the code generation pipeline |
| `model/src/main/epsilon/validations/measure.evl` | Epsilon validation rules for measure models |
| `model/META-INF/MANIFEST.MF` | Eclipse plugin / OSGi bundle manifest |
| `model/build.properties` | Eclipse build configuration |
| `.mvn/extensions.xml` | Maven wagon extensions (file, webdav protocols) |
| `logback-test.xml` | Test logging configuration (INFO level, console appender) |
| `.github/workflows/build.yml` | Main CI/CD pipeline (build, test, deploy, release) |

## Development Environment

**Required:**
- Java 21 JDK
- Maven 3.9.4+

**For Eclipse plugin development:**
- Eclipse IDE with m2e, Epsilon, Modeling Tools, XTend, XText, MWE/MWE2 plugins
- Install the plugin via P2 sites for editor support

**For OSGi testing:**
- No additional setup — Pax Exam provisions Karaf automatically during `mvn verify`

## Git Workflow

- **Main Branch:** `develop`
- **Release Branch:** `master` (latest released version)
- **Versioning:** `${revision}` = `1.0.2-SNAPSHOT` (CI-friendly, resolved by flatten plugin)
- **Branch naming:** `feature/JNG-XXXX_description`, `bugfix/JNG-XXXX_*`, `support/JNG-XXXX_*`, `hotfix/JNG-XXXX_*`
- **Rule:** Every commit must reference a JIRA ticket (`JNG-xxx`)
- **CI/CD:** GitHub Actions with automated versioning, Nexus deployment, P2 site publishing, and GitHub releases

## Important Notes

1. **Never hand-edit files in `model/src-gen/`** — these are generated from `measure.ecore` via the MWE2 workflow. Hand-written code belongs in `model/src/main/java/`
2. **Tycho and Lombok are incompatible** — Lombok is only used in non-Eclipse modules (`model-test`, `osgi`, `osgi-itest`). Eclipse plugin modules (`model`) use only generated code
3. **Dual packaging:** The `model` module produces an Eclipse plugin (Tycho), while `osgi` repackages it as a standard OSGi bundle (Felix). Both export the same API but target different runtimes
4. **Version duality:** Maven uses `-SNAPSHOT` suffixes while Eclipse uses `.qualifier`. The Tycho Versions Plugin reconciles them during builds, producing versions like `1.0.2.20260225_143000_abc123_develop`
5. **Epsilon validation scripts** in `model/src/main/epsilon/validations/` define model constraints. The `MeasureEpsilonValidator` class handles script location resolution across JAR, OSGi bundle, and filesystem environments
6. **OSGi auto-discovery:** Bundles containing measure models declare them via the `Measure-Models` manifest header. The `MeasureModelBundleTracker` in the `osgi` module automatically loads and registers them as OSGi services
7. **The `measure.evl` validation file is currently empty** — validation rules should be added there as model constraints are defined

## Related Documentation

- [README.md](README.md) — Project introduction and quick start
- [CONTRIBUTING.md](CONTRIBUTING.md) — Development setup, troubleshooting, and submission guidelines
- [.github/CIFLOW.md](.github/CIFLOW.md) — Detailed CI/CD pipeline flows and branching strategy
- [judo-community](https://github.com/BlackBeltTechnology/judo-community) — Parent aggregator project
