
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Accomodation;

@Component
public class AccomodationToStringConverter implements Converter<Accomodation, String> {

	@Override
	public String convert(final Accomodation accomodation) {
		String result;

		if (accomodation == null)
			result = null;
		else
			result = String.valueOf(accomodation.getId());
		System.out.println("CONVERTIDOR AccomodationToStringConverter.java " + result);
		return result;
	}
}
