
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import domain.Transport;
import repositories.TransportRepository;

@Component
public class StringToTransportConverter implements Converter<String, Transport> {

	@Autowired
	TransportRepository transportRepository;


	@Override
	public Transport convert(final String text) {
		Transport result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.transportRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
