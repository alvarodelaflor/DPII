
package rookiesTest;

import java.math.BigDecimal;
import java.util.ArrayList;
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


	/*
	 * IR
	 * Auditors write audits on the positions published by the companies. For every audit, the system must store the moment when 
	 * it's written, a piece of text, and a score in range 0..10 points.
	 * 
	 * FR
	 * 
	 * Create an audit
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	@Test
	public void Driver01() {
		this.authenticate("auditor");
		Position position = new ArrayList<>(this.positionService.findAllPositionWithStatusTrueNotCancelNotAudit()).get(0);
		this.unauthenticate();
		
		final Object testingData[][] = {
			{ // Positive
				"auditor", "Hola bro", 2, false,  position ,null
			}, {
				"auditor", "Hola bro", 2.4, true, position ,null
			}, {
				"auditor", "Hola bro", 2.15, true, position ,null
			}, { // Negative
				"auditor", "Hola bro", -8, true, position, IllegalArgumentException.class
			}, {
				"auditor", "Hola bro", -0.1, true, position, IllegalArgumentException.class
			}, {
				"auditor", "Hola bro", -0.18, true, position, IllegalArgumentException.class
			}, {
				"auditor", "Hola bro", -0.189, true, position, IllegalArgumentException.class
			}, {
				"auditor", "Hola bro", 0.178, true, position, IllegalArgumentException.class
			}, {
				"auditor", "Hola bro", 11, true, position, IllegalArgumentException.class
			}, {
				"auditor", "Hola bro", 10.1, true, position, IllegalArgumentException.class
			}, {
				"auditor", "Hola bro", 10.12, true, position, IllegalArgumentException.class
			}, {
				"auditor", "Hola bro", 10.123, true, position, IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.checkDriver01((String) testingData[i][0], (String) testingData[i][1], (BigDecimal) testingData[i][2], (Boolean) testingData[i][3], (Position) testingData[i][4],(Class<?>) testingData[i][5]);
	}

	private void checkDriver01(final String user, String text, BigDecimal score, Boolean status, Position position, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);

			Audit savedAudit = this.auditService.save(this.createAudit(text, score, status, position));
			this.auditService.delete(savedAudit);
		} catch (final Exception e) {
			caught = e.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	private Audit createAudit(String text, BigDecimal score, Boolean status, Position position) {
		final Audit audit = this.auditService.create();
		final Auditor auditor = this.auditorService.getAuditorLogin();
		audit.setCreationMoment(DateTime.now().toDate());
		audit.setText(text);
		audit.setScore(score);
		audit.setStatus(status);
		audit.setAuditor(auditor);
		audit.setPosition(position);
		return audit;
	}
}
