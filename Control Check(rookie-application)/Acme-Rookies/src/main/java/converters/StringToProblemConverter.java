
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ProblemRepository;
import domain.Problem;

@Component
public class StringToProblemConverter implements Converter<String, Problem> {

	@Autowired
	ProblemRepository	problemRepository;


	@Override
	public Problem convert(final String text) {
		Problem result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.problemRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
