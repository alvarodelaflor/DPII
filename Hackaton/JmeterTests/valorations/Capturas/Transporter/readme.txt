Las pruebas se han realizado para medir el rendimiento con respecto a las valora
ciones de transporter. Solo se prueba hacia un tipo de actor porque el rendimiento 
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

#usuariosConcurrentes = 100;
   - 90%Line: Extremadamente altos.
================================================================================
Prueba 2: 

#usuariosConcurrentes = 50;
   - 90%Line: Bajo. Admite más usuarios.
================================================================================
Prueba 3: 

#usuariosConcurrentes = 75;
   - 90%Line: Delays bajo.
================================================================================
--CONCLUSIÓN--
================================================================================
El nº de usuarios concurrentes oscila en unos 80 usuarios.
================================================================================