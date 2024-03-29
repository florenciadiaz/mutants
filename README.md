# Mutants
[![version: 1.1-SNAPSHOT](https://img.shields.io/badge/version-1.1-orange.svg)](https://github.com/florenciadiaz/mutants/tree/master)
[![Build Status](https://travis-ci.com/florenciadiaz/mutants.svg?token=Ge3CQrgSPTiaZowMpjFM&branch=master)](https://travis-ci.com/florenciadiaz/mutants)
[![codecov](https://codecov.io/gh/florenciadiaz/mutants/branch/master/graph/badge.svg?token=6HmSxqkGop)](https://codecov.io/gh/florenciadiaz/mutants)
[![license: MIT](https://img.shields.io/badge/license-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Test project for detecting mutant DNA, requested by **Mercadolibre**. 

* [Live demo](https://meli-mutants-test.herokuapp.com)

*Read this in other languages: [English](README.md), [Español](README.es.md).*
 
### Getting started

Post a [JSON DNA sequence](#samples) to the **/mutants/** service on the [live demo](https://meli-mutants-test.herokuapp.com) using your favorite tool.

The input JSON format should be like this (NxN):  
```
{
  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```
*Remember to set ```Content-Type: application/json``` on the header of the POST request.*

The response of the service will tell you whether the DNA belongs to a mutant or not.

Then check the **/stats/** service in order to see the statistics of DNA verifications.

Post as many samples as you want, considering the [restrictions](#restrictions).

#### Available features

| Feature      | Description                                                   | Method       |
|--------------|---------------------------------------------------------------|--------------|
| /mutant/     | Detects if the DNA sequence in the JSON belongs to a mutant   |     POST     |
| /stats/      | Returns a JSON with the statistics of the DNA checks          |     GET      |

### Samples
This project contains some JSON files with [sample with DNA sequences](https://github.com/florenciadiaz/mutants/tree/master/src/test/resources/samples/integration) to try on the [live demo](https://meli-mutants-test.herokuapp.com). 

### Restrictions
Since this is a sample application, the [live demo](https://meli-mutants-test.herokuapp.com) only processes DNA sequences of 51x51 maximum.

If you need bigger sequences (up to 180x180), you can download the source code and [deploy locally](#instructions) or in a server with H2 database support, 
changing the key ```app.detection.max-nb-sequence-length=180``` on the `application-[env].properties` file, where _[env]_ is related to the environment used. 

### Instructions
To run the project locally, download the latest version's source code, step on the `/mutants` folder and execute the following commands:

- ```gradlew check``` to compile, run the tests and verify everything is OK (*).
 
- ```gradlew bootRun``` to start the service at the development environment.

- ```gradlew clean``` to clear the latest build and be able to run all check tasks again.

*(\*) After running the tests, check the **Tests Sumary Report** at the `/mutants/build/reports/tests/test` folder for more information.* 

#
Copyright &copy;2019 florenciadiaz. Licensed under the MIT License, for details see `LICENSE.txt`.