# measure-osgi Specification

## Purpose

Repackages the measure metamodel as a standard OSGi bundle and provides automatic discovery and registration of measure models embedded in other bundles via the `Measure-Models` manifest header.

## Architecture

The `osgi` module uses the Felix Maven Bundle Plugin to produce an OSGi bundle that exports all `hu.blackbelt.judo.meta.measure.*` packages. It includes the Ecore model files (under `meta/measure`) and Epsilon validation scripts (under `validations/`) as bundle resources.

The key runtime component is `MeasureModelBundleTracker` (annotated `@Component(immediate=true)`), which uses the `BundleTrackerManager` service to watch for bundles declaring a `Measure-Models` manifest header. When such a bundle is detected, the tracker loads the referenced model files and registers them as OSGi services.

The `osgi-itest` module verifies this behavior using Pax Exam with a Karaf 4.4.7 container.

## Requirements

### Requirement: Bundle Export

The OSGi bundle SHALL export all `hu.blackbelt.judo.meta.measure.*` packages with the project version.

#### Scenario: Package visibility

- **GIVEN** the osgi bundle is installed in an OSGi container
- **WHEN** another bundle imports `hu.blackbelt.judo.meta.measure` packages
- **THEN** all measure model classes, utilities, and runtime support classes are accessible

### Requirement: Model Auto-Discovery

`MeasureModelBundleTracker` SHALL automatically discover and register measure models from bundles that declare a `Measure-Models` manifest header.

#### Scenario: Bundle with measure model arrives

- **GIVEN** the MeasureModelBundleTracker component is active
- **WHEN** a bundle with header `Measure-Models: name=test;file=/model/test-measure.model` is installed
- **THEN** the tracker loads the model from the bundle entry
- **THEN** registers a `MeasureModel` OSGi service with the model name as a service property

#### Scenario: Bundle with measure model departs

- **GIVEN** a measure model service was registered from a tracked bundle
- **WHEN** the tracked bundle is uninstalled
- **THEN** the tracker unregisters the corresponding OSGi service
- **THEN** removes the model from its internal cache

### Requirement: Bundle Filtering

The tracker SHALL only process bundles that contain the `Measure-Models` manifest header.

#### Scenario: Bundle without measure models

- **GIVEN** the MeasureModelBundleTracker is active
- **WHEN** a bundle without the `Measure-Models` header is installed
- **THEN** the tracker ignores it (MeasureBundlePredicate returns false)

### Requirement: Resource Inclusion

The OSGi bundle SHALL include the Ecore metamodel files and Epsilon validation scripts as embedded resources.

#### Scenario: Validation scripts available

- **GIVEN** the osgi bundle is loaded in a container
- **WHEN** `MeasureEpsilonValidator.calculateMeasureValidationScriptURI()` is called
- **THEN** the validation scripts are resolved from the bundle's `validations/` resource path

### Requirement: Karaf Integration

The measure OSGi bundle SHALL load and function correctly in an Apache Karaf container.

#### Scenario: Bundle starts in Karaf

- **GIVEN** a Karaf 4.4.7 container provisioned with the measure bundle and its dependencies
- **WHEN** the container starts
- **THEN** the measure bundle reaches ACTIVE state
- **THEN** the MeasureModelBundleTracker component activates
- **THEN** measure model validation can be executed against discovered models
