
package services;

/*
 * CONTROL DE CAMBIOS AdministratorService.java
 * FRAN 19/02/2019 11:36 CREACIÓN DE LA CLASE
 */

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ProclaimRepository;

@Service
@Transactional
public class ProclaimService {

	@Autowired
	private ProclaimRepository	proclaimRepository;

	//Simple CRUD Methods ------------------

}
