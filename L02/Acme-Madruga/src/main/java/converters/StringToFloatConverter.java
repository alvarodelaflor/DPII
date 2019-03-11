
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import domain.BigDecimal;
import repositories.FloatRepository;

@Component
@Transactional
public class StringToFloatConverter implements Converter<String, domain.BigDecimal> {

	@Autowired
	FloatRepository	floatRepository;


	@Override
	public BigDecimal convert(final String text) {
		BigDecimal result;
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
