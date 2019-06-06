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
import services.MessageService;
import services.TagService;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest{
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private TagService tagService;
	
	@Test
	public void diverSendMessage() throws ParseException {
		

		final Object testingData[][] = {

				{ "subjectTest", "bodyTest", "tagTest" , "admin@gmail.com", 402, null
			}, {
				null, "bodyTest", "tagTest" , "admin@gmail.com", 402, IllegalArgumentException.class
			},{
				"subjectTest", null, "tagTest" , "admin@gmail.com", 402, NullPointerException.class
			}
				};

		for (int i = 0; i < testingData.length; i++)
			this.diverSendMessage((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (int) testingData[i][4],(Class<?>) testingData[i][5]);

	}
	
	protected void diverSendMessage(final String subject, final String body, final String tag, final String emailSender,
			final int receiverId, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			
			super.authenticate("admin");

			final Message message = messageService.create();
			message.setBody(body);
			message.setSubject(subject);
			message.setSender(emailSender);
			message.setMoment(LocalDate.now().toDate());
			message.setMailboxes(new ArrayList<Mailbox>());
			message.setEmailReceiver(new ArrayList<String>());
			
			Tag tagMessage = tagService.create();
			tagMessage.setTag(tag);
			
			Collection<Tag> tags = new ArrayList<Tag>();
			tags.add(tagMessage);
			
			message.setTags(tags);
			
			Message sended = messageService.exchangeMessage(message, receiverId);
			
			Message saveSended = messageService.save(sended);
			
			List<Tag> tagsList = new ArrayList<Tag>();
			tagsList.addAll(saveSended.getTags());
			
			for (int i = 0; i < tagsList.size(); i++) {
				tagsList.get(i).setMessageId(saveSended.getId());
				tagService.save(tagsList.get(i));
			}
			
			Assert.isTrue(saveSended.getTags().size() == 2);
			
						
			this.flushTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void diverSendNotificationMessage() throws ParseException {
		

		final Object testingData[][] = {

				{ "subjectTest", "bodyTest" , "admin@gmail.com","admin", 262, null
			}, {
				"subjectTest", null , "admin@gmail.com", "admin" , 262, NullPointerException.class
			}, {
				"subjectTest", "bodyTest" , "admin@gmail.com", "cleaner" , 262, NullPointerException.class
			}
				};

		for (int i = 0; i < testingData.length; i++)
			this.diverSendNotificationMessage((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
					 (String) testingData[i][3],(int) testingData[i][4],(Class<?>) testingData[i][5]);

	}
	
	protected void diverSendNotificationMessage(final String subject, final String body, final String emailSender, String user,
			final int receiverId, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			
			super.authenticate(user);

			final Message message = messageService.create();
			message.setBody(body);
			message.setSubject(subject);
			message.setSender(emailSender);
			message.setMoment(LocalDate.now().toDate());
			message.setMailboxes(new ArrayList<Mailbox>());
			message.setEmailReceiver(new ArrayList<String>());
			
			message.setTags(new ArrayList<Tag>());
			
			Message sended = messageService.sendBroadcast(message);
			
			Message saveSended = messageService.save(sended);
			
			List<Tag> tagsList = new ArrayList<Tag>();
			tagsList.addAll(saveSended.getTags());
			
			for (int i = 0; i < tagsList.size(); i++) {
				tagsList.get(i).setMessageId(saveSended.getId());
				tagService.save(tagsList.get(i));
			}
			
			Assert.isTrue(message.getTags().size() == 9);
			
						
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
