
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Transport;

@Component
public class TransportToStringConverter implements Converter<Transport, String> {

	@Override
	public String convert(final Transport transport) {
		String result;

		if (transport == null)
			result = null;
		else
			result = String.valueOf(transport.getId());
		return result;
	}
}
