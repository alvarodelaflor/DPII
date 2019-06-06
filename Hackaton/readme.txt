
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


	Para realizar los test de rendimiento, hemos realizado nada más que un test de registro y uno de editar, crear, mostrar, listar de un único actor (cleaner).
Si se realizara dichos casos de uso con otros actores, la misma complejidad sería la misma por lo que decidimos solo analizarlo con un actor.

	El equipo de desarrollo ha decidido no realizar pruebas de rendimiento de la funcionalidad de admin debido a la baja probabilidad de una gran cantidad de admins
modificando la app de forma concurrente.

	Creemos que parte de las pruebas de aceptación realizadas por el otro equipo sobre nuestro sistema no han sido correctamente completadas ya que, en muchos caso
no han hecho lo que el desarrollador pretendía que se hiciese y siendo consideradas por parte de ellos errores del sistema. Como ejemplo podemos señalar
el caso de mostrar una curricula sin estar logueado, donde el testeador señala que se devuelve a la página de inicio, caso que ocurre porque intentó realizar un mostrado de 
curriculum usando la URL de la captura ejemplo en lugar de utilizar la URL de la curricula que tuvo que crear en su test anterior. Numerosas variantes de estas ocurriencias
se repiten en nuestras pruebas de aceptación.
