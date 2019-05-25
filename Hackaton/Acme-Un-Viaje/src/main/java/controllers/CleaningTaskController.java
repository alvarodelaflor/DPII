package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.CleaningTask;
import domain.Host;
import domain.Mailbox;
import domain.Message;
import domain.SocialProfile;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
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
			result = new ModelAndView("cleaningTask/create");
			result.addObject("cleaningTask", cleaningTask);
			result.addObject("requestURI", "cleaningTask/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit( CleaningTask cleaningTask, final BindingResult binding) {
		ModelAndView result;
		try {
			cleaningTask = cleaningTaskService.reconstruct(cleaningTask, binding);
			if (binding.hasErrors()) {
				result = new ModelAndView("cleaningTask/edit");
				result.addObject("requestURI", "socialProfile/list.do");
			} else
				try {
					Assert.isTrue(cleaningTask != null);
					final CleaningTask savedCleaningTask = this.cleaningTaskService.save(cleaningTask);
					final Host host = hostService.getHostLogin();
					Assert.isTrue(cleaningTask.getAccomodation().getHost().equals(host));

					result = listCleaningTask();
				
				} catch (final Throwable oops) {
					result = new ModelAndView("cleaningTask/edit");
				}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;

	}

}
