# Mutantes
[![version: 1.1-SNAPSHOT](https://img.shields.io/badge/version-1.1-orange.svg)](https://github.com/florenciadiaz/mutants/tree/master)
[![Build Status](https://travis-ci.com/florenciadiaz/mutants.svg?token=Ge3CQrgSPTiaZowMpjFM&branch=master)](https://travis-ci.com/florenciadiaz/mutants)
[![codecov](https://codecov.io/gh/florenciadiaz/mutants/branch/master/graph/badge.svg?token=6HmSxqkGop)](https://codecov.io/gh/florenciadiaz/mutants)
[![license: MIT](https://img.shields.io/badge/license-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Proyecto de prueba para detectar ADN mutante, solicitado por **Mercadolibre**. 

* [Demo en vivo](https://meli-mutants-test.herokuapp.com)

*Leer esto en otros idiomas: [English](README.md), [Español](README.es.md).*
 
### Empezando

Env&iacute;a por _POST_ una [secuencia de ADN](#muestras) en formato _JSON_ al servicio **/mutants/** en la [demo en vivo](https://meli-mutants-test.herokuapp.com) usando tu herramienta favorita.

El formato de entrada _JSON_ debería ser as&iacute; (NxN):  
```
{
  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```
*Recuerda configurar ```Content-Type: application/json``` en el encabezado de la petici&oacute;n POST.*

La respuesta del servicio te dir&aacute; si el ADN pertenece o no a un mutante.

Luego revisa el servicio **/stats/** para ver las estad&iacute;sticas de verificaci&oacute;n de ADN.

Env&iacute;a tantos ejemplos como quieras, teniendo en cuenta las [restricciones](#restricciones).

#### Funcionalidades disponibles

| Funcionalidad  | Descripci&oacute;n                                                   | M&eacute;todo       |
|----------------|----------------------------------------------------------------------|--------------|
| /mutant/       | Detecta si la secuencia de ADN en el _JSON_ pertenece a un mutante   |     POST     |
| /stats/        | Devuelve un JSON con las estadísticas de las comprobaciones de ADN   |     GET      |

### Muestras
Este proyecto contiene algunos archivos _JSON_ con [muestras de secuencias de ADN](https://github.com/florenciadiaz/mutants/tree/master/src/test/resources/samples/integration) para probar en la [live demo](https://meli-mutants-test.herokuapp.com). 

### Restricciones
Dado que se trata de una aplicación de prueba, la [demo en vivo](https://meli-mutants-test.herokuapp.com) sólo procesa secuencias de ADN de 51x51 como máximo.

Si necesitas secuencias m&aacute; grandes (de hasta 180x180), puedes descargar el c&oacute;digo fuente e instalarlo localmente o en un servidor con soporte para bases de datos H2, 
cambiando la clave ```app.detection.max-nb-sequence-length=180``` en el archivo `application-[env].properties`, donde _[env]_ corresponde al ambiente que utilices.



Copyright &copy;2019 florenciadiaz. Licenciado bajo la _MIT License_, para m&aacute;s detalles ver `LICENSE.txt`.