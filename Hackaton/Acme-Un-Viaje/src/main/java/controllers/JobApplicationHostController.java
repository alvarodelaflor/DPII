package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Cleaner;
import domain.Host;
import domain.JobApplication;
import services.CleanerService;
import services.CurriculaService;
import services.HostService;
import services.JobApplicationService;

@Controller
@RequestMapping("/jobApplication/host")
public class JobApplicationHostController extends AbstractController {

	@Autowired
	private CleanerService cleanerService;
	
	@Autowired
	private HostService hostService;
	
	@Autowired
	private JobApplicationService	jobApplicationService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	// Default String
	
	private String redirectWelcome = "redirect:/welcome/index.do";
	private String cleanerNotLogin = "Any cleaner is login";
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			Host host = this.hostService.getHostLogin();
			Assert.notNull(host, "Not host found in DB");
			result = new ModelAndView("jobApplication/host/list");
			Collection<JobApplication> pending = this.jobApplicationService.getJobApplicationPendingByHostId(host.getId());
			Collection<JobApplication> rejected = this.jobApplicationService.getJobApplicationByStatusAndHostId(false, host.getId());
			Collection<JobApplication> accepted = this.jobApplicationService.getJobApplicationByStatusAndHostId(true, host.getId());
			Collection<JobApplication> exCleaners = this.jobApplicationService.getExCleaners(host.getId());
			result.addObject("pending", pending);
			result.addObject("rejected", rejected);
			result.addObject("accepted", accepted);
			result.addObject("exCleaners", exCleaners);
			result.addObject("numberPending", listNumber(pending.size()));
			result.addObject("requestURI", "jobApplication/host/list.do");
		} catch (final Exception e) {
			System.out.println("Error e en GET /list jobApplicationController.java: " + e);
			result = new ModelAndView(redirectWelcome);
		}
		return result;
	}
	
	private List<Integer> listNumber(int size) {
		List<Integer> res = new ArrayList<>();
		int aux = 0;
		while (aux < size) {
			res.add(aux);
			aux++;
		}
		return res;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "jobApplicationId", defaultValue = "-1") final int jobApplicationId) {
		ModelAndView result;
		final Host hostLogin = this.hostService.getHostLogin();
		try {
			final JobApplication jobApplicationDB = this.jobApplicationService.findOne(jobApplicationId);
			Assert.notNull(jobApplicationDB, "Job Application not found in DB");
			Assert.isTrue(jobApplicationDB.getStatus()==null, "Trying to edit an acept or reject application");
			Assert.isTrue(jobApplicationDB.getDropMoment()==null, "Trying to edit a drop application");
			Assert.notNull(hostLogin, "No cleaner is login");
			Assert.isTrue(hostLogin.equals(jobApplicationDB.getHost()));
			jobApplicationDB.setStatus(true);
			this.jobApplicationService.save(jobApplicationDB);
			result = new ModelAndView("jobApplication/host/list");
			Collection<JobApplication> pending = this.jobApplicationService.getJobApplicationPendingByHostId(hostLogin.getId());
			Collection<JobApplication> rejected = this.jobApplicationService.getJobApplicationByStatusAndHostId(false, hostLogin.getId());
			Collection<JobApplication> accepted = this.jobApplicationService.getJobApplicationByStatusAndHostId(true, hostLogin.getId());
			Collection<JobApplication> exCleaners = this.jobApplicationService.getExCleaners(hostLogin.getId());
			result.addObject("pending", pending);
			result.addObject("rejected", rejected);
			result.addObject("accepted", accepted);
			result.addObject("exCleaners", exCleaners);
			result.addObject("numberPending", listNumber(pending.size()));
			result.addObject("requestURI", "jobApplication/host/list.do");
		} catch (final Exception e) {
			System.out.println("Error en EDIT JobApplicationHostController.java Throwable: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(JobApplication jobApplication, final BindingResult binding) {
		ModelAndView result;
		try {
			jobApplication = this.jobApplicationService.reconstruct(jobApplication, binding);
		} catch (final Exception e) {
			System.out.println("Error e reconstruct de jobApplication: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}

		if (binding.hasErrors()) {
			System.out.println("Error en JobApplicationCleanerController.java, binding: " + binding);
			result = new ModelAndView("jobApplication/cleaner/create");
			result.addObject("curriculas", this.curriculaService.findAllNotCopyByCleaner(this.cleanerService.getCleanerLogin()));
			result.addObject("jobApplication", jobApplication);
		} else
			try {
				final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
				Assert.notNull(cleanerLogin, "No cleaner is login");
				Assert.isTrue(jobApplication != null, "jobApplication.null");
				Assert.isTrue(jobApplication.getStatus()==null, "Trying to edit an accept JobApplication");
				Assert.isTrue(jobApplication.getDropMoment()==null, "Trying to edit a drop JobApplication");
				jobApplication.setCurricula(this.curriculaService.createCurriculaCopyAndSave(jobApplication.getCurricula()));
				final JobApplication saveJobApplication = this.jobApplicationService.save(jobApplication);
				result = new ModelAndView("redirect:/jobApplication/cleaner/list.do?jobApplicationId=" + saveJobApplication.getId());
				result.addObject("requestURI", "jobApplication/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE JobApplicationCleanerController.java Throwable: " + oops);
				result = new ModelAndView("jobApplication/cleaner/edit");
				result.addObject("jobApplication", jobApplication);
				try {
					result.addObject("curriculas", this.curriculaService.findAllNotCopyByCleaner(this.cleanerService.getCleanerLogin()));
				} catch (Exception e) {
					
				}
				result.addObject("message", "jobApplication.commit.error");
			}
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "jobApplicationId", defaultValue = "-1") final int jobApplicationId) {
		ModelAndView result;
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();

		try {
			Assert.notNull(cleanerLogin, "No cleaner is login");
			final JobApplication jobApplicationDB = this.jobApplicationService.findOne(jobApplicationId);
			Assert.notNull(jobApplicationDB, "Not found jobApplication in DB");
			Assert.isTrue(jobApplicationDB.getStatus()==null, "Application is not pending");
			Assert.isTrue(jobApplicationDB.getCleaner().equals(cleanerLogin), "Not allow to delete, diferent cleaner");
			this.jobApplicationService.delete(jobApplicationDB);
			result = new ModelAndView("redirect:/jobApplication/cleaner/list.do");
		} catch (final Throwable oops) {
			System.out.println("Error en CurriculaCleanerController.java Throwable: " + oops);
			result = new ModelAndView("redirect:/jobApplication/cleaner/list.do");
			result.addObject("message", "curricula.commit.error");
		}
		return result;
	}
}
