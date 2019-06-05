Las pruebas se han realizado para medir el rendimiento con respecto a las valora
ciones de un customer. Solo se prueba hacia un tipo de actor porque el rendimiento 
debe ser el mismo.
================================================================================
Información General:

Todas las pruebas se han realizado con #loops=10.
Todas las pruebas se han realizado con constante de 1500 y desviación de 100 ms.
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

#usuariosConcurrentes = 80;
   - 90%Line: Muy alto.
================================================================================
Prueba 2: 

#usuariosConcurrentes = 50;
   - 90%Line: Tiempos bajos.
================================================================================
Prueba 3: 

#usuariosConcurrentes = 65;
   - 90%Line: Delays aceptables, algo por encima de 2000 ms.
================================================================================
--CONCLUSIÓN--
================================================================================
El nº de usuarios concurrentes válido recomendado es unos 60 usuarios según las
pruebas realizadas. 
================================================================================