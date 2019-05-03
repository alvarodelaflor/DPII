
package rookiesTest;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import domain.Audit;
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
		 * POSITIVE TEST
		 * 
		 * In this test we will test the creation of records in a history that already had an inception Record.
		 * 
		 * 1. Brotherhoods can manage their histories. A history is composed of one inception record, ze-ro or more period records, zero or more legal records,
		 * zero or more link records, and zero or more miscellaneous records. For every record, the system must store its title and a piece of text that describes it.
		 * For every inception record, it must also store some photos; for every period record, it must also store a start year, an end year, and some photos; for every
		 * legal record, it must also store a legal name, a VAT number, and the applicable laws; for every link record, it must also store a link to another brotherhood
		 * with which the original brotherhood is linked.
		 * 
		 * 3. An actor who is authenticated as a brotherhood must be able to:
		 * 1. Manage their history, which includes listing, displaying, creating, updating, and de-leting its records.
		 * 
		 * Analysis of sentence coverage
		 * 92%
		 * Analysis of data coverage
		 * 99%
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
				"admin", "Hola bro", 6l, false, IllegalArgumentException.class
			}, {
				"auditor", null, 6l, false, IllegalArgumentException.class
			}, {
				"auditor", "Hola bro", 7l, true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest((String) testingData[i][0], (String) testingData[i][1], (Long) testingData[i][2], (Boolean) testingData[i][3], (Class<?>) testingData[i][4]);
	}


	protected void checkTest(final String userName, final String text, final Long score, final Boolean status, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);

			Position position = ((List<Position>) this.positionService.findAllPositionWithStatusTrueCancelFalse()).get(0);
			Audit audit = this.auditService.create();
			audit.setAuditor(this.auditorService.getAuditorLogin());
			audit.setCreationMoment(DateTime.now().toDate());
			audit.setPosition(position);
			audit.setScore(BigDecimal.valueOf(score));
			audit.setText(text);
			audit.setStatus(status);
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
}
