
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.HistoryService;
import services.WelcomeService;
import domain.History;

@Controller
@RequestMapping("/history/brotherhood")
public class HistoryBrotherhoodController {

	@Autowired
	HistoryService		historyService;

	@Autowired
	BrotherhoodService	brotherhoodService;

	@Autowired
	WelcomeService		welcomeService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView res = new ModelAndView("history/brotherhood/create");

		try {
			// History + InceptionRecord
			//--------------------------------------------------------------------------------
			final History history = this.historyService.create();
			res.addObject("history", history);
			//--------------------------------------------------------------------------------
			// System
			res.addObject("logo", this.welcomeService.getLogo());
			res.addObject("system", this.welcomeService.getSystem());
			//--------------------------------------------------------------------------------
		} catch (final Throwable oops) {
			// Por si un actor != brotherhood intenta crear una "history"
			if (oops.getMessage() == "user.logged.error")
				res = new ModelAndView("redirec:index.do");
		}

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "historyId", defaultValue = "-1") final int historyId) {

		ModelAndView res = new ModelAndView("history/brotherhood/edit");

		try {
			// History
			//--------------------------------------------------------------------------------
			final History history = this.historyService.findOne(historyId);
			res.addObject("history", history);
			//--------------------------------------------------------------------------------
			// System
			res.addObject("logo", this.welcomeService.getLogo());
			res.addObject("system", this.welcomeService.getSystem());
			//--------------------------------------------------------------------------------
		} catch (final Throwable oops) {
			// Por si un actor != brotherhood intenta crear una "history"
			if (oops.getMessage() == "user.logged.error")
				res = new ModelAndView("redirec:index.do");
		}

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final History history, final BindingResult binding) {

		ModelAndView res = new ModelAndView("history/brotherhood/edit");

		if (binding.hasErrors())

			res = new ModelAndView("history/brotherhood/edit");
		else

			try {

				this.historyService.save(history);
			} catch (final Throwable oops) {

				if (oops.getMessage() == "inception.record.error") {

					res = new ModelAndView("history/administrator/edit");
					res.addObject("message", "inception.record.error");
				} else {

					res = new ModelAndView("redirect:index.do");
					res.addObject("message", "owner.error");
				}
			}

		res.addObject("logo", this.welcomeService.getLogo());
		res.addObject("system", this.welcomeService.getSystem());
		return res;
	}
}
