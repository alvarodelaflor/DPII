
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MessageBox;

@Component
@Transactional
public class MessageBoxToStringConverter implements Converter<MessageBox, String> {

	@Override
	public String convert(final MessageBox messageBox) {
		String result;

		if (messageBox == null)
			result = null;
		else
			result = String.valueOf(messageBox.getId());
		System.out.println("CONVERTIDOR messageBoxToStringConverter.java " + result);
		return result;
	}
}
