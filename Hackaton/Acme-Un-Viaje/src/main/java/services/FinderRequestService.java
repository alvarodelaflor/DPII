
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.FinderRequest;
import domain.Request;
import domain.TravelAgency;
import repositories.FinderRequestRepository;
import security.Authority;
import security.LoginService;

@Service
@Transactional
public class FinderRequestService {

	@Autowired
	private FinderRequestRepository	finderRepository;

	@Autowired
	private TravelAgencyService		travelAgencyService;

	@Autowired
	private Validator				validator;


	public FinderRequest create() {
		final FinderRequest res = new FinderRequest();
		res.setPlace("");
		res.setPrice(0.);

		return res;
	}

	public FinderRequest findByLoggedTravelAgencyWithCache() {
		// We have to check rookie authority
		Assert.isTrue(this.checkAuthority("TRAVELAGENCY"));

		final TravelAgency travelAgency = this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
		final FinderRequest res = this.finderRepository.getByTravelAgency(travelAgency.getId());
		// Si la cache ha expirado volvemos a buscar los resultados con los criterios definidos en el finder

		final Collection<Request> requests = this.findByFilter(res.getPlace(), res.getPrice(), res.getStartDate());
		res.setRequests(requests);

		return res;
	}
	/**
	 * Vuelve a buscar hospedajes y actualiza el finder
	 *
	 * @return Finder con los datos nuevos
	 */
	public FinderRequest findNoCache(final FinderRequest finder) {
		// We have to check travelAgency authority
		Assert.isTrue(this.checkAuthority("TRAVELAGENCY"));
		final Collection<Request> requests = this.findByFilter(finder.getPlace(), finder.getPrice(), finder.getStartDate());
		finder.setRequests(requests);
		return finder;
	}

	public FinderRequest save(final FinderRequest finder) {
		if (finder.getId() != 0)
			Assert.isTrue(this.checkAuthority("TRAVELAGENCY"));
		return this.finderRepository.save(finder);
	}

	public FinderRequest reconstructNoCache(final FinderRequest finder, final BindingResult binding) {
		final TravelAgency travelAgency = this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
		final FinderRequest aux = this.finderRepository.getByTravelAgency(travelAgency.getId());
		finder.setId(aux.getId());
		finder.setVersion(aux.getVersion());
		this.validator.validate(finder, binding);
		return finder;
	}

	public Collection<Request> findByFilter(String place, Double price, Date startDate) {
		Collection<Request> requests;
		place = place == null ? "" : place;
		startDate = startDate == null ? new GregorianCalendar(0, Calendar.JANUARY, 1).getTime() : startDate;
		price = price == null ? 0. : price;
		requests = this.finderRepository.findByFilter(place, price, startDate);
		return requests;
	}

	public void delete(final FinderRequest finder) {
		// We don't have to check any authority because this won't be called on the client side
		this.finderRepository.delete(finder);
	}

	public void updateRequests(final Request p) {
		final Collection<FinderRequest> finders = this.finderRepository.getFindersWithRequest(p.getId());
		for (final FinderRequest f : finders)
			f.getRequests().remove(p);
	}

	private boolean checkAuthority(final String authority) {
		final Authority au = new Authority();
		au.setAuthority(authority);
		return LoginService.getPrincipal().getAuthorities().contains(au);
	}

}
