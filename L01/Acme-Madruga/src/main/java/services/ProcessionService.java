
package services;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProcessionRepository;
import security.LoginService;
import security.UserAccount;
import auxiliar.PositionAux;
import domain.Brotherhood;
import domain.Procession;

/*
 * CONTROL DE CAMBIOS ProcessionService.java
 * 
 * ALVARO 17/02/2019 12:02 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 16:35 AÑADIDO RECONSTRUIDOR PROCESSION Y REPARADO GENERADOR DE TICKETS
 */

@Service
public class ProcessionService {

	//Managed Repository -------------------	

	@Autowired
	private ProcessionRepository	processionRepository;

	//Supporting services ------------------
	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	Validator						validator;

	@Autowired
	PositionAuxService				positionAuxService;


	//Simple CRUD Methods ------------------

	public Procession create() {

		final Procession procession = new Procession();
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
		final Procession processionUpdate = this.processionRepository.save(procession);
		if (procession.getIsFinal().equals(true))
			for (int i = 0; i < procession.getMaxRow() - 1; i++) {
				final PositionAux positionAux1 = this.positionAuxService.create();
				positionAux1.setColum(1);
				positionAux1.setRow(i);
				positionAux1.setProcession(processionUpdate);
				positionAux1.setStatus(false);
				this.positionAuxService.save(positionAux1);

				final PositionAux positionAux2 = this.positionAuxService.create();
				positionAux2.setColum(2);
				positionAux2.setRow(i);
				positionAux2.setProcession(processionUpdate);
				positionAux2.setStatus(false);
				this.positionAuxService.save(positionAux2);
			}
		return processionUpdate;
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

		if (procession.getId() == 0) {
			procession.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
			if (procession.getMoment() != null)
				procession.setTicker(this.randomTicker(procession));
			result = procession;
		} else {
			result = this.processionRepository.findOne(procession.getId());
			result.setTitle(procession.getTitle());
			result.setDescription(procession.getDescription());
			result.setMoment(procession.getMoment());
			result.setIsFinal(procession.getIsFinal());
			result.setMaxRow(procession.getMaxRow());
			result.setFloatBro(procession.getFloatBro());
			if (procession.getMoment() != null && result.getTicker() == null)
				result.setTicker(this.randomTicker(procession));
			if (result.getBrotherhood() == null)
				result.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
		}
		this.validator.validate(result, binding);
		return result;
	}
}
