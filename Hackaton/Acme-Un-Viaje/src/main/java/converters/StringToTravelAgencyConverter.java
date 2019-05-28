
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.TravelAgencyRepository;
import domain.TravelAgency;

@Component
public class StringToTravelAgencyConverter implements Converter<String, TravelAgency> {

	@Autowired
	TravelAgencyRepository	travelAgencyRepository;


	@Override
	public TravelAgency convert(final String text) {
		TravelAgency result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.travelAgencyRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToHostConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
