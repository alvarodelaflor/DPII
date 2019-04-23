Las pruebas se han realizado para medir el rendimiento con respecto al 
requisito 11.1

================================================================================
Información General:

Todas las pruebas se han realizado con #loops=FOREVER.
Todas las pruebas se han realizado con constante de 1500ms y desviación de 100 ms 
en el "login"("Gaussian Random Timer").
Todas las pruebas se han realizado con constante de 4000ms y desviación de 2000 ms 
en el "registerAdmin"("Gaussian Random Timer").
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

#usuariosConcurrentes = 50;
   - Error%: aparece una pequeña cantidad de errores para este nº de usuarios.
   - 90%Line: no aparecen "delays" destacables.
================================================================================
Prueba 2: 

#usuariosConcurrentes = 100;
   - Error%: aparecen una pequeña cantidad de errores.
   - 90%Line: Los "delays" son superiores a 4 segundos. Inaceptables.
================================================================================
Prueba 3: 

#usuariosConcurrentes = 80;
   - Error%: aparecen una pequeña cantidad de errores.
   - 90%Line: Altos. Los "delays" se encuentran ligeramente por debajo de 4 
     segundos. Tiempos muy superiores al umbral válido.
================================================================================
Prueba 4: 

#usuariosConcurrentes = 65;
   - Error%: aparecen una pequeña cantidad de errores.
   - 90%Line: Altos. Los "delays" son cercanos a 3 segundos. Algo mas aceptables,
     pero muy altos.
================================================================================
--CONCLUSIÓN--
================================================================================
El nº de usuarios concurrentes válido recomendado es unos 55 usuarios según las
pruebas realizadas. 
================================================================================