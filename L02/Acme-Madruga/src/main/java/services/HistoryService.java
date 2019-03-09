
package services;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.History;
import domain.Inception;
import domain.Record;

@Service
public class HistoryService {

	@Autowired
	HistoryRepository	historyRepository;

	@Autowired
	BrotherhoodService	brotherhoodService;


	public History create() {

		// Creo el resultado
		final History res = new History();
		// Capturo actor loggeado y compruebo que es un Brotherhood
		final Brotherhood bro = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(bro, "user.logged.error");
		// Inicializo la lista de Records vacia
		final Collection<Record> records = Arrays.asList();
		// Setteo los atributos
		res.setBrotherhood(bro);
		res.setRecords(records);
		// Devuelvo el resultado
		return res;
	}

	public History save(final History history) {

		// Comprueba que el "brotherhood" que hace el save sea el dueño del "history"
		Assert.isTrue(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()) == history.getBrotherhood(), "owner.error");
		// Comprueba que el "history" tiene 1 "inception"
		int count = 0;
		for (final Record record : history.getRecords())
			if (record.getClass() == Inception.class)
				count++;
		Assert.isTrue(count == 1, "inception.record.error");
		// Guarda el "history"
		return this.historyRepository.save(history);
	}

	public History findOne(final int historyId) {

		return this.historyRepository.findOne(historyId);
	}
}
