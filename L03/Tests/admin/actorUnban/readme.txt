Las pruebas se han realizado para medir el rendimiento con respecto al 
requisito 24.2

================================================================================
Información General:

Todas las pruebas se han realizado con #loops=10.
Todas las pruebas se han realizado con constante de 1500 y desviación de 100 ms 
en el "login", en el "actorList" y en "unbanActor"("Gaussian Random Timer").
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
   - 90%Line: aunque altos, tanto en "actorList"(1837ms), como en 
     "banActor"(2179ms), los "delays" siguen siendo aceptables (±2000ms). Sin 
      embargo, en "login"(2644ms) supera bastante el umbral válido.
================================================================================
Prueba 3: 

#usuariosConcurrentes = 130;
   - Error%: aparecen una pequeña cantidad de errores.
   - 90%Line: Altos. El "delay" en "login"(2944ms), el "delay en "actorList"
     (2581ms) y el "delay" en "banActor"(2973ms) son excesivos.
================================================================================
--CONCLUSIÓN--
================================================================================
El nº de usuarios concurrentes válido recomendado es unos 100 usuarios según las
prubas realizadas. 
================================================================================