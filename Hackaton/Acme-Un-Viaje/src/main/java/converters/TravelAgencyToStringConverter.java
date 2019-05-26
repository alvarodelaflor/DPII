
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.TravelAgency;

@Component
public class TravelAgencyToStringConverter implements Converter<TravelAgency, String> {

	@Override
	public String convert(final TravelAgency travelAgency) {
		String result;

		if (travelAgency == null)
			result = null;
		else
			result = String.valueOf(travelAgency.getId());
		System.out.println("CONVERTIDOR TravelAgencyToStringConverter.java " + result);
		return result;
	}
}
