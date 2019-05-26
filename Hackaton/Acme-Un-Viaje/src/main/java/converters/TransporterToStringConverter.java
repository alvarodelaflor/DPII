
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Transporter;

@Component
public class TransporterToStringConverter implements Converter<Transporter, String> {

	@Override
	public String convert(final Transporter transporter) {
		String result;

		if (transporter == null)
			result = null;
		else
			result = String.valueOf(transporter.getId());
		System.out.println("CONVERTIDOR TransporterToStringConverter.java " + result);
		return result;
	}
}
