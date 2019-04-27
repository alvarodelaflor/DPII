
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import domain.Curricula;

@Component
public class CurriculaToStringConverter implements Converter<Curricula, String> {

	@Override
	public String convert(final Curricula curricula) {
		String result;

		if (curricula == null)
			result = null;
		else
			result = String.valueOf(curricula.getId());
		System.out.println("CONVERTIDOR CurriculaToStringConverter.java " + result);
		return result;
	}
}
