
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.XXXXRepository;
import domain.XXXX;

@Component
public class StringToXXXXConverter implements Converter<String, XXXX> {

	@Autowired
	XXXXRepository	xxxxRepository;


	@Override
	public XXXX convert(final String text) {
		XXXX result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.xxxxRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
