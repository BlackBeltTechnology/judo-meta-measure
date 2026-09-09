# judo-meta-measure — module agent doctrine

## Module purpose

`judo-meta-measure` is the estate's authority on *how a measured quantity is
expressed and converted*: it owns the `measure` Ecore metamodel
(`nsURI http://blackbelt.hu/judo/meta/measure`) so that "this attribute is a
duration in minutes" is a typed model fact rather than a naming convention. A
`BaseMeasure` (mass, length, time) names a dimension; a `DerivedMeasure` defines
its dimension as a product of `BaseMeasureTerm`s — a `baseMeasure` raised to an
integer `exponent` — which is what makes `velocity = length¹ · time⁻¹`
machine-checkable instead of documentation. Each `Measure` contains `Unit`s, and
a unit carries its conversion rule as an exact `rateDividend`/`rateDivisor`
`BigDecimal` pair relative to the measure's base unit, so unit conversion in
generated applications is rational arithmetic with no floating-point drift. The
`DurationUnit`/`DurationType` pair is the one calendar-aware exception: a
duration unit additionally declares which of `nanosecond … year` it means, so
downstream code can tell an exactly-convertible unit (second, minute) from a
calendar-dependent one (month, year).

Who validates against it: the EVL rules in
`model/src/main/epsilon/validations/measure.evl` and the equivalent Zeta Java
rules (`MeasureValidations`, `UnitValidations`) enforce the invariants a
conversion engine depends on — derived-measure term graphs must be acyclic,
measure symbols globally unique, unit symbols unique inside their measure — and
raise critiques where a model is merely suspect (no base unit with
`rateDividend == rateDivisor`, duplicate case-insensitive names, globally
duplicated unit symbols). `MeasureEpsilonValidator` resolves those scripts
across JAR, OSGi-bundle and filesystem layouts; `MeasureUtils` loads/queries
measure resources and enforces XMI id uniqueness.

What it ships: the EMF-generated Java API (interfaces, `*Impl`, factory,
package, builders, helpers — regenerated from `measure.genmodel` by the MWE2
workflow in `model/src/workflow/`), the hand-written runtime under
`model/src/main/java/`, and three delivery shapes of the same artifact — an
Eclipse plugin, an installable feature plus P2 update site, and a standalone
Felix OSGi bundle for Karaf.

**Repository:** BlackBeltTechnology/judo-meta-measure ·
**Artifact:** `hu.blackbelt.judo.meta:hu.blackbelt.judo.meta.measure`
(packaging `pom`, version `${revision}` = `1.0.2-SNAPSHOT`) ·
**License:** EPL-2.0 · **Java:** 21 ·
**Build:** Maven 3.9.4+ / Eclipse Tycho 4.0.13 ·
Part of the [judo-community](https://github.com/BlackBeltTechnology/judo-community) ecosystem.

## Reactor map

The root `pom.xml` declares its `<modules>` inside the `modules` profile, which
is active unless `-DskipModules=true` is passed. Build order is the declared
order — `model` produces the artifact every other module consumes.

<modules>
  <module>model</module>
  <module>model-test</module>
  <module>osgi</module>
  <module>osgi-itest</module>
  <module>feature</module>
  <module>site</module>
</modules>

| Module | Artifact / packaging | What it contributes |
|---|---|---|
| `model` | `…measure.model`, `eclipse-plugin` | The metamodel and everything derived from it: `model/model/measure.ecore` + `.genmodel`, the MWE2 workflow that regenerates `src-gen/`, the EVL validation scripts, and the hand-written runtime (`MeasureUtils`, `MeasureEpsilonValidator`, `MeasureValidator`/`MeasureConstraints` and the Zeta rule classes, plus the Eclipse `Activator`). Every other reactor module consumes or repackages this one. |
| `model-test` | `…measure.model.test`, `jar` | JUnit 5 proof that the runtime behaves: model resource creation (`MeasureExecutionContextTest`), XMI id uniqueness (`MeasureUtilsTest`), and EVL constraint execution (`MeasureValidationTest`). Consumes `model` as a dependency, so it exercises the published API surface. |
| `osgi` | `…measure.osgi`, `bundle` (Felix) | Repackages `model` for non-Eclipse OSGi containers, exporting the `hu.blackbelt.judo.meta.measure.*` packages and shipping `MeasureModelBundleTracker`, which discovers measure models advertised by the `Measure-Models` manifest header and registers them as OSGi services. |
| `osgi-itest` | `…measure.osgi.itest`, `jar` | Pax Exam 4.13.5 integration tests that boot an Apache Karaf 4.4.7 container and assert the bundle really resolves and validates there (`MeasureModelLoadITest`) — the check unit tests structurally cannot make. |
| `feature` | `…measure.feature`, `eclipse-feature` | Installable Eclipse feature grouping the plugin so the metamodel can be consumed from an IDE workspace. Descriptor-only module, no sources. |
| `site` | `…measure.site`, `eclipse-repository` | Builds the P2 update site published for "Install New Software" and consumed as a P2 repository by downstream Tycho builds. Its category P2 URLs carry hardcoded versions (see `update-category-versions`). |

`targetdefinition/` is not a reactor module — it holds the Tycho target
platform definition consumed by the Tycho builds above.

## Build commands

A Maven wrapper is bundled; prefer `./mvnw` over a repo-root `mvn`.

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

### Maven profiles

| Profile | Purpose |
|---|---|
| `modules` | Activates all submodule builds (active by default unless `-DskipModules=true`) |
| `sign-artifacts` | GPG-signs artifacts using sign-maven-plugin |
| `release-dummy` | Deploys to local `/tmp/` filesystem for testing |
| `release-judong` | Deploys to Judo NG Nexus (`nexus.judo.technology`) |
| `release-central` | Deploys to Maven Central via Sonatype OSSRH |
| `generate-github-asciidoc-diagrams` | Generates PNG diagrams from AsciiDoc via AsciidoctorJ |
| `update-source-code-license` | Updates EPL-2.0 license headers in source files |

## Technology stack

- **EMF/Ecore 2.38.0** — metamodel definition and Java code generation
- **Tycho 4.0.13** — builds the Eclipse plugin, feature and P2 site
- **Epsilon Runtime 2.8.0** — EVL model validation
- **MWE2 2.13.0** — code generation workflow (EcoreGenerator → Helpers → Builders → RuntimeModel)
- **OSGi 7.0.0** — bundle tracking and service registration
- **Lombok 1.18.34** — annotation processing, non-Eclipse modules only (Tycho incompatible)
- **JUnit 5.9.1**; **Pax Exam 4.13.5** + **Karaf 4.4.7** for OSGi integration tests
- **JaCoCo 0.8.12** coverage, **SonarQube** via sonar-maven-plugin 3.9.1
- **Flatten Maven Plugin 1.3.0** resolves the CI-friendly `${revision}`
- Maven 3.9.4+ with wrapper (`mvnw`); `.mvn/extensions.xml` adds the file and webdav wagons

## Development environment

**Required:** Java 21 JDK, Maven 3.9.4+.

**Eclipse plugin development:** Eclipse IDE with m2e, Epsilon, Modeling Tools,
XTend, XText and MWE/MWE2 plugins; install the plugin via P2 sites for editor
support.

**OSGi testing:** no additional setup — Pax Exam provisions Karaf during
`mvn verify`.

The root `pom.xml` is the parent POM (properties, dependency management, build
plugins, profiles). Root-level `logback-test.xml` configures test logging at
INFO to a console appender. CI/CD lives in `.github/workflows/build.yml`
(build, test, deploy, release).

## Git workflow

- **Main branch:** `develop`; **release branch:** `master` (latest released version)
- **Versioning:** `${revision}` = `1.0.2-SNAPSHOT`, CI-friendly, resolved by the flatten plugin
- **Branch naming:** `feature/JNG-XXXX_description`, `bugfix/JNG-XXXX_*`, `support/JNG-XXXX_*`, `hotfix/JNG-XXXX_*`
- **Rule:** every commit must reference a JIRA ticket (`JNG-xxx`)
- **CI/CD:** GitHub Actions with automated versioning, Nexus deployment, P2 site publishing and GitHub releases

## Important notes

1. **Never hand-edit `model/src-gen/`** — it is generated from `measure.ecore`
   via the MWE2 workflow (`model/src/workflow/generateModel.mwe2`). Hand-written
   code belongs in `model/src/main/java/`.
2. **Tycho and Lombok are incompatible** — Lombok is used only in the
   non-Eclipse modules (`model-test`, `osgi`, `osgi-itest`). The Eclipse plugin
   module (`model`) relies on generated code.
3. **Dual packaging:** `model` produces an Eclipse plugin via Tycho while `osgi`
   repackages it as a standard OSGi bundle via Felix. Both export the same API
   but target different runtimes.
4. **Version duality:** Maven uses `-SNAPSHOT` suffixes, Eclipse uses
   `.qualifier`. The Tycho Versions Plugin reconciles them during builds,
   producing versions like `1.0.2.20260225_143000_abc123_develop`.
5. **Validation script resolution:** the EVL scripts live in
   `model/src/main/epsilon/validations/`. `MeasureEpsilonValidator` resolves
   their location across JAR, OSGi bundle and filesystem environments.
   `measure-plugin-validation.evl` imports `measure.evl` and injects a native
   `MeasureUtils` for the Eclipse editor's on-the-fly validation.
6. **OSGi auto-discovery:** bundles carrying measure models declare them via the
   `Measure-Models` manifest header; `MeasureModelBundleTracker` in the `osgi`
   module loads and registers them as OSGi services.
7. **Metamodel change ⇒ validation change.** Adding a classifier or feature to
   `measure.ecore` obliges a matching rule (or a deliberate decision not to) in
   both `measure.evl` and the Zeta rule classes under
   `model/src/main/java/hu/blackbelt/judo/meta/measure/validation/rules/` —
   the two validation paths are expected to agree.

## Related documentation

- [README.md](README.md) — project introduction and quick start
- [CONTRIBUTING.md](CONTRIBUTING.md) — development setup, troubleshooting, submission guidelines
- [.github/CIFLOW.md](.github/CIFLOW.md) — CI/CD pipeline flows and branching strategy
- [docs/validation/](docs/validation) — validation rule documentation
- [judo-community](https://github.com/BlackBeltTechnology/judo-community) — parent aggregator project

<!-- dox-doctrine -->
## Documentation Update Protocol (WRITE discipline)

Per-directory `AGENTS.md` files form a tree. Each directory `AGENTS.md` is the
per-file record for the files in that directory. This module-root `AGENTS.md`
holds doctrine + architecture pointers only — never a per-file index.

**Keep the root lean.** This file loads into every agent turn — every byte costs
tokens on every turn. A verbose root file buries the rules the model must follow
(signal dilution) and measurably degrades adherence; a lean file keeps doctrine
salient. Default assumption: your update does NOT belong in the root — route it
by the table below.

**Route every doc update by kind:**

| Kind of update | Goes in |
|---|---|
| New file in a directory, or its per-file detail / change history | Nearest directory `AGENTS.md`. Add a `` | `<basename>` | <purpose> | `` row, path-alphabetical. |
| Data flow, protocol, architecture rationale | `docs/architecture.md` or a `docs/<topic>.md` |
| End-user / developer setup | `README.md` |
| Cross-cutting rule every agent needs every turn (rare) | this module-root `AGENTS.md` |

**Read before editing (chain walk).** Before editing a file, read the nearest
`AGENTS.md` chain root→leaf so you know the file's recorded purpose, contracts,
and change history. Do not edit blind.

**Update after editing (closeout pass).** After changing a file, update its row
in the nearest directory `AGENTS.md`: find the file's row, update its purpose in
place; if absent, add it in path-alphabetical order. New directory → scaffold
its `AGENTS.md`. One row per file. The purpose carries a one-line summary, key
exported symbols, contracts/invariants, and `See change: <id>` history.

**Row style (caveman).** Short declarative fragments. Drop articles. Subject →
verb → object, present tense. One fact per row. Prefer concrete tokens (paths,
symbols, env vars) over prose. Keep identifiers verbatim.

**Size rule — split an over-large directory `AGENTS.md` file-based.** pi
auto-injects a directory `AGENTS.md` on every turn when cwd sits at/below it, so
an over-large directory `AGENTS.md` is not supported. Split it file-based: a row
exceeding the length threshold promotes to a per-file `<File>.AGENTS.md`
sidecar carrying that file's full detail (including every `See change:`). The
sidecar is pull-only — its name is not `AGENTS.md`, so pi never auto-injects it
— yet it stays search-indexed (`agents` doc_type). The directory `AGENTS.md`
keeps a one-line summary plus a `→ see `<File>.AGENTS.md`` pointer. Rows within
the threshold stay verbatim (lossless).

## Finding docs (READ discipline)

`kb_*` tools are faster and cheaper than raw search — they return a one-line
purpose + key exports per file, not raw bytes. **This fires on the ACTION, not
the intent** — before you `grep`/`rg` for a symbol, `cat`/read a file to learn
what it does, or chase an import, the kb call goes first. It fires **even
mid-task when you already know the file**; knowing the file does not exempt you.
When your reflex is the left column, run the right column instead:

| You're about to… | Do this FIRST instead |
|---|---|
| `grep -rn "SymbolName" src/` — find where a fn / type / const lives | `kb_search --doc-type agents "SymbolName"` — tree indexes key exports per file |
| `grep -rn "feature\|topic" src/` — how does X work / where's X handled | `kb_search "feature topic"` |
| `cat` / read a file just to learn its purpose before editing | `kb agents <path>` — one-line purpose + exports + change history |
| chase imports / callers across files | `kb_neighbors <path\|heading>` |
| read one doc section in full | `kb_get <path> <section>` |

**Fall-through (explicit):** if the kb call returns nothing relevant, `rg` /
source read is allowed — then add the missing directory `AGENTS.md` row per the
WRITE discipline. kb does NOT replace grep; it goes first.
