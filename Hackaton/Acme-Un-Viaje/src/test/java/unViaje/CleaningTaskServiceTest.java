package unViaje;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.CleaningTask;
import domain.CreditCard;
import domain.Customer;
import domain.SocialProfile;
import security.LoginService;
import services.AccomodationService;
import services.CleanerService;
import services.CleaningTaskService;
import services.MessageService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CleaningTaskServiceTest extends AbstractTest{
	
	@Autowired
	private CleaningTaskService cleaningTaskService;
	
	@Autowired
	private CleanerService cleanerService;
	
	@Autowired
	private AccomodationService accomodationService;
	
	
	@Test
	public void diver01() throws ParseException {
		
		SimpleDateFormat parseador = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date dateStart = parseador.parse("2019/09/11 11:00");
		Date dateEnd = parseador.parse("2019/09/13 11:00");
		Date dateEnd2 = parseador.parse("2019/09/10 11:00");

		final Object testingData[][] = {

				{ 
					392,dateStart,dateEnd,427, "descriptionTry", "hosthost", null
				}, {
					392, dateStart, dateEnd2,427, "descriptionTry","hosthost", IllegalArgumentException.class 
				}, {
					0, dateStart,dateEnd,427, "descriptionTry","hosthost", DataIntegrityViolationException.class 
				}, {
					392, dateStart, dateEnd,0, "descriptionTry","hosthost", DataIntegrityViolationException.class 
				},{ 
					392, dateStart,dateEnd,427, "descriptionTry", "cleaner", IllegalArgumentException.class 
				} };

		for (int i = 0; i < testingData.length; i++)
			this.diver01((int) testingData[i][0], (Date) testingData[i][1], (Date) testingData[i][2],(int) testingData[i][3],(String) testingData[i][4],(String) testingData[i][5],
					(Class<?>) testingData[i][6]);

	}
	
	protected void diver01(final int cleaner, final Date startDate, final Date endDate,final int accomodation, final String description, final String user, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			
			this.authenticate(user);
			
			CleaningTask cleaningTask = cleaningTaskService.create();
			cleaningTask.setCleaner(cleanerService.findOne(cleaner));
			cleaningTask.setStartMoment(startDate);
			cleaningTask.setEndMoment(endDate);
			cleaningTask.setAccomodation(accomodationService.findOne(accomodation));
			cleaningTask.setDescription(description);
			
			CleaningTask cleaningTaskSave = cleaningTaskService.save(cleaningTask);
			
			Assert.isTrue(cleaningTaskService.getCleaningTaskCleaner(cleaner).contains(cleaningTaskSave));
			Assert.isTrue(cleaningTaskService.getCleaningTaskHost(accomodationService.findOne(accomodation).getHost().getId()).contains(cleaningTaskSave));
						
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

}
