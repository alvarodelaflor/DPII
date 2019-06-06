package unViaje;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Complaint;
import domain.Mailbox;
import domain.Message;
import domain.Review;
import domain.Tag;
import services.ComplaintService;
import services.MessageService;
import services.ReviewService;
import services.TagService;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReviewServiceTest extends AbstractTest{
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private TagService tagService;
	
	@Test
	public void diverCreateReview() throws ParseException {
		

		final Object testingData[][] = {

				{ "descriptionTry","referee", 438 ,null
			}, {
				null,"referee", 438, ConstraintViolationException.class
			}, {
				 "descriptionTry","cleaner",438,  IllegalArgumentException.class
			}, {
				null,"cleaner", 438, IllegalArgumentException.class
			} };

		for (int i = 0; i < testingData.length; i++)
			this.diverCreateReview((String) testingData[i][0], (String) testingData[i][1],(int) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	
	protected void diverCreateReview(final String description, final String user,int complaintId,  final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			
			super.authenticate("admin");

			final Review review = reviewService.create();
			review.setDescription(description);
			review.setMoment(LocalDate.now().toDate());
			
			super.authenticate(user);
			
			
			Review saveReview = reviewService.save(review);
			
			Complaint complaint = complaintService.findOne(complaintId);
			
			complaint.setReview(saveReview);
			complaintService.saveWithoutSetMoment(complaint);
					
			Message msg = messageService.sendBroadcastWithoutAdminReview(complaint, saveReview);
			this.messageService.save(msg);
			List<Tag> tags = new ArrayList<Tag>();
			tags.addAll(msg.getTags());
			
			for (int i = 0; i < tags.size(); i++) {
				tags.get(i).setMessageId(msg.getId());
				tagService.save(tags.get(i));
			}
			
			Assert.isTrue(msg.getTags().size() == 3);
								
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
