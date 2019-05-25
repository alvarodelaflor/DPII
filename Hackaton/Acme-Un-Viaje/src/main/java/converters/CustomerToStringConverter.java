
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Customer;

@Component
public class CustomerToStringConverter implements Converter<Customer, String> {

	@Override
	public String convert(final Customer customer) {
		String result;

		if (customer == null)
			result = null;
		else
			result = String.valueOf(customer.getId());
		System.out.println("CONVERTIDOR CustomerToStringConverter.java " + result);
		return result;
	}
}
