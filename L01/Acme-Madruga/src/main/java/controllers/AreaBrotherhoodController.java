/*
 * BrotherhoodController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import domain.Area;
import domain.Brotherhood;
import security.LoginService;
import services.AreaService;
import services.BrotherhoodService;


/*
 * CONTROL DE CAMBIOS AreaBrotherhoodController.java
 * 
 * ALVARO 03/03/2019 21:06 CREACIÓN DE LA CLASE
 */

@Controller
@RequestMapping("/area/brotherhood")
public class AreaBrotherhoodController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	AreaService areaService;
	
	@Autowired
	BrotherhoodService brotherhoodService;
	
	// Constructors -----------------------------------------------------------

	public AreaBrotherhoodController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		return createEditModelAndView();
	}
	
	private Boolean validBrotherhood(Brotherhood brotherhood, Collection<Area> areas) {
		Boolean res = null;
		if (!areas.isEmpty() && brotherhood.getArea()==null) {
			res = true;
		} else if (!areas.isEmpty() && brotherhood.getArea()!=null) {
			res = false;
		}
		return res;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Brotherhood brotherhoodInit, final BindingResult binding) {
		ModelAndView result;
		Area area = brotherhoodInit.getArea();
		System.out.println(area);
		if (binding.hasErrors()) {
			System.out.println("Error en binding AreaBrotherhoodController.java (binding.hasErrors()): " + binding);
			result = this.createEditModelAndView();
		} else
			try {
				System.out.println("Paso por try de AreaBrotherhoodController.java (try): " + binding);
				Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
				brotherhood.setArea(area);
//				this.brotherhoodService.save(brotherhood);
				result = this.createEditModelAndView();
			} catch (final Throwable oops) {
				System.out.println("Error en catch AreaBrotherhoodController.java: " + oops);
				result = this.createEditModelAndView("area.commit.error");
			}
		return result;
	}
	
	private ModelAndView createEditModelAndView() {
		ModelAndView res;
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "area.brotherhood.null");
		Collection<Area> areas = this.areaService.findAll();
		Boolean validBrotherhood = validBrotherhood(brotherhood, areas);
		res = new ModelAndView("area/brotherhood/edit");
		res.addObject("areas", areas);
		res.addObject("validBrotherhood", validBrotherhood);
		res.addObject("brotherhoodInit", brotherhood);
		if (brotherhood.getArea()!=null) {
			res.addObject("areaSelected", brotherhood.getArea().getName());
		}
		return res;
	}
	private ModelAndView createEditModelAndView(final String messageCode) {
		ModelAndView res = createEditModelAndView();
		res.addObject("message", messageCode);

		return res;
	}

}
