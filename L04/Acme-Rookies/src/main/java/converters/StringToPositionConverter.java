
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import domain.Position;

import repositories.PositionRepository;

@Component
public class StringToPositionConverter implements Converter<String, Position> {

	@Autowired
	PositionRepository	positionRepository;


	@Override
	public Position convert(final String text) {
		Position result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.positionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToPositionConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
