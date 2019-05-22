package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Cleaner;
import domain.JobApplication;
import services.CleanerService;
import services.CurriculaService;
import services.JobApplicationService;

@Controller
@RequestMapping("/jobApplication/cleaner")
public class JobApplicationCleanerController extends AbstractController {

	@Autowired
	private CleanerService cleanerService;
	
	@Autowired
	private JobApplicationService	jobApplicationService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "cleanerId", defaultValue = "-1") final int cleanerId) {
		ModelAndView result;
		try {
			Cleaner cleaner = this.cleanerService.findOne(cleanerId);
			final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
			if (cleaner == null && cleanerLogin != null)
				cleaner = cleanerLogin;
			Assert.notNull(cleaner, "Not cleaner found in DB");
			result = new ModelAndView("jobApplication/cleaner/list");
			if (cleanerLogin != null && cleaner.equals(cleanerLogin))
				result.addObject("cleanerLogger", true);
			Assert.notNull(cleaner, "Cleaner is null");
			final Collection<JobApplication> jobApplications = this.jobApplicationService.findAllByCleanerId(this.cleanerService.getCleanerLogin().getId());
			result.addObject("jobApplications", jobApplications);
			result.addObject("requestURI", "jobApplication/cleaner/list");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "hostId", defaultValue = "-1") final int hostId) {
		ModelAndView result;
		JobApplication jobApplication;
		try {			
			final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
			Assert.notNull(cleanerLogin, "No cleaner is login");
			jobApplication = this.jobApplicationService.create(hostId);
			result = new ModelAndView("jobApplication/cleaner/edit");
			result.addObject("curriculas", this.curriculaService.findAllNotCopyByCleaner(this.cleanerService.getCleanerLogin()));
			result.addObject("jobApplication", jobApplication);
		} catch (final Exception e) {
			System.out.println("Error e en GET /create jobApplicationController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "jobApplicationId", defaultValue = "-1") final int jobApplicationId) {
		ModelAndView result;
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		try {
			final JobApplication jobApplicationDB = this.jobApplicationService.findOne(jobApplicationId);
			Assert.notNull(jobApplicationDB, "Job Application not found in DB");
			Assert.isTrue(jobApplicationDB.getStatus()==null, "Trying to edit an acept application");
			Assert.isTrue(jobApplicationDB.getDropMoment()==null, "Trying to edit a drop application");
			Assert.notNull(cleanerLogin, "No cleaner is login");
			Assert.isTrue(cleanerLogin.equals(jobApplicationDB.getCleaner()));
			result = new ModelAndView("jobApplication/cleaner/edit");
			result.addObject("curriculas", this.curriculaService.findAllNotCopyByCleaner(this.cleanerService.getCleanerLogin()));
			result.addObject("jobApplication", jobApplicationDB);
		} catch (final Exception e) {
			System.out.println("Error en EDIT JobApplicationCleanerController.java Throwable: " + e);
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
				// CHECK COPY CURRICULA WORK
				//				this.jobApplicationService.createJobApplicationCopyAndSave(saveJobApplication);
				//CHECK COPY CURRICULA WORK
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
//		result.addObject("logo", this.getLogo());
//		result.addObject("system", this.getSystem());
		return result;
	}
}
