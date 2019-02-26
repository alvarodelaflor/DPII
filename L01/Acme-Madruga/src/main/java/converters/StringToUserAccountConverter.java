
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import security.UserAccount;
import security.UserAccountRepository;

@Component
@Transactional
public class StringToUserAccountConverter implements Converter<String, UserAccount> {

	@Autowired
	UserAccountRepository	userAccountRepository;


	@Override
	public UserAccount convert(final String text) {
		UserAccount result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				System.out.println("Error en StringToUserAccountConverter IF: " + text);
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.userAccountRepository.findOne(id);
				System.out.println("Error en StringToUserAccountConverter ELSE: " + result);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToUserAccountConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
