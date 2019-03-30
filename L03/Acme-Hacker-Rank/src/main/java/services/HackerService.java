
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import domain.Hacker;
import repositories.HackerRepository;
import security.LoginService;

@Service
@Transactional
public class HackerService {

	@Autowired
	private HackerRepository	hackerRepository;


	// DashBoard:
	public String findHackerWithMoreApplications() {

		return this.hackerRepository.findHackerWithMoreApplications();
	}
	
	/**
	 * Find a hacker by his userAccount id.
	 * 
	 * @author Álvaro de la Flor Bonilla
	 * @return Hacker with the given userAccountId.
	 */
	public Hacker getHackerByUserAccountId(int userAccountId) {
		return this.hackerRepository.getHackerByUserAccountId(userAccountId);
	}
	
	/**
	 * Check that any user is login
	 * 
	 * @author Álvaro de la Flor Bonilla
	 * @return True if an user is login, false in otherwise.
	 */
	public Boolean checkAnyLogger() {
		Boolean res = true;
		try {
			LoginService.getPrincipal().getId();
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
	
	/**
	 * Get the hackerLogin. Must exits an user login.
	 * 
	 * @author Álvaro de la Flor Bonilla
	 * @return The hacker login. Null if the user not is a hacker.
	 */
	public Hacker getHackerLogin() {
		Hacker res;
		try {
			res = this.getHackerByUserAccountId(LoginService.getPrincipal().getId());
		} catch (Exception e) {
			res = null;
		}
		return res;
	}
}
