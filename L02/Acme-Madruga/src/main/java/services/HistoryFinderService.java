
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Brotherhood;
import forms.HistoryFinderForm;

@Service
@Transactional
public class HistoryFinderService {
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private BrotherhoodService brotherhoodService;
	

	public HistoryFinderForm create() {
		final HistoryFinderForm res = new HistoryFinderForm();
		return res;
	}
	
	public Collection<Brotherhood> findByFilter(final String title, String name) {
		Collection<Brotherhood> brotherhoods;

		if (title.toString().length()> 0 && name.toString().length()<=0) {
			brotherhoods = this.historyService.findHistoryByBrotherhoodTitle(title);
		} else if (title.toString().length()<= 0 && name.toString().length()>0) {
			brotherhoods = this.historyService.findHistoryByBrotherhoodName(name);
		} else if (title.toString().length()>0 && name.toString().length()>0){
			brotherhoods = this.historyService.findHistoryByBrotherhoodTitleAndName(title, name);
		} else {
			brotherhoods = this.brotherhoodService.findAll();
		}
		System.out.println("Brotherhoods encontrados: " + brotherhoods);
		return brotherhoods;
	}
}
