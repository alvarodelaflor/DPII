
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PathRepository;
import security.Authority;
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


	public Path create() {
		final Path path = new Path();
		return path;
	}

	public Path save(final Path path) {
		// We have to be a brotherhood
		this.assertAuthority("BROTHERHOOD");

		final Path res = this.pathRepository.save(path);

		return res;
	}

	public List<Segment> getAllSegments(final Path path) {
		final List<Segment> fullPath = new ArrayList<>();
		Segment next = path.getOrigin();
		while (next != null) {
			fullPath.add(next);
			next = next.getDestination();
		}
		return fullPath;
	}

	public Collection<Path> getParadePaths(final int paradeId) {
		// In case we aren't the parade owner we have to check that the parade is in final mode
		final Parade parade = this.paradeService.findOne(paradeId);
		Collection<Path> res = null;
		try {
			final int loggedAccountId = LoginService.getPrincipal().getId();
			if (parade.getIsFinal() || parade.getBrotherhood().getUserAccount().getId() == loggedAccountId)
				res = this.pathRepository.getParadePaths(paradeId);
		} catch (final Exception e) {
			// This is because LoginService.getPrincipal() throws an exception in case none is logged
			if (parade.getIsFinal())
				res = this.pathRepository.getParadePaths(paradeId);
		}
		return res;
	}
	// ------------------------------- Private methods
	private boolean checkAuthority(final String authority) {
		final Authority au = new Authority();
		au.setAuthority(authority);
		return LoginService.getPrincipal().getAuthorities().contains(au);
	}

	private void assertAuthority(final String authority) {
		Assert.isTrue(this.checkAuthority(authority));
	}

}
