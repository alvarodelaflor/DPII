
package services;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProcessionRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Procession;

/*
 * CONTROL DE CAMBIOS ProcessionService.java
 * 
 * ALVARO 17/02/2019 12:02 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 16:35 AÑADIDO RECONSTRUIDOR PROCESSION Y REPARADO GENERADOR DE TICKETS
 */

@Service
@Transactional
public class ProcessionService {

	//Managed Repository -------------------	

	@Autowired
	private ProcessionRepository	processionRepository;

	//Supporting services ------------------
	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	Validator						validator;


	//Simple CRUD Methods ------------------

	public Procession create() {

		final Procession procession = new Procession();
		final UserAccount login = LoginService.getPrincipal();
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		procession.setBrotherhood(brotherhood);
		return procession;

	}

	public String randomTicker(final Procession procession) {
		final String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			final int randomInt = new SecureRandom().nextInt(characterSet.length());
			sb.append(characterSet.substring(randomInt, randomInt + 1));
		}
		final Date date = procession.getMoment();
		final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		final String dateConvert = sdf.format(date);

		return dateConvert.replaceAll("-", "") + "-" + sb.toString();
	}
	public Collection<Procession> findAll() {
		return this.processionRepository.findAll();
	}

	public Procession findOne(final int id) {
		return this.processionRepository.findOne(id);
	}
	public Procession save(final Procession procession) {
		if (procession.getTicker() != null && procession.getTicker().length() > 0) {
			// CONSERVO SU ANTERIOR TICKER
		} else
			procession.setTicker(this.randomTicker(procession));
		/*
		 * Ya que no le podemos pasar nada al create porque el reconstruidor hace que se lo cargue decidimos colocar
		 * aquí la asignación del brotherhood y será en el controlador donde se vigile que la edición la realiza el creador
		 * de esa procession
		 */
		procession.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
		return this.processionRepository.save(procession);
	}

	public void delete(final Procession procession) {
		Assert.notNull(this.processionRepository.findOne(procession.getId()), "La procession no existe");
		Assert.isTrue(LoginService.getPrincipal().getId() == procession.getBrotherhood().getUserAccount().getId(), "brotherhoodLoggerDiferent");
		this.processionRepository.delete(procession);
	}

	public Procession update(final Procession procession) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		Assert.isTrue(procession.getBrotherhood().equals(brotherhood));
		Assert.isTrue(this.findOne(procession.getId()) != null);
		final Procession saveProcession = this.processionRepository.save(procession);
		return saveProcession;
	}

	//Other Methods

	public Collection<Procession> getProcessionByBrotherhoodId(final int brotherhoodId) {
		return this.processionRepository.findProcessionsByBrotherhood(brotherhoodId);
	}

	public Collection<Procession> findAllBrotherhoodLogged() {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		return this.processionRepository.findProcessionsByBrotherhood(brotherhood.getId());
	}

	public Procession show(final int processionId) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhoodLogin = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		Assert.isTrue(this.findOne(processionId) != null);
		final Brotherhood brotherhoodProcession = this.findOne(processionId).getBrotherhood();
		Assert.isTrue(brotherhoodLogin.equals(brotherhoodProcession));
		return this.findOne(processionId);
	}

	public Procession reconstruct(final Procession procession, final BindingResult binding) {
		Procession result;

		if (procession.getId() == 0)
			result = procession;
		else {
			result = this.processionRepository.findOne(procession.getId());
			result.setTitle(procession.getTitle());
			result.setDescription(procession.getDescription());
			result.setMoment(procession.getMoment());
			result.setIsFinal(procession.getIsFinal());
			this.validator.validate(procession, binding);
		}
		return result;
	}
}
