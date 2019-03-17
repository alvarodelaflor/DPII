
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.InceptionRecordRepository;
import domain.InceptionRecord;

@Component
@Transactional
public class StringToInceptionRercordConverter implements Converter<String, InceptionRecord> {

	@Autowired
	InceptionRecordRepository	inceptionRecordRepository;


	@Override
	public InceptionRecord convert(final String text) {
		InceptionRecord result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.inceptionRecordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToInceptionRecordConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
