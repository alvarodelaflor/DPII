
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import domain.Position;

@Controller
@RequestMapping("/position/company")
public class PositionCompanyController {

	@Autowired
	private PositionService	positionService;


	// listCompany ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<Position> positions = this.positionService.findAllPositionsByLoggedCompany();

			result = new ModelAndView("position/company/list");
			result.addObject("positions", positions);
			result.addObject("requestURI", "position/company/list.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
