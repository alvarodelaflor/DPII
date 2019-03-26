
package acmeParadeTest;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import repositories.PathRepository;
import repositories.SegmentRepository;
import services.ParadeService;
import services.PathService;
import services.SegmentService;
import utilities.AbstractTest;
import domain.Parade;
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


	/*
	 * (LEVEL B 3.3) Testing creating a Path and updating it's origin
	 * A path won't be deleted, only segments can be deleted, so the rest of this test suite is in SegmentTest
	 * 
	 * Analysis of sentence coverage: 88.2% (Source: EclEmma)
	 * 
	 * Analysis of data coverage: ~75% (Source: I think these tests could cover this percentage because we aren't testing a lot of parade values)
	 */
	@Test
	public void PathDriver1() {
		// DATA: user, isParadeOwner, useCase
		final Object testingData[][] = {
			// ----- Positive tests (insert parade owner)
			{
				"brotherhood", true, "create", null
			}, {
				"brotherhood", true, "modify", null
			}, {
				"brotherhood", true, "deleteEntirePath", null
			}, { // This is called only when there's one segment, the origin
				"brotherhood", true, "delete", null
			},
			// ------ Negative tests (insert parade not owner)
			{
				"brotherhood", false, "create", IllegalArgumentException.class
			}, {
				"brotherhood", false, "modify", IllegalArgumentException.class
			}, {
				"brotherhood", false, "deleteEntirePath", IllegalArgumentException.class
			}, { // This is called only when there's one segment, the origin
				"brotherhood", false, "delete", IllegalArgumentException.class
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

			if (mode == "create")
				this.pathService.createFromParade(parade.getId());
			else if (mode == "modify") {
				this.pathService.getParadePath(parade.getId());
				Segment segment = this.segmentService.create();
				segment.setLatitude(BigDecimal.valueOf(0));
				segment.setLongitude(BigDecimal.valueOf(0));
				final BindingResult binding = null;
				segment = this.pathService.reconstruct(segment, binding);
				final Segment savedSegment = this.segmentService.save(segment, parade.getId());
				this.pathService.setOrigin(parade.getId(), savedSegment);
			} else if (mode == "delete") {
				this.pathService.getParadePath(parade.getId());
				Segment segment = this.segmentService.create();
				segment.setLatitude(BigDecimal.valueOf(0));
				segment.setLongitude(BigDecimal.valueOf(0));
				final BindingResult binding = null;
				segment = this.pathService.reconstruct(segment, binding);
				final Segment savedSegment = this.segmentService.save(segment, parade.getId());
				this.pathService.setOrigin(parade.getId(), savedSegment);
				this.pathService.delete(savedSegment.getId(), parade.getId());
			} else if (mode == "deleteEntirePath")
				this.pathService.deleteWithParade(parade.getId());

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
}
