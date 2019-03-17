
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.History;

/*
 * CONTROL DE CAMBIOS HistroyService.java
 * 
 * ALVARO 17/02/2019 12:02 CREACION DE LA CLASE
 */

@Service
public class HistoryService {

	//Managed Repository -------------------	

	@Autowired
	private HistoryRepository	historyRepository;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	//Supporting services ------------------

	//Simple CRUD Methods ------------------

	public History create() {

		final History history = new History();
		return history;
	}

	public Collection<History> findAll() {
		return this.historyRepository.findAll();
	}

	public History findHistoryByBrotherhood(final int brotherhoodId) {
		return this.historyRepository.findHistoryByBrotherhood(brotherhoodId);
	}

	public History findHistoryByInceptionRecordId(final int inceptionRecordId) {
		return this.historyRepository.findHistoryByInceptionRecordId(inceptionRecordId);
	}

	public History save(final History history) {
		try {
			final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
			Assert.isTrue(brotherhood.getHistory().getId() == history.getId());
		} catch (final Exception e) {
			System.out.println("El usuario no esta registrado a�n");
		}
		return this.historyRepository.save(history);
	}

	public Collection<Brotherhood> findHistoryByBrotherhoodTitle(final String title) {
		return this.historyRepository.findHistoryByBrotherhoodTitle(title);
	}

	public Collection<Brotherhood> findHistoryByBrotherhoodName(final String name) {
		return this.historyRepository.findHistoryByBrotherhoodName(name);
	}

	public Collection<Brotherhood> findHistoryByBrotherhoodTitleAndName(final String title, final String name) {
		return this.historyRepository.findHistoryByBrotherhoodTitleAndName(title, name);
	}

	public History findOne(final int id) {
		return this.historyRepository.findOne(id);
	}

	public void flush() {
		this.historyRepository.flush();
	}

	public Collection<History> findHistoriesPerSize(final float t) {

		final Collection<History> res = new ArrayList<History>();

		//final Float size = this.historyRepository.maxRecordPerHistory();
		final List<History> all = this.historyRepository.findAll();
		System.out.println("Todas las history:");
		System.out.println(all);

		for (int i = 0; i < all.size(); i++) {

			float count = 0;
			count = count + all.get(i).getLegalRecord().size();
			count = count + all.get(i).getLinkRecord().size();
			count = count + all.get(i).getPeriodRecord().size();
			count = count + all.get(i).getMiscellaneousRecord().size();
			count = count + 1; // Inception Record

			System.out.println(count);

			if (count >= t)
				res.add(all.get(i));
			System.out.println("==================== Iteraciones del for:");
			System.out.println(res);
		}

		System.out.println("Lista en el repo: ");
		System.out.println(res);
		return res;
	}

	public float maxRecordPerHistory() {

		return this.historyRepository.maxRecordPerHistory();
	}

	public float minRecordPerHistory() {

		return this.historyRepository.minRecordPerHistory();
	}

	public float avgRecordPerHistory() {

		return this.historyRepository.avgRecordPerHistory();
	}

	public float stddevRecordPerHistory() {

		return this.historyRepository.stddevRecordPerHistory();
	}
}
