
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

import repositories.TravelAgencyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.TravelAgency;
import domain.CreditCard;
import forms.RegisterActor;

@Service
@Transactional
public class TravelAgencyService {

	@Autowired
	private TravelAgencyRepository travelAgencyRepository;

	@Autowired
	private Validator validator;

	@Autowired
	private ActorService actorService;

	// REGISTER AS TRAVEL
	// ---------------------------------------------------------------
	public TravelAgency create() {
		final TravelAgency travelAgency = new TravelAgency();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.TRAVELAGENCY);

		autoridades.add(authority);
		user.setAuthorities(autoridades);
		travelAgency.setUserAccount(user);
		return travelAgency;
	}

	// SAVE REGISTER AS TRAVEL
	// ---------------------------------------------------------------
	public TravelAgency saveRegisterAsTravelAgency(final TravelAgency travelAgency) {
		// if (travelAgency.getPhone().matches("^([0-9]{4,})$"))
		// travelAgency.setPhone(this.configurationService.getConfiguration().getCountryCode()
		// + " " + travelAgency.getPhone());
		return this.travelAgencyRepository.save(travelAgency);
	}

	// RECONSTRUCT REGISTER AS TRAVEL
	// ---------------------------------------------------------------
	public TravelAgency reconstructRegisterAsTravelAgency(final RegisterActor registerActor, final BindingResult binding) {
		final TravelAgency travelAgency = this.create();

		this.actorService.checkActor(registerActor, binding);

		travelAgency.getUserAccount().setUsername(registerActor.getUserName());
		final String password = registerActor.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		travelAgency.getUserAccount().setPassword(hashPassword);
		travelAgency.setBirthDate(registerActor.getBirthDate());
		travelAgency.setEmail(registerActor.getEmail());
		travelAgency.setPhone(registerActor.getPhone());
		travelAgency.setName(registerActor.getName());
		travelAgency.setSurname(registerActor.getSurname());
		travelAgency.setPhoto(registerActor.getPhoto());
		final CreditCard creditCard = new CreditCard();
		creditCard.setCVV(registerActor.getCVV());
		creditCard.setExpiration(registerActor.getExpiration());
		creditCard.setHolder(registerActor.getHolder());
		creditCard.setMake(registerActor.getMake());
		creditCard.setNumber(registerActor.getNumber());
		travelAgency.setCreditCard(creditCard);

		this.validator.validate(travelAgency, binding);
		return travelAgency;
	}

	// FIND TRAVEL
	// ------------------------------------------------------------------------------------
	public TravelAgency getTravelAgencyByUserAccountId(final int userAccountId) {
		return this.travelAgencyRepository.findByUserAccountId(userAccountId);
	}

	// RECONSTRUCT EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public TravelAgency reconstructEditDataPeronal(final TravelAgency registerActor, final BindingResult binding) {
		TravelAgency travelAgency;

		travelAgency = this.travelAgencyRepository.findOne(registerActor.getId());

		this.checkActorEdit(registerActor, binding);
		this.validator.validate(registerActor, binding);

		if (!binding.hasErrors()) {
			travelAgency.setBirthDate(registerActor.getBirthDate());
			travelAgency.setEmail(registerActor.getEmail());
			travelAgency.setPhone(registerActor.getPhone());
			travelAgency.setName(registerActor.getName());
			travelAgency.setSurname(registerActor.getSurname());
			travelAgency.setPhoto(registerActor.getPhoto());
			CreditCard creditCard = travelAgency.getCreditCard();
			creditCard.setCVV(registerActor.getCreditCard().getCVV());
			creditCard.setExpiration(registerActor.getCreditCard().getExpiration());
			creditCard.setHolder(registerActor.getCreditCard().getHolder());
			creditCard.setMake(registerActor.getCreditCard().getMake());
			creditCard.setNumber(registerActor.getCreditCard().getNumber());
			travelAgency.setCreditCard(creditCard);
		}


		return travelAgency;
	}
	
	// CHECK EDIT DATA PERONAL
		// ---------------------------------------------------------------
		public void checkActorEdit(final TravelAgency registerActor, final BindingResult binding) {

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
			TravelAgency t = this.getTravelAgencyByUserAccountId(user.getId());
			
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
