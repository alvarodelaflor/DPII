
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import domain.Cleaner;

import repositories.CleanerRepository;

@Component
public class StringToCleanerConverter implements Converter<String, Cleaner> {

	@Autowired
	CleanerRepository	cleanerRepository;


	@Override
	public Cleaner convert(final String text) {
		Cleaner result;
		String name;
		String surname;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				String[] trozos = text.split(",");
				surname = trozos[0];
				name = trozos [1];
				result = this.cleanerRepository.findByNameSurname(name, surname);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToCleanerConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
