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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.MessageService;
import services.PositionAuxService;
import services.PositionService;
import services.RequestService;
import auxiliar.PositionAux;
import domain.Brotherhood;
import domain.Message;
import domain.Position;
import domain.Procession;
import domain.Request;

/*
 * CONTROL DE CAMBIOS RequestBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 19:18 CREACIÓN DE LA CLASE
 */

@Controller
@RequestMapping("/request/brotherhood")
public class RequestBrotherhoodController extends AbstractController {

	@Autowired
	private RequestService		requestService;
	@Autowired
	private PositionService		positionService;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private PositionAuxService	positionAuxService;
	@Autowired
	private MessageService		messageService;


	// Constructors -----------------------------------------------------------

	public RequestBrotherhoodController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "requestId", defaultValue = "-1") final int requestId) {
		ModelAndView result;
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		final List<Procession> listProcessions = new ArrayList<>(brotherhood.getProcessions());
		final Collection<Request> requestsAccepted = new ArrayList<>();
		final Collection<Request> requestsRejected = new ArrayList<>();
		final Collection<Request> requestsPending = new ArrayList<>();
		for (final Procession procession : listProcessions) {
			requestsAccepted.addAll(this.requestService.findAllByProcessionAccepted(procession));
			requestsRejected.addAll(this.requestService.findAllByProcessionRejected(procession));
			requestsPending.addAll(this.requestService.findAllByProcessionPending(procession));
			System.out.println(requestsAccepted);
			System.out.println(requestsPending);
			System.out.println(requestsRejected);
		}
		result = new ModelAndView("request/brotherhood/list");
		result.addObject("requestsAccepted", requestsAccepted);
		result.addObject("requestsRejected", requestsRejected);
		result.addObject("requestsPending", requestsPending);
		result.addObject("requestURI", "request/brotherhood/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "requestId", defaultValue = "-1") final int requestId) {
		ModelAndView result;
		final Request request = this.requestService.findOne(requestId);

		if (this.requestService.findOne(requestId) == null || LoginService.getPrincipal().getId() != request.getPositionAux().getProcession().getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(request, "request.nul");

			result = new ModelAndView("request/brotherhood/show");
			result.addObject("request", request);
			result.addObject("requestURI", "request/brotherhood/show.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id", defaultValue = "-1") final int requestId) {
		ModelAndView result;
		Request request;
		request = this.requestService.findOne(requestId);
		if (this.requestService.findOne(requestId) == null || LoginService.getPrincipal().getId() != request.getPositionAux().getProcession().getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(request);
			result = this.createEditModelAndView(request);
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int requestId) {
		ModelAndView result;

		final Request request = this.requestService.findOne(requestId);
		System.out.println("Request encontrado: " + request);
		if (this.requestService.findOne(requestId) == null || LoginService.getPrincipal().getId() != request.getPositionAux().getProcession().getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(request, "request.null");

			try {
				this.requestService.delete(request);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(request, "request.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Request request, final BindingResult binding) {
		ModelAndView result;
		System.out.println("Comprobación del status en controller");
		System.out.println(request.getStatus());
		final Request noti = this.requestService.findOne(request.getId());
		System.out.println(noti.getStatus());

		request = this.requestService.reconstruct(request, binding);
		System.out.println("Comprobación del status en controller");
		System.out.println(request.getStatus());
		final Request notir = this.requestService.findOne(request.getId());
		System.out.println(notir.getStatus());
		if (binding.hasErrors()) {
			System.out.println("El error pasa por aquí alvaro (IF de save())");
			System.out.println(binding);
			result = this.createEditModelAndView(request);
		} else
			try {
				System.out.println("El error pasa por aquí alvaro (TRY de save())");
				System.out.println(binding);
				if (request.getStatus().equals(false))
					request.getPositionAux().setStatus(false);
				else if (request.getStatus().equals(true))
					request.getPositionAux().setStatus(true);
				this.positionAuxService.save(request.getPositionAux());
				this.requestService.save(request);
				if (request.getStatus() == true || request.getStatus() == false) {
					String estado = null;
					if (request.getStatus() == true)
						estado = "aceptada";
					else
						estado = "rechazada";

					final Message msg = this.messageService.create();
					msg.setBody("Su petición a la procesión " + request.getPositionAux().getProcession().getTitle() + " ha sido " + estado);
					msg.setSubject("Notifación sobre cambio de estado de petición");
					final Collection<String> emails = new ArrayList<>();
					emails.add(request.getMember().getEmail());
					msg.setEmailReceiver(emails);
					final Message notification = this.messageService.sendNotification(msg, request.getMember());
					this.messageService.save(notification);
				}
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("El error: ");
				System.out.println(oops);
				System.out.println(binding);
				if (oops.getMessage().equals("request.wrongDate"))
					result = this.createEditModelAndView(request, "request.wrongDate");
				else if (oops.getMessage().equals("request.wrongMomentDate"))
					result = this.createEditModelAndView(request, "request.wrongMomentDate");
				else
					result = this.createEditModelAndView(request, "request.commit.error");
			}
		return result;
	}
	private ModelAndView createEditModelAndView(final Request request) {
		ModelAndView result;

		result = new ModelAndView("request/brotherhood/edit");

		final Collection<PositionAux> positionsAux = this.positionAuxService.findFreePositionByProcesion(request.getPositionAux().getProcession().getId());
		final String actuallanguage = LocaleContextHolder.getLocale().getDisplayLanguage();
		Boolean language;
		if (actuallanguage.equals("English")) {
			System.out.println("Actual languge: " + actuallanguage);
			language = true;
		} else {
			System.out.println("Actual languge: " + actuallanguage);
			language = false;
		}

		result.addObject("language", language);
		result.addObject("request", request);
		result.addObject("positionsAux", positionsAux);

		return result;
	}
	private ModelAndView createEditModelAndView(final Request request, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("request/brotherhood/edit");

		final Collection<Position> positions = this.positionService.findAll();

		result.addObject("request", request);
		result.addObject("positions", positions);
		result.addObject("message", messageCode);

		return result;
	}

}
