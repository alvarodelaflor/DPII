
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FloatBroRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.FloatBro;
import domain.Procession;

/*
 * CONTROL DE CAMBIOS FloatBroService.java
 * 
 * ALVARO 17/02/2019 19:14 CREACIÓN DE LA CLASE
 */

@Service
public class FloatBroService {

	//Managed Repository -------------------	

	@Autowired
	private FloatBroRepository	floatBroRepository;

	//Supporting services ------------------
	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	Validator					validator;


	//Simple CRUD Methods ------------------

	public FloatBro create() {

		final FloatBro floatBro = new FloatBro();
		final UserAccount login = LoginService.getPrincipal();
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		floatBro.setBrotherhood(brotherhood);
		return floatBro;

	}

	public Collection<FloatBro> findAll() {
		return this.floatBroRepository.findAll();
	}

	public FloatBro findOne(final int id) {
		return this.floatBroRepository.findOne(id);
	}
	public FloatBro save(final FloatBro floatBro) {
		/*
		 * Ya que no le podemos pasar nada al create porque el reconstruidor hace que se lo cargue decidimos colocar
		 * aquí la asignación del brotherhood y será en el controlador donde se vigile que la edición la realiza el creador
		 * de esa floatBro
		 */
		floatBro.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
		return this.floatBroRepository.save(floatBro);
	}

	public void delete(final FloatBro floatBro) {
		Assert.notNull(this.floatBroRepository.findOne(floatBro.getId()), "La floatBro no existe");
		Assert.isTrue(LoginService.getPrincipal().getId() == floatBro.getBrotherhood().getUserAccount().getId(), "brotherhoodLoggerDiferent");
		final Collection<Procession> processions = this.processionService.getProcessionByFloatId(floatBro.getId());
		for (final Procession procession : processions)
			this.processionService.delete(procession);
		this.floatBroRepository.delete(floatBro);
	}

	public FloatBro update(final FloatBro floatBro) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		Assert.isTrue(floatBro.getBrotherhood().equals(brotherhood));
		Assert.isTrue(this.findOne(floatBro.getId()) != null);
		final FloatBro saveFloatBro = this.floatBroRepository.save(floatBro);
		return saveFloatBro;
	}

	//Other Methods

	public Collection<FloatBro> getFloatBroByBrotherhoodId(final int brotherhoodId) {
		return this.floatBroRepository.findFloatBroByBrotherhood(brotherhoodId);
	}

	public Collection<FloatBro> findAllBrotherhoodLogged() {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		return this.floatBroRepository.findFloatBroByBrotherhood(brotherhood.getId());
	}

	public FloatBro show(final int floatBroId) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhoodLogin = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		Assert.isTrue(this.findOne(floatBroId) != null);
		final Brotherhood brotherhoodFloatBro = this.findOne(floatBroId).getBrotherhood();
		Assert.isTrue(brotherhoodLogin.equals(brotherhoodFloatBro));
		return this.findOne(floatBroId);
	}

	public FloatBro reconstruct(final FloatBro floatBro, final BindingResult binding) {
		FloatBro result;

		if (floatBro.getId() == 0) {
			floatBro.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
			result = floatBro;
		} else {
			result = this.floatBroRepository.findOne(floatBro.getId());
			//			result.setTitle(floatBro.getTitle());
			//			result.setDescription(floatBro.getDescription());
			//			result.setPictures(floatBro.getPictures());
			//			if (result.getBrotherhood() == null)
			//				result.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
			floatBro.setId(result.getId());
			floatBro.setVersion(result.getVersion());
			result = floatBro;
		}
		this.validator.validate(floatBro, binding);
		return result;
	}
}
