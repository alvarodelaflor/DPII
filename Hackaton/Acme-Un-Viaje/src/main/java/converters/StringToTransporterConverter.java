
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.TransporterRepository;
import domain.Transporter;

@Component
public class StringToTransporterConverter implements Converter<String, Transporter> {

	@Autowired
	TransporterRepository	transporterRepository;


	@Override
	public Transporter convert(final String text) {
		Transporter result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.transporterRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToTransporterConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
