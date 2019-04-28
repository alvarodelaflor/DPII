
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.MessageService;
import services.PositionService;
import services.ProblemService;
import services.RookieService;
import services.TagService;
import domain.Actor;
import domain.Message;
import domain.Position;
import domain.Problem;
import domain.Rookie;
import domain.Tag;

@Controller
@RequestMapping("/position/company")
public class PositionCompanyController extends AbstractController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private RookieService	rookieService;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private TagService		tagService;

	@Autowired
	private ActorService	actorService;


	// List of my positions ---------------------------------------------------------------		
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
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// Showing a position I own -----------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "positionId", defaultValue = "-1") final int positionId) {
		ModelAndView result;
		try {
			final Position position = this.positionService.findOneLoggedIsOwner(positionId);
			result = new ModelAndView("position/company/show");
			result.addObject("position", position);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// Getting creation form -----------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final Position position = this.positionService.create();
			result = new ModelAndView("position/company/form");
			result.addObject("position", position);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// Creating a new position --------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(final Position position, final BindingResult binding) {
		ModelAndView result;
		Position pos = null;
		final UserAccount user = LoginService.getPrincipal();
		final Actor logged = this.actorService.getActorByUserId(user.getId());
		try {
			pos = this.positionService.reconstruct(position, binding);
		} catch (final Exception e) {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		System.out.println(binding.getAllErrors());
		if (binding.hasErrors()) {
			result = new ModelAndView("position/company/form");
			result.addObject("position", position);
		} else
			try {
				final Collection<Rookie> rookieReceiver = this.rookieService.findRookieRegardlessFinder(pos.getTitle(), pos.getSalary(), pos.getDeadline(), pos.getDescription());
				final List<Rookie> rookieReceiverList = new ArrayList<>();
				rookieReceiverList.addAll(rookieReceiver);

				final List<String> emails = new ArrayList<>();
				for (int i = 0; i < rookieReceiverList.size(); i++)
					emails.add(rookieReceiverList.get(i).getEmail());

				Message sended = this.messageService.create();
				sended.setBody("A position with your preferences have been created");
				final Collection<String> me = new ArrayList<>();
				sended.setRecipient(me);
				sended.setSubject("Notification of new position");
				sended.setMoment(LocalDate.now().toDate());

				final Tag noti = this.tagService.create();
				noti.setTag("SYSTEM");
				final Collection<Tag> tags = new ArrayList<>();
				tags.add(noti);
				sended.setTags(tags);
				for (int i = 0; i < emails.size(); i++) {
					final Actor a = this.actorService.getActorByEmailOnly(emails.get(i));
					sended = this.messageService.exchangeMessage(sended, a.getId());
				}
				sended.setSender("null");

				final List<Tag> listTag = new ArrayList<>();
				listTag.addAll(sended.getTags());
				for (int i = 0; i < listTag.size(); i++)
					if (logged.getId() == listTag.get(i).getActorId()) {
						final Integer idTag = listTag.get(i).getId();
						listTag.remove(listTag.get(i));
						logged.getMessages().remove(sended);
						this.tagService.delete(this.tagService.findOne(idTag));
					}
				sended.setTags(listTag);
				this.messageService.save(sended);

				final List<Tag> newList = new ArrayList<>();
				newList.addAll(sended.getTags());
				for (int i = 0; i < listTag.size(); i++) {
					listTag.get(i).setMessageId(sended.getId());
					final Tag save = this.tagService.save(listTag.get(i));
				}

				this.positionService.save(pos);
				result = new ModelAndView("redirect:/position/company/list.do");
			} catch (final Exception e) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
	// Creating a new position --------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(final Position position, final BindingResult binding, final RedirectAttributes redirectAttributes) {
		ModelAndView result;
		Position pos = null;
		try {
			pos = this.positionService.reconstruct(position, binding);
		} catch (final Exception e) {
			return new ModelAndView("redirect:/welcome/index.do");
		}
		if (binding.hasErrors()) {
			result = new ModelAndView("position/company/show");
			// To reset the view
			position.setStatus(false);
			result.addObject("position", position);
		} else
			try {
				this.positionService.save(pos);
				result = new ModelAndView("redirect:/position/company/list.do");
			} catch (final Exception e) {
				final int problemCount = this.problemService.getProblemCount(pos.getId());
				if (problemCount < 2) {
					result = new ModelAndView("redirect:/position/company/show.do?positionId=" + pos.getId());
					redirectAttributes.addFlashAttribute("problemCountError", true);
				} else
					result = new ModelAndView("redirect:/welcome/index.do");
			}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// Cancelling a position --------------------------------------------------
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam(value = "positionId", defaultValue = "-1") final int positionId) {
		ModelAndView result;
		try {
			this.positionService.cancel(positionId);
			result = new ModelAndView("redirect:/position/company/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// Deleting a position --------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "positionId", defaultValue = "-1") final int positionId) {
		ModelAndView result;
		try {
			this.positionService.delete(positionId);
			result = new ModelAndView("redirect:/position/company/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// Add problem view
	@RequestMapping(value = "/addProblemView", method = RequestMethod.GET)
	public ModelAndView addProblem(@RequestParam(value = "positionId", defaultValue = "-1") final int positionId) {
		ModelAndView result;
		try {
			// TODO: Make the view
			result = new ModelAndView("problem/company/addList");

			final Collection<Problem> problems = this.problemService.getFinalModeFromLoggedCompanyNotInPosition(positionId);

			result.addObject("positionId", positionId);
			result.addObject("finalMode", this.positionService.findOneLoggedIsOwner(positionId).getStatus());
			result.addObject("problems", problems);
			result.addObject("adding", true);
			result.addObject("requestURI", "position/company/addProblem.do?positionId=" + positionId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// Add problem action method
	@RequestMapping(value = "/addProblemAction", method = RequestMethod.GET)
	public ModelAndView addProblem(@RequestParam(value = "problemId", defaultValue = "-1") final int problemId, @RequestParam(value = "positionId", defaultValue = "-1") final int positionId) {
		ModelAndView result;
		try {

			this.positionService.addProblemToPosition(problemId, positionId);

			result = new ModelAndView("redirect:/position/company/problemList.do?positionId=" + positionId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
	// Detach problem action
	@RequestMapping(value = "/detachProblem", method = RequestMethod.GET)
	public ModelAndView detachProblem(@RequestParam(value = "problemId", defaultValue = "-1") final int problemId, @RequestParam(value = "positionId", defaultValue = "-1") final int positionId) {
		ModelAndView result;
		try {

			this.positionService.detachProblemFromPosition(problemId, positionId);

			result = new ModelAndView("redirect:/position/company/problemList.do?positionId=" + positionId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// List of problems view
	@RequestMapping(value = "/problemList", method = RequestMethod.GET)
	public ModelAndView problems(@RequestParam(value = "positionId", defaultValue = "-1") final int positionId) {
		ModelAndView result;
		try {
			result = new ModelAndView("problem/company/problemList");
			final Collection<Problem> problems = this.problemService.findFromPosition(positionId);

			result.addObject("problems", problems);
			result.addObject("positionId", positionId);
			result.addObject("finalMode", this.positionService.findOneLoggedIsOwner(positionId).getStatus());
			result.addObject("requestURI", "position/company/problemList.do?positionId=" + positionId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
}
