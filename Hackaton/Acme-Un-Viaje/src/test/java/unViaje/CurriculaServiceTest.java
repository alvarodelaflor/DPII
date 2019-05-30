package unViaje;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Cleaner;
import domain.CreditCard;
import domain.Curricula;
import domain.EducationalData;
import domain.MiscellaneousAttachment;
import services.CleanerService;
import services.ConfigService;
import services.CurriculaService;
import services.EducationalDataService;
import services.MiscellaneousAttachmentService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	@Autowired
	private CurriculaService	curriculaService;
		
	@Autowired
	private CleanerService cleanerService;
	
	@Autowired
	private MiscellaneousAttachmentService miscellaneousAttachmentService;
	
	@Autowired
	private EducationalDataService educationalDataService;
	
	@Autowired
	private ConfigService configService;



	/*
	 * CREACIÓN DE CURRICULA
	 * 
	 * RI
	 * 
	 * 14\. Un operario de limpieza puede registrar uno o más curriculums. Los curriculums constan de los siguientes datos: 
	 * 		datos personales, que incluyen un nombre completo, una declaración, un número de teléfono (siguiendo el patrón de la web)
	 * 		y un perfil de LinkedIn; datos de trabajo, que incluyen el título, la descripción, la fecha de inicio y la fecha de 
	 * 		finalización opcional de trabajos que ha tenido este operario así como el propio trabajo adjunto; datos de educación, 
	 * 		que incluyen el grado, la institución, la marca, la fecha de inicio y la fecha de finalización opcional del título de 
	 * 		grado que tiene un operario; y datos misceláneos, que es texto libre con complementos opcionales.
	 * 
	 * RF
	 * 
	 * 16. 
	 * 		B) Administre sus currículums, que incluyen enumerarlos, mostrarlos, crearlos, actualizarlos y eliminarlos. 
	 * 			Cuando un operario hace una solicitud, él o ella debe seleccionar un currículum para que se adjunte a la 
	 * 			solicitud de empleo. Tenga en cuenta que adjuntar un currículum hace una copia; Las actualizaciones que 
	 * 			realiza un operario en el currículum original no se propagan a las solicitudes a las que ha adjuntado una 
	 * 			versión anterior.
	 * 
	 * Analysis of sentence coverage
	 * 40.6%
	 * Analysis of data coverage
	 * ~3%
	 */
	@Test
	public void Diver01Data() {
		final Object testingData[][] = {
			{
				"Me gustara entrar en su empresa", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false, null
			} , {
				null, "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustara entrar en su empresa", null, "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustara entrar en su empresa", "Soy un experto programador Java", null, "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustara entrar en su empresa", "Soy un experto programador Java", "+34695456123", null, "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustara entrar en su empresa", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", null, true, false,IllegalArgumentException.class
			} , {
				" ", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustara entrar en su empresa", " ", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustara entrar en su empresa", "Soy un experto programador Java", " ", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustara entrar en su empresa", "Soy un experto programador Java", "+34695456123", " ", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustara entrar en su empresa", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", " ", true, false,IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Boolean) testingData[i][5], (Boolean) testingData[i][6],(Class<?>) testingData[i][7]);

	}
	
	/*
	 * EDICIÓN DE CURRICULA
	 * 
	 * RI
	 * 
	 * 14\. Un operario de limpieza puede registrar uno o más curriculums. Los curriculums constan de los siguientes datos: 
	 * 		datos personales, que incluyen un nombre completo, una declaración, un número de teléfono (siguiendo el patrón de la web)
	 * 		y un perfil de LinkedIn; datos de trabajo, que incluyen el título, la descripción, la fecha de inicio y la fecha de 
	 * 		finalización opcional de trabajos que ha tenido este operario así como el propio trabajo adjunto; datos de educación, 
	 * 		que incluyen el grado, la institución, la marca, la fecha de inicio y la fecha de finalización opcional del título de 
	 * 		grado que tiene un operario; y datos misceláneos, que es texto libre con complementos opcionales.
	 * 
	 * RF
	 * 
	 * 16. 
	 * 		B) Administre sus currículums, que incluyen enumerarlos, mostrarlos, crearlos, actualizarlos y eliminarlos. 
	 * 			Cuando un operario hace una solicitud, él o ella debe seleccionar un currículum para que se adjunte a la 
	 * 			solicitud de empleo. Tenga en cuenta que adjuntar un currículum hace una copia; Las actualizaciones que 
	 * 			realiza un operario en el currículum original no se propagan a las solicitudes a las que ha adjuntado una 
	 * 			versión anterior.
	 * 
	 * Analysis of sentence coverage
	 * 40.6%
	 * Analysis of data coverage
	 * ~1%
	 */
	@Test
	public void Diver02Data() {
		final Object testingData[][] = {
			{
				"Me gustar�a entrar en su empresa", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, true, null
			} , {
				"Me gustar�a entrar en su empresa", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", false, true, IllegalArgumentException.class
			} , {
				null, "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", false, true, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Boolean) testingData[i][5], (Boolean) testingData[i][6],(Class<?>) testingData[i][7]);

	}
	
	/*
	 * LISTAR Y MOSTRAR CURRICULA
	 * 
	 * RI
	 * 
	 * 14\. Un operario de limpieza puede registrar uno o más curriculums. Los curriculums constan de los siguientes datos: 
	 * 		datos personales, que incluyen un nombre completo, una declaración, un número de teléfono (siguiendo el patrón de la web)
	 * 		y un perfil de LinkedIn; datos de trabajo, que incluyen el título, la descripción, la fecha de inicio y la fecha de 
	 * 		finalización opcional de trabajos que ha tenido este operario así como el propio trabajo adjunto; datos de educación, 
	 * 		que incluyen el grado, la institución, la marca, la fecha de inicio y la fecha de finalización opcional del título de 
	 * 		grado que tiene un operario; y datos misceláneos, que es texto libre con complementos opcionales.
	 * 
	 * RF
	 * 
	 * 16. 
	 * 		B) Administre sus currículums, que incluyen enumerarlos, mostrarlos, crearlos, actualizarlos y eliminarlos. 
	 * 			Cuando un operario hace una solicitud, él o ella debe seleccionar un currículum para que se adjunte a la 
	 * 			solicitud de empleo. Tenga en cuenta que adjuntar un currículum hace una copia; Las actualizaciones que 
	 * 			realiza un operario en el currículum original no se propagan a las solicitudes a las que ha adjuntado una 
	 * 			versión anterior.
	 * 
	 * Analysis of sentence coverage
	 * 80.4%
	 * Analysis of data coverage
	 * ~54%
	 */
	@Test
	public void Diver03Data() {
		final Object testingData[][] = {
			{
				true, null
			}, {
				false, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver02((Boolean) testingData[i][0],(Class<?>) testingData[i][1]);

	}
	
	/*
	 * ELIMINAR CURRICULA
	 * 
	 * RI
	 * 
	 * 14\. Un operario de limpieza puede registrar uno o más curriculums. Los curriculums constan de los siguientes datos: 
	 * 		datos personales, que incluyen un nombre completo, una declaración, un número de teléfono (siguiendo el patrón de la web)
	 * 		y un perfil de LinkedIn; datos de trabajo, que incluyen el título, la descripción, la fecha de inicio y la fecha de 
	 * 		finalización opcional de trabajos que ha tenido este operario así como el propio trabajo adjunto; datos de educación, 
	 * 		que incluyen el grado, la institución, la marca, la fecha de inicio y la fecha de finalización opcional del título de 
	 * 		grado que tiene un operario; y datos misceláneos, que es texto libre con complementos opcionales.
	 * 
	 * RF
	 * 
	 * 16. 
	 * 		B) Administre sus currículums, que incluyen enumerarlos, mostrarlos, crearlos, actualizarlos y eliminarlos. 
	 * 			Cuando un operario hace una solicitud, él o ella debe seleccionar un currículum para que se adjunte a la 
	 * 			solicitud de empleo. Tenga en cuenta que adjuntar un currículum hace una copia; Las actualizaciones que 
	 * 			realiza un operario en el currículum original no se propagan a las solicitudes a las que ha adjuntado una 
	 * 			versión anterior.
	 * 
	 * Analysis of sentence coverage
	 * 25.6%
	 * Analysis of data coverage
	 * ~65%
	 */
	@Test
	public void Diver04Data() {
		final Object testingData[][] = {
			{
				true, null
			}, {
				false, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver03((Boolean) testingData[i][0],(Class<?>) testingData[i][1]);

	}

	//Ancillary methods------------------------------------------------------
	
	private Cleaner createCleaner() {
		try {
			final Cleaner cleaner2 = this.cleanerService.create();
			List<String> make = (List<String>) this.configService.getConfiguration().getCreditCardMakeList();
			
			SimpleDateFormat parseador = new SimpleDateFormat("yyyy/MM/dd");
			Date date = parseador.parse("1998/11/11");
			
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("password", null);
			cleaner2.getUserAccount().setPassword(hashPassword);
			cleaner2.getUserAccount().setUsername("userAccountwrggrts");
			cleaner2.setName("name");
			cleaner2.setSurname("surname");
			cleaner2.setEmail("email");
			cleaner2.setPhone("phone");
			cleaner2.setPhoto("https:///photo");
			cleaner2.setBirthDate(date);
			CreditCard creditCard = new CreditCard();
			creditCard.setHolder("holder");
			creditCard.setMake(make.get(0));
			creditCard.setNumber("1234567890987654");
			creditCard.setCVV("123");
			creditCard.setExpiration("03/20");
			cleaner2.setCreditCard(creditCard);
			Cleaner cleanerCreate = this.cleanerService.saveRegisterAsCleaner(cleaner2);
			return cleanerCreate;
		} catch (Exception e) {
			return null;
		}
	}
	
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

	protected void Diver01(String name, String statement, String phone, String linkStatement, String bannerLogo, Boolean cleanerBoolean, Boolean edit, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			// Creo una curricula con el actor cleaner
			super.authenticate("cleaner");
			Curricula theSaved = this.createCurricula(name, statement, phone, linkStatement, bannerLogo, cleanerBoolean);
			super.unauthenticate();
			
			if (edit) {
				if (cleanerBoolean) {
					// Logueo el actor que ha creado la curricula inicial
					super.authenticate("cleaner");
				} else {
					// Creo un actor para intentar editar la curricula
					Cleaner cleanerCreate = this.createCleaner();
					super.authenticate(cleanerCreate.getUserAccount().getUsername());
				}
			theSaved.setName("name edit");
			this.curriculaService.save(theSaved);
			Assert.isTrue(this.curriculaService.findOne(theSaved.getId()).getName().equals("name edit"), "Diferent");
			
			this.curriculaService.flush();
		}
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void Diver02(Boolean cleanerLogin, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			Cleaner cleaner = this.cleanerService.findAll().iterator().next();
			

			super.authenticate(cleaner.getUserAccount().getUsername());
			
			Curricula curriculaII = this.createCurricula("Nombre", "Statement", "+34 665 381 121", "https://", "https://", false);	
			this.curriculaService.save(curriculaII);
			
			Collection<Curricula> curriculas = this.curriculaService.findAllByCleaner(cleaner);
			this.curriculaService.findAll();
			this.curriculaService.findAllNotCopyByCleaner(cleaner);
			
			for (Curricula curricula : curriculas) {
				
				curricula.getBannerLogo();
				curricula.getCleaner();
				curricula.getLinkLinkedin();
				curricula.getName();
				curricula.getPhone();
				curricula.getStatement();
				this.curriculaService.minCurriculaPerCleaner();
				this.curriculaService.maxCurriculaPerCleaner();
				this.curriculaService.avgCurriculaPerCleaner();
				this.curriculaService.stddevCurriculaPerCleaner();
				super.unauthenticate();
				if (cleanerLogin) {
					super.authenticate(cleaner.getUserAccount().getUsername());
				}
				MiscellaneousAttachment mis = this.miscellaneousAttachmentService.createWithHistory(curricula);
				mis.setAttachment("Prueba");
				this.miscellaneousAttachmentService.save(mis); 
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
	
	protected void Diver03(Boolean cleanerLogin, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			Cleaner cleaner = this.cleanerService.findAll().iterator().next();
			super.authenticate(cleaner.getUserAccount().getUsername());
			Curricula curriculaII = this.createCurricula("Nombre", "Statement", "+34 665 381 121", "https://", "https://", false);
			MiscellaneousAttachment mis = this.miscellaneousAttachmentService.createWithHistory(curriculaII);
			mis.setAttachment("Prueba");
			this.miscellaneousAttachmentService.save(mis); 
			EducationalData educationalData = this.educationalDataService.createWithHistory(curriculaII);
			educationalData.setDegree("Ing Info");
			SimpleDateFormat parseador = new SimpleDateFormat("yyyy/MM/dd");
			Date dateStart = parseador.parse("1998/11/11");
			Date dateEnd = parseador.parse("2018/11/11");
			educationalData.setStartDate(dateStart);
			educationalData.setEndDate(dateEnd);
			educationalData.setInstitution("ETSII");
			educationalData.setIsCopy(false);
			educationalData.setMark("A+");
			this.educationalDataService.save(educationalData);
			Curricula curriculaReconstruct = this.curriculaService.reconstruct(curriculaII, null);
			Curricula toDelete = this.curriculaService.save(curriculaReconstruct);
			
			if (!cleanerLogin) {
				super.unauthenticate();
			}
			
			this.curriculaService.delete(toDelete);
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
