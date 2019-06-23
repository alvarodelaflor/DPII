
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Application;
import domain.Message;
import domain.Problem;
import domain.Rookie;
import domain.Tag;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private Validator				validator;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private TagService				tagService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private QuoletService			quoletService;


	// DashBoard:
	public Float avgApplicationPerRookie() {

		return this.applicationRepository.avgApplicationPerRookie();
	}

	public Float minApplicationPerRookie() {

		return this.applicationRepository.minApplicationPerRookie();
	}

	public Float maxApplicationPerRookie() {

		return this.applicationRepository.maxApplicationPerRookie();
	}

	public Float stddevApplicationPerRookie() {

		return this.applicationRepository.stddevApplicationPerRookie();
	}

	public String findRookieWithMoreApplications() {

		final List<String> ls = this.applicationRepository.findRookieWithMoreApplications();
		String res = "";
		if (!ls.isEmpty())
			res = ls.get(0);
		return res;
	}

	// FINDALL  ---------------------------------------------------------------	
	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	// getApplicationsByRookie  ---------------------------------------------------------------	
	public Collection<Application> getApplicationsByRookie(final int id) {
		return this.applicationRepository.getApplicationsByRookie(id);
	}

	// FINDONE  ---------------------------------------------------------------	
	public Application findOne(final int id) {
		return this.applicationRepository.findOne(id);
	}

	// CREATE ---------------------------------------------------------------		
	public Application create() {
		final Application application = new Application();

		application.setRookie(this.rookieService.getRookieByUserAccountId(LoginService.getPrincipal().getId()));

		Assert.isTrue(this.rookieService.getRookieByUserAccountId(LoginService.getPrincipal().getId()) != null);

		return application;
	}

	// SAVE ---------------------------------------------------------------		
	public Application save(final Application a) {
		final Application app = this.applicationRepository.save(a);
		this.applicationRepository.flush();
		return app;

	}
	// getApplicationRookieById -------------------------------------------
	public Application getApplicationRookieById(final int id) {
		return this.applicationRepository.getApplicationRookieById(id);
	}

	// RECONSTRUCT-EDIT---------------------------------------------------------------		

	public Application reconstructEdit(final Application application, final BindingResult binding) {
		Application result;
		final Application res = this.applicationRepository.findOne(application.getId());

		System.out.println("Carmen: entro en el reconstructEdict");

		result = application;

		result.setResponse(application.getResponse());
		result.setLink(application.getLink());
		result.setCreationMoment(DateTime.now().toDate());
		result.setApplyMoment(res.getApplyMoment());

		binding.addAllErrors(binding);

		System.out.println(result);

		this.validator.validate(application, binding);
		System.out.println(binding.getAllErrors());

		if (binding.getAllErrors().isEmpty()) {
			res.setLink(result.getLink());
			res.setResponse(result.getResponse());
			res.setStatus(result.getStatus());
			res.setApplyMoment(result.getApplyMoment());
			res.setCreationMoment(result.getCreationMoment());
		}

		return res;
	}

	public void deleteRookieApplications(final int rookieId) {
		final Collection<Application> apps = this.applicationRepository.findRookieApps(rookieId);
		for (final Application application : apps)
			this.quoletService.deleteApplicationQuolets(application);
		this.applicationRepository.deleteInBatch(apps);
	}
	public void flush() {
		this.applicationRepository.flush();
	}

	public Collection<Application> getSubmittedApplicationsByLoggedCompany() {
		final int loggedId = LoginService.getPrincipal().getId();
		final int companyId = this.companyService.getCompanyByUserAccountId(loggedId).getId();
		return this.applicationRepository.getSubmittedApplicationsByLoggedCompany(companyId);
	}

	public Collection<Application> getAcceptedApplicationsByLoggedCompany() {
		final int loggedId = LoginService.getPrincipal().getId();
		final int companyId = this.companyService.getCompanyByUserAccountId(loggedId).getId();
		return this.applicationRepository.getAcceptedApplicationsByLoggedCompany(companyId);
	}

	public Collection<Application> getRejectedApplicationsByLoggedCompany() {
		final int loggedId = LoginService.getPrincipal().getId();
		final int companyId = this.companyService.getCompanyByUserAccountId(loggedId).getId();
		return this.applicationRepository.getRejectedApplicationsByLoggedCompany(companyId);
	}

	public Application getCompanyApplication(final int applicationId) {
		final Application application = this.applicationRepository.findOne(applicationId);
		this.checkApplicationOwner(application);
		return application;
	}

	private void checkApplicationOwner(final Application application) {
		final int loggedId = this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId()).getId();
		Assert.isTrue(application.getProblem().getCompany().getId() == loggedId);
	}

	public void accept(final int applicationId) {
		final Application application = this.applicationRepository.findOne(applicationId);
		this.checkApplicationOwner(application);
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));
		application.setStatus("ACCEPTED");

		// TODO: Notify this rookie
		final Rookie rookie = application.getRookie();
		final Collection<Rookie> rookies = new ArrayList<>();
		rookies.add(rookie);
		this.notifyRookies(rookies, application, "accepted");
	}
	public void reject(final int applicationId) {
		final Application application = this.applicationRepository.findOne(applicationId);
		this.checkApplicationOwner(application);
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));
		application.setStatus("REJECTED");
		// TODO: Notify this rookie
		final Rookie rookie = application.getRookie();
		final Collection<Rookie> rookies = new ArrayList<>();
		rookies.add(rookie);
		this.notifyRookies(rookies, application, "rejected");

	}

	public void notifyRookies(final Collection<Rookie> rookies, final Application application, final String state) {
		final UserAccount log = LoginService.getPrincipal();
		final Actor logged = this.actorService.getActorByUserId(log.getId());

		final List<Rookie> rookieReceiverList = new ArrayList<>();
		rookieReceiverList.addAll(rookies);

		final List<String> emails = new ArrayList<>();
		for (int i = 0; i < rookieReceiverList.size(); i++)
			emails.add(rookieReceiverList.get(i).getEmail());

		Message sended = this.messageService.create();
		sended.setSubject("Change in application state");
		final Collection<String> me = new ArrayList<>();
		sended.setRecipient(me);
		sended.setBody("The application for a position" + application.getPosition().getTicker() + " have been " + state);
		sended.setMoment(LocalDate.now().toDate());

		final Tag noti = this.tagService.create();
		noti.setTag("SYSTEM");
		final Collection<Tag> tags = new ArrayList<>();
		tags.add(noti);
		sended.setTags(tags);
		for (int i = 0; i < emails.size(); i++) {
			final Actor a = this.actorService.getActorByEmailOnly(emails.get(i));
			sended = this.messageService.exchangeMessage(sended, a.getId());
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
		this.messageService.save(sended);

		final List<Tag> newList = new ArrayList<>();
		newList.addAll(sended.getTags());
		for (int i = 0; i < listTag.size(); i++) {
			listTag.get(i).setMessageId(sended.getId());
			final Tag save = this.tagService.save(listTag.get(i));
		}
	}

	public void deleteProblemApps(final Problem problem) {
		final Collection<Application> apps = this.applicationRepository.getProblemApps(problem.getId());
		this.applicationRepository.deleteInBatch(apps);
	}

	public void deleteAllByPosition(final int id) {
		final Collection<Application> apps = this.applicationRepository.getPositionApps(id);
		this.applicationRepository.deleteInBatch(apps);
	}
}
