
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import domain.Configuration;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationAdministratorController {

	@Autowired
	ConfigurationService	configService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView res;

		final Configuration config = this.configService.defaultScoreWords();

		res = new ModelAndView("configuration/administrator/list");

		res.addObject("configuration", config);
		res.addObject("requestURI", "configuration/administrator/list.do");

		return res;
	}

	@RequestMapping(value = "/editWords", method = RequestMethod.GET)
	public ModelAndView editWords() {

		final ModelAndView res;

		final Configuration config = new Configuration();

		res = new ModelAndView("configuration/administrator/editWords");
		res.addObject("configuration", config);
		return res;
	}

	@RequestMapping(value = "/editWords", method = RequestMethod.POST, params = "save")
	public ModelAndView editWords(Configuration configuration, final BindingResult binding) {

		ModelAndView res;

		configuration = this.configService.reconstructWords(configuration, binding);

		if (binding.hasErrors()) {
			System.out.println(binding);
			res = new ModelAndView("configuration/administrator/editWords");
		} else
			try {
				this.configService.save(configuration);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("user.error"))
					res = this.createEditModelAndView(configuration, "user.error");
			}

		res = new ModelAndView("configuration/administrator/editWords");

		return res;
	}

	private ModelAndView createEditModelAndView(final Configuration configuration) {
		return this.createEditModelAndView(configuration, null);
	}

	private ModelAndView createEditModelAndView(final Configuration configuration, final String string) {
		ModelAndView result;

		result = new ModelAndView("configuration/edit");
		result.addObject("message", string);
		result.addObject("configuration", configuration);

		return result;
	}

}
