# judo-meta-measure

[![Build](https://github.com/BlackBeltTechnology/judo-meta-measure/actions/workflows/build.yml/badge.svg?branch=develop)](https://github.com/BlackBeltTechnology/judo-meta-measure/actions/workflows/build.yml)

## Introduction

This repository contains the Measure meta model.

It acts as an eclipse plugin with features and sites, can be used standalone and in standard OSGi (without eclipse).

[SI Standard](https://en.wikipedia.org/wiki/International_System_of_Units) model extended with descriptions.

## Validation

This project supports dual validation using both EVL (Epsilon Validation Language) and a native Java validation framework powered by [Zeta](https://github.com/BlackBeltTechnology/judo-zeta).

### Java Validation Framework

The Java validation framework provides:
- Better IDE integration (debugging, refactoring, code navigation)
- Improved performance compared to EVL
- Standard Java tooling support

For details, see the [Java Validation Framework Documentation](docs/validation/java-validation-framework.md).

## Context

This project is a building block of the [judo-community](https://github.com/BlackBeltTechnology/judo-community) aggregator project. In order to better understand how this module fits into our ecosystem, please check the corresponding documentation!

## Contributing to the project

Everyone is welcome to contribute to JUDO! As a starter, please read the corresponding [CONTRIBUTING](CONTRIBUTING.md) guide for details!

## License

This project is licensed under the [Eclipse Public License - v 2.0](https://www.eclipse.org/legal/epl-2.0/).
