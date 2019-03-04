
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FloatBroRepository;
import domain.FloatBro;

@Component
@Transactional
public class StringToFloatBroConverter implements Converter<String, FloatBro> {

	@Autowired
	FloatBroRepository	floatBroRepository;


	@Override
	public FloatBro convert(final String text) {
		FloatBro result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.floatBroRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToFloatBroConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
