
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

import repositories.CleanerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Cleaner;
import domain.CreditCard;
import domain.JobApplication;
import forms.RegisterActor;

@Service
@Transactional
public class CleanerService {

	@Autowired
	private CleanerRepository		cleanerRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigService			configService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private ValorationService		valorationService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private CleaningTaskService		cleaningTaskService;


	// REGISTER AS CLEANER
	// ---------------------------------------------------------------
	public Cleaner create() {
		final Cleaner cleaner = new Cleaner();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CLEANER);

		autoridades.add(authority);
		user.setAuthorities(autoridades);
		cleaner.setUserAccount(user);
		return cleaner;
	}

	// SAVE REGISTER AS CLEANER
	// ---------------------------------------------------------------
	public Cleaner saveRegisterAsCleaner(final Cleaner cleaner) {
		if (cleaner.getPhone().matches("^([0-9]{4,})$"))

			//BUG: WE ADD PHONE + 6
			cleaner.setPhone("+" + this.configService.getConfiguration().getDefaultPhoneCode() + " " + cleaner.getPhone() + 6);
		return this.cleanerRepository.save(cleaner);
	}

	// RECONSTRUCT REGISTER AS CLEANER
	// ---------------------------------------------------------------
	public Cleaner reconstructRegisterAsCleaner(final RegisterActor registerActor, final BindingResult binding) {
		final Cleaner cleaner = this.create();

		this.actorService.checkActor(registerActor, binding);

		cleaner.getUserAccount().setUsername(registerActor.getUserName());
		final String password = registerActor.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		cleaner.getUserAccount().setPassword(hashPassword);
		cleaner.setBirthDate(registerActor.getBirthDate());
		cleaner.setEmail(registerActor.getEmail());
		cleaner.setPhone(registerActor.getPhone());
		cleaner.setName(registerActor.getName());
		cleaner.setSurname(registerActor.getSurname());
		cleaner.setPhoto(registerActor.getPhoto());
		final CreditCard creditCard = new CreditCard();
		creditCard.setCVV(registerActor.getCVV());
		creditCard.setExpiration(registerActor.getExpiration());
		creditCard.setHolder(registerActor.getHolder());
		creditCard.setMake(registerActor.getMake());
		creditCard.setNumber(registerActor.getNumber());
		cleaner.setCreditCard(creditCard);

		this.validator.validate(cleaner, binding);
		return cleaner;
	}

	// FIND CLEANER
	// ------------------------------------------------------------------------------------
	public Cleaner getCleanerByUserAccountId(final int userAccountId) {
		return this.cleanerRepository.findByUserAccountId(userAccountId);
	}

	// RECONSTRUCT EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public Cleaner reconstructEditDataPeronal(final Cleaner registerActor, final BindingResult binding) {
		Cleaner cleaner;

		cleaner = this.cleanerRepository.findOne(registerActor.getId());

		this.checkActorEdit(registerActor, binding);
		this.validator.validate(registerActor, binding);

		if (!binding.hasErrors()) {
			cleaner.setBirthDate(registerActor.getBirthDate());
			cleaner.setEmail(registerActor.getEmail());
			cleaner.setPhone(registerActor.getPhone());
			cleaner.setName(registerActor.getName());
			cleaner.setSurname(registerActor.getSurname());
			cleaner.setPhoto(registerActor.getPhoto());
			final CreditCard creditCard = cleaner.getCreditCard();
			creditCard.setCVV(registerActor.getCreditCard().getCVV());
			creditCard.setExpiration(registerActor.getCreditCard().getExpiration());
			creditCard.setHolder(registerActor.getCreditCard().getHolder());
			creditCard.setMake(registerActor.getCreditCard().getMake());
			creditCard.setNumber(registerActor.getCreditCard().getNumber());
			cleaner.setCreditCard(creditCard);
		}

		return cleaner;
	}

	// CHECK EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public void checkActorEdit(final Cleaner registerActor, final BindingResult binding) {

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

		if (registerActor.getCreditCard().getHolder().contains(">") || registerActor.getCreditCard().getHolder().contains("<"))
			binding.rejectValue("creditCard.holder", "error.html");

		if (registerActor.getCreditCard().getMake().contains(">") || registerActor.getCreditCard().getMake().contains("<"))
			binding.rejectValue("creditCard.make", "error.html");

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

		final UserAccount user = LoginService.getPrincipal();
		final Cleaner t = this.getCleanerByUserAccountId(user.getId());

		if (!registerActor.getEmail().equals(t.getEmail()) && this.actorService.getActorByEmail(registerActor.getEmail()).size() >= 1)
			binding.rejectValue("email", "error.email");

		if (registerActor.getBirthDate() != null) {
			if (registerActor.getBirthDate().after(calendar.getTime()))
				binding.rejectValue("birthDate", "error.birthDate");
			calendar.add(Calendar.YEAR, -18);
			if (registerActor.getBirthDate().after(calendar.getTime()))
				binding.rejectValue("birthDate", "error.birthDateM");

		}
	}

	/**
	 * 
	 * Return the cleaner who is login if exits, null otherwise
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Cleaner}
	 */
	public Cleaner getCleanerLogin() {
		Cleaner res;
		try {
			res = this.getCleanerByUserAccountId(LoginService.getPrincipal().getId());
		} catch (final Exception e) {
			res = null;
		}
		return res;
	}

	/**
	 * 
	 * Find a cleaner by id
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Cleaner}
	 */
	public Cleaner findOne(final int cleanerId) {
		return this.cleanerRepository.findOne(cleanerId);
	}

	public Collection<Cleaner> findAll() {
		return this.cleanerRepository.findAll();
	}

	public Collection<Cleaner> findByHostId(final int hostId) {
		return this.cleanerRepository.findByHostId(hostId);
	}

	public Cleaner findByNameSurname(final String name, final String surname) {
		return this.cleanerRepository.findByNameSurname(name, surname);
	}

	public Collection<String> getCompleteName() {
		final Collection<String> result = new ArrayList<String>();

		final List<Object[]> namesSurnames = this.cleanerRepository.getCleanersName();

		for (int i = 0; i < namesSurnames.size(); i++) {

			final String name = (String) namesSurnames.get(i)[0];

			final String surname = (String) namesSurnames.get(i)[1];

			final String res = surname + "," + name;

			result.add(res);

		}

		return result;
	}

	public void delete(final Cleaner cleaner) {
		Assert.isTrue(cleaner.getUserAccount().getId() == LoginService.getPrincipal().getId());
		this.socialProfileService.deleteActorSocialProfiles(cleaner);
		this.cleaningTaskService.deleteCleanerTasks(cleaner);
		this.curriculaService.deleteAllByCleaner(cleaner);
		this.valorationService.deleteAllByCleaner(cleaner);
		this.cleanerRepository.delete(cleaner);
	}

	public Collection<Cleaner> getAllCleanersInJobList(final Collection<JobApplication> jobs) {

		final Collection<Cleaner> res = new ArrayList<>();
		for (final JobApplication job : jobs)
			res.add(job.getCleaner());

		return res;
	}

	public List<String> bestCleaner() {
		final List<String> res = new ArrayList<>();
		final List<Cleaner> cleaners = new ArrayList<>();
		cleaners.addAll(this.cleanerRepository.bestCleaner());
		for (final Cleaner cleaner : cleaners)
			res.add(cleaner.getUserAccount().getUsername());
		if (cleaners.size() <= 3)
			return res;
		else
			return res.subList(0, 2);
	}

	public void save(final Cleaner cleaner) {

		this.cleanerRepository.save(cleaner);
	}

}
