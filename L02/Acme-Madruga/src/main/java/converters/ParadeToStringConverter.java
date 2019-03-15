
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Parade;

@Component
@Transactional
public class ParadeToStringConverter implements Converter<Parade, String> {

	@Override
	public String convert(final Parade parade) {
		String result;

		if (parade == null)
			result = null;
		else
			result = String.valueOf(parade.getId());
		System.out.println("CONVERTIDOR paradeToStringConverter.java " + result);
		return result;
	}
}
