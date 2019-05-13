
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CleanerRepository;
import security.Authority;
import security.UserAccount;
import domain.Cleaner;
import domain.CreditCard;
import forms.RegisterActor;

@Service
@Transactional
public class CleanerService {

	@Autowired
	private CleanerRepository	cleanerRepository;

	@Autowired
	private Validator			validator;

	@Autowired
	private ActorService		actorService;


	// REGISTER AS CLEANER ---------------------------------------------------------------		
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

	// SAVE REGISTER AS CLEANER ---------------------------------------------------------------		
	public Cleaner saveRegisterAsCleaner(final Cleaner cleaner) {
		//		if (cleaner.getPhone().matches("^([0-9]{4,})$"))
		//			cleaner.setPhone(this.configurationService.getConfiguration().getCountryCode() + " " + cleaner.getPhone());
		return this.cleanerRepository.save(cleaner);
	}

	// RECONSTRUCT REGISTER AS CLEANER ---------------------------------------------------------------		
	public Cleaner reconstructRegisterAsCleaner(final RegisterActor registerActor, final BindingResult binding) {
		final Cleaner cleaner = this.create();

		this.actorService.checkActor(registerActor, binding);

		cleaner.getUserAccount().setUsername(registerActor.getUserName());
		final String password = registerActor.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		cleaner.getUserAccount().setPassword(hashPassword);
		cleaner.setBornDate(registerActor.getBornDate());
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

}
