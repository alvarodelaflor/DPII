
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

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
import domain.Hacker;
import domain.Message;
import domain.Tag;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private HackerService			hackerService;

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


	// DashBoard:
	public Float avgApplicationPerHacker() {

		return this.applicationRepository.avgApplicationPerHacker();
	}

	public Float minApplicationPerHacker() {

		return this.applicationRepository.minApplicationPerHacker();
	}

	public Float maxApplicationPerHacker() {

		return this.applicationRepository.maxApplicationPerHacker();
	}

	public Float stddevApplicationPerHacker() {

		return this.applicationRepository.stddevApplicationPerHacker();
	}

	public String findHackerWithMoreApplications() {

		final List<String> ls = this.applicationRepository.findHackerWithMoreApplications();
		String res = "";
		if (!ls.isEmpty())
			res = ls.get(0);
		return res;
	}

	// FINDALL  ---------------------------------------------------------------	
	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	// getApplicationsByHacker  ---------------------------------------------------------------	
	public Collection<Application> getApplicationsByHacker(final int id) {
		return this.applicationRepository.getApplicationsByHacker(id);
	}

	// FINDONE  ---------------------------------------------------------------	
	public Application findOne(final int id) {
		return this.applicationRepository.findOne(id);
	}

	// CREATE ---------------------------------------------------------------		
	public Application create() {
		final Application application = new Application();

		application.setHacker(this.hackerService.getHackerByUserAccountId(LoginService.getPrincipal().getId()));

		Assert.isTrue(this.hackerService.getHackerByUserAccountId(LoginService.getPrincipal().getId()) != null);

		return application;
	}

	// SAVE ---------------------------------------------------------------		
	public Application save(final Application a) {
		final Application app = this.applicationRepository.save(a);
		this.applicationRepository.flush();
		return app;

	}
	// getApplicationHackerById -------------------------------------------
	public Application getApplicationHackerById(final int id) {
		return this.applicationRepository.getApplicationHackerById(id);
	}

	// RECONSTRUCT-EDIT---------------------------------------------------------------		

	public Application reconstructEdit(final Application application, final BindingResult binding) {
		Application result;
		final Application res = this.applicationRepository.findOne(application.getId());

		System.out.println("Carmen: entro en el reconstructEdict");

		result = application;

		result.setResponse(application.getResponse());
		result.setLink(application.getLink());

		binding.addAllErrors(binding);

		System.out.println(result);

		this.validator.validate(application, binding);
		System.out.println(binding.getAllErrors());

		if (binding.getAllErrors().isEmpty()) {
			res.setLink(result.getLink());
			res.setResponse(result.getResponse());
			res.setStatus(result.getStatus());
		}

		return res;
	}

	public void deleteHackerApplications(final int hackerId) {
		final Collection<Application> apps = this.applicationRepository.findHackerApps(hackerId);
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

		// TODO: Notify this hacker
		final Hacker hacker = application.getHacker();
		final Collection<Hacker> hackers = new ArrayList<>();
		hackers.add(hacker);
		this.notifyHackers(hackers, application, "accepted");
	}
	public void reject(final int applicationId) {
		final Application application = this.applicationRepository.findOne(applicationId);
		this.checkApplicationOwner(application);
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));
		application.setStatus("REJECTED");
		// TODO: Notify this hacker
		final Hacker hacker = application.getHacker();
		final Collection<Hacker> hackers = new ArrayList<>();
		hackers.add(hacker);
		this.notifyHackers(hackers, application, "rejected");

	}

	public void notifyHackers(final Collection<Hacker> hackers, final Application application, final String state) {
		final UserAccount log = LoginService.getPrincipal();
		final Actor logged = this.actorService.getActorByUserId(log.getId());

		final List<Hacker> hackerReceiverList = new ArrayList<>();
		hackerReceiverList.addAll(hackers);

		final List<String> emails = new ArrayList<>();
		for (int i = 0; i < hackerReceiverList.size(); i++)
			emails.add(hackerReceiverList.get(i).getEmail());

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
}
