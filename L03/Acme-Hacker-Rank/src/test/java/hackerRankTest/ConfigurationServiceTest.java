
package hackerRankTest;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ConfigurationService;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationServiceTest {

	@Autowired
	private ConfigurationService	configurationService;

	/*
	 * 14. The system must be easy to customise at run time, including banner, system message and country code
	 * //
	 */
	//	@Test
	//	public void Diver01() {
	//		final Object testingData[][] = {
	//			{
	//				// Test positivo: Change configuration
	//				// name, surname, photo, email, phone, address, userName, password, commercialName
	//				"pruebaCreateHacker", "pruebaCreateHacker", "http://pruebaCreateHacker", "pruebaCreateHacker@pruebaCreateHacker", "123456", "", "pruebaCreateHacker", "pruebaCreateHacker", "pruebaCreateHacker", null
	//			}, {
	//				// Test negativo: Create a Hacker
	//				// name, surname, photo, email, phone, address, userName, password, commercialName
	//				"", "", "pruebaCreateHacker", "pruebaCreateHacker", "", "", "", "", "", IllegalArgumentException.class
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.Diver01((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
	//				(String) testingData[i][8], (Class<?>) testingData[i][9]);
	//	}

	// Ancillary methods ------------------------------------------------------

	//	protected void Diver01(final String name, final String surname, final String photo, final String email, final String phone, final String address, final String userName, final String password, final String comercialName, final Class<?> expected) {
	//		Class<?> caught = null;
	//
	//		try {
	////
	////			final Hacker hacker = this.hackerService.create();
	////
	////			hacker.setAddress(address);
	////			hacker.setEmail(email);
	////			hacker.setName(name);
	////			hacker.setPhone(phone);
	////			hacker.setPhoto(photo);
	////			hacker.setSurname(surname);
	////
	////			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
	////			final String hashPassword = encoder.encodePassword(password, null);
	////			hacker.getUserAccount().setPassword(hashPassword);
	////
	////			hacker.getUserAccount().setUsername(userName);
	////
	////			this.hackerService.saveCreate(hacker);
	////			System.out.println(hacker);
	////
	////			this.hackerService.flush();
	////
	////		} catch (final Throwable oops) {
	////			caught = oops.getClass();
	////		}
	////
	////		this.checkExceptions(expected, caught);
	////	}
	//
}
