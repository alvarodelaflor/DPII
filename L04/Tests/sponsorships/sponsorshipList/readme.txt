Las pruebas se han realizado para medir el rendimiento con respecto al 
requisito 13

================================================================================
Información General:

Todas las pruebas se han realizado con #loops=10.
Todas las pruebas se han realizado con constante de 1500 y desviación de 100 ms 
en el "login" y en el "sponsorshipList" ("Gaussian Random Timer").
================================================================================

================================================================================
Prestaciones:

Procesador: Intel(R) Core(TM) i7-8750H CPU @ 2.20GHz 2.20GHz.
Memoria instalada (RAM): 8.00 GB.
Sistema Operativo de 64b, procesador x64.

**USANDO MAQUINA VIRTUAL APORTADA PARA LA ASIGNATURA**
================================================================================

================================================================================
--PRUEBAS--
================================================================================
Prueba 1: 

#usuariosConcurrentes = 200;
   - Error%: no aparecen errores para este nº de usuarios.
   - 90%Line: Altos, pero aceptables. En torno a los 2000ms.
================================================================================
Prueba 2: 

#usuariosConcurrentes = 230;
   - Error%: aparecen una cantidad de errores considerables.
   - 90%Line: aunque aceptables, la cantidad de errores hace inviable esta
     cantidad de usuarios concurrentes.
================================================================================
Prueba 3: 

#usuariosConcurrentes = 215;
   - Error%: aparecen una pequeña cantidad de errores.
   - 90%Line: Delays aceptables, pero es necesario considerar la cantidad de
     errores que aparecen.
================================================================================
--CONCLUSIÓN--
================================================================================
El nº de usuarios concurrentes válido recomendado es unos 207 usuarios según las
pruebas realizadas. 
================================================================================