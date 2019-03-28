
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Float;

@Component
public class FloatToStringConverter implements Converter<domain.Float, String> {

	@Override
	public String convert(final domain.Float floatt) {
		String result;

		if (floatt == null)
			result = null;
		else
			result = String.valueOf(floatt.getId());
		System.out.println("CONVERTIDOR FloatToStringConverter.java " + result);
		return result;
	}
}
