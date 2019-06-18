
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.XXXX;

@Component
public class XXXXToStringConverter implements Converter<XXXX, String> {

	@Override
	public String convert(final XXXX xxxx) {
		String result;

		if (xxxx == null)
			result = null;
		else
			result = String.valueOf(xxxx.getId());
		return result;
	}
}
