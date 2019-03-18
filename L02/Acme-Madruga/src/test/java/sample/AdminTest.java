
package sample;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.AdministratorService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminTest extends AbstractTest {

	// SUT:
	private AdministratorService	adminService;

	// Drivers:

	// Positives:

	// Negatives:

	// Abstract:
}
