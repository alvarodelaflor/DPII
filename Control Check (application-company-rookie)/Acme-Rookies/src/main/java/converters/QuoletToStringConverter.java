
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Quolet;

@Component
public class QuoletToStringConverter implements Converter<Quolet, String> {

	@Override
	public String convert(final Quolet quolet) {
		String result;

		if (quolet == null)
			result = null;
		else
			result = String.valueOf(quolet.getId());
		return result;
	}
}
