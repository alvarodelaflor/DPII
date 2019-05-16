Las pruebas se han realizado para medir el rendimiento con respecto a sponsorships

================================================================================
Información General:

Todas las pruebas se han realizado con #loops=10.
Todas las pruebas se han realizado con constante de 1500 y desviación de 100 ms 
excepto editar, que tiene 2000 ms constantes ("Gaussian Random Timer").
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
   - 90%Line: Muy altos. Editar supera por mucho lo esperado.
================================================================================
Prueba 2: 

#usuariosConcurrentes = 70;
   - Error%: no aparecen errores para este nº de usuarios.
   - 90%Line: Tiempo de editar algo superior a 2000 ms.
================================================================================
--CONCLUSIÓN--
================================================================================
El nº de usuarios concurrentes válido recomendado es unos 65 usuarios según las
pruebas realizadas. 
================================================================================