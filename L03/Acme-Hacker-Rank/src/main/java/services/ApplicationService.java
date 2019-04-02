
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ApplicationRepository;
import domain.Application;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private HackerService			hackerService;


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
}
