Establecemos como límite los 190 usuario ya que comprendemos que una acción de iniciar sesión no debe tomar más
de 3 segundos, aunque el resto de percentiles aún se mantienen estables entre 1'8 y 1'6 segundos.

Los parámetros de esta prueba han sido 250 usuarios con 20 bucles.

La CPU ha sido la causante de nuestro cuello de botella, por mantenerse al 100% todo el tiempo a pesar de que
otros componentes se mantienen al mínimo nivel.

Prestaciones:

HP ay150ns, intel i7 7500U con SSD Samsung EVO 860 como memoria principal y 16 GB de memoria RAM.

No se usa la máquina virtual, JMETER y Tomcat directo sobre Windows 10.