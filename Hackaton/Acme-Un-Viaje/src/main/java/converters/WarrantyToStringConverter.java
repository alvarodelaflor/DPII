
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Warranty;

@Component
public class WarrantyToStringConverter implements Converter<Warranty, String> {

	@Override
	public String convert(final Warranty warranty) {
		String result;

		if (warranty == null)
			result = null;
		else
			result = String.valueOf(warranty.getId());
		return result;
	}
}
