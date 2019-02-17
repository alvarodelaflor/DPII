
package services;

import java.security.SecureRandom;
import java.util.Collection;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Procession;

/*
 * CONTROL DE CAMBIOS ProcessionService.java
 * 
 * ALVARO 17/02/2019 12:02 CREACIÓN DE LA CLASE
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


	//Simple CRUD Methods ------------------

	public Procession create() {

		final Procession procession = new Procession();
		final UserAccount login = LoginService.getPrincipal();
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		procession.setBrotherhood(brotherhood);
		procession.setMoment(LocalDateTime.now().toDate());
		return procession;

	}

	public String randomTicker(final Procession procession) {
		final String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			final int randomInt = new SecureRandom().nextInt(characterSet.length());
			sb.append(characterSet.substring(randomInt, randomInt + 1));
		}
		return procession.getMoment().toString().replaceAll("-", "") + "-" + sb.toString();
	}

	public Collection<Procession> findAll() {
		return this.processionRepository.findAll();
	}

	public Procession findOne(final int id) {
		return this.processionRepository.findOne(id);
	}
	public Procession save(final Procession procession) {
		procession.setTicker(this.randomTicker(procession));
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
}
