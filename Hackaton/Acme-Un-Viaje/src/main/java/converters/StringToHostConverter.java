
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import domain.Host;

import repositories.HostRepository;

@Component
public class StringToHostConverter implements Converter<String, Host> {

	@Autowired
	HostRepository	hostRepository;


	@Override
	public Host convert(final String text) {
		Host result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.hostRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToHostConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
