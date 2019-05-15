
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TransporterRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Transporter;
import domain.Cleaner;
import domain.CreditCard;
import forms.RegisterActor;

@Service
@Transactional
public class TransporterService {

	@Autowired
	private TransporterRepository transporterRepository;

	@Autowired
	private Validator validator;

	@Autowired
	private ActorService actorService;

	// REGISTER AS TRASNSPORTER
	// ---------------------------------------------------------------
	public Transporter create() {
		final Transporter transporter = new Transporter();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.TRASNSPORTER);

		autoridades.add(authority);
		user.setAuthorities(autoridades);
		transporter.setUserAccount(user);
		return transporter;
	}

	// SAVE REGISTER AS TRASNSPORTER
	// ---------------------------------------------------------------
	public Transporter saveRegisterAsTransporter(final Transporter transporter) {
		// if (transporter.getPhone().matches("^([0-9]{4,})$"))
		// transporter.setPhone(this.configurationService.getConfiguration().getCountryCode()
		// + " " + transporter.getPhone());
		return this.transporterRepository.save(transporter);
	}

	// RECONSTRUCT REGISTER AS TRASNSPORTER
	// ---------------------------------------------------------------
	public Transporter reconstructRegisterAsTransporter(final RegisterActor registerActor,
			final BindingResult binding) {
		final Transporter transporter = this.create();

		this.actorService.checkActor(registerActor, binding);

		transporter.getUserAccount().setUsername(registerActor.getUserName());
		final String password = registerActor.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		transporter.getUserAccount().setPassword(hashPassword);
		transporter.setBirthDate(registerActor.getBirthDate());
		transporter.setEmail(registerActor.getEmail());
		transporter.setPhone(registerActor.getPhone());
		transporter.setName(registerActor.getName());
		transporter.setSurname(registerActor.getSurname());
		transporter.setPhoto(registerActor.getPhoto());
		final CreditCard creditCard = new CreditCard();
		creditCard.setCVV(registerActor.getCVV());
		creditCard.setExpiration(registerActor.getExpiration());
		creditCard.setHolder(registerActor.getHolder());
		creditCard.setMake(registerActor.getMake());
		creditCard.setNumber(registerActor.getNumber());
		transporter.setCreditCard(creditCard);

		this.validator.validate(transporter, binding);
		return transporter;
	}

	// FIND TRASNSPORTER
	// ------------------------------------------------------------------------------------
	public Transporter getTransporterByUserAccountId(final int userAccountId) {
		return this.transporterRepository.findByUserAccountId(userAccountId);
	}

	// RECONSTRUCT EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public Transporter reconstructEditDataPeronal(final Transporter registerActor, final BindingResult binding) {
		Transporter transporter;

		transporter = this.transporterRepository.findOne(registerActor.getId());

		this.checkActorEdit(registerActor, binding);

		if (!binding.hasErrors()) {
			transporter.setBirthDate(registerActor.getBirthDate());
			transporter.setEmail(registerActor.getEmail());
			transporter.setPhone(registerActor.getPhone());
			transporter.setName(registerActor.getName());
			transporter.setSurname(registerActor.getSurname());
			transporter.setPhoto(registerActor.getPhoto());
			CreditCard creditCard = transporter.getCreditCard();
			creditCard.setCVV(registerActor.getCreditCard().getCVV());
			creditCard.setExpiration(registerActor.getCreditCard().getExpiration());
			creditCard.setHolder(registerActor.getCreditCard().getHolder());
			creditCard.setMake(registerActor.getCreditCard().getMake());
			creditCard.setNumber(registerActor.getCreditCard().getNumber());
			transporter.setCreditCard(creditCard);
		}

		this.validator.validate(registerActor, binding);

		return transporter;
	}

	// CHECK EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public void checkActorEdit(final Transporter registerActor, final BindingResult binding) {

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
		if (!registerActor.getEmail().matches(pattern)) {
			binding.rejectValue("email", "email.wrong");
		}
		
		UserAccount user = LoginService.getPrincipal();
		Transporter t = this.getTransporterByUserAccountId(user.getId());
		
		if (!registerActor.getEmail().equals(t.getEmail()) && this.actorService.getActorByEmail(registerActor.getEmail()).size() >= 1)
			binding.rejectValue("email", "error.email");

		if (registerActor.getBirthDate() != null && registerActor.getBirthDate().after(calendar.getTime())) {
			binding.rejectValue("birthDate", "error.birthDate");
			Integer ageActor = calendar.getTime().getYear() - registerActor.getBirthDate().getYear();
			if (ageActor < 18) {
				binding.rejectValue("birthDate", "error.birthDateM");
			}
		}
	}

}
