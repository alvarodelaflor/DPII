Las pruebas se han realizado para medir el rendimiento con respecto al 
requisito 23.1

================================================================================
Información General:

Todas las pruebas se han realizado con #loops=10.
Todas las pruebas se han realizado con constante de 1500ms y desviación de 100 ms 
en el "login" y en "list"("Gaussian Random Timer").
Todas las pruebas se han realizado con constante de 3000ms y desviación de 1000 ms 
en el "create"("Gaussian Random Timer").
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

#usuariosConcurrentes = 100;
   - Error%: no aparecen errores para este nº de usuarios.
   - 90%Line: no aparecen "delays" destacables.
================================================================================
Prueba 2: 

#usuariosConcurrentes = 150;
   - Error%: no aparecen errores para este nº de usuarios.
   - 90%Line: Altos. Los "delays" no son aceptables (>2500ms).
================================================================================
--CONCLUSIÓN--
================================================================================
El nº de usuarios concurrentes válido recomendado es unos 140 usuarios según las
pruebas realizadas. 
================================================================================