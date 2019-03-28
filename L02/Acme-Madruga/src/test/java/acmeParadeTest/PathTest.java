
package acmeParadeTest;

import java.math.BigDecimal;
import java.util.Calendar;

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
	 * Analysis of data coverage: ~55% (Source: I think these tests could cover this percentage because we aren't testing a lot of parade values)
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
			//		 ------ Negative tests (insert parade not owner)
			{
				"brotherhood", false, "create", NullPointerException.class
			}, {
				"brotherhood", false, "modify", NullPointerException.class
			}, {
				"brotherhood", false, "deleteEntirePath", NullPointerException.class
			}, { // This is called only when there's one segment, the origin
				"brotherhood", false, "delete", NullPointerException.class
			},
			//		 ------ Negative tests (insert invalid user)
			{
				"member", false, "create", NullPointerException.class
			}, {
				"sponsor", false, "create", NullPointerException.class
			}, {
				"admin", false, "create", NullPointerException.class
			}, {
				"chapter", false, "create", NullPointerException.class
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
			Parade parade = null;
			if (owner)
				// I need at least one parade of the logged brotherhood to make this work
				parade = this.paradeService.findOne(this.getEntityId("parade01"));

			if (mode == "create")
				this.pathService.createFromParade(parade.getId());
			else if (mode == "modify") {
				final Calendar calendar = Calendar.getInstance();
				Segment segment = this.segmentService.create();
				segment.setLatitude(BigDecimal.valueOf(0));
				segment.setLongitude(BigDecimal.valueOf(0));
				segment.setArrivalTime(calendar.getTime());

				final Segment destination = this.segmentService.create();
				destination.setLatitude(BigDecimal.valueOf(0));
				destination.setLongitude(BigDecimal.valueOf(0));
				calendar.add(Calendar.HOUR, 1);
				destination.setArrivalTime(calendar.getTime());
				segment.setDestination(destination);
				final BindingResult binding = null;
				segment = this.pathService.reconstruct(segment, binding);
				final Segment savedSegment = this.segmentService.save(segment, parade.getId());
				this.pathService.setOrigin(parade.getId(), savedSegment);
			} else if (mode == "delete") {
				final Calendar calendar = Calendar.getInstance();
				Segment segment = this.segmentService.create();
				segment.setLatitude(BigDecimal.valueOf(0));
				segment.setLongitude(BigDecimal.valueOf(0));
				segment.setArrivalTime(calendar.getTime());

				final Segment destination = this.segmentService.create();
				destination.setLatitude(BigDecimal.valueOf(0));
				destination.setLongitude(BigDecimal.valueOf(0));
				calendar.add(Calendar.HOUR, 1);
				destination.setArrivalTime(calendar.getTime());
				segment.setDestination(destination);
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
