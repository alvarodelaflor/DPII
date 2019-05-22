
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Accomodation;
import domain.FinderAccomodation;
import domain.TravelAgency;
import repositories.FinderAccomodationRepository;
import security.Authority;
import security.LoginService;

@Service
@Transactional
public class FinderAccomodationService {

	@Autowired
	private FinderAccomodationRepository	finderRepository;

	@Autowired
	private TravelAgencyService				travelAgencyService;

	@Autowired
	private Validator						validator;


	public FinderAccomodation create() {
		final FinderAccomodation res = new FinderAccomodation();
		res.setKeyword("");
		res.setPrice(99999.99);
		res.setPeople(1);
		res.setAddress("");
		return res;
	}

	public FinderAccomodation findByLoggedTravelAgencyWithCache() {
		// We have to check rookie authority
		Assert.isTrue(this.checkAuthority("TRAVELAGENCY"));

		final TravelAgency travelAgency = this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
		final FinderAccomodation res = this.finderRepository.getByTravelAgency(travelAgency.getId());
		// Si la cache ha expirado volvemos a buscar los resultados con los criterios definidos en el finder

		final Collection<Accomodation> accomodations = this.findByFilter(res.getKeyword(), res.getPrice(), res.getPeople(), res.getAddress());
		res.setAccomodations(accomodations);

		return res;
	}
	/**
	 * Vuelve a buscar hospedajes y actualiza el finder
	 *
	 * @return Finder con los datos nuevos
	 */
	public FinderAccomodation findNoCache(final FinderAccomodation finder) {
		// We have to check travelAgency authority
		Assert.isTrue(this.checkAuthority("TRAVELAGENCY"));
		final Collection<Accomodation> accomodations = this.findByFilter(finder.getKeyword(), finder.getPrice(), finder.getPeople(), finder.getAddress());
		finder.setAccomodations(accomodations);
		return finder;
	}

	public FinderAccomodation save(final FinderAccomodation finder) {
		if (finder.getId() != 0)
			Assert.isTrue(this.checkAuthority("TRAVELAGENCY"));
		return this.finderRepository.save(finder);
	}

	public FinderAccomodation reconstructNoCache(final FinderAccomodation finder, final BindingResult binding) {
		final TravelAgency travelAgency = this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
		final FinderAccomodation aux = this.finderRepository.getByTravelAgency(travelAgency.getId());
		finder.setId(aux.getId());
		finder.setVersion(aux.getVersion());
		this.validator.validate(finder, binding);
		return finder;
	}

	public Collection<Accomodation> findByFilter(String keyword, Double price, Integer maxPeople, String address) {
		Collection<Accomodation> accomodations;
		keyword = keyword == null ? "" : keyword;
		address = address == null ? "" : address;
		price = price == null ? 2000. : price;
		maxPeople = maxPeople == null ? 1 : maxPeople;
		accomodations = this.finderRepository.findByFilter(keyword, price, maxPeople, address);
		return accomodations;
	}

	public void delete(final FinderAccomodation finder) {
		// We don't have to check any authority because this won't be called on the client side
		this.finderRepository.delete(finder);
	}

	public void updateAccomodations(final Accomodation p) {
		final Collection<FinderAccomodation> finders = this.finderRepository.getFindersWithAccomodation(p.getId());
		for (final FinderAccomodation f : finders)
			f.getAccomodations().remove(p);
	}

	private boolean checkAuthority(final String authority) {
		final Authority au = new Authority();
		au.setAuthority(authority);
		return LoginService.getPrincipal().getAuthorities().contains(au);
	}

}
