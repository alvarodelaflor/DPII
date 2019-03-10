/*
 * HistoryController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;


import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import domain.Brotherhood;
import domain.History;
import forms.HistoryFinderForm;
import security.LoginService;
import services.BrotherhoodService;
import services.HistoryFinderService;
import services.WelcomeService;

/*
 * CONTROL DE CAMBIOS History.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/history")
public class HistoryController extends AbstractController {
	
	@Autowired
	private BrotherhoodService brotherhoodService;
		
	@Autowired
	private HistoryFinderService historyFinderService;
	
	@Autowired
	private WelcomeService welcomeService;


	// Constructors -----------------------------------------------------------

	public HistoryController() {
		super();
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "brotherhoodId", defaultValue = "-1") final int brotherhoodId) {
		ModelAndView result;
		try {
			Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
			History history = brotherhood.getHistory();
			result = new ModelAndView("history/show");
			result.addObject("history", history);
			result.addObject("ownerBrotherhood", false);
			// En el caso de que el usuario este logueado compruebo si es el dueño de la inceptionRecord para que puede editarla
			if (checkAnyUserLogger()) {	
				System.out.println("Usuario autenticado");
				result.addObject("ownerBrotherhood", LoginService.getPrincipal().getId()==brotherhood.getUserAccount().getId());
			}
			result.addObject("requestURI", "history/show.do");
			result.addObject("system", this.welcomeService.getSystem());
			result.addObject("logo", this.welcomeService.getLogo());
		} catch (Exception e) {
			System.out.println("Error en HistoryController.java SHOW: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	private Boolean checkAnyUserLogger() {
		Boolean res = false;
		try {
			res = LoginService.getPrincipal().getId()>0;
			System.out.println("El id del usuario logueado es: " + LoginService.getPrincipal().getId());
		} catch (Exception e) {
			res = false;
			System.out.println("No existe ningún usuario logueado");
		}
		return res;
	}
	
	private Integer getIdBrotherhoodLogger() {
		Integer res = -1;
		try {
			res = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId();
		} catch (Exception e) {
			System.out.println("El usuario no esta logueado o no es un brotherhood: " + e);
		}
		return res;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			HistoryFinderForm historyFinderForm = this.historyFinderService.create();
			result = new ModelAndView("history/list");
			Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			result.addObject("historyFinderForm", historyFinderForm);
			result.addObject("brotherhoods", brotherhoods);
			result.addObject("brotherhoodId", getIdBrotherhoodLogger());
			result.addObject("requestURI", "history/list.do");
			result.addObject("system", this.welcomeService.getSystem());
			result.addObject("logo", this.welcomeService.getLogo());
		} catch (Exception e) {
			System.out.println("Error en HistoryController.java LIST GET: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "save")
	public ModelAndView save(HistoryFinderForm historyFinderForm, final BindingResult binding) {
		ModelAndView result;

		try {
			System.out.println("Name: " + historyFinderForm.getName());
			System.out.println("Title: " + historyFinderForm.getTitle());
			Collection<Brotherhood> brotherhoods = this.historyFinderService.findByFilter(historyFinderForm.getTitle().toLowerCase(), historyFinderForm.getName().toLowerCase());
			result = new ModelAndView("history/list");
			result.addObject("brotherhoods", brotherhoods);
//			result.addObject("brotherhoods", new ArrayList<Brotherhood>());
			
			result.addObject("historyFinderForm", historyFinderForm);
			result.addObject("requestURI", "history/list.do");
		} catch (final Throwable oops) {
			System.out.println("Error en HistoryController.java LIST SAVE: " + oops);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
}
