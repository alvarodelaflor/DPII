package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.runner.Computer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.CleaningTask;
import domain.Complaint;
import domain.Customer;
import domain.Host;
import domain.Mailbox;
import domain.Message;
import domain.Review;
import domain.Tag;
import domain.Transporter;
import domain.TravelAgency;
import services.ComplaintService;
import services.MessageService;
import services.ReviewService;
import services.TagService;

@Controller
@RequestMapping("/review")
public class ReviewController extends AbstractController{
	
	@Autowired
	private ReviewService		reviewService;
	
	@Autowired
	private ComplaintService		complaintService;
	
	@Autowired
	private MessageService		messageService;
	
	@Autowired
	private TagService		tagService;
	
	public ReviewController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listReviews() {
		ModelAndView result;
		try {
			Collection<Review> reviews = reviewService.findReviewRefereeLogged();

			result = new ModelAndView("review/list");
			result.addObject("reviews", reviews);
			result.addObject("requestURI", "review/list.do");
		} catch (Exception e) {
			result = new ModelAndView("welcome/index");
		}
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			Review review;
			List<Complaint> complaints = (List<Complaint>) complaintService.getComplaintsWithoutReview();
			Random rnd = new Random();
			int random = rnd.nextInt(complaints.size());
			Complaint complaint = complaints.get(random);
			review = this.reviewService.create();
			result = new ModelAndView("review/create");
			result.addObject("review", review);
			result.addObject("complaint",complaint);
			result.addObject("requestURI", "review/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam("reviewId") final int reviewId) {
		ModelAndView result;
		try {
			final Review review = reviewService.findOne(reviewId);	
			Complaint complaint = complaintService.getComplaintOfReview(review.getId());
				result = new ModelAndView("review/show");
				result.addObject("review", review);
				result.addObject("complaint", complaint);
				result.addObject("requestURI", "review/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;

	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit( Review review, final BindingResult binding,@RequestParam(value = "complaintId", defaultValue = "-1") final int complaintId) {
		ModelAndView result;
		try {
			Complaint complaint = complaintService.findOne(complaintId);
			if (binding.hasErrors()) {
				result = new ModelAndView("review/create");
				result.addObject("review",review);
				result.addObject("complaint",complaint);
				result.addObject("requestURI", "review/list.do");
			} else {
					Assert.isTrue(review != null);
					
					Review reviewSave = reviewService.save(review);
										
					complaint.setReview(reviewSave);
					complaintService.saveWithoutSetMoment(complaint);
					
				
					Message msg = messageService.sendBroadcastWithoutAdminReview(complaint, reviewSave);
					this.messageService.save(msg);
					List<Tag> tags = new ArrayList<Tag>();
					tags.addAll(msg.getTags());
					
					for (int i = 0; i < tags.size(); i++) {
						tags.get(i).setMessageId(msg.getId());
						tagService.save(tags.get(i));
					}
					

					result = new ModelAndView("review/list");
					Collection<Review> reviews = reviewService.findReviewRefereeLogged();
					result.addObject("reviews", reviews);
					result.addObject("requestURI", "review/list.do");
				
			}
		}catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
