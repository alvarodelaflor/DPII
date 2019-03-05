
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

import repositories.FinderRepository;
import security.Authority;
import security.LoginService;
import domain.Area;
import domain.Finder;
import domain.Member;
import domain.Procession;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	finderRepository;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private Validator			validator;


	public Finder create() {
		final Finder res = new Finder();
		return res;
	}

	/**
	 * Comprueba si la caché ha expirado, vuelve a buscar procesiones en caso afirmativo y actualiza el finder
	 * 
	 * @return Finder con los datos de la caché o nuevos si ha expirado
	 */
	public Finder findByLoggedMemberWithCache() {
		// We have to check member authority
		Assert.isTrue(this.checkAuthority("MEMBER"));

		final Member member = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		final Finder res = this.finderRepository.findOne(member.getId());

		// Si la caché ha expirado volvemos a buscar los resultados con los criterios definidos en el finder
		if (res.getExpirationDate() == null || res.getExpirationDate().before(new Date())) {
			final Collection<Procession> processions = this.findByFilter(res.getKeyword(), res.getMinDate(), res.getMaxDate(), res.getArea());
			res.setProcessions(processions);
			// TODO: Actualizar fecha de expiración
		}
		return res;
	}

	/**
	 * Vuelve a buscar procesiones y actualiza el finder
	 * 
	 * @return Finder con los datos nuevos
	 */
	public Finder findByLoggedMemberNoCache() {
		// We have to check member authority
		Assert.isTrue(this.checkAuthority("MEMBER"));
		final Member member = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());

		final Finder res = this.finderRepository.findOne(member.getId());
		final Collection<Procession> processions = this.findByFilter(res.getKeyword(), res.getMinDate(), res.getMaxDate(), res.getArea());
		res.setProcessions(processions);
		// TODO: Actualizar fecha de expiración
		return res;
	}

	public Finder save(final Finder finder) {
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
		final Finder aux = this.findByLoggedMemberNoCache();
		finder.setId(aux.getId());
		finder.setVersion(aux.getVersion());
		finder.setExpirationDate(aux.getExpirationDate());
		this.validator.validate(finder, binding);
		return finder;
	}

	public Collection<Procession> findByFilter(final String keyword, Date minDate, Date maxDate, final Area area) {
		final Collection<Procession> processions;
		minDate = minDate == null ? new GregorianCalendar(0, Calendar.JANUARY, 1).getTime() : minDate;
		maxDate = maxDate == null ? new GregorianCalendar(9999, Calendar.DECEMBER, 31).getTime() : maxDate;
		if (area == null)
			processions = this.finderRepository.findByFilterNoArea(keyword, minDate, maxDate);
		else
			processions = this.finderRepository.findByFilterWithArea(keyword, minDate, maxDate, area);
		return null;
	}

	private boolean checkAuthority(final String authority) {
		final Authority au = new Authority();
		au.setAuthority(authority);
		return LoginService.getPrincipal().getAuthorities().contains(au);
	}

	public Float minNumberOfResult() {
		return this.finderRepository.minNumberOfResult();
	}

	public Float maxNumberOfResult() {
		return this.finderRepository.maxNumberOfResult();
	}

	public Float avgNumberOfResult() {
		return this.finderRepository.avgNumberOfResult();
	}

	public Float stddevNumberOfResult() {
		return this.finderRepository.stddevNumberOfResult();
	}

	public Float ratioResult() {
		return this.finderRepository.ratioFinder();
	}

}
