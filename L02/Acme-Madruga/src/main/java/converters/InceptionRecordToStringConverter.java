
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.InceptionRecord;

@Component
public class InceptionRecordToStringConverter implements Converter<InceptionRecord, String> {

	@Override
	public String convert(final InceptionRecord inceptionRecord) {
		String result;

		if (inceptionRecord == null)
			result = null;
		else
			result = String.valueOf(inceptionRecord.getId());
		System.out.println("CONVERTIDOR InceptionRecordToStringConverter.java " + result);
		return result;
	}
}
