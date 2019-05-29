
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.CreditCard;
import domain.Referee;
import forms.RegisterActor;
import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class RefereeService {

	@Autowired
	private RefereeRepository		refereeRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigService			configService;

	@Autowired
	private ReviewService			reviewService;

	@Autowired
	private SocialProfileService	socialProfileService;


	// REGISTER AS REFEREE
	// ---------------------------------------------------------------
	public Referee create() {
		final Referee referee = new Referee();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.REFEREE);

		autoridades.add(authority);
		user.setAuthorities(autoridades);
		referee.setUserAccount(user);
		return referee;
	}

	// SAVE REGISTER AS REFEREE
	// ---------------------------------------------------------------
	public Referee saveRegisterAsReferee(final Referee referee) {
		if (referee.getPhone().matches("^([0-9]{4,})$"))
			referee.setPhone("+" + this.configService.getConfiguration().getDefaultPhoneCode() + " " + referee.getPhone());
		return this.refereeRepository.save(referee);
	}

	// RECONSTRUCT REGISTER AS REFEREE
	// ---------------------------------------------------------------
	public Referee reconstructRegisterAsReferee(final RegisterActor registerActor, final BindingResult binding) {
		final Referee referee = this.create();

		this.actorService.checkActor(registerActor, binding);

		referee.getUserAccount().setUsername(registerActor.getUserName());
		final String password = registerActor.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		referee.getUserAccount().setPassword(hashPassword);
		referee.setBirthDate(registerActor.getBirthDate());
		referee.setEmail(registerActor.getEmail());
		referee.setPhone(registerActor.getPhone());
		referee.setName(registerActor.getName());
		referee.setSurname(registerActor.getSurname());
		referee.setPhoto(registerActor.getPhoto());
		final CreditCard creditCard = new CreditCard();
		creditCard.setCVV(registerActor.getCVV());
		creditCard.setExpiration(registerActor.getExpiration());
		creditCard.setHolder(registerActor.getHolder());
		creditCard.setMake(registerActor.getMake());
		creditCard.setNumber(registerActor.getNumber());
		referee.setCreditCard(creditCard);
		this.validator.validate(referee, binding);

		return referee;
	}

	// FIND REFEREE
	// ------------------------------------------------------------------------------------
	public Referee getRefereeByUserAccountId(final int userAccountId) {
		return this.refereeRepository.findByUserAccountId(userAccountId);
	}

	// RECONSTRUCT EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public Referee reconstructEditDataPeronal(final Referee registerActor, final BindingResult binding) {
		Referee referee;

		referee = this.refereeRepository.findOne(registerActor.getId());

		this.checkActorEdit(registerActor, binding);
		this.validator.validate(registerActor, binding);

		if (!binding.hasErrors()) {
			referee.setBirthDate(registerActor.getBirthDate());
			referee.setEmail(registerActor.getEmail());
			referee.setPhone(registerActor.getPhone());
			referee.setName(registerActor.getName());
			referee.setSurname(registerActor.getSurname());
			referee.setPhoto(registerActor.getPhoto());
			final CreditCard creditCard = referee.getCreditCard();
			creditCard.setCVV(registerActor.getCreditCard().getCVV());
			creditCard.setExpiration(registerActor.getCreditCard().getExpiration());
			creditCard.setHolder(registerActor.getCreditCard().getHolder());
			creditCard.setMake(registerActor.getCreditCard().getMake());
			creditCard.setNumber(registerActor.getCreditCard().getNumber());
			referee.setCreditCard(creditCard);
		}

		return referee;
	}

	// CHECK EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public void checkActorEdit(final Referee registerActor, final BindingResult binding) {

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
		final Referee t = this.getRefereeByUserAccountId(user.getId());

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

	public Referee findOne(final int id) {
		return this.refereeRepository.findOne(id);
	}

	public void delete(final Referee referee) {

		Assert.isTrue(referee.getUserAccount().getId() == LoginService.getPrincipal().getId());
		this.socialProfileService.deleteActorSocialProfiles(referee);
		this.reviewService.deleteAllByReferee(referee);
		this.refereeRepository.delete(referee);
	}

}
