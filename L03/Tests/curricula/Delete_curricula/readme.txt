Entendemos como 70 usuarios, 100 bucles y 7000 curriculas a borrar el límite de nuestra aplicación.

Con esta configuración a pesar de que la aplicación no muestra porcentaje de error el percentil alcanza
un valor de 2.5 segundos, algo ya no factible para un uso normal de esta.

La CPU ha sido la causante de nuestro cuello de botella, véase las capturas de rendimiento.

El procedimiento ha sido crear un número de curriculas (hilos * iteraciones) usando el programa java 
"generateCurriculaPopulate.zip" en el que le indicamos el número de curriculas que queremos. Insertamos
estas curriculas en el populate y lo ejecutamos.

Posteriormente creamos un contador en Jmeter cuyo número de inicio sea la id de la primera curricula que
se ha generado en mySql, donde su incremento sea de 1 y su nombre de variable counter_number por ejemplo. 
Buscamos el método get de delete e introducimos el nombre del contador (counter_number) como variable. Así
por cada usuario e iteracción el contador le indicará el número de id que tiene que borrar, el cual gracias a
jmeter será único por cada hilo e iteracción, siendo así un caso totalmente real de prueba donde podemos 
dividir el caso de crear y de borrar.

Prestaciones:

HP ay150ns, intel i7 7500U con SSD Samsung EVO 860 como memoria principal y 16 GB de memoria RAM.

No se usa la máquina virtual, JMETER y Tomcat directo sobre Windows 10.