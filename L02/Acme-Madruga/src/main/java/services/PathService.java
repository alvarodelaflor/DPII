
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


	public Path create() {
		final Path path = new Path();
		return path;
	}

	public Path save(final Path path) {
		// We have to be the brotherhood owner of this parade
		final Parade parade = this.paradeService.findOne(path.getParade().getId());
		final int loggedAccountId = LoginService.getPrincipal().getId();

		Assert.isTrue(parade.getBrotherhood().getUserAccount().getId() == loggedAccountId);

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

}
