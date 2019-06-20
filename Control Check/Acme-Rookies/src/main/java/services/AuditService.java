
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

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Service
@Transactional
public class AuditService {

	// Repositories
	@Autowired
	private AuditRepository	auditRepository;

	// Services
	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private PositionService	positionService;

	@Autowired
	private QuoletService	quoletService;

	@Autowired
	private Validator		validator;

	// Default messages
	private final String	notAuditorLogin	= "No auditor is login";
	private final String	notDraftMode	= "Audit is not in draft mode";
	private final String	notAuditor		= "Audit has not got auditor";
	private final String	diferentAuditor	= "Loger Auditor is not the owner of the audit";


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
	public Audit findOne(final Integer auditId) {
		final Audit audit = this.auditRepository.findOne(auditId);
		final Auditor auditor = this.auditorService.getAuditorLogin();
		if (audit != null && audit.getStatus() != null && audit.getStatus().equals(false)) {
			Assert.notNull(auditor, this.notAuditorLogin);
			Assert.isTrue(audit.getAuditor().equals(auditor), this.diferentAuditor);
		}
		return audit;
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
	public Map<Boolean, Collection<Audit>> findAllByAuditorLogin(final Integer auditorId) {
		Auditor auditor = null;
		if (auditorId == -1)
			auditor = this.auditorService.getAuditorLogin();
		else
			auditor = this.auditorService.findOne(auditorId);
		Assert.notNull(auditor, this.notAuditorLogin);
		final Map<Boolean, Collection<Audit>> res = new HashMap<>();
		final Collection<Audit> finalMode = new ArrayList<>(this.getAuditByStatusAndAuditorId(true, auditor.getId()));
		final Collection<Audit> draftMode = new ArrayList<>(this.getAuditByStatusAndAuditorId(false, auditor.getId()));
		res.put(true, finalMode);
		res.put(false, draftMode);
		return res;
	}

	/**
	 * Get all Position available by an audit
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection} < {@link Position} >
	 */
	public Collection<Position> getPositionAvailable(final Audit audit) {
		final Collection<Position> res = new ArrayList<>(this.positionService.findAllPositionWithStatusTrueCancelFalse());
		final Auditor auditor = this.auditorService.getAuditorLogin();
		Assert.notNull(auditor, this.notAuditor);
		res.removeAll(this.positionService.findAllPositionByAuditor(auditor.getId()));
		final Audit auditDB = this.findOne(audit.getId());
		if (audit.getPosition() != null && auditDB != null && audit.getStatus() != null && audit.getStatus().equals(false)) {
			Assert.isTrue(auditDB.getPosition().equals(audit.getPosition()) || res.contains(audit.getPosition()));
			res.add(auditDB.getPosition());
		} else if (auditDB != null && auditDB.getPosition() != null)
			res.add(auditDB.getPosition());
		return res;
	}

	/**
	 * Save an audit and return it. Must exist a {@link Auditor} login to create it.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Audit}
	 */
	public Audit save(final Audit audit) {
		final Auditor auditorLogin = this.auditorService.getAuditorLogin();
		Assert.notNull(auditorLogin, this.notAuditorLogin);
		Assert.notNull(audit, "Audit is null");
		Assert.isTrue(audit.getStatus() != null && (audit.getStatus().equals(true) || audit.getStatus().equals(false)));
		Assert.notNull(audit.getPosition(), "Audit has not got position");
		Assert.notNull(audit.getAuditor(), this.notAuditor);
		Assert.isTrue(audit.getAuditor().equals(auditorLogin), this.diferentAuditor);
		final List<Position> positionsByAudit = (List<Position>) this.positionService.findAllPositionByAuditor(auditorLogin.getId());
		final Audit auditDB = this.findOne(audit.getId());
		if (auditDB != null)
			Assert.isTrue(!auditDB.getStatus(), this.notDraftMode);
		else
			Assert.isTrue(!positionsByAudit.contains(audit.getPosition()), "Audit has an already exits position");
		if (audit.getScore() != null) {
			Assert.isTrue(audit.getScore().signum() != -1, "Value is less than 0");
			Assert.isTrue(audit.getScore().doubleValue() <= 10., "Value is more than 10");
			Assert.isTrue(audit.getScore().precision() == 1 || audit.getScore().precision() == 2 || audit.getScore().precision() == 3, "Not valid precision");
		}
		Assert.isTrue(audit.getText() != null && audit.getText().length() > 0, "No valid text");
		audit.setAuditor(auditorLogin);
		return this.auditRepository.save(audit);
	}

	/**
	 * Must exist a {@link Auditor} login to delete and he must be de owner of the audit.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void delete(final Audit audit) {
		final Auditor auditorLogin = this.auditorService.getAuditorLogin();
		Assert.notNull(auditorLogin, this.notAuditorLogin);
		Assert.notNull(audit.getAuditor(), this.notAuditor);
		Assert.isTrue(audit.getStatus().equals(false), this.notDraftMode);
		Assert.isTrue(audit.getAuditor().equals(auditorLogin), this.diferentAuditor);
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
	public Audit reconstruct(final Audit audit, final BindingResult binding) {
		final Auditor auditorLogin = this.auditorService.getAuditorLogin();
		Assert.notNull(auditorLogin, this.notAuditorLogin);

		if (audit.getId() == 0) {
			audit.setAuditor(auditorLogin);
			audit.setCreationMoment(DateTime.now().toDate());
		} else {
			final Audit auditDB = this.findOne(audit.getId());
			Assert.notNull(auditDB, "No audit in database with that ID");
			Assert.isTrue(auditDB.getStatus().equals(false), this.notDraftMode);
			Assert.isTrue(auditDB.getAuditor().equals(auditorLogin), this.diferentAuditor);
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
	public Collection<Audit> getAuditByStatusAndAuditorId(final Boolean status, final int auditorId) {
		return this.auditRepository.getAuditByStatusAndAuditorId(status, auditorId);
	}

	/**
	 * Return an audit of a position
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection} < {@link Audit} >
	 */
	public Collection<Audit> getAuditByPositionId(final int positionId) {
		return this.auditRepository.findAllAuditByPositionId(positionId);
	}
	// AUXILIAR METHODS

	public void deleteAuditorAudits(final Auditor auditor) {
		final Collection<Collection<Audit>> audits = this.findAllByAuditorLogin(auditor.getId()).values();
		for (final Collection<Audit> collection : audits) {
			for (final Audit audit : collection)
				this.quoletService.deleteAuditQuolets(audit);
			this.auditRepository.deleteInBatch(collection);
		}
	}

	public void deleteAllByPosition(final int id) {
		final Collection<Audit> apps = this.auditRepository.getPositionApps(id);
		for (final Audit audit : apps)
			this.quoletService.deleteAuditQuolets(audit);
		this.auditRepository.deleteInBatch(apps);
	}
}
