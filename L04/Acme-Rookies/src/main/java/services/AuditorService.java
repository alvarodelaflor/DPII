package services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import domain.Audit;
import domain.Auditor;
import repositories.AuditorRepository;
import security.LoginService;

@Service
@Transactional
public class AuditorService {

	@Autowired
	private AuditorRepository		auditorRepository;

	// CRUD METHODS

	/**
	 * Get an auditor by auditorId
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Audit}
	 */
	public Auditor findOne(int auditorId) {
		return this.auditorRepository.findOne(auditorId);
	}
	
	// CRUD METHODS
	
	// AUXILIAR METHODS
	
	/**
	 * Get an auditor by useraccount ID.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Audit}
	 */
	public Auditor getAuditorByUserAccountId(int userAccountId) {
		return this.auditorRepository.getAuditorByUserAccountId(userAccountId);
	}

	/**
	 * Get the auditor login.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Audit} if is login, null otherwise.
	 */
	public Auditor getAuditorLogin() {
		Auditor res;
		try {
			int aux = LoginService.getPrincipal().getId();
			res = this.getAuditorByUserAccountId(aux);
		} catch (Exception e) {
			res = null;
		}
		return res;
	}
	
	// AUXILIAR METHODS
}
