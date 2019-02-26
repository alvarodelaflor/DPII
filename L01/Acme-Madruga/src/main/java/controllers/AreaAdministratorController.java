
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import domain.Area;

@Controller
@RequestMapping("/area/administrator")
public class AreaAdministratorController {

	@Autowired
	private AreaService	areaService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView res;

		try {
			final Collection<Area> areas = this.areaService.findAll();

			res = new ModelAndView("area/administrator/list");
			res.addObject("areas", areas);
			res.addObject("requestURI", "area/administrator/list.do");
		} catch (final Exception e) {
			res = new ModelAndView("redirect:index.do");
		}

		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "areaId", defaultValue = "-1") final int areaId) {

		ModelAndView res;

		try {
			final Area area = this.areaService.findOne(areaId);

			res = new ModelAndView("area/administrator/show");

			res.addObject("area", area);
			res.addObject("requestURI", "area/administrator/show.do");
		} catch (final Exception e) {
			res = new ModelAndView("redirect:index.do");
		}

		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "areaId", defaultValue = "-1") final int areaId) {

		ModelAndView res;

		try {
			this.areaService.delete(areaId);
			res = new ModelAndView("redirect:list.do");
		} catch (final Exception oops) {
			res = new ModelAndView("redirect:show.do");
		}

		return res;
	}

	// CREATE will call a EDIT view with a blank Area Object 
}
