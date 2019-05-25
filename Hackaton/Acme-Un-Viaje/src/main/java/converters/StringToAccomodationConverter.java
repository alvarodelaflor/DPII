
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import domain.Accomodation;

import repositories.AccomodationRepository;

@Component
public class StringToAccomodationConverter implements Converter<String, Accomodation> {

	@Autowired
	AccomodationRepository	accomodationRepository;


	@Override
	public Accomodation convert(final String text) {
		Accomodation result;
		String address;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				address = text;
				result = this.accomodationRepository.findByAddress(address);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToAccomodationConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
