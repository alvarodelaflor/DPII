
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

import repositories.AdminRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Admin;
import domain.Config;
import domain.CreditCard;
import domain.Mailbox;
import domain.Message;
import forms.RegisterActor;

@Service
@Transactional
public class AdminService {

	@Autowired
	private AdminRepository	adminRepository;

	@Autowired
	private Validator		validator;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ConfigService	configService;
	
	@Autowired
	private MailboxService	mailboxService;


	// GET CONFIG
	// ---------------------------------------------------------------
	public Config getConfig() {

		Assert.notNull(this.adminRepository.findByUserAccountId(LoginService.getPrincipal().getId()), "authority.error");

		return this.adminRepository.findByUserAccountId(LoginService.getPrincipal().getId()).getConfig();
	}

	// BAN||UNBAN ACTOR
	// ---------------------------------------------------------------
	public void banOrUnbanActorById(final int id) {

		Assert.notNull(this.adminRepository.findByUserAccountId(LoginService.getPrincipal().getId()), "authority.error");

		final Actor actor = this.actorService.findOne(id);
		Assert.notNull(actor, "not.found.error");
		if (!actor.getUserAccount().getBanned())
			Assert.isTrue(actor.getUserAccount().getSpammerFlag(), "not.spammer.error"); //TODO: a�adir la restriccion del score

		final UserAccount uacc = actor.getUserAccount();
		uacc.setBanned(!uacc.getBanned());

		this.actorService.save(actor);
	}

	// REGISTER AS ADMIN
	// ---------------------------------------------------------------
	public Admin create() {

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority1));

		final Admin admin = new Admin();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		
		Mailbox inBox = mailboxService.create();
		Mailbox outBox = mailboxService.create();
		
		inBox.setName("inBox");
		outBox.setName("outBox");
		
		inBox.setIsDefault(true);
		outBox.setIsDefault(true);
		
		inBox.setMessages(new ArrayList<Message>());
		outBox.setMessages(new ArrayList<Message>());
		
		Mailbox inBoxSave = mailboxService.save(inBox);
		Mailbox outBoxSave = mailboxService.save(outBox);
		
		Collection<Mailbox> boxes = new ArrayList<Mailbox>();
		
		boxes.add(inBoxSave);
		boxes.add(outBoxSave);
		
		admin.setMailboxes(boxes);
		
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		admin.setUserAccount(user);
		return admin;
	}

	// SAVE REGISTER AS ADMIN
	// ---------------------------------------------------------------
	public Admin saveRegisterAsAdmin(final Admin admin) {
		if (admin.getPhone().matches("^([0-9]{4,})$"))
			admin.setPhone("+" + this.configService.getConfiguration().getDefaultPhoneCode() + " " + admin.getPhone());
		return this.adminRepository.save(admin);
	}

	// RECONSTRUCT REGISTER AS ADMIN
	// ---------------------------------------------------------------
	public Admin reconstructRegisterAsAdmin(final RegisterActor registerActor, final BindingResult binding) {
		final Admin admin = this.create();

		this.actorService.checkActor(registerActor, binding);
		
		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		final String pattern2 = "(^((([a-zA-Z]|[0-9]){1,}[@])$)|(^(([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]>))$)";
		if (!(!registerActor.getEmail().matches(pattern) || !registerActor.getEmail().matches(pattern2)))
			binding.rejectValue("email", "email.wrong");

		admin.getUserAccount().setUsername(registerActor.getUserName());
		final String password = registerActor.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		admin.getUserAccount().setPassword(hashPassword);
		admin.setBirthDate(registerActor.getBirthDate());
		admin.setEmail(registerActor.getEmail());
		admin.setPhone(registerActor.getPhone());
		admin.setName(registerActor.getName());
		admin.setSurname(registerActor.getSurname());
		admin.setPhoto(registerActor.getPhoto());
		final CreditCard creditCard = new CreditCard();
		creditCard.setCVV(registerActor.getCVV());
		creditCard.setExpiration(registerActor.getExpiration());
		creditCard.setHolder(registerActor.getHolder());
		creditCard.setMake(registerActor.getMake());
		creditCard.setNumber(registerActor.getNumber());
		admin.setCreditCard(creditCard);

		this.validator.validate(admin, binding);
		return admin;
	}

	// FIND ADMIN
	// ------------------------------------------------------------------------------------
	public Admin getAdminByUserAccountId(final int userAccountId) {
		return this.adminRepository.findByUserAccountId(userAccountId);
	}

	// RECONSTRUCT EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public Admin reconstructEditDataPeronal(final Admin registerActor, final BindingResult binding) {
		Admin admin;

		admin = this.adminRepository.findOne(registerActor.getId());

		this.checkActorEdit(registerActor, binding);
		this.validator.validate(registerActor, binding);

		if (!binding.hasErrors()) {
			admin.setBirthDate(registerActor.getBirthDate());
			admin.setEmail(registerActor.getEmail());
			admin.setPhone(registerActor.getPhone());
			admin.setName(registerActor.getName());
			admin.setSurname(registerActor.getSurname());
			admin.setPhoto(registerActor.getPhoto());
			final CreditCard creditCard = admin.getCreditCard();
			creditCard.setCVV(registerActor.getCreditCard().getCVV());
			creditCard.setExpiration(registerActor.getCreditCard().getExpiration());
			creditCard.setHolder(registerActor.getCreditCard().getHolder());
			creditCard.setMake(registerActor.getCreditCard().getMake());
			creditCard.setNumber(registerActor.getCreditCard().getNumber());
			admin.setCreditCard(creditCard);
		}

		return admin;
	}

	// CHECK EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public void checkActorEdit(final Admin registerActor, final BindingResult binding) {

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
		final String pattern2 = "(^((([a-zA-Z]|[0-9]){1,}[@])$)|(^(([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]>))$)";
		if (!(!registerActor.getEmail().matches(pattern) || !registerActor.getEmail().matches(pattern2)))
			binding.rejectValue("email", "email.wrong");

		if (registerActor.getCreditCard().getHolder().contains(">") || registerActor.getCreditCard().getHolder().contains("<"))
			binding.rejectValue("creditCard.holder", "error.html");

		if (registerActor.getName().contains(">") || registerActor.getName().contains("<"))
			binding.rejectValue("name", "error.html");

		if (registerActor.getSurname().contains(">") || registerActor.getSurname().contains("<"))
			binding.rejectValue("surname", "error.html");

		if (registerActor.getPhone().contains(">") || registerActor.getPhone().contains("<"))
			binding.rejectValue("phone", "error.html");

		if (registerActor.getCreditCard().getMake().contains(">") || registerActor.getCreditCard().getMake().contains("<"))
			binding.rejectValue("creditCard.make", "error.html");

		final UserAccount user = LoginService.getPrincipal();
		final Admin t = this.getAdminByUserAccountId(user.getId());

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

	public Admin findByUserAccountId(final int id) {
		return this.adminRepository.findByUserAccountId(id);
	}

}
