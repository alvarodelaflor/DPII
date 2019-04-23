
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AuthUtils;
import domain.Actor;
import domain.Company;
import domain.Hacker;
import domain.Message;
import domain.Position;
import domain.Problem;
import domain.Tag;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private HackerService		hackerService;

	@Autowired
	private MessageService		msgService;

	@Autowired
	private TagService			tagService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionDataService	positionDataService;

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private Validator			validator;


	public Position create() {
		final Position res = new Position();
		return res;
	}
	// FINDALL ---------------------------------------------------------------
	public Collection<Position> findALL() {
		return this.positionRepository.findAll();
	}

	// findAllPositionStatusTrueCancelFalseByCompany ---------------------------------------------------------------
	public Collection<Position> findAllPositionStatusTrueCancelFalseByCompany(final int companyId) {
		System.out.println(companyId);
		final Collection<Position> p = this.positionRepository.findAllPositionStatusTrueCancelFalseByCompany(companyId);
		return p;
	}

	// findAllPositionWithStatusTrueCancelFalse ---------------------------------------------------------------
	public Collection<Position> findAllPositionWithStatusTrueCancelFalse() {
		final Collection<Position> p = this.positionRepository.findAllPositionWithStatusTrueCancelFalse();
		return p;
	}

	// findAllPositionByCompany ---------------------------------------------------------------
	public Collection<Position> findAllPositionWithStatusTrue() {
		final Collection<Position> p = this.positionRepository.findAllPositionWithStatusTrue();
		return p;
	}

	// FINDONE ---------------------------------------------------------------
	public Position findOne(final int id) {
		return this.positionRepository.findOne(id);
	}

	// searchPosition ---------------------------------------------------------------
	public Collection<Position> search(final String palabra) {
		final HashSet<Position> p = new HashSet<>();
		p.addAll(this.positionRepository.findWithDescription(palabra));
		p.addAll(this.positionRepository.findWithCompanyName(palabra));
		p.addAll(this.positionRepository.findWitheProfile(palabra));
		p.addAll(this.positionRepository.findWithSkills(palabra));
		p.addAll(this.positionRepository.findWithTitle(palabra));
		p.addAll(this.positionRepository.findWithTechs(palabra));
		System.out.println(p);
		return p;
	}

	// DashBoard:
	public Float avgPositionPerCompany() {

		return this.positionRepository.avgPositionPerCompany();
	}

	public Float minPositionPerCompany() {

		return this.positionRepository.minPositionPerCompany();
	}

	public Float maxPositionPerCompany() {

		return this.positionRepository.maxPositionPerCompany();
	}

	public Float stddevPositionPerCompany() {

		return this.positionRepository.stddevPositionPerCompany();
	}

	public Double avgSalaryPerPosition() {

		return this.positionRepository.avgSalaryPerPosition();
	}

	public Double minSalaryPerPosition() {

		return this.positionRepository.minSalaryPerPosition();
	}

	public Double maxSalaryPerPosition() {

		return this.positionRepository.maxSalaryPerPosition();
	}

	public Double stddevSalaryPerPosition() {

		return this.positionRepository.stddevSalaryPerPosition();
	}

	public String bestPosition() {

		final List<String> ls = this.positionRepository.bestPositon();
		String res = "";
		if (!ls.isEmpty())
			res = ls.get(0);
		return res;
	}

	public String worstPosition() {

		final List<String> ls = this.positionRepository.worstPositon();
		String res = "";
		if (!ls.isEmpty())
			res = ls.get(0);
		return res;
	}

	public Collection<Position> findAllPositionsByLoggedCompany() {
		Assert.isTrue(AuthUtils.checkLoggedAuthority("COMPANY"));
		final int companyId = this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId()).getId();
		return this.positionRepository.findAllPositionsByCompany(companyId);
	}

	public Position findOneLoggedIsOwner(final int positionId) {
		Assert.isTrue(this.checkPositionOwner(positionId), "Logged company is not the owner of position " + positionId);
		return this.positionRepository.findOne(positionId);
	}
	private boolean checkPositionOwner(final int positionId) {
		final int loggedId = LoginService.getPrincipal().getId();
		final int ownerId = this.positionRepository.findOne(positionId).getCompany().getUserAccount().getId();
		return loggedId == ownerId;
	}
	public Position reconstruct(final Position position, final BindingResult binding) {
		Position res = this.create();
		if (position.getId() == 0) {
			res = position;
			final Company owner = this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId());

			res.setCompany(owner);
			res.setStatus(false);
			res.setCancel(false);
			res.setTicker(this.getTickerForCompany(owner));
		} else {
			final Position dbPosition = this.positionRepository.findOne(position.getId());
			// These we recover from db
			res.setId(dbPosition.getId());
			res.setVersion(dbPosition.getVersion());
			res.setCompany(dbPosition.getCompany());
			res.setTicker(dbPosition.getTicker());
			res.setCancel(false);

			// These we want to modify
			res.setDeadline(position.getDeadline());
			res.setDescription(position.getDescription());
			res.setProfile(position.getProfile());
			res.setSalary(position.getSalary());
			res.setSkills(position.getSkills());
			res.setStatus(position.getStatus());
			res.setTechs(position.getTechs());
			res.setTitle(position.getTitle());
		}
		this.validator.validate(res, binding);
		return res;
	}

	private String getTickerForCompany(final Company owner) {
		String ticker = "";
		final String commercialName = owner.getCommercialName();
		String croppedName;
		if (commercialName.length() >= 4)
			croppedName = commercialName.substring(0, 4).toUpperCase();
		else
			croppedName = commercialName + "XXXX".substring(commercialName.length());

		int validTicker = 1;
		Integer randomNumber = new Integer(this.generateRandomNumber());
		while (validTicker != 0) {
			validTicker = 0;
			final String number = String.valueOf(randomNumber);
			ticker = croppedName + "-" + "0000".substring(0, 4 - number.length()) + number;
			randomNumber = randomNumber == 9999 ? 0 : randomNumber + 1;
			validTicker += this.positionRepository.countByTicker(ticker);
		}
		return ticker;
	}
	private String generateRandomNumber() {
		String res = "";
		final Random random = new Random();
		for (int i = 0; i < 4; i++)
			res += random.nextInt(10);
		return res;
	}

	public Position save(final Position pos) {
		Assert.isTrue(AuthUtils.checkLoggedAuthority("COMPANY"), "Logged user is not a company");
		if (pos.getId() != 0) {
			// Position exists so we must be the owner
			Assert.isTrue(this.checkPositionOwner(pos.getId()), "Logged user is not the position owner");

			// Database position has to be in draft mode
			Assert.isTrue(this.getPositionDatabaseStatus(pos.getId()) == false, "Position is in final mode");
			Assert.isTrue(this.getPositionDatabaseCancel(pos.getId()) == false, "Position is cancelled");

			// In case we are setting this as final, we have to have at least 2 problems
			if (pos.getStatus()) {
				final int problemCount = this.problemService.getProblemCount(pos.getId());
				Assert.isTrue(problemCount >= 2, "Position can't be setted to final mode because it has less than 2 problems");

				final Collection<Hacker> hackers = this.hackerService.findHackerRegardlessFinder(pos.getTitle(), pos.getSalary(), pos.getDeadline(), pos.getDescription());

				final Message msg = this.msgService.create();
				msg.setSubject("New Suitable Position 4 U tt");
				msg.setBody("New Position: " + pos.getTitle());
				msg.setRecipient(new ArrayList<String>());
				for (final Hacker hacker : hackers) {

					msg.getRecipient().add(hacker.getEmail());
					this.msgService.exchangeMessage(msg, hacker.getId());
					this.msgService.save(msg);
				}

			}
		} else {
			Assert.isTrue(pos.getStatus() == false);
			Assert.isTrue(pos.getCancel() == false);
		}
		return this.positionRepository.save(pos);
	}
	private boolean getPositionDatabaseStatus(final int positionId) {
		final Position dbPosition = this.positionRepository.findOne(positionId);
		// Database position has to be in draft mode
		return dbPosition.getStatus();
	}

	private boolean getPositionDatabaseCancel(final int positionId) {
		final Position dbPosition = this.positionRepository.findOne(positionId);
		// Database position has to be in draft mode
		return dbPosition.getCancel();
	}

	public String findCompanyWithMorePositions() {

		final List<String> ls = this.positionRepository.findCompanyWithMorePositions();
		String res = "";
		if (!ls.isEmpty())
			res = ls.get(0);
		return res;
	}

	public void cancel(final int positionId) {
		// We must be the owner
		Assert.isTrue(this.checkPositionOwner(positionId), "Logged user is not the position owner");
		final Position dbPosition = this.positionRepository.findOne(positionId);
		// We can cancel a position if it is in final mode
		Assert.isTrue(dbPosition.getStatus(), "Only positions in final mode can be cancelled");
		final Collection<Problem> positionProblems = this.problemService.findFromPosition(positionId);
		final Collection<Hacker> hackers = new ArrayList<>();
		for (final Problem p : positionProblems) {
			this.positionRepository.rejectAllApplications(p.getId(), positionId);
			// TODO: notify all hackers in this collection
			hackers.addAll(this.hackerService.findByProblem(p.getId()));
		}

		this.notifyHackers(hackers, dbPosition);

		dbPosition.setCancel(true);
	}

	public void notifyHackers(final Collection<Hacker> hackers, final Position position) {
		final UserAccount log = LoginService.getPrincipal();
		final Actor logged = this.actorService.getActorByUserId(log.getId());

		final List<Hacker> hackerReceiverList = new ArrayList<>();
		hackerReceiverList.addAll(hackers);

		for (int i = 0; i < hackerReceiverList.size() - 1; i++)
			if (hackerReceiverList.get(i).getId() == hackerReceiverList.get(i + 1).getId())
				hackerReceiverList.remove(hackerReceiverList.get(i + 1));

		final List<String> emails = new ArrayList<>();
		for (int i = 0; i < hackerReceiverList.size(); i++)
			emails.add(hackerReceiverList.get(i).getEmail());

		Message sended = this.msgService.create();
		sended.setSubject("Position cancelled");
		final Collection<String> me = new ArrayList<>();
		sended.setRecipient(me);
		sended.setBody("The position " + position.getTicker() + " have been cancelled");
		sended.setMoment(LocalDate.now().toDate());

		final Tag noti = this.tagService.create();
		noti.setTag("SYSTEM");
		final Collection<Tag> tags = new ArrayList<>();
		tags.add(noti);
		sended.setTags(tags);
		for (int i = 0; i < emails.size(); i++) {
			final Actor a = this.actorService.getActorByEmailOnly(emails.get(i));
			sended = this.msgService.exchangeMessage(sended, a.getId());
		}
		sended.setSender("null");

		final List<Tag> listTag = new ArrayList<>();
		listTag.addAll(sended.getTags());
		for (int i = 0; i < listTag.size(); i++)
			if (logged.getId() == listTag.get(i).getActorId()) {
				final Integer idTag = listTag.get(i).getId();
				listTag.remove(listTag.get(i));
				logged.getMessages().remove(sended);
				this.tagService.delete(this.tagService.findOne(idTag));
			}
		sended.setTags(listTag);
		this.msgService.save(sended);

		final List<Tag> newList = new ArrayList<>();
		newList.addAll(sended.getTags());
		for (int i = 0; i < listTag.size(); i++) {
			listTag.get(i).setMessageId(sended.getId());
			final Tag save = this.tagService.save(listTag.get(i));
		}
	}

	public void delete(final int positionId) {
		// We must be the owner
		Assert.isTrue(this.checkPositionOwner(positionId), "Logged user is not the position owner");
		// We can delete a position if it is not in final mode
		Assert.isTrue(this.getPositionDatabaseStatus(positionId) == false, "Position is not in draft mode");
		// We have to detach all position problems
		this.detachAllProblems(positionId);
		this.positionRepository.delete(positionId);
	}
	public void deleteCompanyPositions(final int companyId) {

		final Collection<Position> positions = this.positionRepository.findAllPositionsByCompany(companyId);
		if (!positions.isEmpty())
			for (final Position position : positions) {
				this.problemService.deleteAllByPosition(position.getId());
				this.positionDataService.deleteAllByPosition(position.getId());
				this.positionRepository.delete(position);
			}
	}

	public void flush() {
		this.positionRepository.flush();
	}

	private void detachAllProblems(final int positionId) {
		final Collection<Problem> problems = this.problemService.findFromPosition(positionId);
		for (final Problem p : problems) {
			final Position position = this.findOneLoggedIsOwner(positionId);
			final Problem problem = this.problemService.findOneLoggedIsOwner(p.getId());
			// This should persist since we are linked to the db
			problem.getPosition().remove(position);
		}
	}
	public void addProblemToPosition(final int problemId, final int positionId) {
		// We must be the owner of both
		final Position position = this.findOneLoggedIsOwner(positionId);
		final Problem problem = this.problemService.findOneLoggedIsOwner(problemId);

		// Position must be in draft mode and problem must be in final mode
		Assert.isTrue(position.getStatus() == false);
		Assert.isTrue(problem.getFinalMode() == true);

		// This should persist since we are linked to the db
		problem.getPosition().add(position);
	}

	public void detachProblemFromPosition(final int problemId, final int positionId) {
		// We must be the owner of both
		final Position position = this.findOneLoggedIsOwner(positionId);
		final Problem problem = this.problemService.findOneLoggedIsOwner(problemId);
		// Position must be in draft mode and problem must be in final mode
		Assert.isTrue(position.getStatus() == false);
		Assert.isTrue(problem.getFinalMode() == true);
		// This should persist since we are linked to the db
		problem.getPosition().remove(position);
	}

	/**
	 * 
	 * Return a collection of all {@link Position} in database that is valid for a curricula.<br>
	 * An empty collection if any hacker is logger
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link Position}>
	 */
	public Collection<Position> findValidPositionToCurriculaByHackerId(final int hackerId) {
		final Hacker hacker = this.hackerService.getHackerLogin();
		Collection<Position> res = new ArrayList<>();
		if (hacker != null && hacker.getId() == hackerId)
			res = this.positionRepository.findValidPositionToCurriculaByHackerId(hackerId);
		else
			System.out.println("Any hacker is logger, system can not find any valid position");
		return res;
	}
}
