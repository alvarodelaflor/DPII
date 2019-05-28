package unViaje;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Mailbox;
import domain.Message;
import domain.Tag;
import services.MailboxService;
import services.MessageService;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MailboxServiceTest extends AbstractTest{
	
	@Autowired
	private MailboxService mailboxService;
	
	@Test
	public void diverCreateMailbox() throws ParseException {
		

		final Object testingData[][] = {

				{ "mailboxName", true, IllegalArgumentException.class
			}, {
				"mailboxName", false, null
			}, {
				"inBox", true, IllegalArgumentException.class
			}, {
				"inBox", false, null
			} };

		for (int i = 0; i < testingData.length; i++)
			this.diverCreateMailbox((String) testingData[i][0], (Boolean) testingData[i][1],(Class<?>) testingData[i][2]);

	}
	
	protected void diverCreateMailbox(final String name, final Boolean isDefault, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			
			super.authenticate("admin");

			final Mailbox mailbox = mailboxService.create();
			mailbox.setIsDefault(isDefault);
			mailbox.setName(name);
			
			Mailbox saveMailbox = mailboxService.save(mailbox);
			
			saveMailbox.setName(name + "tryFail");
			
			Mailbox saveMailBox2 = mailboxService.update(saveMailbox);
			
			mailboxService.delete(saveMailBox2);
								
			this.flushTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

}
