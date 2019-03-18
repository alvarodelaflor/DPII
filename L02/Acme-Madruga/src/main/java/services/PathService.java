
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PathRepository;
import security.LoginService;
import domain.Parade;
import domain.Path;

@Service
public class PathService {

	@Autowired
	private PathRepository	pathRepository;

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private SegmentService	segmentService;


	public Path create() {
		final Path path = new Path();
		return path;
	}

	public Path save(final Path path) {
		// We have to be the brotherhood owner of this parade
		this.assertParadeOwner(path.getParade().getId());
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
				res = this.pathRepository.getParadePath(paradeId);
		} catch (final Exception e) {
			// This is because LoginService.getPrincipal() throws an exception in case none is logged
			if (parade.getIsFinal())
				res = this.pathRepository.getParadePath(paradeId);
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
		final Path path = this.create();
		final Parade parade = this.paradeService.findOne(paradeId);
		path.setParade(parade);
		return this.pathRepository.save(path);
	}

	// ========================== PRIVATE METHODS
	private void assertParadeOwner(final int paradeId) {
		final Parade parade = this.paradeService.findOne(paradeId);
		final int loggedAccountId = LoginService.getPrincipal().getId();

		Assert.isTrue(parade.getBrotherhood().getUserAccount().getId() == loggedAccountId);

	}

}
