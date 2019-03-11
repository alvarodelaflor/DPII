
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PositionAux;


@Component
@Transactional
public class PositionAuxToStringConverter implements Converter<PositionAux, String> {

	@Override
	public String convert(final PositionAux positionAux) {
		String result;

		if (positionAux == null)
			result = null;
		else
			result = String.valueOf(positionAux.getId());
		System.out.println("CONVERTIDOR PositionAuxToStringConverter.java " + result);
		return result;
	}
}
