
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import domain.Warranty;
import repositories.WarrantyRepository;

@Component
public class StringToWarrantyConverter implements Converter<String, Warranty> {

	@Autowired
	WarrantyRepository warrantyRepository;


	@Override
	public Warranty convert(final String text) {
		Warranty result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.warrantyRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToWarrantyConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
