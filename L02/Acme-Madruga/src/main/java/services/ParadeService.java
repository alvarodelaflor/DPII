
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ParadeRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Parade;
import domain.PositionAux;

/*
 * CONTROL DE CAMBIOS ParadeService.java
 * 
 * ALVARO 17/02/2019 12:02 CREACI�N DE LA CLASE
 * ALVARO 17/02/2019 16:35 A�ADIDO RECONSTRUIDOR PARADE Y REPARADO GENERADOR DE TICKETS
 */

@Service
public class ParadeService {

	//Managed Repository -------------------	

	@Autowired
	private ParadeRepository	paradeRepository;

	//Supporting services ------------------
	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private Validator			validator;

	@Autowired
	private PositionAuxService	positionAuxService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private PathService			pathService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private TickerService		tickerService;


	//Simple CRUD Methods ------------------

	public Parade create() {

		final Parade parade = new Parade();
		return parade;

	}

	public Collection<Parade> findAll() {
		return this.paradeRepository.findAll();
	}

	public Parade findOne(final int id) {
		return this.paradeRepository.findOne(id);
	}
	public Parade save(final Parade parade) {
		final Parade paradeUpdate = this.paradeRepository.save(parade);
		final List<PositionAux> positionAuxs = new ArrayList<PositionAux>();
		if (parade.getIsFinal().equals(true))
			for (int i = 0; i < parade.getMaxRow(); i++)
				for (int j = 0; j < parade.getMaxColum(); j++) {
					final PositionAux positionAux = this.positionAuxService.create();
					positionAux.setRow(i);
					positionAux.setColum(j);
					positionAux.setParade(paradeUpdate);
					positionAux.setStatus(false);
					positionAuxs.add(positionAux);
				}
		this.positionAuxService.saveAll(positionAuxs);
		return paradeUpdate;
	}

	public void delete(final Parade parade) {
		Assert.notNull(this.paradeRepository.findOne(parade.getId()), "La parade no existe");
		Assert.isTrue(LoginService.getPrincipal().getId() == parade.getBrotherhood().getUserAccount().getId(), "brotherhoodLoggerDiferent");
		this.pathService.deleteWithParade(parade.getId());
		this.sponsorshipService.deleteParadeSponsorships(parade.getId());
		this.requestService.deleteAllRequestByParade(parade.getId());
		this.positionAuxService.deleteAllPositionByParade(parade.getId());
		this.finderService.updateParades(parade);
		this.paradeRepository.delete(parade);
	}

	public Parade update(final Parade parade) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		Assert.isTrue(parade.getBrotherhood().equals(brotherhood));
		Assert.isTrue(this.findOne(parade.getId()) != null);
		final Parade saveParade = this.paradeRepository.save(parade);
		return saveParade;
	}

	//Other Methods

	public Collection<Parade> getParadeByBrotherhoodId(final int brotherhoodId) {
		return this.paradeRepository.findParadesByBrotherhood(brotherhoodId);
	}

	public Collection<Parade> getParadeByFloatId(final int floatId) {
		return this.paradeRepository.findParadesByFloat(floatId);
	}

	public Collection<Parade> findAllBrotherhoodLogged() {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		return this.paradeRepository.findParadesByBrotherhood(brotherhood.getId());
	}

	public Parade show(final int paradeId) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId()) != null);
		final Brotherhood brotherhoodLogin = this.brotherhoodService.getBrotherhoodByUserAccountId(login.getId());
		Assert.isTrue(this.findOne(paradeId) != null);
		final Brotherhood brotherhoodParade = this.findOne(paradeId).getBrotherhood();
		Assert.isTrue(brotherhoodLogin.equals(brotherhoodParade));
		return this.findOne(paradeId);
	}

	public Parade reconstruct(final Parade parade, final BindingResult binding) {
		Parade result;

		if (parade.getId() == 0) {
			parade.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
			if (parade.getMoment() != null)
				parade.setTicker(this.tickerService.randomTicker());
			result = parade;
		} else {
			result = this.paradeRepository.findOne(parade.getId());
			//			result.setTitle(parade.getTitle());
			//			result.setDescription(parade.getDescription());
			//			result.setMoment(parade.getMoment());
			//			result.setIsFinal(parade.getIsFinal());
			//			result.setMaxRow(parade.getMaxRow());
			//			result.setFloat(parade.getFloat());
			//			if (parade.getMoment() != null && result.getTicker() == null)
			//				result.setTicker(this.randomTicker(parade));
			//			if (result.getBrotherhood() == null)
			parade.setId(result.getId());
			parade.setVersion(result.getVersion());
			parade.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
			parade.setMoment(result.getMoment());
			parade.setTicker(result.getTicker());
			result = parade;
		}
		this.validator.validate(result, binding);
		return result;
	}

	public Collection<Parade> findParadesByTicker(final String ticker) {
		return this.paradeRepository.findParadesByTicker(ticker);
	}

	public Collection<Parade> paradeOrganised() {
		return this.paradeRepository.findAllWithCreationDateTimeBeforeI(LocalDateTime.now().toDate(), this.sumarMes(LocalDateTime.now().toDate()));
	}

	@SuppressWarnings("deprecation")
	private Date sumarMes(final Date fecha) {
		final Date res = LocalDateTime.now().toDate();
		if (res.getMonth() == 11) {
			res.setYear(res.getYear() + 1);
			res.setMonth(1);
		} else
			res.setMonth(res.getMonth() + 1);

		System.out.println(res);
		return res;
	}

	public String minParade() {
		final Parade p = this.paradeRepository.minParade();
		return p == null ? null : p.getTitle();
	}
	public String maxParade() {
		final Parade p = this.paradeRepository.maxParade();
		return p == null ? null : p.getTitle();
	}
	public Integer minParadeN() {
		return this.paradeRepository.minParadeN();
	}
	public Integer maxParadeN() {
		return this.paradeRepository.maxParadeN();
	}

	public Collection<Parade> findParadesBrotherhoodFinal(final Integer brotherhood) {
		return this.paradeRepository.findParadesBrotherhoodFinal(brotherhood);
	}

	public void createCopy(final int paradeId) {
		final Parade parade = this.paradeRepository.findOne(paradeId);

		Assert.notNull(parade);
		// We have to check that we are the owner of this parade
		this.assertParadeOwner(parade);

		final Parade res = this.create();

		res.setTicker(this.tickerService.randomTicker());
		res.setStatus(null);
		res.setRejectionReason(null);
		res.setIsFinal(false);

		res.setBrotherhood(parade.getBrotherhood());
		res.setDescription(parade.getDescription());
		res.setFloatt(parade.getFloatt());
		res.setMaxColum(parade.getMaxColum());
		res.setMaxRow(parade.getMaxRow());
		res.setMoment(parade.getMoment());
		res.setTitle(parade.getTitle());

		this.paradeRepository.save(res);
	}

	private void assertParadeOwner(final Parade parade) {
		final int loggedAccountId = LoginService.getPrincipal().getId();

		Assert.isTrue(parade.getBrotherhood().getUserAccount().getId() == loggedAccountId);

	}
	
	public Collection<Parade> findParadesByChapter(final int chapterId) {
		final Collection<Parade> parades = this.paradeRepository.findParadesByChapter(chapterId);
		return parades;
	}

	public Collection<Parade> findSubmittedParadesByChapter(final int chapterId) {
		final Collection<Parade> parades = this.paradeRepository.findSubmittedParadesByChapter(chapterId);
		return parades;
	}

	public Collection<Parade> findAcceptedParadesByChapter(final int chapterId) {
		final Collection<Parade> parades = this.paradeRepository.findAcceptedByChapter(chapterId);
		return parades;
	}

	public Collection<Parade> findRejectedParadesByChapter(final int chapterId) {
		final Collection<Parade> parades = this.paradeRepository.findRejectedByChapter(chapterId);
		return parades;
	}

	public Parade reconstructStatus(final Parade parade, final BindingResult binding) {
		Parade result;
		result = this.paradeRepository.findOne(parade.getId());
		parade.setId(result.getId());
		parade.setVersion(result.getVersion());
		parade.setBrotherhood(result.getBrotherhood());
		parade.setMoment(result.getMoment());
		parade.setTicker(result.getTicker());
		parade.setTitle(result.getTitle());
		parade.setDescription(result.getDescription());
		parade.setIsFinal(result.getIsFinal());
		parade.setMaxRow(result.getMaxRow());
		parade.setMaxColum(result.getMaxColum());
		parade.setFloatt(result.getFloatt());
		parade.setPath(result.getPath());
		result = parade;
		this.validator.validate(result, binding);
		return result;
	}
	public Parade updateStatus(final Parade parade) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.chapterService.findByUserAccount(login.getId()) != null);
		final Chapter chapter = this.chapterService.findByUserAccount(login.getId());
		Assert.isTrue(parade.getBrotherhood().getArea().getChapter().equals(chapter));
		Assert.isTrue(this.findOne(parade.getId()) != null);
		final Parade saveParade = this.paradeRepository.save(parade);
		return saveParade;
	}
}
