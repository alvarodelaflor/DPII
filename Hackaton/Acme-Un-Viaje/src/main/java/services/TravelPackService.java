
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.TravelAgency;
import domain.TravelPack;
import repositories.TravelPackRepository;
import security.LoginService;

@Service
@Transactional
public class TravelPackService {

	@Autowired
	private TravelPackRepository	travelPackRepository;
	@Autowired
	private TravelAgencyService		travelAgencyService;


	public void delete(final TravelPack pack) {

		Assert.isTrue(pack.getTravelAgency().getUserAccount().getId() == LoginService.getPrincipal().getId());
		this.travelPackRepository.delete(pack.getId());
	}

	public TravelPack create() {
		final TravelPack pack = new TravelPack();
		pack.setDraft(true);
		pack.setTravelAgency(this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId()));
		return pack;

	}

	public TravelPack save(final TravelPack travelPack) {
		Assert.isTrue(travelPack.getDraft(), "The travel pack is already in final mode");
		return this.travelPackRepository.save(travelPack);
	}

	public TravelPack findOne(final int travelPackId) {
		return this.travelPackRepository.findOne(travelPackId);
	}

	public Collection<TravelPack> getTravelAgencyPacks() {
		final TravelAgency travel = this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
		return this.travelPackRepository.getTravelAgencyPacks(travel.getId());
	}

}
