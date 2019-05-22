
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import domain.JobApplication;

@Component
public class JobApplicationToStringConverter implements Converter<JobApplication, String> {

	@Override
	public String convert(final JobApplication jobApplication) {
		String result;

		if (jobApplication == null)
			result = null;
		else
			result = String.valueOf(jobApplication.getId());
		System.out.println("CONVERTIDOR JobApplicationToStringConverter.java " + result);
		return result;
	}
}
