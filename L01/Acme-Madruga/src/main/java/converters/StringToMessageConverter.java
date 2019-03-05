
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MessageRepository;
import domain.Message;

@Component
@Transactional
public class StringToMessageConverter implements Converter<String, Message> {

	@Autowired
	MessageRepository	MessageRepository;


	@Override
	public Message convert(final String text) {
		Message result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				System.out.println("Error en StringToMessageConverter IF: " + text);
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.MessageRepository.findOne(id);
				System.out.println("Error en StringToMessageConverter ELSE: " + result);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToMessageConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
