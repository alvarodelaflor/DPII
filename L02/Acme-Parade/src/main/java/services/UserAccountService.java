
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import security.Authority;
import security.UserAccount;
import forms.RegistrationForm;

@Service
public class UserAccountService {

	@Autowired
	private Validator	validator;


	public UserAccount create() {
		final UserAccount ua = new UserAccount();
		return ua;
	}

	public UserAccount reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		final UserAccount result = this.create();

		final String password = registrationForm.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);

		result.setId(0);
		result.setVersion(0);

		result.setUsername(registrationForm.getUserName());

		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		autoridades.add(authority);
		result.setAuthorities(autoridades);

		result.setPassword(hashPassword);

		this.validator.validate(result, binding);
		return result;
	}

}
