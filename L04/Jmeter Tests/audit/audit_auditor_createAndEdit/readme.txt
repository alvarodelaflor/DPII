Entendemos como 450 usuarios y 5 bucles el límite de nuestra aplicación para el caso de uso conjunto de
crear y editar.

La causa de parar con esta configuración de usuarios y bucles es debido a que la acción de iniciar sesión
alcance valores cercanos a los 3 segundos.

**************************************************************************************************************
INSTRUCCIONES DE USO

Antes de comenzar a leer es recomendable ver la captura de vídeo con el nombre "example_video.mp4"

Para la realización de este test hemos obtado por el uso conjunto de un contador y un archivo .CSV

Ha sido necesaria la utilización de esta metodología debido a que una "Audit" requiere de una "Position" para
ser creada, sin embargo, por cada "Position" cada usuario solo puede crear una entidad "Audit". En nuestra
base de datos populada solo contamos con 5 "Position" viables para la creación de una "Audit" y como su labor
de creación es muy engorrosa hemos decidido crear un usuario por cada hilo que utilicemos (con la ayuda del 
archivo CSV) y por cada uno de estos usuarios serán creadas 5 "Audits" cada una de ellas referenciando a una
"Position" diferente siendo aquí el elementos contador necesario para la diferenciación de cada una de ellas.

Para una mejor comprensión de lo expresado anteriormente se recomienda una vez más la visión del vídeo 
"example_video.mp4"
**************************************************************************************************************


Prestaciones:

HP ay150ns, intel i7 7500U con SSD Samsung EVO 860 como memoria principal y 16 GB de memoria RAM.

No se usa la máquina virtual, JMETER y Tomcat directo sobre Windows 10.