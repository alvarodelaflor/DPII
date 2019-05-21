
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Accomodation;
import repositories.AccomodationRepository;

@Service
@Transactional
public class AccomodationService {

	@Autowired
	private HostService				hostService;

	@Autowired
	private AccomodationRepository	accomodationRepo;


	// ---------- public class methods

	public Collection<Accomodation> findAll() {
		return this.accomodationRepo.findAll();
	}

	public Accomodation findOne(final int id) {
		return this.accomodationRepo.findOne(id);
	}

}
