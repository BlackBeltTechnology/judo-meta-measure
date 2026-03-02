# measure-metamodel Specification

## Purpose

Defines the Ecore metamodel for the SI (International System of Units) standard, providing a type-safe representation of base measures, derived measures, units, and conversion rates. This metamodel is the source of truth from which all Java classes, builders, helpers, and runtime model support are generated.

## Architecture

The metamodel is defined in `model/model/measure.ecore` (namespace URI: `http://blackbelt.hu/judo/meta/measure`, prefix: `measure`). EMF generates Java classes into `model/src-gen/` via the MWE2 workflow in `model/src/workflow/generateModel.mwe2`. The generation pipeline runs four stages: EcoreGenerator, HelperGeneratorWorkflow, BuilderGeneratorWorkflow, and RuntimeModelGeneratorWorkflow.

Hand-written code in `model/src/main/java/` includes `MeasureUtils` (XMI ID management) and `MeasureEpsilonValidator` (validation integration).

### Metamodel Type Hierarchy

| Type | Kind | Supertype | Description |
|------|------|-----------|-------------|
| `Measure` | abstract class | — | Base type with `namespace`, `name`, `symbol` attributes and `units` containment reference |
| `BaseMeasure` | class | `Measure` | Fundamental SI quantities (length, mass, time, etc.) |
| `DerivedMeasure` | class | `Measure` | Composite measures with `terms` containment of `BaseMeasureTerm` |
| `BaseMeasureTerm` | class | — | Term in a derived measure with `exponent` (EInt) and `baseMeasure` reference |
| `Unit` | class | — | Unit of measurement with `name`, `symbol`, `rateDividend`, `rateDivisor` |
| `DurationUnit` | class | `Unit` | Specialized temporal unit with `type` (DurationType enum) |
| `DurationType` | enum | — | Literals: nanosecond, microsecond, millisecond, second, minute, hour, day, week, month, year |

## Requirements

### Requirement: Base Measure Definition

A BaseMeasure SHALL represent a fundamental SI quantity with a namespace, name, optional symbol, and zero or more associated units.

#### Scenario: Create a base measure with units

- **GIVEN** an empty measure model resource
- **WHEN** a BaseMeasure is created with namespace "demo", name "Length", and a Unit with name "meter", symbol "m", rateDividend 1, rateDivisor 1
- **THEN** the model contains one BaseMeasure with one contained Unit
- **THEN** the Unit's rateDividend/rateDivisor express the conversion factor relative to other units of the same measure

### Requirement: Derived Measure Composition

A DerivedMeasure SHALL be composed of one or more BaseMeasureTerms, each referencing a BaseMeasure with an integer exponent.

#### Scenario: Create a derived measure for speed

- **GIVEN** BaseMeasures for "Length" and "Time" exist in the model
- **WHEN** a DerivedMeasure "Speed" is created with terms: Length (exponent=1) and Time (exponent=-1)
- **THEN** the DerivedMeasure contains two BaseMeasureTerm objects
- **THEN** each term references its respective BaseMeasure with the correct exponent

### Requirement: Unit Conversion Rates

Each Unit SHALL define a conversion rate as rateDividend/rateDivisor relative to the measure's canonical unit.

#### Scenario: Define kilometer as a unit of length

- **GIVEN** a BaseMeasure "Length" with a base Unit "meter" (rateDividend=1, rateDivisor=1)
- **WHEN** a second Unit "kilometer" is added with rateDividend=1000, rateDivisor=1
- **THEN** the conversion factor from kilometer to meter is rateDividend/rateDivisor = 1000

### Requirement: Duration Type Support

DurationUnit SHALL specialize Unit with a DurationType enumeration covering temporal granularities from nanosecond to year.

#### Scenario: Create duration units for a time measure

- **GIVEN** a BaseMeasure "Time"
- **WHEN** DurationUnits are created for millisecond, second, and hour types
- **THEN** each DurationUnit has its `type` attribute set to the corresponding DurationType literal

### Requirement: Code Generation Pipeline

The MWE2 workflow SHALL generate Java classes, builders, helpers, and runtime model support from the Ecore metamodel.

#### Scenario: Run code generation

- **GIVEN** a valid `measure.ecore` and `measure.genmodel`
- **WHEN** the MWE2 workflow `generateModel.mwe2` is executed
- **THEN** Java classes are generated into `src-gen/` including EMF model interfaces, implementation classes, builder classes, helper classes, and runtime model support classes

### Requirement: XMI ID Management

MeasureUtils SHALL provide static methods to get and set XMI IDs on EObjects, and an instance method to validate uniqueness of all IDs in a ResourceSet.

#### Scenario: Validate unique XMI IDs

- **GIVEN** a model with multiple EObjects each assigned an XMI ID
- **WHEN** `MeasureUtils.validateUniqueXmiids()` is called
- **THEN** it throws IllegalStateException if any two EObjects share the same ID

#### Scenario: Validate with null ResourceSet

- **WHEN** `validateUniqueXmiids()` is called on a MeasureUtils with no ResourceSet
- **THEN** it throws IllegalStateException with message "Model's ResourceSet is unknown (null)"
