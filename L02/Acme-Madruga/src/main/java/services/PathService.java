
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PathRepository;
import security.LoginService;
import domain.Parade;
import domain.Path;
import domain.Segment;

@Service
public class PathService {

	@Autowired
	private PathRepository	pathRepository;

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private SegmentService	segmentService;

	@Autowired
	private Validator		validator;


	public Path create(final int paradeId) {
		final Path path = new Path();
		final Parade parade = this.paradeService.findOne(paradeId);
		path.setParade(parade);
		return path;
	}

	public Path save(final Path path, final int paradeId) {
		// We have to be the brotherhood owner of this parade
		this.assertParadeOwner(paradeId);
		final Path res = this.pathRepository.save(path);

		return res;
	}

	public Path getParadePath(final int paradeId) {
		// In case we aren't the parade owner we have to check that the parade is in final mode
		final Parade parade = this.paradeService.findOne(paradeId);
		Path res = null;
		try {
			final int loggedAccountId = LoginService.getPrincipal().getId();
			if (parade.getIsFinal() || parade.getBrotherhood().getUserAccount().getId() == loggedAccountId)
				res = this.pathRepository.findFromParade(paradeId);
		} catch (final Exception e) {
			// This is because LoginService.getPrincipal() throws an exception in case none is logged
			if (parade.getIsFinal())
				res = this.pathRepository.findFromParade(paradeId);
		}
		return res;
	}

	public void delete(final int segmentId, final int paradeId) {
		// We have to be the brotherhood owner of this parade
		this.assertParadeOwner(paradeId);
		// We don't really want to delete the path, we want to delete the origin segment
		final Path path = this.pathRepository.findFromOriginSegment(segmentId);
		final int segmentToDelete = path.getOrigin().getId();
		path.setOrigin(null);
		this.segmentService.delete(segmentToDelete, path.getParade().getId());
	}

	public Path createFromParade(final int paradeId) {
		final Path path = this.create(paradeId);
		return this.pathRepository.save(path);
	}

	public void setOrigin(final int paradeId, final Segment pathOrigin) {
		// We have to be the parade owner :3
		this.assertParadeOwner(paradeId);
		// REMIND YOURSELF THAT THIS IS ONLY BEING CALLED WHEN WE HAVE NO ORIGIN
		final Path path = this.pathRepository.findFromParade(paradeId);
		path.setOrigin(pathOrigin);
		this.pathRepository.save(path);
	}

	// ========================== PRIVATE METHODS
	private void assertParadeOwner(final int paradeId) {
		final Parade parade = this.paradeService.findOne(paradeId);
		final int loggedAccountId = LoginService.getPrincipal().getId();
		Assert.isTrue(parade.getBrotherhood().getUserAccount().getId() == loggedAccountId);

	}

	public Segment reconstruct(final Segment segment, final BindingResult binding) {
		// This should only be called when there's no path, segment id is always 0
		Assert.isTrue(segment.getId() == 0);

		this.validator.validate(segment, binding);
		// We have to also validate the destination
		if (segment.getDestination() != null)
			this.validator.validate(segment.getDestination(), binding);
		return segment;
	}

	public void deleteWithParade(final int paradeID) {
		this.assertParadeOwner(paradeID);
		final Path path = this.pathRepository.findFromParade(paradeID);
		if (path != null) {
			final List<Segment> segments = this.segmentService.getAllSegments(path);
			this.pathRepository.delete(path.getId());
			for (int i = 0; i < segments.size(); i++)
				this.segmentService.deleteFromDB(segments.get(i).getId());
		}
	}

}
