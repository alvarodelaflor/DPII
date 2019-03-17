
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SegmentRepository;
import security.LoginService;
import domain.Parade;
import domain.Path;
import domain.Segment;

@Service
public class SegmentService {

	@Autowired
	private SegmentRepository	segmentRepository;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private Validator			validator;


	public Segment create() {
		final Segment res = new Segment();

		return res;
	}

	public Segment save(final Segment segment, final int paradeId) {
		// We need the paradeId to check if we are the owner of the parade
		final int loggedId = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId();
		final Parade parade = this.paradeService.findOne(paradeId);
		Assert.isTrue(loggedId == parade.getBrotherhood().getId());
		return this.segmentRepository.save(segment);
	}
	public List<Segment> getAllSegments(final Path path) {
		List<Segment> fullPath = null;
		Segment next = null;
		if (path != null) {
			next = path.getOrigin();
			fullPath = new ArrayList<>();
			while (next.getDestination() != null) {
				fullPath.add(next);
				next = next.getDestination();
			}
		}
		return fullPath;
	}

	public Segment reconstruct(final Segment segment, final BindingResult binding) {
		Segment res = this.create();
		if (segment.getId() == 0)
			res = segment;
		else {
			Segment aux;
			aux = this.segmentRepository.findOne(segment.getId());
			res.setId(aux.getId());
			res.setVersion(aux.getVersion());
			res.setLatitude(segment.getLatitude());
			res.setLongitude(segment.getLongitude());
			res.setDestination(aux.getDestination());
			res.getDestination().setLatitude(segment.getDestination().getLatitude());
			res.getDestination().setLongitude(segment.getDestination().getLongitude());
		}
		this.validator.validate(res, binding);

		return res;
	}
}
