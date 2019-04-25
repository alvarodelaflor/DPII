Entendemos como 140 usuarios, 20 bucles y 2800 registros de socialProfile a borrar el límite de nuestra aplicación.

A partir de este porcentaje de usuarios comienzan a darse tiempos de 3 segundos en login, entendiéndose que hemos alcanzado
nuestro límite.

El procedimiento ha sido crear un número de socialProfile (hilos * iteraciones) usando el programa java 
"generateSocialProfilePopulate.zip" en el que le indicamos el número de entidades que queremos. Insertamos
estas en el populate,  y lo ejecutamos.

Posteriormente creamos un contador en Jmeter cuyo número de inicio sea la id de la primera entidad que
se ha generado en mySql, donde su incremento sea de 1 y su nombre de variable counter_number por ejemplo. 
Buscamos el método get de delete e introducimos el nombre del contador (counter_number) como variable. Así
por cada usuario e iteracción el contador le indicará el número de id que tiene que borrar, el cual gracias a
jmeter será único por cada hilo e iteracción, siendo así un caso totalmente real de prueba donde podemos 
dividir el caso de crear y de borrar.

Prestaciones:

HP ay150ns, intel i7 7500U con SSD Samsung EVO 860 como memoria principal y 16 GB de memoria RAM.

No se usa la máquina virtual, JMETER y Tomcat directo sobre Windows 10.