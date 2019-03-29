
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.HackerRepository;

@Service
@Transactional
public class HackerService {

	@Autowired
	private HackerRepository	hackerRepository;


	// DashBoard:
	public String findHackerWithMoreApplications() {

		return this.hackerRepository.findHackerWithMoreApplications();
	}
}
