
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import domain.Float;
import repositories.FloatRepository;

@Component
@Transactional
public class StringToFloatConverter implements Converter<String, domain.Float> {

	@Autowired
	FloatRepository	floatRepository;


	@Override
	public Float convert(final String text) {
		Float result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.floatRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToFloatConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
