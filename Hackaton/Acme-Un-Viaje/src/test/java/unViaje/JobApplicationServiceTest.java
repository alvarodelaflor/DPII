package unViaje;

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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import domain.Cleaner;
import domain.Curricula;
import domain.Host;
import domain.JobApplication;
import repositories.JobApplicationRepository;
import services.CleanerService;
import services.CurriculaService;
import services.HostService;
import services.JobApplicationService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class JobApplicationServiceTest extends AbstractTest {

	@Autowired
	private CurriculaService	curriculaService;
		
	@Autowired
	private CleanerService cleanerService;
	
	@Autowired
	private JobApplicationRepository jobApplicationRepository;
	
	@Autowired
	private HostService hostService;
	
	@Autowired
	private JobApplicationService jobApplicationService;



	/*
	 * 14. The system must be easy to customise at run time, including banner, system message and country code
	 * //
	 * * //
	 * Analysis of sentence coverage
	 * 90.6%
	 * Analysis of data coverage
	 * ~35%
	 */
	@Test
	public void Diver01Data() {
		final Object testingData[][] = {
			{
				false, false, ConstraintViolationException.class
			} , {
				false, true, null
			} , {
				true, true, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.createJobApplication((Boolean) testingData[i][0], (Boolean) testingData[i][1],(Class<?>) testingData[i][2]);

	}
	
	/*
	 * 14. The system must be easy to customise at run time, including banner, system message and country code
	 * //
	 * * //
	 * Analysis of sentence coverage
	 * 98.6%
	 * Analysis of data coverage
	 * ~35%
	 */
	@Test
	public void Diver02Data() {
		final Object testingData[][] = {
				// Boolean cleanerOrHost (true -> cleaner, false -> host), Boolean acceptOrReject (edit-> acept or delete-> edit if he is an cleaner), Boolean isAccept,final Class<?> expected
			{
				true, false, false, null // Intenta borrar un jobApplication no comprobada por el host
			} , {
				true, true, false, null // Intenta editar un jobApplication no comprobada por el host
			} , {
				true, false, true, IllegalArgumentException.class // Intenta borrar un jobApplication no comprobada por el host
			} , {
				true, true, true, IllegalArgumentException.class // Intenta editar un jobApplication si comprobada por el host
			} , {
				false, true, false, null // Host acepta el cleaner en una petición no final
			} , {
				false, false, false, null // Host rechaza el cleaner en una petición no final
			} , {
				false, true, true, IllegalArgumentException.class // Host acepta el cleaner en una petición final
			} , {
				false, false, true, IllegalArgumentException.class // Host rechaza el cleaner en una petición final
			} , {
				false, false, true, IllegalArgumentException.class // Host rechaza el cleaner en una petición final
			} , {
				false, null, false, null // Host expulsa un cleaner
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.editJobApplication((Boolean) testingData[i][0], (Boolean) testingData[i][1], (Boolean) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	//Ancillary methods------------------------------------------------------
	
	private Curricula createCurricula(String name, String statement, String phone, String linkStatement, String bannerLogo, Boolean cleanerBoolean) {
		final Cleaner cleaner = this.cleanerService.findAll().iterator().next();
		this.curriculaService.checkAnyLogger();
		final Curricula curricula = this.curriculaService.create();
		curricula.setName(name);
		curricula.setStatement(statement);
		curricula.setPhone(phone);
		curricula.setLinkLinkedin(linkStatement);
		curricula.setBannerLogo(bannerLogo);
		curricula.setIsCopy(false);
		curricula.setCleaner(cleaner);				
		Curricula theSaved = this.curriculaService.save(curricula);		
		this.curriculaService.reconstruct(curricula, null);
		return theSaved;
	}
	
	private JobApplication createJobApplication(Host host, String cleanerMessage) {
		final JobApplication jobApplication = this.jobApplicationService.create(host.getId());		
		jobApplication.setCleanerMessage(cleanerMessage);
		Collection<Curricula> curriculas = this.curriculaService.findAllByCleaner(this.cleanerService.getCleanerLogin());
		Collection<Curricula> curriculaCopy = new ArrayList<Curricula>();
		for (Curricula curricula : curriculas) {
			if (curricula.getIsCopy().equals(false)) {
				curriculaCopy.add(curricula);
			}
		}
		if (!curriculaCopy.isEmpty()) {			
			jobApplication.setCurricula(curriculaCopy.iterator().next());
		}
		jobApplication.setStatus(null);
		BindingResult result = new BeanPropertyBindingResult(jobApplication, "jobApplication");
		this.jobApplicationService.reconstruct(jobApplication, result);
		return this.jobApplicationService.save(jobApplication);
	}

	protected void createJobApplication(Boolean repetirPeticion, Boolean tieneCurricula,final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			

			super.authenticate("cleaner");
			Collection<JobApplication> jobApplications = this.jobApplicationService.findAllByCleanerId(this.cleanerService.getCleanerLogin().getId());
			this.jobApplicationRepository.delete(jobApplications);
			if (tieneCurricula) {
				this.createCurricula("Alvaro", "Me gustaria trabajar con usted", "+34 665 381 121", "https://www.google.com/", "https://www.imgnur.es/", true);				
			}
			JobApplication jobApplication = this.createJobApplication(this.hostService.findAll().iterator().next(), "Hola");
			Assert.isTrue(this.jobApplicationService.findOne(jobApplication.getId())!=null);
			BindingResult result = new BeanPropertyBindingResult(jobApplication, "jobApplication");
			this.jobApplicationService.reconstruct(jobApplication, result);
			if (repetirPeticion) {
				this.createCurricula("Alvaro", "Me gustaria trabajar con usted", "+34 665 381 121", "https://www.google.com/", "https://www.imgnur.es/", true);
				JobApplication jobApplication2 = this.createJobApplication(this.hostService.findAll().iterator().next(), "Hola");
				Assert.isTrue(this.jobApplicationService.findOne(jobApplication2.getId())!=null);			
			}
			super.unauthenticate();
			this.curriculaService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void editJobApplication(Boolean cleanerOrHost, Boolean acceptOrReject, Boolean isAccept,final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			Collection<JobApplication> jobApplications = this.jobApplicationService.findAll();
			this.jobApplicationRepository.delete(jobApplications);
			
			super.authenticate("cleaner");
			this.createCurricula("Alvaro", "Me gustaria trabajar con usted", "+34 665 381 121", "https://www.google.com/", "https://www.imgnur.es/", true);				
			JobApplication jobApplication = this.createJobApplication(this.hostService.findAll().iterator().next(), "Hola");
			super.unauthenticate();
			if (isAccept) {
				super.authenticate("hosthost");
				jobApplication = this.jobApplicationService.acceptApplication(jobApplication.getId());
				super.unauthenticate();
			}

			if (cleanerOrHost) { // Es un cleaner
				super.authenticate("cleaner");
				if (acceptOrReject!=null && acceptOrReject.equals(false)) { // Trata de borrarlo
					this.jobApplicationService.delete(jobApplication);
					Assert.isNull(this.jobApplicationService.findOne(jobApplication.getId()));
				} else { // Trata de editarlo
					jobApplication.setCleanerMessage("Editado");
					JobApplication saveJobApplication = this.jobApplicationService.save(jobApplication);
					Assert.notNull(this.jobApplicationService.findOne(saveJobApplication.getId()));
					Assert.isTrue(saveJobApplication.getCleanerMessage().equals("Editado"));
				}
				super.unauthenticate();
			} else { // Es un host
				super.authenticate("hosthost");
				if (acceptOrReject!=null && acceptOrReject.equals(true)) { // Se acepta la aplicación
					BindingResult result = new BeanPropertyBindingResult(jobApplication, "jobApplication");
					this.jobApplicationService.reconstruct(jobApplication, result);
					JobApplication saveJobApplication = this.jobApplicationService.acceptApplication(jobApplication.getId());
					Assert.notNull(this.jobApplicationService.findOne(saveJobApplication.getId()));
					Assert.isTrue(saveJobApplication.getStatus().equals(true));
				} else if (acceptOrReject != null && acceptOrReject.equals(false)) { // Se rechaza la applicación
					jobApplication.setRejectMessage("No me gusta su curricula");
					JobApplication saveJobApplication = this.jobApplicationService.rejectUser(jobApplication);
					Assert.notNull(this.jobApplicationService.findOne(saveJobApplication.getId()));
					Assert.isTrue(saveJobApplication.getStatus().equals(false) && saveJobApplication.getRejectMessage()!=null);
				} else { // Se acepta y luego se expulsa
					Assert.isTrue(this.jobApplicationService.getExCleaners(this.hostService.getHostLogin().getId()).size() == 0);
					Assert.isTrue(this.jobApplicationService.getJobApplicationPendingByHostId(this.hostService.getHostLogin().getId()).size() == 1);
					Assert.isTrue(this.jobApplicationService.getJobApplicationByStatusAndHostId(true, this.hostService.getHostLogin().getId()).size() == 0);
					Assert.isTrue(this.jobApplicationService.getJobApplicationByStatusAndHostId(false, this.hostService.getHostLogin().getId()).size() == 0);
					JobApplication saveJobApplication = this.jobApplicationService.acceptApplication(jobApplication.getId());
					Assert.notNull(this.jobApplicationService.findOne(saveJobApplication.getId()));
					Assert.isTrue(saveJobApplication.getStatus().equals(true));
					Assert.isTrue(this.jobApplicationService.getExCleaners(this.hostService.getHostLogin().getId()).size() == 0);
					Assert.isTrue(this.jobApplicationService.getJobApplicationPendingByHostId(this.hostService.getHostLogin().getId()).size() == 0);
					Assert.isTrue(this.jobApplicationService.getJobApplicationByStatusAndHostId(true, this.hostService.getHostLogin().getId()).size() == 1);
					Assert.isTrue(this.jobApplicationService.getJobApplicationByStatusAndHostId(false, this.hostService.getHostLogin().getId()).size() == 0);
					this.jobApplicationService.dropUser(saveJobApplication.getId());
					Assert.isTrue(this.jobApplicationService.getExCleaners(this.hostService.getHostLogin().getId()).size() > 0);
					this.jobApplicationService.deleteHostApplications(this.hostService.getHostLogin());
				}
				super.unauthenticate();
			}
			this.curriculaService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
