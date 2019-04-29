package services;

/**
 * AuditService.java
 * 
 * @author Alvaro de la Flor Bonilla GitHub: alvar017
 * 
 *         CONTROL:
 *         29/04/2019 14:00 Creation
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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
	
	@Autowired
	private Validator						validator;
	
	// Default messages
	private String notAuditorLogin = "No auditor is login";
	private String notDraftMode = "Audit is not in draft mode";
	private String notAuditor = "Audit has not got auditor";
	private String diferentAuditor = "Loger Auditor is not the owner of the audit";


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
	 * Find an audit by ID
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Audit}
	 */
	public Audit findOne(Integer auditId) {
		return this.auditRepository.findOne(auditId);
	}

	/**
	 * Return all audit in DataBase
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection} < {@link Audit} > 
	 */
	public Collection<Audit> findAll() {
		return this.auditRepository.findAll();
	}

	
	/**
	 * Return a map, audit in final mode has de key true, draft audits has the key false
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Map}< {@link Boolean} , {@link Audit} >
	 */
	public Map<Boolean, Collection<Audit>> findAllByAuditorLogin(Integer auditorId) {
		Auditor auditor = null;
		if (auditorId == -1) {
			auditor = this.auditorService.getAuditorLogin();			
		} else {
			auditor = this.auditorService.findOne(auditorId);
		}
		Assert.notNull(auditor, notAuditorLogin);
		Map<Boolean, Collection<Audit>> res = new HashMap<>();
		Collection<Audit> finalMode = new ArrayList<>(this.getAuditByStatusAndAuditorId(true, auditor.getId()));
		Collection<Audit> draftMode = new ArrayList<>(this.getAuditByStatusAndAuditorId(false, auditor.getId()));
		res.put(true, finalMode);
		res.put(false, draftMode);
		return res;
	}
	
	/**
	 * Save an audit and return it. Must exist a {@link Auditor} login to create it.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Audit}
	 */
	public Audit save(Audit audit) {
		Auditor auditorLogin = this.auditorService.getAuditorLogin();
		Assert.notNull(auditorLogin, notAuditorLogin);
		Assert.notNull(audit.getPosition(), "Audit has not got position");
		Assert.notNull(audit.getAuditor(), notAuditor);
		Assert.isTrue(audit.getAuditor().equals(auditorLogin), diferentAuditor);
		audit.setAuditor(auditorLogin);
		audit.setCreationMoment(DateTime.now().toDate());
		return this.auditRepository.save(audit);
	}
	
	/**
	 * Must exist a {@link Auditor} login to delete and he must be de owner of the audit.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */	
	public void delete(Audit audit) {
		Auditor auditorLogin = this.auditorService.getAuditorLogin();
		Assert.notNull(auditorLogin, notAuditorLogin);
		Assert.notNull(audit.getAuditor(), notAuditor);
		Assert.isTrue(audit.getStatus().equals(false), notDraftMode);
		Assert.isTrue(audit.getAuditor().equals(auditorLogin), diferentAuditor);
		this.auditRepository.delete(audit);
	}
	
	// CRUD Methods

	// AUXILIAR METHODS

	
	/**
	 * Return the reconstruct Audit. Must exits an auditor login. <br>
	 * If the audit exits in database it must be in final mode and the login Auditor must be his owner.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Audit}
	 */
	public Audit reconstruct(Audit audit, BindingResult binding) {
		Auditor auditorLogin = this.auditorService.getAuditorLogin();
		Assert.notNull(auditorLogin, notAuditorLogin);
		
		if (audit.getId()==0) {
			audit.setAuditor(auditorLogin);
			audit.setCreationMoment(DateTime.now().toDate());
		} else {
			Audit auditDB = this.findOne(audit.getId());
			Assert.notNull(auditDB, "No audit in database with that ID");
			Assert.isTrue(auditDB.getStatus().equals(false), notDraftMode);
			Assert.isTrue(auditDB.getAuditor().equals(auditorLogin), diferentAuditor);
			audit.setVersion(auditDB.getVersion());
			audit.setAuditor(auditDB.getAuditor());
			audit.setCreationMoment(auditDB.getCreationMoment());
		}
		this.validator.validate(audit, binding);
		return audit;
	}

	/**
	 * Return a collection of audit filter by status and auditorId
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection} < {@link Audit} >
	 */
	public Collection<Audit> getAuditByStatusAndAuditorId(Boolean status, int auditorId) {
		return this.auditRepository.getAuditByStatusAndAuditorId(status, auditorId);
	}
	// AUXILIAR METHODS

}
