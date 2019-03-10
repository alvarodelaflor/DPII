
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.Authority;
import security.LoginService;
import domain.Area;
import domain.Brotherhood;
import domain.Finder;
import domain.History;
import domain.Member;
import domain.Procession;

@Service
@Transactional
public class HistoryFinderService {
	
	@Autowired
	private HistoryService historyServiceService;

	public Finder create() {
		final Finder res = new Finder();
		return res;
	}

	public Collection<Brotherhood> findByFilter(final String title, String name) {
		Collection<Brotherhood> brotherhoods = new ArrayList<Brotherhood>();

		if (title!=null && name==null) {
			brotherhoods = historyServiceService.findHistoryByBrotherhoodTitle(title);
		} else if (title == null && name!=null) {
			brotherhoods = historyServiceService.findHistoryByBrotherhoodName(name);
		} else {
			brotherhoods = historyServiceService.findHistoryByBrotherhoodTitleAndName(title, name);
		}
		
		return brotherhoods;
	}
}
