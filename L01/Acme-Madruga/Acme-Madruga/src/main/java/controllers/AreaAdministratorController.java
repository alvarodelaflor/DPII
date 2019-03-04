
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import services.BrotherhoodService;
import domain.Area;

@Controller
@RequestMapping("/area/administrator")
public class AreaAdministratorController {

	@Autowired
	private AreaService			areaService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


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
			final Collection<String> pictures = area.getPictures();

			res = new ModelAndView("area/administrator/show");

			res.addObject("area", area);
			res.addObject("pictures", pictures);
			final int currentlyUsed = this.brotherhoodService.findByAreaId(areaId).size();
			res.addObject("currentlyUsed", currentlyUsed);
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
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:show.do?areaId=" + areaId);
		}

		return res;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView res;
		res = new ModelAndView("area/administrator/create");
		try {
			final Area area = this.areaService.create();
			res.addObject("area", area);
		} catch (final Exception e) {
			res = new ModelAndView("redirect:index.do");
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam(value = "areaId", defaultValue = "-1") final int areaId) {

		ModelAndView res;
		res = new ModelAndView("area/administrator/edit");
		try {
			final Area area = this.areaService.findOne(areaId);
			res.addObject("area", area);
		} catch (final Exception e) {
			res = new ModelAndView("redirect:index.do");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Area area, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors()) {

			System.out.println("El error pasa por PositionAdministratorController");
			System.out.println(binding);
			res = new ModelAndView("area/administrator/create");
		} else
			try {

				System.out.println("El error pasa por el try de AreaAdminController");
				System.out.println(binding);

				this.areaService.save(area);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {

				System.out.println("El error pasa por el catch de AreaAdminController");
				System.out.println(binding);

				res = this.createEditModelAndView(area);
			}

		return res;
	}

	// Other Methods:
	private ModelAndView createEditModelAndView(final Area area) {
		return this.createEditModelAndView(area, null);
	}

	private ModelAndView createEditModelAndView(final Area area, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("area/administrator/create");
		result.addObject("area", area);
		result.addObject("message", messageCode);

		return result;
	}
}
