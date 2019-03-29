
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ApplicationRepository;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepository;


	//DashBoard:
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
}
