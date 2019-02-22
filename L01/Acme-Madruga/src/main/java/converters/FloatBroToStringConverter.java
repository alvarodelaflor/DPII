
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.FloatBro;

@Component
public class FloatBroToStringConverter implements Converter<FloatBro, String> {

	@Override
	public String convert(final FloatBro floatBro) {
		String result;

		if (floatBro == null)
			result = null;
		else
			result = String.valueOf(floatBro.getId());
		System.out.println("CONVERTIDOR FloatBroToStringConverter.java " + result);
		return result;
	}
}
