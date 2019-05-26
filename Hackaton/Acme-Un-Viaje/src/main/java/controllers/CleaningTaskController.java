package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Accomodation;
import domain.Actor;
import domain.Cleaner;
import domain.CleaningTask;
import domain.Host;
import domain.Mailbox;
import domain.Message;
import domain.SocialProfile;
import security.LoginService;
import security.UserAccount;
import services.AccomodationService;
import services.ActorService;
import services.CleanerService;
import services.CleaningTaskService;
import services.HostService;
import services.MailboxService;
import services.MessageService;
import services.TagService;

@Controller
@RequestMapping("/cleaningTask")
public class CleaningTaskController extends AbstractController{
	
	@Autowired
	private CleaningTaskService		cleaningTaskService;
	
	@Autowired
	private HostService		hostService;
	
	@Autowired
	private CleanerService		cleanerService;
	
	@Autowired
	private AccomodationService		accomodationService;
	
	@Autowired
	private ActorService		actorService;
	


	// Constructors -----------------------------------------------------------

	public CleaningTaskController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listCleaningTask() {
		final ModelAndView result;

		Host host = hostService.getHostLogin();
		Collection<CleaningTask> cleaningTasks = cleaningTaskService.getCleaningTaskHost(host.getId());

		result = new ModelAndView("cleaningTask/list");
		result.addObject("cleaningTasks", cleaningTasks);
		result.addObject("requestURI", "cleaningTask/list.do");

		return result;
	}
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam("cleaningTaskId") final int cleaningTaskId) {
		ModelAndView result;
		try {
			final Host h = hostService.getHostLogin();
			final CleaningTask cleaningTask = this.cleaningTaskService.findOne(cleaningTaskId);
			if (!cleaningTask.getAccomodation().getHost().equals(h))
				result = new ModelAndView("welcome/index");
			else {
				result = new ModelAndView("cleaningTask/show");
				result.addObject("cleaningTask", cleaningTask);
				result.addObject("requestURI", "cleaningTask/list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;

	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			CleaningTask cleaningTask;
			cleaningTask = this.cleaningTaskService.create();
			final Host h = hostService.getHostLogin();
			Assert.notNull(h);
			Collection<String> cleaners = cleanerService.getCompleteName();
			Collection<String> accomodations = accomodationService.getAddressAccomodationsByActor(h.getId());
			result = new ModelAndView("cleaningTask/create");
			result.addObject("cleaningTask", cleaningTask);
			//result.addObject("cleaners",cleaners);
			//To begin
			result.addObject("cleaners", cleaners);
			result.addObject("accomodations",accomodations);
			result.addObject("requestURI", "cleaningTask/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit( CleaningTask cleaningTask, final BindingResult binding) {
		ModelAndView result;
		try {
			cleaningTask = cleaningTaskService.reconstruct(cleaningTask, binding);
			
			if (cleaningTask.getStartMoment() != null && cleaningTask.getEndMoment() != null && this.cleaningTaskService.checkDate(cleaningTask.getStartMoment(), cleaningTask.getEndMoment())) {
				final ObjectError error = new ObjectError("startDate", "Start date mus be before than end Date");
				binding.addError(error);
				binding.rejectValue("startMoment", "error.startMoment");
			}

			if (cleaningTask.getStartMoment() != null && cleaningTask.getEndMoment() != null && this.cleaningTaskService.checkDate(cleaningTask.getStartMoment(), cleaningTask.getEndMoment())) {
				final ObjectError error = new ObjectError("endDate", "Start date mus be before than end Date");
				binding.addError(error);
				binding.rejectValue("endMoment", "error.endMoment");
			}
			
			
			if (binding.hasErrors()) {
				Host h = hostService.getHostLogin();
				Collection<String> cleaners = cleanerService.getCompleteName();
				Collection<String> accomodations = accomodationService.getAddressAccomodationsByActor(h.getId());
				result = new ModelAndView("cleaningTask/create");
				result.addObject("cleaners",cleaners);
				result.addObject("accomodations",accomodations);
				result.addObject("requestURI", "cleaningTask/list.do");
			} else
				try {
					Assert.isTrue(cleaningTask != null);
					final CleaningTask savedCleaningTask = this.cleaningTaskService.save(cleaningTask);
					final Host host = hostService.getHostLogin();
					Assert.isTrue(cleaningTask.getAccomodation().getHost().equals(host));

					result = listCleaningTask();
				
				} catch (final Throwable oops) {
					result = new ModelAndView("cleaningTask/create");
				}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;

	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(
			@RequestParam(value = "cleaningTaskId", defaultValue = "-1") final int cleaningTaskId) {
		ModelAndView result;

		final CleaningTask cleaningTask = this.cleaningTaskService.findOne(cleaningTaskId);

		Host h = hostService.getHostLogin();
		
			try {
				if (!cleaningTask.getAccomodation().getHost() 
					.equals(this.actorService.getActorByUserId(LoginService.getPrincipal().getId()))) {
					result = new ModelAndView("welcome/index");
				}
				
				Assert.isTrue(cleaningTask != null);

				final int userLoggin = LoginService.getPrincipal().getId();
				final Actor actor = this.actorService.getActorByUserId(userLoggin);
				Assert.isTrue(actor != null);
				this.cleaningTaskService.delete(cleaningTask);
				result = listCleaningTask();
			} catch (final Throwable oops) {
				System.out.println(oops);
				result = listCleaningTask();				
			}
		
		return result;

	}

}
