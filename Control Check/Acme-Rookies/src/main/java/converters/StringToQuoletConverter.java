
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.QuoletRepository;
import domain.Quolet;

@Component
public class StringToQuoletConverter implements Converter<String, Quolet> {

	@Autowired
	QuoletRepository	quoletRepository;


	@Override
	public Quolet convert(final String text) {
		Quolet result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.quoletRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
