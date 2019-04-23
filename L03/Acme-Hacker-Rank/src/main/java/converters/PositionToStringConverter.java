
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import domain.Position;

@Component
public class PositionToStringConverter implements Converter<Position, String> {

	@Override
	public String convert(final Position position) {
		String result;

		if (position == null)
			result = null;
		else
			result = String.valueOf(position.getId());
		System.out.println("CONVERTIDOR PositionToStringConverter.java " + result);
		return result;
	}
}
