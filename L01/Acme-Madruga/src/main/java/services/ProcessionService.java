
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
 * ALVARO 17/02/2019 12:02 CREACI�N DE LA CLASE
 * ALVARO 17/02/2019 16:35 A�ADIDO RECONSTRUIDOR PROCESSION Y REPARADO GENERADOR DE TICKETS
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

	@Autowired
	RequestService					requestService;


	//Simple CRUD Methods ------------------

	public Procession create() {

		final Procession procession = new Procession();
		return procession;

	}

	public String randomTicker(final Procession procession) {
		String ticker = "";
		Boolean res = true;
		while (res) {
			ticker = "";
			final String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 6; i++) {
				final int randomInt = new SecureRandom().nextInt(characterSet.length());
				sb.append(characterSet.substring(randomInt, randomInt + 1));
			}
			final Date date = procession.getMoment();
			final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
			final String dateConvert = sdf.format(date);
			ticker = dateConvert.replaceAll("-", "") + "-" + sb.toString();
			if (this.processionRepository.findProcessionsByTicker(ticker).isEmpty())
				res = false;
		}
		return ticker;
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
			for (int i = 0; i < procession.getMaxRow() - 1; i++)
				for (int j = 0; j < procession.getMaxColum() - 1; j++) {
					final PositionAux positionAux = this.positionAuxService.create();
					positionAux.setRow(i);
					positionAux.setColum(j);
					positionAux.setProcession(processionUpdate);
					positionAux.setStatus(false);
					this.positionAuxService.save(positionAux);
				}
		return processionUpdate;
	}

	public void delete(final Procession procession) {
		Assert.notNull(this.processionRepository.findOne(procession.getId()), "La procession no existe");
		Assert.isTrue(LoginService.getPrincipal().getId() == procession.getBrotherhood().getUserAccount().getId(), "brotherhoodLoggerDiferent");
		this.requestService.deleteAllRequestByProcession(procession.getId());
		this.positionAuxService.deleteAllPositionByProcession(procession.getId());
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

	public Collection<Procession> getProcessionByFloatId(final int floatId) {
		return this.processionRepository.findProcessionsByFloat(floatId);
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
			//			result.setTitle(procession.getTitle());
			//			result.setDescription(procession.getDescription());
			//			result.setMoment(procession.getMoment());
			//			result.setIsFinal(procession.getIsFinal());
			//			result.setMaxRow(procession.getMaxRow());
			//			result.setFloatBro(procession.getFloatBro());
			//			if (procession.getMoment() != null && result.getTicker() == null)
			//				result.setTicker(this.randomTicker(procession));
			//			if (result.getBrotherhood() == null)
			procession.setId(result.getId());
			procession.setVersion(result.getVersion());
			procession.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
			procession.setMoment(result.getMoment());
			procession.setTicker(result.getTicker());
			result = procession;
		}
		this.validator.validate(result, binding);
		return result;
	}

	public Collection<Procession> findProcessionsByTicker(final String ticker) {
		return this.processionRepository.findProcessionsByTicker(ticker);
	}
}
