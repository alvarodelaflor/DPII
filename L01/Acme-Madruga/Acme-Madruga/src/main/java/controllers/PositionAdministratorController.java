
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

import services.EnrolledService;
import services.PositionService;
import domain.Position;

@Controller
@RequestMapping("/position/administrator")
public class PositionAdministratorController extends AbstractController {

	@Autowired
	private PositionService	positionService;
	@Autowired
	private EnrolledService	enrolledService;


	// Constructors:

	public PositionAdministratorController() {

		super();
	}

	// CRUD Methods:

	// 12.2: Manage Positions by Admin (Delete only it Position is not used)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView res;
		res = new ModelAndView("position/administrator/list");
		try {
			final Collection<Position> positions = this.positionService.findAll();
			res.addObject("positions", positions);
			res.addObject("requestURI", "position/administrator/list.do");
		} catch (final Exception e) {
			res = new ModelAndView("redirect:index.do");
		}

		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "positionId", defaultValue = "-1") final int positionId) {

		ModelAndView res;
		res = new ModelAndView("position/administrator/show");

		try {

			final Integer currentlyUsed = this.enrolledService.findAllByPositionId(positionId).size();
			final Position position = this.positionService.findOne(positionId);
			res.addObject("position", position);
			res.addObject("currentlyUsed", currentlyUsed);
			res.addObject("requestURI", "position/administrator/show.do");
		} catch (final Exception e) {
			res = new ModelAndView("redirect:index.do");
		}

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView res;
		res = new ModelAndView("position/administrator/create");
		try {
			final Position position = this.positionService.create();
			res.addObject("position", position);
		} catch (final Exception e) {
			res = new ModelAndView("redirect:index.do");
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam(value = "positionId", defaultValue = "-1") final int positionId) {

		ModelAndView res;
		res = new ModelAndView("position/administrator/edit");
		try {
			final Position pos = this.positionService.findOne(positionId);
			res.addObject("position", pos);
		} catch (final Exception e) {
			res = new ModelAndView("redirect:index.do");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Position position, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors()) {

			System.out.println("El error pasa por PositionAdministratorController");
			System.out.println(binding);
			res = new ModelAndView("position/administrator/create");
		} else
			try {

				System.out.println("El error pasa por el try de PosAdminController");
				System.out.println(binding);

				this.positionService.save(position);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {

				System.out.println("El error pasa por el catch de PosAdminController");
				System.out.println(binding);

				if (oops.getMessage().equals("name.error"))
					res = this.createEditModelAndView(position, "name.error");
				else
					res = this.createEditModelAndView(position, "commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "positionId", defaultValue = "-1") final int positionId) {

		ModelAndView res;

		// Control of URL ID injection
		if (this.positionService.findOne(positionId) == null)
			res = new ModelAndView("redirect:index.do");

		try {
			final Position position = this.positionService.findOne(positionId);
			this.positionService.delete(position);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {

			final Position position = this.positionService.findOne(positionId);
			res = this.createEditModelAndView(position, "commit.error");
		}

		return res;
	}

	// Other Methods:
	private ModelAndView createEditModelAndView(final Position pos) {
		return this.createEditModelAndView(pos, null);
	}

	private ModelAndView createEditModelAndView(final Position pos, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("position/administrator/create");
		result.addObject("position", pos);
		result.addObject("message", messageCode);

		return result;
	}
}
