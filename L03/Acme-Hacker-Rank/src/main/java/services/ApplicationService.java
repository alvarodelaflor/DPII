
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import security.LoginService;
import domain.Application;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private Validator				validator;


	// DashBoard:
	public Float avgApplicationPerHacker() {

		return this.applicationRepository.avgApplicationPerHacker();
	}

	public Float minApplicationPerHacker() {

		return this.applicationRepository.minApplicationPerHacker();
	}

	public Float maxApplicationPerHacker() {

		return this.applicationRepository.maxApplicationPerHacker();
	}

	public Float stddevApplicationPerHacker() {

		return this.applicationRepository.stddevApplicationPerHacker();
	}

	public String findHackerWithMoreApplications() {

		final List<String> ls = this.applicationRepository.findHackerWithMoreApplications();
		String res = "";
		if (!ls.isEmpty())
			res = ls.get(0);
		return res;
	}

	// FINDALL  ---------------------------------------------------------------	
	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	// getApplicationsByHacker  ---------------------------------------------------------------	
	public Collection<Application> getApplicationsByHacker(final int id) {
		return this.applicationRepository.getApplicationsByHacker(id);
	}

	// FINDONE  ---------------------------------------------------------------	
	public Application findOne(final int id) {
		return this.applicationRepository.findOne(id);
	}

	// CREATE ---------------------------------------------------------------		
	public Application create() {
		final Application application = new Application();

		application.setCreationMoment(LocalDateTime.now().toDate());
		application.setHacker(this.hackerService.getHackerByUserAccountId(LoginService.getPrincipal().getId()));

		Assert.isTrue(this.hackerService.getHackerByUserAccountId(LoginService.getPrincipal().getId()) != null);

		return application;
	}
	
	// SAVE ---------------------------------------------------------------		
	public Application save(final Application a) {
		return this.applicationRepository.save(a);
	}


}
