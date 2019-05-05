
package rookiesTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Audit;
import domain.Auditor;
import domain.Position;
import services.AuditService;
import services.AuditorService;
import services.PositionService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditServiceTest extends AbstractTest {

	@Autowired
	private AuditService auditService;

	@Autowired
	private AuditorService auditorService;
	
	@Autowired
	private PositionService positionService;


	// Tests ------------------------------------------------------------------

	@Test
	public void test01() {
		/*
		 * CREATE, EDIT AND DELETE AUDIT
		 * 
		 * In this test we will test the creation and delete of a new audit
		 * 
		 * Information Requirements
		 * 
		 * Auditors write audits on the positions published by the companies. For every audit, the sys-tem must store the moment when it's written, 
		 * a piece of text, and a score in range 0..10 points.
		 * 
		 * 
		 * Funcional Requirements
		 * An actor who is authenticated as an auditor must be able to:
		 * 
		 *		1. Self-assign a position to audit it. 
		 *		2. Manage his or her audits, which includes listing them, showing them, creating them, updating, and deleting them. An audit can be 
		 *		   updated or deleted as long as it's saved in draft mode.
		 * 
		 * Analysis of sentence coverage
		 * 80%
		 * Analysis of data coverage
		 * 12%
		 */
		final Object testingData[][] = { 
			{
				// Positive
				"auditor", "Hola bro", 3l, false, null
			}, {
				"auditor", "Hola bro", 3l, true, null
			}, {
				// Negative
				"auditor", "Hola bro", -8l, false, IllegalArgumentException.class
			}, {
				"auditor", "Hola bro", 11l, false, IllegalArgumentException.class
			}, {
				"admin", "Hola bro", 6l, false, NullPointerException.class
			}, {
				"auditor", null, 6l, false, IllegalArgumentException.class
			}, {
				"auditor", "Hola bro", 7l, true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest01((String) testingData[i][0], (String) testingData[i][1], (Long) testingData[i][2], (Boolean) testingData[i][3], (Class<?>) testingData[i][4]);
	}


	protected void checkTest01(final String userName, final String text, final Long score, final Boolean status, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);
			Collection<Audit> audits = this.auditService.findAllByAuditorLogin(this.auditorService.getAuditorLogin().getId()).get(false);
			this.auditService.delete(audits.iterator().next());
			Position position = ((List<Position>) this.positionService.findAllPositionWithStatusTrueNotCancelNotAudit()).get(0);
			Audit audit = this.auditService.create();
			audit.setAuditor(this.auditorService.getAuditorLogin());
			audit.setPosition(position);
			audit.setScore(BigDecimal.valueOf(score));
			audit.setText(text);
			audit.setStatus(status);
			audit.setCreationMoment(DateTime.now().toDate());
			Audit saveAudit = this.auditService.save(audit);
			if (expected!=null) {				
				this.auditService.delete(saveAudit);
			}
			
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void test02() {
		/*
		 * LIST AND SHOW AUDIT
		 * 
		 * In this test we will test list audit and showing it.
		 * 
		 * Information Requirement
		 * 
		 * Auditors write audits on the positions published by the companies. For every audit, the sys-tem must store the moment when it's written, 
		 * a piece of text, and a score in range 0..10 points.
		 * 
		 * Funtional Requirements
		 * 
		 * An actor who is authenticated as an auditor must be able to:
		 * 
		 *		1. Self-assign a position to audit it. 
		 *		2. Manage his or her audits, which includes listing them, showing them, creating them, updating, and deleting them. An audit can be 
		 *		   updated or deleted as long as it's saved in draft mode.
		 * 
		 * Analysis of sentence coverage
		 * 75%
		 * Analysis of data coverage
		 * 80%
		 */
		final Object testingData[][] = { 
			{
				// Positive
				"auditor", true, null
			}, {
				"company", false, null
			}, {
				"rookie", false, null
			}, {
				// Negative
				"company", true, IllegalArgumentException.class
			}, {
				"rookie", true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest02((String) testingData[i][0], (Boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}


	protected void checkTest02(final String userName, final Boolean show, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			
			super.authenticate("auditor");
			Collection<Audit> audits = this.auditService.findAllByAuditorLogin(this.auditorService.getAuditorLogin().getId()).get(false);
			this.auditService.delete(audits.iterator().next());
			Position position = ((List<Position>) this.positionService.findAllPositionWithStatusTrueNotCancelNotAudit()).get(0);
			Auditor auditor = ((List<Auditor>) this.auditorService.findAll()).get(0);
			Audit audit = this.auditService.create();
			audit.setAuditor(auditor);
			audit.setPosition(position);
			audit.setScore(BigDecimal.valueOf(8l));
			audit.setText("El Viso del Alcor S. L.");
			audit.setStatus(false);
			audit.setCreationMoment(DateTime.now().toDate());
			Audit saveAudit = this.auditService.save(audit);
			Collection<Audit> auditsList = new ArrayList<Audit>();
			// Get all audit created by the auditor with the username "auditor". Audit can be in final mode and in draft mode
			for (Collection<Audit> aux : this.auditService.findAllByAuditorLogin(auditor.getId()).values()) {
				auditsList.addAll(aux);
			}
			super.unauthenticate();
			
			super.authenticate(userName);
			this.auditorService.findOne(saveAudit.getId());
			if (show.equals(true)) { // Try to show all audit, draft mode audits are incluyed				
				for (Audit auditToShow : auditsList) {
					/*
					 * If user try to show a draft audit and he not is his owner
					 * a IllegalArgumentException appear
					 */
					this.auditService.findOne(auditToShow.getId());
				}
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}
}
