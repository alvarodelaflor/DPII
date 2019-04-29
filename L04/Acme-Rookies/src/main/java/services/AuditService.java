package services;

/**
 * AuditService.java
 * 
 * @author Alvaro de la Flor Bonilla GitHub: alvar017
 * 
 *         CONTROL:
 *         29/04/2019 14:00 Creation
 */

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Audit;
import domain.Auditor;
import repositories.AuditRepository;

@Service
@Transactional
public class AuditService {
	
	// Repositories
	@Autowired
	private AuditRepository auditRepository;
	
	// Services
	@Autowired
	private AuditorService auditorService;


	// CRUD Methods

	/**
	 * Create an audit. Must exist a {@link Auditor} login to create it.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Audit}
	 */
	public Audit create() {
		Assert.notNull(this.auditorService.getAuditorLogin());
		return new Audit();
	}
	
	/**
	 * Save an audit and return it. Must exist a {@link Auditor} login to create it.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Audit}
	 */
	public Audit save(Audit audit) {
		Auditor auditorLogin = this.auditorService.getAuditorLogin();
		Assert.notNull(auditorLogin, "No auditor is login");
		Assert.notNull(audit.getPosition(), "Position have not got position");
		audit.setAuditor(auditorLogin);
		audit.setCreationMoment(DateTime.now().toDate());
		return this.auditRepository.save(audit);
	}
	
	

	// CRUD Methods

	// AUXILIAR METHODS

	
	// AUXILIAR METHODS

}
