
package rookiesTest;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import services.MessageService;
import services.RookieService;
import services.TagService;
import utilities.AbstractTest;
import domain.Message;
import domain.Rookie;
import domain.Tag;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	MessageService	msgService;

	@Autowired
	TagService		tagService;

	@Autowired
	RookieService	rookieService;


	////////////////////////////////////////////////////////////

	@Test
	/*
	 * Test Message create
	 * 
	 * POSITIVE TEST
	 * Actor creates a message
	 * 
	 * Requirement under test: 23 (Acme-Rookie-Rank)
	 * 
	 * Analysis of sentence coverage: 70%
	 * Analysis of data coverage: 85%
	 */
	public void messageCreate() {

		final Object testingData[][] = {

			{
				true, null
			}, {
				false, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.messageCreate((Boolean) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	/*
	 * Test Message save
	 * 
	 * POSITIVE TEST
	 * Actor saves a message
	 * 
	 * Requirement under test: 23 (Acme-Rookie-Rank)
	 * 
	 * Analysis of sentence coverage: 70%
	 * Analysis of data coverage: 70%
	 */
	public void messageSave() {

		final Object testingData[][] = {

			{
				null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.messageSave((Class<?>) testingData[i][0]);
	}

	@Test
	/*
	 * Test Message delete
	 * 
	 * NEGATIVES TEST
	 * Actor removes a message (message <- DELETED tag)
	 * Actor removes a message from the system
	 * 
	 * Requirement under test: 23 (Acme-Rookie-Rank)
	 * 
	 * Analysis of sentence coverage: 70%
	 * Analysis of data coverage: 85%
	 */
	public void messageDelete() {

		final Object testingData[][] = {

			{
				false, ConstraintViolationException.class
			}, {

				true, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.messageDelete((Boolean) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	////////////////////////////////////////////////////////////

	protected void messageDelete(final Boolean deleted, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("calle rookie test");
			rookie.setCreditCard(null);
			rookie.setEmail("elcejas@rookie.com");
			rookie.setName("rookiename");
			rookie.setPhone("123456789");
			rookie.setVatNumber("hh55g");
			rookie.setSurname("rookiesurname");
			rookie.getUserAccount().setUsername("rookieuser");
			rookie.getUserAccount().setPassword("rookiepass");
			this.rookieService.saveCreate(rookie);

			this.authenticate("admin");
			final Message msg = this.msgService.create();
			msg.setSubject("subject");
			msg.setBody("body");
			final Collection<String> recipients = new ArrayList<String>();
			recipients.add("elcejas@rookie.com");
			msg.setRecipient(recipients);
			final Collection<Tag> tags = new ArrayList<>();
			msg.setTags(tags);
			final Message savedMsg = this.msgService.save(msg);
			final Tag tag = this.tagService.create();
			tag.setActorId(LoginService.getPrincipal().getId());
			tag.setTag("Tagged");
			tag.setMessageId(savedMsg.getId());
			this.tagService.save(tag);
			savedMsg.getTags().add(tag);

			this.msgService.remove(savedMsg);
			for (final Tag tagged : savedMsg.getTags())
				Assert.isTrue(tagged.getTag().equals("DELETED"));
			if (deleted) {
				this.msgService.remove(savedMsg);
				Assert.isTrue(this.msgService.findOne(savedMsg.getId()) == null);
			}

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			this.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

	protected void messageSave(final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("calle rookie test");
			rookie.setCreditCard(null);
			rookie.setEmail("elcejas@rookie.com");
			rookie.setName("rookiename");
			rookie.setPhone("123456789");
			rookie.setSurname("rookiesurname");
			rookie.setVatNumber("hh55g");
			rookie.getUserAccount().setUsername("rookieuser");
			rookie.getUserAccount().setPassword("rookiepass");
			this.rookieService.saveCreate(rookie);

			this.authenticate("admin");
			final Message msg = this.msgService.create();
			msg.setSubject("subject");
			msg.setBody("body");
			final Collection<String> recipients = new ArrayList<String>();
			recipients.add("elcejas@rookie.com");
			msg.setRecipient(recipients);
			this.msgService.save(msg);
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			this.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

	protected void messageCreate(final Boolean logged, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			if (logged)
				this.authenticate("admin");

			Assert.isTrue(LoginService.getPrincipal() != null);
			this.msgService.create();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			this.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

}
