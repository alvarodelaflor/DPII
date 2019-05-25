
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ValorationRepository;
import domain.Valoration;

@Service
@Transactional
public class ValorationService {

	@Autowired
	private ValorationRepository	valorationRepository;


	public Valoration create() {

		final Valoration res = new Valoration();
		return res;
	}

	public Valoration save(final Valoration valoration) {

		return this.valorationRepository.save(valoration);
	}
}
