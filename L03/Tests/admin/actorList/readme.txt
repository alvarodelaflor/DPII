Las pruebas se han realizado para medir el rendimiento con respecto al 
requisito 24.1

================================================================================
Información General:

Todas las pruebas se han realizado con #loops=10.
Todas las pruebas se han realizado con constante de 1500 y desviación de 100 ms 
en el "login" y en el "actorList" ("Gaussian Random Timer").
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
   - Error%: no aparecen errores para este nº de usuarios.
   - 90%Line: no aparecen "delays" destacables.
================================================================================
Prueba 2: 

#usuariosConcurrentes = 100;
   - Error%: aparecen una pequeña cantidad de errores.
   - 90%Line: aunque altos, tanto el "delay" en "login"(1505ms) y en "actorList"
     (1909ms) siguen siendo aceptables (<2000ms).
================================================================================
Prueba 3: 

#usuariosConcurrentes = 125;
   - Error%: aparecen una pequeña cantidad de errores.
   - 90%Line: Altos. Aunque el "delay" en "login"(1986ms) se mantiene ligeramente
     bajo el limite esperado (<2000ms), el "delay en "actorList"(2216ms) no.
================================================================================
Prueba 4: 

#usuariosConcurrentes = 180;
   - Error%: aparecen una gran cantidad de errores.
   - 90%Line: Altos. Tanto el "delay" en "login"(4715ms), como el "delay en 
     "actorList"(3643ms) son inaceptables.
================================================================================
--CONCLUSIÓN--
================================================================================
El nº de usuarios concurrentes válido recomendado es unos 120 usuarios según las
prubas realizadas. 
================================================================================
NOTA: existe una errata en las imagenes. El requisito testeado es el 24.