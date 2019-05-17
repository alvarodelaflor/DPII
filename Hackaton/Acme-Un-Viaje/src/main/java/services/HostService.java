
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

import repositories.HostRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Host;
import domain.CreditCard;
import forms.RegisterActor;

@Service
@Transactional
public class HostService {

	@Autowired
	private HostRepository hostRepository;

	@Autowired
	private Validator validator;

	@Autowired
	private ActorService actorService;

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
		// if (host.getPhone().matches("^([0-9]{4,})$"))
		// host.setPhone(this.configurationService.getConfiguration().getCountryCode()
		// + " " + host.getPhone());
		return this.hostRepository.save(host);
	}

	// RECONSTRUCT REGISTER AS HOST
	// ---------------------------------------------------------------
	public Host reconstructRegisterAsHost(final RegisterActor registerActor, final BindingResult binding) {
		final Host host = this.create();

		this.actorService.checkActor(registerActor, binding);

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
			CreditCard creditCard = host.getCreditCard();
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
			if (!registerActor.getEmail().matches(pattern)) {
				binding.rejectValue("email", "email.wrong");
			}
			
			UserAccount user = LoginService.getPrincipal();
			Host t = this.getHostByUserAccountId(user.getId());
			
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
