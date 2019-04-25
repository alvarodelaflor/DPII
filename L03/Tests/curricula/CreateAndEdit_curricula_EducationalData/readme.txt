Establecemos como límite los 180 usuario ya que comprendemos que una acción de iniciar sesión no debe tomar más
de 3 segundos, como ocurre en esta prueba, a pesar de lo que los percentiles de otras tareas se mantienen estables
entre 1'8 y 1'6 segundos.

Los parámetros de esta prueba han sido 180 usuarios con 20 bucles.

Una vez más podemos observar en las capturas de rendimiento que la CPU ha sido nuestro cuello de botella.


Prestaciones:

HP ay150ns, intel i7 7500U con SSD Samsung EVO 860 como memoria principal y 16 GB de memoria RAM.

No se usa la máquina virtual, JMETER y Tomcat directo sobre Windows 10.