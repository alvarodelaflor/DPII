package hackerRankTest;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Cleaner;
import domain.Curricula;
import domain.MiscellaneousAttachment;
import services.CleanerService;
import services.CurriculaService;
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



	/*
	 * 14. The system must be easy to customise at run time, including banner, system message and country code
	 * //
	 * * //
	 * Analysis of sentence coverage
	 * 12.6%
	 * Analysis of data coverage
	 * ~10%
	 */
//	@Test
	public void Diver01Data() {
		final Object testingData[][] = {
			{
				"Me gustaría entrar en su empresa", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false, null
			} , {
				null, "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustaría entrar en su empresa", null, "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustaría entrar en su empresa", "Soy un experto programador Java", null, "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustaría entrar en su empresa", "Soy un experto programador Java", "+34695456123", null, "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustaría entrar en su empresa", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", null, true, false,IllegalArgumentException.class
			} , {
				"Me gustaría entrar en su empresa", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", false, false,IllegalArgumentException.class
			} , {
				" ", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustaría entrar en su empresa", " ", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustaría entrar en su empresa", "Soy un experto programador Java", " ", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustaría entrar en su empresa", "Soy un experto programador Java", "+34695456123", " ", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, false,IllegalArgumentException.class
			} , {
				"Me gustaría entrar en su empresa", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", " ", true, false,IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Boolean) testingData[i][5], (Boolean) testingData[i][6],(Class<?>) testingData[i][7]);

	}
	
//	@Test
	public void Diver02Data() {
		final Object testingData[][] = {
			{
				"Me gustaría entrar en su empresa", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", true, true, null
			} , {
				"Me gustaría entrar en su empresa", "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", false, true, IllegalArgumentException.class
			} , {
				null, "Soy un experto programador Java", "+34695456123", "http://www.prueba.com", "https://www.dzoom.org.es/wp-content/uploads/2008/12/panoramica-13-734x243.jpg", false, true, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Boolean) testingData[i][5], (Boolean) testingData[i][6],(Class<?>) testingData[i][7]);

	}
	
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

	//Ancillary methods------------------------------------------------------

	protected void Diver01(String name, String statement, String phone, String linkStatement, String bannerLogo, Boolean cleanerBoolean, Boolean edit, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Cleaner cleaner = this.cleanerService.findAll().iterator().next();

			super.authenticate(cleaner.getUserAccount().getUsername());

			final Curricula curricula = this.curriculaService.create();
			
			
			if (edit) {
				

				curricula.setName(name);
				curricula.setStatement(statement);
				curricula.setPhone(phone);
				curricula.setLinkLinkedin(linkStatement);
				curricula.setBannerLogo(bannerLogo);
				curricula.setIsCopy(false);
				curricula.setCleaner(cleaner);				
				Curricula theSaved = this.curriculaService.save(curricula);
				
				super.unauthenticate();
				
				if (cleanerBoolean) {
					super.authenticate(cleaner.getUserAccount().getUsername());
				}
				
				
				theSaved.setName("name edit");
				this.curriculaService.save(theSaved);
				Assert.isTrue(this.curriculaService.findOne(theSaved.getId()).getName().equals("name edit"), "Diferent");
			} else {				
				curricula.setName(name);
				curricula.setStatement(statement);
				curricula.setPhone(phone);
				curricula.setLinkLinkedin(linkStatement);
				curricula.setBannerLogo(bannerLogo);
				curricula.setIsCopy(false);
				if (cleanerBoolean) {
					curricula.setCleaner(cleaner);				
				} else {
					curricula.setCleaner(null);
				}
				this.curriculaService.save(curricula);
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
			
			Curricula curriculaII = this.curriculaService.create();
			
			curriculaII.setName("Nombre");
			curriculaII.setStatement("Statement");
			curriculaII.setPhone("+34 665 381 121");
			curriculaII.setLinkLinkedin("https://");
			curriculaII.setBannerLogo("https://");
			curriculaII.setIsCopy(false);
			curriculaII.setCleaner(cleaner);				
			this.curriculaService.save(curriculaII);
			
			Collection<Curricula> curriculas = this.curriculaService.findAllByCleaner(cleaner);
			
			for (Curricula curricula : curriculas) {
				
				curricula.getBannerLogo();
				curricula.getCleaner();
				curricula.getLinkLinkedin();
				curricula.getName();
				curricula.getPhone();
				curricula.getStatement();
				super.unauthenticate();
				if (cleanerLogin) {
					super.authenticate(cleaner.getUserAccount().getUsername());
				}
				MiscellaneousAttachment mis = this.miscellaneousAttachmentService.createWithHistory(curricula);
				mis.setAttachment("Prueba");
				MiscellaneousAttachment misSaved = this.miscellaneousAttachmentService.save(mis); 
				super.unauthenticate();
			}
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
