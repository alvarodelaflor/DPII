
	ACTORES:
		cleaner/cleaner
		hosthost/host
		hackerhost/host
		travelAgency/travelAgency
		admin/admin
		referee/referee
		transporter/transporter
		customer/customer
		customerHacker/customer


	

	Creemos que parte de las pruebas de aceptación realizadas por el otro equipo sobre nuestro sistema no han sido correctamente completadas ya que, en muchos caso
no han hecho lo que el desarrollador pretendía que se hiciese y siendo consideradas por parte de ellos errores del sistema. Como ejemplo podemos señalar
el caso de mostrar una curricula sin estar logueado, donde el testeador señala que se devuelve a la página de inicio, caso que ocurre porque intentó realizar un mostrado de 
curriculum usando la URL de la captura ejemplo en lugar de utilizar la URL de la curricula que tuvo que crear en su test anterior. Numerosas variantes de estas ocurriencias
se repiten en nuestras pruebas de aceptación.

	El equipo de desarrollo ha decidido no realizar pruebas de rendimiento de la funcionalidad de admin debido a la baja probabilidad de una gran cantidad de admins
modificando la app de forma concurrente. Por otro lado, no se han realizado la mayor parte de la pruebas de borrado por los siguientes motivos:
		- Sabemos por entregas anteriores que el rendimiento de nuestra aplicación no se va a ver afectada por las pruebas de borrado, si no por otros casos de usos.
		- Realizar los test de rendimiento de un delete son muy costosos, ya que hay que hacer un csv y de un contador para que estas se realicen correctamente.
	Aun así se han realizado algunos test de borrado, como por ejemplo: curricula, educational data... 
	Hemos realizado nada más que un test de registro y uno de editar, crear, mostrar, listar de un único actor (cleaner). Si realizamos dichos casos de uso con otros actores,
obtendremos la misma complejidad, por tanto decidimos solo analizarlo con un actor.

	Hemos realizado un video tutorial informal de la aplicación: 
