
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import domain.Customer;
import repositories.CustomerRepository;

@Component
public class StringToCustomerConverter implements Converter<String, Customer> {

	@Autowired
	CustomerRepository customerRepository;


	@Override
	public Customer convert(final String text) {
		Customer result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.customerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToCustomerConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
