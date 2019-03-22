
package acmeParadeTest;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.PathRepository;
import repositories.SegmentRepository;
import services.ParadeService;
import services.PathService;
import services.SegmentService;
import utilities.AbstractTest;
import domain.Parade;
import domain.Path;
import domain.Segment;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PathTest extends AbstractTest {

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private PathService			pathService;
	@Autowired
	private PathRepository		pathRepository;

	@Autowired
	private SegmentService		segmentService;
	@Autowired
	private SegmentRepository	segmentRepository;


	// (LEVEL B 3.3) Testing creating a Path and updating it's origin
	// A path won't be deleted, only segments can be deleted, so the rest of this test suite is in SegmentTest
	// 
	@Test
	public void PathDriver1() {

		final Object testingData[][] = {
			// ----- Positive tests (insert parade owner)
			{
				"brotherhood", true, "create", null
			}, {
				"brotherhood", true, "modify", null
			},
			// ------ Negative tests (insert parade not owner)
			{
				"brotherhood", false, "create", IllegalArgumentException.class
			}, {
				"brotherhood", false, "modify", IllegalArgumentException.class
			},
			// ------ Negative tests (insert invalid user)
			{
				"member", false, "create", IllegalArgumentException.class
			}, {
				"sponsor", false, "create", IllegalArgumentException.class
			}, {
				"admin", false, "create", IllegalArgumentException.class
			}, {
				"chapter", false, "create", IllegalArgumentException.class
			}, {
				"member", true, "create", IllegalArgumentException.class
			}, {
				"sponsor", true, "create", IllegalArgumentException.class
			}, {
				"admin", true, "create", IllegalArgumentException.class
			}, {
				"chapter", true, "create", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.PathTemplate((String) testingData[i][0], (boolean) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	// Ancillary methods ------------------------------------------------------

	protected void PathTemplate(final String userName, final boolean owner, final String mode, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			super.authenticate(userName);
			Parade parade = this.paradeService.findParadesByTicker("211503-ABCDE4").iterator().next();
			if (owner)
				// I need at least one parade of the logged brotherhood to make this work
				parade = this.paradeService.findAllBrotherhoodLogged().iterator().next();

			Path path = null;
			if (mode == "create") {
				path = this.pathService.create(parade.getId());
				this.pathService.save(path, parade.getId());
			} else if (mode == "modify") {
				path = this.pathService.getParadePath(parade.getId());
				final Segment segment = this.segmentService.create();
				segment.setLatitude(BigDecimal.valueOf(0));
				segment.setLongitude(BigDecimal.valueOf(0));
				final Segment savedSegment = this.segmentService.save(segment, parade.getId());
				path.setOrigin(savedSegment);
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
