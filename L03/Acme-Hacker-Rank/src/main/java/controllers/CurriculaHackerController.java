/*
 * CurricculaHackerController.java
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
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import domain.Curricula;
import domain.Hacker;
import services.CurriculaService;
import services.HackerService;

/*
 * CONTROL DE CAMBIOS CurriculaHackerController.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/curricula/hacker")
public class CurriculaHackerController extends AbstractController {
	
	@Autowired
	private HackerService hackerService;

	@Autowired
	private CurriculaService curriculaService;

	// Constructors -----------------------------------------------------------

	public CurriculaHackerController() {
		super();
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			Hacker hackerLogin = this.hackerService.getHackerLogin();
			Assert.notNull(hackerLogin, "No hacker is login");
			Curricula curricula = this.curriculaService.create();
			result = new ModelAndView("curricula/hacker/edit");
			result.addObject("curricula", curricula);		
		} catch (Exception e) {
			System.out.println("Error e en GET /create CurriculaController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "curriculaId", defaultValue = "-1") final int curriculaId) {
		ModelAndView result;
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		try {
			Curricula curriculaDB = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curriculaDB, "Curricula not found in DB");
			Assert.isTrue(!curriculaDB.getIsCopy(), "Trying to edit a copyCurricula");
			Assert.notNull(hackerLogin, "No hacker is login");
			Assert.isTrue(hackerLogin.equals(curriculaDB.getHacker()));
			result = new ModelAndView("curricula/hacker/edit");
			result.addObject("curricula", curriculaDB);
		} catch (final Exception e) {
			if (hackerLogin!=null) {
				result = new ModelAndView("curricula/list");
				final Collection<Curricula> curriculas = this.curriculaService.findAllNotCopyByHacker(hackerLogin);
				result.addObject("curriculas", curriculas);
			} else {
				result = new ModelAndView("redirect:/welcome/index.do");	
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Curricula curricula, final BindingResult binding) {
		ModelAndView result;
		try {
			curricula = this.curriculaService.reconstruct(curricula, binding);	
		} catch (Exception e) {
			System.out.println("Error e reconstruct de curricula: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}
		
		if (binding.hasErrors()) {
			System.out.println("Error en CurriculaHackerController.java, binding: " + binding);
			result = new ModelAndView("curricula/hacker/create");
			result.addObject("curricula", curricula);
		} else
			try {
				Hacker hackerLogin = this.hackerService.getHackerLogin();
				Assert.notNull(hackerLogin, "No hacker is login");
				Assert.isTrue(curricula != null, "curricula.null");
				Assert.isTrue(!curricula.getIsCopy(), "Trying to edit a copyCurricula");
				Curricula saveCurricula = this.curriculaService.save(curricula);
				result = new ModelAndView("redirect:/curricula/show.do?curriculaId="+saveCurricula.getId());
				result.addObject("requestURI", "curricula/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE CurriculaHackerController.java Throwable: " + oops);
				result = new ModelAndView("curricula/hacker/edit");
				result.addObject("curricula", curricula);
				result.addObject("message", "curricula.commit.error");
			}
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "curriculaId", defaultValue = "-1") final int curriculaId) {
		ModelAndView result;
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		
		try {
			Assert.notNull(hackerLogin, "No hacker is login");
			Curricula curriculaDB = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curriculaDB, "Not found curricula in DB");
			Assert.isTrue(curriculaDB.getHacker().equals(hackerLogin), "Not allow to delete, diferent hacker");
			this.curriculaService.delete(curriculaDB);
			result = new ModelAndView("redirect:/curricula/list.do?hackerId="+hackerLogin.getId());
		} catch (final Throwable oops) {
			System.out.println("Error en CurriculaHackerController.java Throwable: " + oops);
			if (hackerLogin!=null) {
				result = new ModelAndView("redirect:/curricula/list.do?hackerId="+hackerLogin.getId());				
			} else {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
			result.addObject("message", "curricula.commit.error");
		}
		return result;
	}
}
