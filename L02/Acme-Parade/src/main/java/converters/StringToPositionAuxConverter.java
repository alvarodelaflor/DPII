
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import domain.PositionAux;

import repositories.PositionAuxRepository;

@Component
@Transactional
public class StringToPositionAuxConverter implements Converter<String, PositionAux> {

	@Autowired
	PositionAuxRepository	positionAuxRepository;


	@Override
	public PositionAux convert(final String text) {
		PositionAux result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.positionAuxRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToPositionAuxConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
