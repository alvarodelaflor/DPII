
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FloatRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Float;
import domain.Procession;

/*
 * CONTROL DE CAMBIOS floatt Service.java
 * 
 * ALVARO 17/02/2019 19:14 CREACIÓN DE LA CLASE
 */

@Service
public class FloatService {

	//Managed Repository -------------------	

	@Autowired
	private FloatRepository	floatRepository;

	//Supporting services ------------------
	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	Validator					validator;


	//Simple CRUD Methods ------------------

	public domain.Float create() {

		final domain.Float floatt  = new Float();
		final UserAccount login = LoginService.getPrincipal();
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		floatt.setBrotherhood(brotherhood);
		return floatt ;

	}

	public Collection<Float> findAll() {
		return this.floatRepository.findAll();
	}

	public domain.Float findOne(final int id) {
		return this.floatRepository.findOne(id);
	}
	public domain.Float save(final domain.Float floatt ) {
		/*
		 * Ya que no le podemos pasar nada al create porque el reconstruidor hace que se lo cargue decidimos colocar
		 * aquí la asignación del brotherhood y será en el controlador donde se vigile que la edición la realiza el creador
		 * de esa floatt 
		 */
		floatt.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
		return this.floatRepository.save(floatt);
	}

	public void delete(final domain.Float floatt) {
		Assert.notNull(this.floatRepository.findOne(floatt.getId()), "La floatt  no existe");
		Assert.isTrue(LoginService.getPrincipal().getId() == floatt .getBrotherhood().getUserAccount().getId(), "brotherhoodLoggerDiferent");
		final Collection<Procession> processions = this.processionService.getProcessionByFloatId(floatt.getId());
		for (final Procession procession : processions)
			this.processionService.delete(procession);
		this.floatRepository.delete(floatt);
	}

	public domain.Float update(final domain.Float floatt) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		Assert.isTrue(floatt.getBrotherhood().equals(brotherhood));
		Assert.isTrue(this.findOne(floatt.getId()) != null);
		final domain.Float saveFloat = this.floatRepository.save(floatt);
		return saveFloat;
	}

	//Other Methods

	public Collection<domain.Float> getFloatByBrotherhoodId(final int brotherhoodId) {
		return this.floatRepository.findFloatByBrotherhood(brotherhoodId);
	}

	public Collection<domain.Float> findAllBrotherhoodLogged() {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		return this.floatRepository.findFloatByBrotherhood(brotherhood.getId());
	}

	public domain.Float show(final int floatId) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhoodLogin = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		Assert.isTrue(this.findOne(floatId) != null);
		final Brotherhood brotherhoodFloat = this.findOne(floatId).getBrotherhood();
		Assert.isTrue(brotherhoodLogin.equals(brotherhoodFloat));
		return this.findOne(floatId);
	}

	public domain.Float reconstruct(final domain.Float floatt , final BindingResult binding) {
		Float result;

		if (floatt.getId() == 0) {
			floatt.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
			result = floatt ;
		} else {
			result = this.floatRepository.findOne(floatt.getId());
			//			result.setTitle(floatt.getTitle());
			//			result.setDescription(floatt.getDescription());
			//			result.setPictures(floatt.getPictures());
			//			if (result.getBrotherhood() == null)
			//				result.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
			floatt.setId(result.getId());
			floatt.setVersion(result.getVersion());
			result = floatt ;
		}
		this.validator.validate(floatt, binding);
		return result;
	}
}
