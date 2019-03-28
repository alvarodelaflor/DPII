
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
import domain.Area;
import domain.Finder;
import domain.Member;
import domain.Parade;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository		finderRepository;

	@Autowired
	private MemberService			memberService;

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
	public Finder findByLoggedMemberWithCache() {
		// We have to check member authority
		Assert.isTrue(this.checkAuthority("MEMBER"));

		final Member member = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		final Finder res = this.finderRepository.getByMember(member.getId());
		// Si la cach� ha expirado volvemos a buscar los resultados con los criterios definidos en el finder
		if (res.getExpirationDate() == null || res.getExpirationDate().before(new Date())) {
			final Collection<Parade> parades = this.findByFilter(res.getKeyword(), res.getMinDate(), res.getMaxDate(), res.getArea());
			res.setParades(this.getParadeAmount(parades));

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
		// We have to check member authority
		Assert.isTrue(this.checkAuthority("MEMBER"));
		final Collection<Parade> parades = this.findByFilter(finder.getKeyword(), finder.getMinDate(), finder.getMaxDate(), finder.getArea());
		finder.setParades(this.getParadeAmount(parades));
		final Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, this.configurationService.getConfiguration().getCacheHours());
		finder.setExpirationDate(c.getTime());
		return finder;
	}

	public Finder save(final Finder finder) {
		if (finder.getId() != 0)
			Assert.isTrue(this.checkAuthority("MEMBER"));
		return this.finderRepository.save(finder);
	}

	public Finder reconstructWithCache(final Finder finder, final BindingResult binding) {
		final Finder aux = this.findByLoggedMemberWithCache();
		finder.setId(aux.getId());
		finder.setVersion(aux.getVersion());
		finder.setExpirationDate(aux.getExpirationDate());
		this.validator.validate(finder, binding);
		return finder;
	}

	public Finder reconstructNoCache(final Finder finder, final BindingResult binding) {
		final Member member = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		final Finder aux = this.finderRepository.getByMember(member.getId());
		finder.setId(aux.getId());
		finder.setVersion(aux.getVersion());
		finder.setExpirationDate(aux.getExpirationDate());
		this.validator.validate(finder, binding);
		return finder;
	}

	public Collection<Parade> findByFilter(final String keyword, Date minDate, Date maxDate, final Area area) {
		Collection<Parade> parades;

		minDate = minDate == null ? new GregorianCalendar(0, Calendar.JANUARY, 1).getTime() : minDate;
		maxDate = maxDate == null ? new GregorianCalendar(9999, Calendar.DECEMBER, 31).getTime() : maxDate;
		if (area == null)
			parades = this.finderRepository.findByFilterNoArea(keyword, minDate, maxDate);
		else
			parades = this.finderRepository.findByFilterWithArea(keyword, minDate, maxDate, area);
		return parades;
	}

	public void delete(final Finder finder) {
		// We don't have to check any authority because this won't be called on the client side
		this.finderRepository.delete(finder);
	}

	public void updateParades(final Parade p) {
		final Collection<Finder> finders = this.finderRepository.getFindersWithParade(p.getId());
		for (final Finder f : finders)
			f.getParades().remove(p);
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

	private Collection<Parade> getParadeAmount(final Collection<Parade> parades) {
		final Collection<Parade> amount = new HashSet<>();
		int i = 0;
		for (final Parade p : parades) {
			if (i >= this.configurationService.getConfiguration().getCacheAmount())
				break;
			else
				amount.add(p);
			i++;
		}
		return amount;
	}
}
