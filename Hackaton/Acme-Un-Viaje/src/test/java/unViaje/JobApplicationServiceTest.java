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

import domain.Actor;
import domain.Cleaner;
import domain.Curricula;
import domain.Host;
import domain.JobApplication;
import domain.MiscellaneousAttachment;
import repositories.JobApplicationRepository;
import services.CleanerService;
import services.CurriculaService;
import services.HostService;
import services.JobApplicationService;
import services.MiscellaneousAttachmentService;
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
	
	@Autowired
	private MiscellaneousAttachmentService miscellaneousAttachmentService;



	/*
	 * 16\. Un actor autenticado como operario de limpieza podrá:
	 *
	 *		- 1\. Crear solicitudes de empleo a los anfitriones. Una vez que la solicitud ha sido creada no podrá ser 
	 *			editada pero si borrada. Una vez borrada el operario podr� volver a realizar una petición de trabajo.
	 * 
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
	 * 16\. Un actor autenticado como operario de limpieza podrá:
	 *					
	 *		- 2\. Administre sus currículums, que incluyen enumerarlos, mostrarlos, crearlos, actualizarlos y eliminarlos. 
	 *				Cuando un operario hace una solicitud, él o ella debe seleccionar un currículum para que se adjunte a la solicitud de empleo. 
	 *				Tenga en cuenta que adjuntar un currículum hace una copia; Las actualizaciones 
	 *				que realiza un operario en el currículum original no se propagan a las solicitudes a las que ha adjuntado una 
	 *				versión anterior.
	 *
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
				false, true, true, IllegalArgumentException.class // Host acepta el cleaner en una petici�n final
			} , {
				false, false, true, IllegalArgumentException.class // Host rechaza el cleaner en una petici�n final
			} , {
				false, false, true, IllegalArgumentException.class // Host rechaza el cleaner en una petici�n final
			} , {
				false, null, false, null // Host expulsa un cleaner
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.editJobApplication((Boolean) testingData[i][0], (Boolean) testingData[i][1], (Boolean) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	
	/*
	 * 16\. Un actor autenticado como operario de limpieza podrá:
	 *		
	 *		- 2\. Administre sus currículums, que incluyen enumerarlos, mostrarlos, crearlos, actualizarlos y eliminarlos. 
	 *				Cuando un operario hace una solicitud, él o ella debe seleccionar un currículum para que se adjunte a la solicitud de empleo. 
	 *				Tenga en cuenta que adjuntar un currículum hace una copia; Las actualizaciones 
	 *				que realiza un operario en el currículum original no se propagan a las solicitudes a las que ha adjuntado una 
	 *				versión anterior.
	 *
	 * Analysis of sentence coverage
	 * 90.6%
	 * Analysis of data coverage
	 * ~35%
	 */
	@Test
	public void Diver03Data() {
		// Boolean tryEdit, Boolean max, final Class<?> expected) {
		final Object testingData[][] = {
			{
				false, false, null
			} , {
				true, false, null
			} , {
				true, true, IllegalArgumentException.class
			} , {
				false, true, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.showAndList((Boolean) testingData[i][0], (Boolean) testingData[i][1],(Class<?>) testingData[i][2]);

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
				if (acceptOrReject!=null && acceptOrReject.equals(true)) { // Se acepta la aplicaci�n
					BindingResult result = new BeanPropertyBindingResult(jobApplication, "jobApplication");
					this.jobApplicationService.reconstruct(jobApplication, result);
					JobApplication saveJobApplication = this.jobApplicationService.acceptApplication(jobApplication.getId());
					Assert.notNull(this.jobApplicationService.findOne(saveJobApplication.getId()));
					Assert.isTrue(saveJobApplication.getStatus().equals(true));
				} else if (acceptOrReject != null && acceptOrReject.equals(false)) { // Se rechaza la applicaci�n
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
	
	protected void showAndList(Boolean tryEdit, Boolean max, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			Collection<Actor> actors = new ArrayList<>();
			
			super.authenticate("cleaner");
			Curricula curriculaAux = this.createCurricula("Hola", "Hola", "Hola", "http://Hola", "https://Hola", false);
			Curricula save = this.curriculaService.save(curriculaAux);
			if (tryEdit && max) {
				Integer i = 0;
				while (i<4) {
					MiscellaneousAttachment mis = this.miscellaneousAttachmentService.createWithHistory(save);
					mis.setAttachment("Prueba");
					this.miscellaneousAttachmentService.save(mis);
					i++;
				}
			}
			super.unauthenticate();
			
			Collection<Curricula> curriculas = this.curriculaService.findAll();
			actors.addAll(this.cleanerService.findAll());
			actors.addAll(this.hostService.findAll());
			for (Actor actor : actors) {
				super.authenticate(actor.getUserAccount().getUsername());
				for (Curricula curricula : curriculas) {
					Assert.isTrue(curricula.getName() != null && curricula.getBannerLogo()!=null && curricula.getCleaner()!=null && curricula.getPhone() != null && curricula.getStatement() != null && curricula.getLinkLinkedin()!=null);
					if (curricula.getCleaner().getName().equals(actor.getName()) && curricula.getIsCopy().equals(false) && tryEdit && !max) {
						curricula.setName("Edit");
						this.curriculaService.save(curricula);
					} else if (curricula.getCleaner().getName().equals(actor.getName()) && curricula.getIsCopy().equals(false) && max) {
						Integer i = 0;
						while (i<6) {
							MiscellaneousAttachment mis = this.miscellaneousAttachmentService.createWithHistory(curricula);
							mis.setAttachment("Prueba");
							this.miscellaneousAttachmentService.save(mis);
							i++;
						}
						
					}
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
