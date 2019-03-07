
package controllers;

import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConfigurationService;
import services.WelcomeService;
import domain.Configuration;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationAdministratorController {

	@Autowired
	ConfigurationService	configService;
	@Autowired
	WelcomeService			welcomeService;
	@Autowired
	AdministratorService	administratorService;


	@RequestMapping(value = "/editWords", method = RequestMethod.GET)
	public ModelAndView editWords() {

		final ModelAndView res;

		final Configuration config = new Configuration();

		res = new ModelAndView("configuration/administrator/editWords");
		res.addObject("configuration", config);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		final ModelAndView res;
		final Configuration config = this.configService.getConfiguration();

		res = new ModelAndView("configuration/administrator/edit");
		res.addObject("configuration", config);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Configuration configuration, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors()) {

			System.out.println(binding);
			res = new ModelAndView("configuration/administrator/edit");
		} else
			try {

				this.configService.save(configuration);
				res = this.redirectAdministratorList();
			} catch (final Throwable oops) {

				System.out.println(binding);
				res = this.createEditModelAndView(configuration);
			}

		return res;
	}

	private ModelAndView redirectAdministratorList() {
		ModelAndView result;
		//Logo
		final String logo = this.welcomeService.getLogo();
		HashSet<String> spamWords = new HashSet<>();
		HashSet<String> scoreWordsPos = new HashSet<>();
		HashSet<String> scoreWordsNeg = new HashSet<>();
		HashSet<String> priorities = new HashSet<>();
		//Configuration (FINDER)
		final Configuration configuration = this.configService.getConfiguration();
		//Priorities
		if (this.welcomeService.getPriorities().isEmpty())
			priorities = this.welcomeService.defaultPriorities();
		else
			priorities = this.welcomeService.getPriorities();

		//Spam words
		if (this.welcomeService.getSpamWords().isEmpty())
			spamWords = this.welcomeService.listSpamWords();
		else
			spamWords = this.welcomeService.getSpamWords();

		//Score Words
		if (this.administratorService.getScoreWordsPos().isEmpty())
			scoreWordsPos = this.administratorService.listScoreWordsPos();
		else
			scoreWordsPos = this.administratorService.getScoreWordsPos();

		if (this.administratorService.getScoreWordsNeg().isEmpty())
			scoreWordsNeg = this.administratorService.listScoreWordsNeg();
		else
			scoreWordsNeg = this.administratorService.getScoreWordsNeg();

		//Welcome page
		final String ingles = this.welcomeService.getS();
		final String spanish = this.welcomeService.getE();

		//System
		final String system = this.welcomeService.getSystem();

		//Phone
		final String phone = this.welcomeService.getPhone();

		//Country´s Phone
		final String phoneCountry = this.welcomeService.getCountry();

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		System.out.println("Carmen: Entro en el list");

		result = new ModelAndView("administrator/list");

		result.addObject("logo", logo);

		result.addObject("ingles", ingles);
		result.addObject("spanish", spanish);

		result.addObject("spamWords", spamWords);
		result.addObject("scoreWordsPos", scoreWordsPos);
		result.addObject("scoreWordsNeg", scoreWordsNeg);
		result.addObject("priorities", priorities);
		result.addObject("configuration", configuration);

		result.addObject("system", system);
		result.addObject("phone", phone);
		result.addObject("phoneCountry", phoneCountry);
		result.addObject("language", language);
		result.addObject("requestURI", "administrator/list.do");

		return result;
	}

	private ModelAndView createEditModelAndView(final Configuration configuration) {
		return this.createEditModelAndView(configuration, null);
	}

	private ModelAndView createEditModelAndView(final Configuration configuration, final String string) {
		ModelAndView result;

		result = new ModelAndView("configuration/edit");
		result.addObject("message", string);
		result.addObject("configuration", configuration);
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

}
