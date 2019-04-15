
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.Authority;
import security.LoginService;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository		finderRepository;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ConfigurationService	configurationService;


	public Finder create() {
		final Finder res = new Finder();
		return res;
	}

	/**
	 * Comprueba si la cach� ha expirado, vuelve a buscar desfiles en caso afirmativo y actualiza el finder
	 * 
	 * @return Finder con los datos de la cach� o nuevos si ha expirado
	 */
	public Finder findByLoggedHackerWithCache() {
		// We have to check hacker authority
		Assert.isTrue(this.checkAuthority("HACKER"));

		final Hacker hacker = this.hackerService.getHackerByUserAccountId(LoginService.getPrincipal().getId());
		final Finder res = this.finderRepository.getByHacker(hacker.getId());
		// Si la cach� ha expirado volvemos a buscar los resultados con los criterios definidos en el finder
		if (res.getExpirationDate() == null || res.getExpirationDate().before(new Date())) {
			final Collection<Position> positions = this.findByFilter(res.getKeyword(), res.getMinSalary(), res.getMaxSalary(), res.getDeadline());
			res.setPositions(this.getPositionAmount(positions));

			final Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR, this.configurationService.getConfiguration().getCacheHours());
			res.setExpirationDate(c.getTime());
		}
		return res;
	}
	/**
	 * Vuelve a buscar desfiles y actualiza el finder
	 * 
	 * @return Finder con los datos nuevos
	 */
	public Finder findNoCache(final Finder finder) {
		// We have to check hacker authority
		Assert.isTrue(this.checkAuthority("HACKER"));
		final Collection<Position> positions = this.findByFilter(finder.getKeyword(), finder.getMinSalary(), finder.getMaxSalary(), finder.getDeadline());
		finder.setPositions(this.getPositionAmount(positions));
		final Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, this.configurationService.getConfiguration().getCacheHours());
		finder.setExpirationDate(c.getTime());
		return finder;
	}

	public Finder save(final Finder finder) {
		if (finder.getId() != 0)
			Assert.isTrue(this.checkAuthority("HACKER"));
		return this.finderRepository.save(finder);
	}

	public Finder reconstructWithCache(final Finder finder, final BindingResult binding) {
		final Finder aux = this.findByLoggedHackerWithCache();
		finder.setId(aux.getId());
		finder.setVersion(aux.getVersion());
		finder.setExpirationDate(aux.getExpirationDate());
		this.validator.validate(finder, binding);
		return finder;
	}

	public Finder reconstructNoCache(final Finder finder, final BindingResult binding) {
		final Hacker hacker = this.hackerService.getHackerByUserAccountId(LoginService.getPrincipal().getId());
		final Finder aux = this.finderRepository.getByHacker(hacker.getId());
		finder.setId(aux.getId());
		finder.setVersion(aux.getVersion());
		finder.setExpirationDate(aux.getExpirationDate());
		this.validator.validate(finder, binding);
		return finder;
	}

	public Collection<Position> findByFilter(final String keyword, Double minSalary, Double maxSalary, Date deadline) {
		Collection<Position> positions;

		deadline = deadline == null ? new GregorianCalendar(0, Calendar.JANUARY, 1).getTime() : deadline;
		minSalary = minSalary == null ? 0. : minSalary;
		maxSalary = maxSalary == null ? 999999.9 : maxSalary;
		positions = this.finderRepository.findByFilter(keyword, minSalary, maxSalary, deadline);
		return positions;
	}

	public void delete(final Finder finder) {
		// We don't have to check any authority because this won't be called on the client side
		this.finderRepository.delete(finder);
	}

	public void updatePositions(final Position p) {
		final Collection<Finder> finders = this.finderRepository.getFindersWithPosition(p.getId());
		for (final Finder f : finders)
			f.getPositions().remove(p);
	}

	private boolean checkAuthority(final String authority) {
		final Authority au = new Authority();
		au.setAuthority(authority);
		return LoginService.getPrincipal().getAuthorities().contains(au);
	}

	public Float minNumberOfResult() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.finderRepository.minNumberOfResult();
	}

	public Float maxNumberOfResult() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.finderRepository.maxNumberOfResult();
	}

	public Float avgNumberOfResult() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.finderRepository.avgNumberOfResult();
	}

	public Float stddevNumberOfResult() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.finderRepository.stddevNumberOfResult();
	}

	public Float ratioResult() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.finderRepository.ratioFinder();
	}

	private Collection<Position> getPositionAmount(final Collection<Position> positions) {
		final Collection<Position> amount = new HashSet<>();
		int i = 0;
		for (final Position p : positions) {
			if (i >= this.configurationService.getConfiguration().getCacheAmount())
				break;
			else
				amount.add(p);
			i++;
		}
		return amount;
	}
}
