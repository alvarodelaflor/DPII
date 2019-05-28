
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Cleaner;
import domain.Customer;
import domain.Valoration;
import repositories.ValorationRepository;

@Service
@Transactional
public class ValorationService {

	@Autowired
	private ValorationRepository valorationRepo;


	public void deleteAllByCleaner(final Cleaner cleaner) {
		final Collection<Valoration> valorations = this.valorationRepo.findValorationsByCleaner(cleaner.getId());
		if (valorations != null && !valorations.isEmpty())
			for (final Valoration valoration : valorations)
				this.valorationRepo.delete(valoration);
	}

	public void deleteAllByCustomer(final Customer customer) {
		final Collection<Valoration> valorations = this.valorationRepo.findValorationsByCustomer(customer.getId());
		if (valorations != null && !valorations.isEmpty())
			for (final Valoration valoration : valorations)
				this.valorationRepo.delete(valoration);

	}

}
