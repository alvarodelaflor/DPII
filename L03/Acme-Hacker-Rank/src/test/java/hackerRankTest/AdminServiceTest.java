
package hackerRankTest;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ActorService;
import services.HackerService;
import services.MessageService;
import utilities.AbstractTest;
import domain.Hacker;
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdminServiceTest extends AbstractTest {

	@Autowired
	private HackerService	hackerService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	msgService;


	@Test
	/*
	 * Test SPAMMER checks for finding actors which send spam;
	 * 
	 * POSITIVE TEST
	 * Actor sends normal message and is not setted to spammer.
	 * 
	 * NEGATIVE TEST
	 * Actor sends spam and is setted to spammer.
	 * 
	 * Requirement under test: 31 (Acme-Madruga)
	 * 
	 * Analysis of sentence coverage: 90%
	 * Analysis of data coverage: 27%
	 */
	public void actorSpammer() {

		final Object testingData[][] = {

			{
				0, null
			}, {
				1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.actorSpammer((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	/*
	 * Test BAN actor;
	 * 
	 * POSITIVE TEST
	 * Logged Admin tries to ban a spammer.
	 * Logged Admin tries to ban an actor which have bad polarity.
	 * 
	 * NEGATIVE TEST
	 * Logged Admin tries to ban an "good" actor.
	 * Logged Chapter(NON-Admin) tries to ban.
	 * 
	 * Requirement under test: 28 (Acme-Madruga)
	 * 
	 * Analysis of sentence coverage: 90%
	 * Analysis of data coverage: 75%
	 */
	public void adminBanActor() {
		final Object testingData[][] = {

			{
				"admin", 0, IllegalArgumentException.class
			}, {
				"hackeruser", 0, IllegalArgumentException.class
			}, {
				"hackeruser", 1, IllegalArgumentException.class
			}, {
				"admin", 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminBanActor((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	/*
	 * Test UNBAN actor;
	 * 
	 * POSITIVE TEST
	 * Logged Admin tries to unban a spammer.
	 * Logged Admin tries to unban an actor which have bad polarity.
	 * 
	 * NEGATIVE TEST
	 * Logged Admin tries to unban an "good" actor.
	 * Logged Chapter(NON-Admin) tries to unban.
	 * 
	 * Requirement under test: 28 (Acme-Madruga)
	 * 
	 * Analysis of sentence coverage: 90%
	 * Analysis of data coverage: 75%
	 */
	public void adminUnbanActor() {

		final Object testingData[][] = {

			{
				"admin", null
			}, {
				"hackeruser", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminUnbanActor((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected void actorSpammer(final int spammer, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Hacker hacker = this.hackerService.create();
			hacker.setAddress("calle hacker test");
			hacker.setCreditCard(null);
			hacker.setEmail("elcejas@hacker.com");
			hacker.setName("hackername");
			hacker.setPhone("123456789");
			hacker.setSurname("hackersurname");
			hacker.getUserAccount().setUsername("hackeruser");
			hacker.getUserAccount().setPassword("hackerpass");
			final Hacker savedHacker = this.hackerService.saveCreate(hacker);
			this.authenticate("hackeruser");

			final Message msg = this.msgService.create();
			msg.setSubject("subject");
			if (spammer == 1)
				msg.setBody("sex");
			else
				msg.setBody("no spam");
			final Collection<String> tags = new ArrayList<String>();
			tags.add("<tag>");
			final Collection<String> recipient = new ArrayList<String>();
			recipient.add("admin@admin.com");
			msg.setRecipient(recipient);
			this.msgService.save(msg);

			Assert.isTrue(!savedHacker.getUserAccount().getSpammerFlag());
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminBanActor(final String username, final int spammer, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Hacker hacker = this.hackerService.create();
			hacker.setAddress("calle hacker test");
			hacker.setCreditCard(null);
			hacker.setEmail("elcejas@hacker.com");
			hacker.setName("hackername");
			hacker.setPhone("123456789");
			hacker.setSurname("hackersurname");
			hacker.getUserAccount().setUsername("hackeruser");
			hacker.getUserAccount().setPassword("hackerpass");
			if (spammer == 1)
				hacker.getUserAccount().setSpammerFlag(true);
			final Hacker savedHacker = this.hackerService.saveCreate(hacker);

			this.authenticate(username);
			this.actorService.banByActorId(savedHacker);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminUnbanActor(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Hacker hacker = this.hackerService.create();
			hacker.setAddress("calle hacker test");
			hacker.setCreditCard(null);
			hacker.setEmail("elcejas@hacker.com");
			hacker.setName("hackername");
			hacker.setPhone("123456789");
			hacker.setSurname("hackersurname");
			hacker.getUserAccount().setUsername("hackeruser");
			hacker.getUserAccount().setPassword("hackerpass");
			hacker.getUserAccount().setSpammerFlag(true);
			final Hacker savedHacker = this.hackerService.saveCreate(hacker);

			this.authenticate(username);
			this.actorService.unbanByActorId(savedHacker);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

}
