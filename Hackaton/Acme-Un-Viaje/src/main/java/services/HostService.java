
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Cleaner;
import domain.CreditCard;
import domain.Host;
import forms.RegisterActor;
import repositories.HostRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class HostService {

	@Autowired
	private HostRepository			hostRepository;

	@Autowired
	private CleanerService			cleanerService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigService			configService;

	@Autowired
	private AccomodationService		accomodationService;

	@Autowired
	private ComplaintService		complaintService;

	@Autowired
	private ValorationService		valorationService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private JobApplicationService	jobApplicationService;


	//CRUD METHODS

	/**
	 * Return all hosts save in DataBase
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link Host}>
	 */
	public Collection<Host> findAll() {
		return this.hostRepository.findAll();
	}

	// CRUD METHODS

	// REGISTER AS HOST
	// ---------------------------------------------------------------
	public Host create() {
		final Host host = new Host();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HOST);

		autoridades.add(authority);
		user.setAuthorities(autoridades);
		host.setUserAccount(user);
		return host;
	}

	// SAVE REGISTER AS HOST
	// ---------------------------------------------------------------
	public Host saveRegisterAsHost(final Host host) {
		if (host.getPhone().matches("^([0-9]{4,})$"))
			host.setPhone("+" + this.configService.getConfiguration().getDefaultPhoneCode() + " " + host.getPhone());
		return this.hostRepository.save(host);
	}

	// RECONSTRUCT REGISTER AS HOST
	// ---------------------------------------------------------------
	public Host reconstructRegisterAsHost(final RegisterActor registerActor, final BindingResult binding) {
		final Host host = this.create();

		this.actorService.checkActor(registerActor, binding);
		
		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		if (!registerActor.getEmail().matches(pattern))
			binding.rejectValue("email", "email.wrong");

		host.getUserAccount().setUsername(registerActor.getUserName());
		final String password = registerActor.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		host.getUserAccount().setPassword(hashPassword);
		host.setBirthDate(registerActor.getBirthDate());
		host.setEmail(registerActor.getEmail());
		host.setPhone(registerActor.getPhone());
		host.setName(registerActor.getName());
		host.setSurname(registerActor.getSurname());
		host.setPhoto(registerActor.getPhoto());
		final CreditCard creditCard = new CreditCard();
		creditCard.setCVV(registerActor.getCVV());
		creditCard.setExpiration(registerActor.getExpiration());
		creditCard.setHolder(registerActor.getHolder());
		creditCard.setMake(registerActor.getMake());
		creditCard.setNumber(registerActor.getNumber());
		host.setCreditCard(creditCard);

		this.validator.validate(host, binding);
		return host;
	}

	// FIND HOST
	// ------------------------------------------------------------------------------------
	public Host getHostByUserAccountId(final int userAccountId) {
		return this.hostRepository.findByUserAccountId(userAccountId);
	}

	// RECONSTRUCT EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public Host reconstructEditDataPeronal(final Host registerActor, final BindingResult binding) {
		Host host;

		host = this.hostRepository.findOne(registerActor.getId());

		this.checkActorEdit(registerActor, binding);
		this.validator.validate(registerActor, binding);

		if (!binding.hasErrors()) {
			host.setBirthDate(registerActor.getBirthDate());
			host.setEmail(registerActor.getEmail());
			host.setPhone(registerActor.getPhone());
			host.setName(registerActor.getName());
			host.setSurname(registerActor.getSurname());
			host.setPhoto(registerActor.getPhoto());
			final CreditCard creditCard = host.getCreditCard();
			creditCard.setCVV(registerActor.getCreditCard().getCVV());
			creditCard.setExpiration(registerActor.getCreditCard().getExpiration());
			creditCard.setHolder(registerActor.getCreditCard().getHolder());
			creditCard.setMake(registerActor.getCreditCard().getMake());
			creditCard.setNumber(registerActor.getCreditCard().getNumber());
			host.setCreditCard(creditCard);
		}

		return host;
	}

	// CHECK EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public void checkActorEdit(final Host registerActor, final BindingResult binding) {

		if (!registerActor.getCreditCard().getExpiration().matches("([0-9]){2}" + "/" + "([0-9]){2}"))
			binding.rejectValue("creditCard.expiration", "error.expirationFormatter");

		final Calendar calendar = Calendar.getInstance();
		if (registerActor.getCreditCard().getExpiration().matches("([0-9]){2}" + "/" + "([0-9]){2}")) {
			final String[] parts = registerActor.getCreditCard().getExpiration().split("/");
			final String part1 = parts[0]; // MM
			final String part2 = parts[1]; // YY

			final int monthRigthNow = calendar.getTime().getMonth();
			final int monthCreditCard = Integer.parseInt(part1);

			int yearRigthNow = calendar.getTime().getYear();
			yearRigthNow = yearRigthNow % 100;
			final int yearCredictCard = Integer.parseInt(part2);

			System.out.println(yearCredictCard >= yearRigthNow);
			System.out.println(monthCreditCard > monthRigthNow);

			if (yearCredictCard < yearRigthNow || monthCreditCard == 00 || monthCreditCard > 12)
				binding.rejectValue("creditCard.expiration", "error.expirationFuture");

			if (yearCredictCard >= yearRigthNow && monthCreditCard != 00 && monthCreditCard > 12)
				if (yearCredictCard == yearRigthNow)
					if (monthCreditCard < monthRigthNow)
						binding.rejectValue("creditCard.expiration", "error.expirationFuture");
		}

		if (!registerActor.getCreditCard().getNumber().matches("([0-9]){16}"))
			binding.rejectValue("creditCard.number", "error.numberCredictCard");

		if (!registerActor.getCreditCard().getCVV().matches("([0-9]){3}"))
			binding.rejectValue("creditCard.CVV", "error.CVVCredictCard");

		if (registerActor.getCreditCard().getHolder() == "")
			binding.rejectValue("creditCard.holder", "error.holderCredictCard");

		if (registerActor.getCreditCard().getMake() == "")
			binding.rejectValue("creditCard.make", "error.makeCredictCard");

		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		if (!registerActor.getEmail().matches(pattern))
			binding.rejectValue("email", "email.wrong");

		if (registerActor.getCreditCard().getHolder().contains(">") || registerActor.getCreditCard().getHolder().contains("<"))
			binding.rejectValue("creditCard.holder", "error.html");

		if (registerActor.getCreditCard().getMake().contains(">") || registerActor.getCreditCard().getMake().contains("<"))
			binding.rejectValue("creditCard.make", "error.html");

		final UserAccount user = LoginService.getPrincipal();
		final Host t = this.getHostByUserAccountId(user.getId());

		if (!registerActor.getEmail().equals(t.getEmail()) && this.actorService.getActorByEmail(registerActor.getEmail()).size() >= 1)
			binding.rejectValue("email", "error.email");

		if (registerActor.getBirthDate() != null) {
			if (registerActor.getBirthDate().after(calendar.getTime())) {
				binding.rejectValue("birthDate", "error.birthDate");
			} 
			calendar.add(Calendar.YEAR, -18);
			if (registerActor.getBirthDate().after(calendar.getTime())) {
				binding.rejectValue("birthDate", "error.birthDateM");
			}

		}
	}

	public Host findOne(final Integer id) {
		return this.hostRepository.findOne(id);
	}

	/**
	 *
	 * Return the host who is login if exits, null otherwise
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Host}
	 */
	public Host getHostLogin() {
		Host res;
		try {
			res = this.getHostByUserAccountId(LoginService.getPrincipal().getId());
		} catch (final Exception e) {
			res = null;
		}
		return res;
	}

	/**
	 * Return the host available for an especific cleaner job application<br>
	 * Cleaner must exits in DataBase
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link Host}>
	 */
	public Collection<Host> findHostAvailableForCleaner(final Cleaner cleaner) {
		Assert.notNull(cleaner, "Cleaner is null");
		Assert.notNull(this.cleanerService.findOne(cleaner.getId()), "Cleaner not found in DataBase");
		final Collection<Host> res = this.findAll();
		res.removeAll(this.hostRepository.findHostNotAvailableForCleaner(cleaner.getId()));
		return res;
	}

	public List<String> bestHost() {
		final List<String> res = new ArrayList<>();
		final List<Host> hosts = new ArrayList<>();
		hosts.addAll(this.hostRepository.bestHost());
		for (final Host host : hosts)
			res.add(host.getUserAccount().getUsername());
		if (hosts.size() <= 3)
			return res;
		else
			return res.subList(0, 2);
	}

	public void delete(final Host host) {
		Assert.isTrue(host.getUserAccount().getId() == LoginService.getPrincipal().getId());
		this.socialProfileService.deleteActorSocialProfiles(host);
		this.accomodationService.deleteAllByHost(host);
		this.jobApplicationService.deleteHostApplications(host);
		this.complaintService.deleteHostComplaints(host);
		this.valorationService.deleteAllByHost(host);
		this.hostRepository.delete(host);

	}
}
