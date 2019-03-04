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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.FloatBroService;
import services.PositionAuxService;
import services.ProcessionService;
import domain.Brotherhood;
import domain.FloatBro;
import domain.Procession;

/*
 * CONTROL DE CAMBIOS ProcessionBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 12:29 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 16:35 AÑADIDO RECONSTRUIDOR PROCESSION
 */

@Controller
@RequestMapping("/procession/brotherhood")
public class ProcessionBrotherhoodController extends AbstractController {

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	BrotherhoodService			brotherhoodService;

	@Autowired
	PositionAuxService			positionAuxService;

	@Autowired
	FloatBroService				floatBroService;


	// Constructors -----------------------------------------------------------

	public ProcessionBrotherhoodController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Procession> processions = this.processionService.findAllBrotherhoodLogged();
		//		final Collection<FloatBro> floats = this.floatBroService.findAll();
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<FloatBro> floats = this.floatBroService.getFloatBroByBrotherhoodId(brotherhood.getId());
		Boolean checkValid = false;
		if (floats.isEmpty() || brotherhood.getArea() == null)
			checkValid = true;

		result = new ModelAndView("procession/brotherhood/list");
		result.addObject("processions", processions);
		result.addObject("checkValid", checkValid);
		result.addObject("requestURI", "procession/brotherhood/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "processionId", defaultValue = "-1") final int processionId) {
		ModelAndView result;
		final Procession procession = this.processionService.findOne(processionId);

		if (this.processionService.findOne(processionId) == null || LoginService.getPrincipal().getId() != procession.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(procession, "procession.nul");

			result = new ModelAndView("procession/brotherhood/show");
			result.addObject("procession", procession);
			result.addObject("requestURI", "procession/brotherhood/show.do");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Procession procession;
		procession = this.processionService.create();
		result = this.createEditModelAndView(procession);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id", defaultValue = "-1") final int processionId) {
		ModelAndView result;
		Procession procession;
		procession = this.processionService.findOne(processionId);
		if (this.processionService.findOne(processionId) == null || LoginService.getPrincipal().getId() != procession.getBrotherhood().getUserAccount().getId() || procession.getIsFinal().equals(true))
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(procession);
			result = this.createEditModelAndView(procession);
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int processionId) {
		ModelAndView result;

		final Procession procession = this.processionService.findOne(processionId);
		System.out.println("Procession encontrado: " + procession);
		if (this.processionService.findOne(processionId) == null || LoginService.getPrincipal().getId() != procession.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(procession, "procession.null");

			try {
				this.processionService.delete(procession);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(procession, "procession.commit.error");
			}
		}
		return result;
	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public void saveInitial(final Procession procession, final BindingResult binding) {
	//		final Procession toSendProcession = this.processionService.reconstruct(procession, binding);
	//		this.save(toSendProcession, binding);
	//	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Procession procession, final BindingResult binding) {
		ModelAndView result;

		procession = this.processionService.reconstruct(procession, binding);
		if (binding.hasErrors()) {
			System.out.println("Binding con errores: " + binding.getAllErrors());
			result = this.createEditModelAndView(procession);
		} else
			try {
				System.out.println("El error pasa por aquí alvaro (TRY de save())");
				System.out.println(binding);
				this.processionService.save(procession);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("El error: ");
				System.out.println(oops);
				System.out.println(binding);
				result = this.createEditModelAndView(procession, "procession.commit.error");
			}
		return result;
	}
	public ModelAndView createEditModelAndView(final Procession procession) {
		ModelAndView result;

		result = new ModelAndView("procession/brotherhood/edit");

		final Collection<FloatBro> floatBros = this.floatBroService.findAllBrotherhoodLogged();

		result.addObject("procession", procession);
		result.addObject("floatBros", floatBros);

		return result;
	}
	private ModelAndView createEditModelAndView(final Procession procession, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("procession/brotherhood/edit");

		result.addObject("procession", procession);
		result.addObject("message", messageCode);

		return result;
	}
}
