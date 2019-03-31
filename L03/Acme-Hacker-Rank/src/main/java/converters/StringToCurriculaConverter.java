
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import domain.Curricula;

import repositories.CurriculaRepository;

@Component
@Transactional
public class StringToCurriculaConverter implements Converter<String, Curricula> {

	@Autowired
	CurriculaRepository	curriculaRepository;


	@Override
	public Curricula convert(final String text) {
		Curricula result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.curriculaRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToCurriculaConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
