
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Area;

@Component
public class AreaToStringConverter implements Converter<Area, String> {

	@Override
	public String convert(final Area area) {
		String result;

		if (area == null)
			result = null;
		else
			result = String.valueOf(area.getId());
		System.out.println("CONVERTIDOR AreaToStringConverter.java " + result);
		return result;
	}
}
