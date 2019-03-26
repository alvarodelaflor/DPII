
package acmeParadeTest;

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
import services.MessageBoxService;
import services.MessageService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageTest extends AbstractTest {

	@Autowired
	MessageService		messageService;

	@Autowired
	MessageBoxService	messageBoxService;

	@Autowired
	ActorService		actorService;


	@Test
	public void driver1() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				"Carmen@alum.us.es", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testSendMessage((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testSendMessage(final String email, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Message m = this.messageService.create();
			super.authenticate("sponsor");
			m.setBody("try body");
			m.setSubject("try subject");
			final Actor receive = this.actorService.getActorByEmail(email);
			final Message sended = this.messageService.exchangeMessage(m, receive.getId());
			final Message saveMessage = this.messageService.save(sended);
			final MessageBox inBoxReceive = this.messageBoxService.getInBoxActor(receive.getId());
			Assert.isTrue(saveMessage.getMessageBoxes().contains(inBoxReceive));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver2() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				"Carmen@alum.us.es", 0, IllegalArgumentException.class
			}, {
				"Carmen@alum.us.es", 1, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testSendMessageNegative((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	public void testSendMessageNegative(final String email, final int bin, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Message m = this.messageService.create();
			m.setBody("try body");
			super.authenticate("sponsor");
			if (bin == 0)
				super.unauthenticate();
			else
				m.setBody(null);
			m.setSubject("try subject");
			final Actor receive = this.actorService.getActorByEmail(email);
			final Message sended = this.messageService.exchangeMessage(m, receive.getId());
			final Message saveMessage = this.messageService.save(sended);
			final MessageBox inBoxReceive = this.messageBoxService.getInBoxActor(receive.getId());
			Assert.isTrue(saveMessage.getMessageBoxes().contains(inBoxReceive));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver3() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				"Carmen@alum.us.es", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteMessage((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testDeleteMessage(final String email, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Message m = this.messageService.create();
			super.authenticate("sponsor");
			m.setBody("try body");
			m.setSubject("try subject");
			final Actor receive = this.actorService.getActorByEmail(email);
			final Message sended = this.messageService.exchangeMessage(m, receive.getId());
			final Message saveMessage = this.messageService.save(sended);
			final MessageBox inBoxReceive = this.messageBoxService.getInBoxActor(receive.getId());
			Assert.isTrue(saveMessage.getMessageBoxes().contains(inBoxReceive));

			final MessageBox outBoxSender = this.messageBoxService.getOutBoxActor(1075);

			final Message deleteMessage = this.messageService.delete(saveMessage, outBoxSender.getId());

			Assert.isTrue(!outBoxSender.getMessages().contains(saveMessage));
			Assert.isTrue(this.messageBoxService.getTrashBoxActor(1075).getMessages().contains(saveMessage));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver4() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				"Carmen@alum.us.es", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteMessageNegative((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testDeleteMessageNegative(final String email, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Message m = this.messageService.create();
			super.authenticate("sponsor");
			m.setBody("try body");
			m.setSubject("try subject");
			final Actor receive = this.actorService.getActorByEmail(email);
			final Message sended = this.messageService.exchangeMessage(m, receive.getId());
			final Message saveMessage = this.messageService.save(sended);
			final MessageBox inBoxReceive = this.messageBoxService.getInBoxActor(receive.getId());
			Assert.isTrue(saveMessage.getMessageBoxes().contains(inBoxReceive));

			final MessageBox outBoxSender = this.messageBoxService.getOutBoxActor(1075);

			super.unauthenticate();

			final Message deleteMessage = this.messageService.delete(saveMessage, outBoxSender.getId());

			Assert.isTrue(!outBoxSender.getMessages().contains(saveMessage));
			Assert.isTrue(this.messageBoxService.getTrashBoxActor(1075).getMessages().contains(saveMessage));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver5() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				"Carmen@alum.us.es", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testSendNotiMessage((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testSendNotiMessage(final String email, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Message m = this.messageService.create();
			super.authenticate("admin");
			m.setBody("try body");
			m.setSubject("try subject");
			final Message sended = this.messageService.sendNotification(m);
			final Message saveMessage = this.messageService.save(sended);
			System.out.println(saveMessage.getMessageBoxes().size());
			Assert.isTrue(saveMessage.getMessageBoxes().size() == 9);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver6() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				"Carmen@alum.us.es", 0, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testSendNotiMessageNegative((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	public void testSendNotiMessageNegative(final String email, final int bin, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Message m = this.messageService.create();
			m.setBody("try body");
			super.authenticate("sponsor");
			m.setBody(null);
			m.setSubject("try subject");

			final Message sended = this.messageService.sendNotification(m);
			final Message saveMessage = this.messageService.save(sended);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver7() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				"Carmen@alum.us.es", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testEditMessageBoxMessage((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testEditMessageBoxMessage(final String email, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Message m = this.messageService.create();
			super.authenticate("sponsor");
			m.setBody("try body");
			m.setSubject("try subject");
			final Actor receive = this.actorService.getActorByEmail(email);
			final Message sended = this.messageService.exchangeMessage(m, receive.getId());
			final Message saveMessage = this.messageService.save(sended);
			final MessageBox inBoxReceive = this.messageBoxService.getInBoxActor(receive.getId());
			Assert.isTrue(saveMessage.getMessageBoxes().contains(inBoxReceive));

			final Collection<MessageBox> newBoxes = new ArrayList<>();

			final MessageBox box = this.messageBoxService.findOne(986);
			newBoxes.add(box);

			saveMessage.setMessageBoxes(newBoxes);

			this.messageService.editMessageBox(saveMessage);

			Assert.isTrue(saveMessage.getMessageBoxes().contains(box));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver8() {
		final Object testingData[][] = {
			//	
			{
				"Carmen@alum.us.es", 0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testEditMessageBoxMessageNegative((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	public void testEditMessageBoxMessageNegative(final String email, final int bin, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Message m = this.messageService.create();
			super.authenticate("sponsor");
			m.setBody("try body");
			m.setSubject("try subject");
			final Actor receive = this.actorService.getActorByEmail(email);
			final Message sended = this.messageService.exchangeMessage(m, receive.getId());
			final Message saveMessage = this.messageService.save(sended);
			final MessageBox inBoxReceive = this.messageBoxService.getInBoxActor(receive.getId());
			Assert.isTrue(saveMessage.getMessageBoxes().contains(inBoxReceive));

			final Collection<MessageBox> newBoxes = new ArrayList<>();

			final MessageBox box = this.messageBoxService.findOne(986);
			newBoxes.add(box);

			saveMessage.setMessageBoxes(newBoxes);

			super.unauthenticate();

			this.messageService.editMessageBox(saveMessage);

			Assert.isTrue(saveMessage.getMessageBoxes().contains(box));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

}
