
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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


	@RequestMapping(value = "/editWords", method = RequestMethod.GET)
	public ModelAndView editWords() {

		final ModelAndView res;

		final Configuration config = new Configuration();

		res = new ModelAndView("configuration/administrator/editWords");
		res.addObject("configuration", config);
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
