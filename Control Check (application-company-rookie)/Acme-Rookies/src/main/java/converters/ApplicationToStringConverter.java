
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Application;

@Component
public class ApplicationToStringConverter implements Converter<Application, String> {

	@Override
	public String convert(final Application application) {
		String result;

		if (application == null)
			result = null;
		else
			result = String.valueOf(application.getId());
		System.out.println("CONVERTIDOR CurriculaToStringConverter.java " + result);
		return result;
	}
}
