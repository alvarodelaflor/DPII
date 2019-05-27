
package unViaje;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CleanerService;
import services.CustomerService;
import services.HostService;
import services.ValorationService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ValidationServiceTest extends AbstractTest {

	@Autowired
	private ValorationService	valorationService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HostService			hostService;

	@Autowired
	private CleanerService		cleanerService;

}
