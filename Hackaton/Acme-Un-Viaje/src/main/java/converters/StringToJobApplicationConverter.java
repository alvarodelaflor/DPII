
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import domain.JobApplication;

import repositories.JobApplicationRepository;

@Component
public class StringToJobApplicationConverter implements Converter<String, JobApplication> {

	@Autowired
	JobApplicationRepository	jobApplicationRepository;


	@Override
	public JobApplication convert(final String text) {
		JobApplication result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.jobApplicationRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToJobApplicationConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
