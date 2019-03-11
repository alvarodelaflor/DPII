
package services;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Brotherhood;
import domain.PositionAux;
import domain.Parade;
import repositories.ProcessionRepository;
import security.LoginService;
import security.UserAccount;

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

	@Autowired
	FinderService					finderService;


	//Simple CRUD Methods ------------------

	public Parade create() {

		final Parade procession = new Parade();
		return procession;

	}

	public String randomTicker(final Parade procession) {
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
			final Date date = DateTime.now().toDate();
			final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
			final String dateConvert = sdf.format(date);
			ticker = dateConvert.replaceAll("-", "") + "-" + sb.toString();
			if (this.processionRepository.findProcessionsByTicker(ticker).isEmpty())
				res = false;
		}
		return ticker;
	}
	public Collection<Parade> findAll() {
		return this.processionRepository.findAll();
	}

	public Parade findOne(final int id) {
		return this.processionRepository.findOne(id);
	}
	public Parade save(final Parade procession) {
		final Parade processionUpdate = this.processionRepository.save(procession);
		List<PositionAux> positionAuxs = new ArrayList<PositionAux>();
		if (procession.getIsFinal().equals(true))
			for (int i = 0; i < procession.getMaxRow(); i++)
				for (int j = 0; j < procession.getMaxColum(); j++) {
					final PositionAux positionAux = this.positionAuxService.create();
					positionAux.setRow(i);
					positionAux.setColum(j);
					positionAux.setProcession(processionUpdate);
					positionAux.setStatus(false);
					positionAuxs.add(positionAux);
				}
		this.positionAuxService.saveAll(positionAuxs);
		return processionUpdate;
	}

	public void delete(final Parade procession) {
		Assert.notNull(this.processionRepository.findOne(procession.getId()), "La procession no existe");
		Assert.isTrue(LoginService.getPrincipal().getId() == procession.getBrotherhood().getUserAccount().getId(), "brotherhoodLoggerDiferent");
		this.requestService.deleteAllRequestByProcession(procession.getId());
		this.positionAuxService.deleteAllPositionByProcession(procession.getId());
		this.finderService.updateProcessions(procession);
		this.processionRepository.delete(procession);
	}

	public Parade update(final Parade procession) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		Assert.isTrue(procession.getBrotherhood().equals(brotherhood));
		Assert.isTrue(this.findOne(procession.getId()) != null);
		final Parade saveProcession = this.processionRepository.save(procession);
		return saveProcession;
	}

	//Other Methods

	public Collection<Parade> getProcessionByBrotherhoodId(final int brotherhoodId) {
		return this.processionRepository.findProcessionsByBrotherhood(brotherhoodId);
	}

	public Collection<Parade> getProcessionByFloatId(final int floatId) {
		return this.processionRepository.findProcessionsByFloat(floatId);
	}

	public Collection<Parade> findAllBrotherhoodLogged() {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		return this.processionRepository.findProcessionsByBrotherhood(brotherhood.getId());
	}

	public Parade show(final int processionId) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhoodLogin = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		Assert.isTrue(this.findOne(processionId) != null);
		final Brotherhood brotherhoodProcession = this.findOne(processionId).getBrotherhood();
		Assert.isTrue(brotherhoodLogin.equals(brotherhoodProcession));
		return this.findOne(processionId);
	}

	public Parade reconstruct(final Parade procession, final BindingResult binding) {
		Parade result;

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
			//			result.setFloat(procession.getFloat());
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

	public Collection<Parade> findProcessionsByTicker(final String ticker) {
		return this.processionRepository.findProcessionsByTicker(ticker);
	}

	public Collection<Parade> processionOrganised() {
		return this.processionRepository.findAllWithCreationDateTimeBeforeI(LocalDateTime.now().toDate(), this.sumarMes(LocalDateTime.now().toDate()));
	}

	@SuppressWarnings("deprecation")
	private Date sumarMes(final Date fecha) {
		final Date res = LocalDateTime.now().toDate();
		if (res.getMonth() == 11) {
			res.setYear(res.getYear() + 1);
			res.setMonth(1);
		} else
			res.setMonth(res.getMonth() + 1);

		System.out.println(res);
		return res;
	}

	public String minProcession() {
		final Parade p = this.processionRepository.minProcession();
		return p == null ? null : p.getTitle();
	}
	public String maxProcession() {
		final Parade p = this.processionRepository.maxProcession();
		return p == null ? null : p.getTitle();
	}
	public Integer minProcessionN() {
		return this.processionRepository.minProcessionN();
	}
	public Integer maxProcessionN() {
		return this.processionRepository.maxProcessionN();
	}

	public Collection<Parade> findProcessionsBrotherhoodFinal(final Integer brotherhood) {
		return this.processionRepository.findProcessionsBrotherhoodFinal(brotherhood);
	}
}
