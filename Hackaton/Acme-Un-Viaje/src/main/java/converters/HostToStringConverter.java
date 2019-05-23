
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import domain.Host;

@Component
public class HostToStringConverter implements Converter<Host, String> {

	@Override
	public String convert(final Host host) {
		String result;

		if (host == null)
			result = null;
		else
			result = String.valueOf(host.getId());
		System.out.println("CONVERTIDOR HostToStringConverter.java " + result);
		return result;
	}
}
