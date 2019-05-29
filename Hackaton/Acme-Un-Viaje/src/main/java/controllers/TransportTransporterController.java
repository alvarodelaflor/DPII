
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TransportService;
import utilities.Log;
import domain.Transport;
import forms.TransportForm;

@Controller
@RequestMapping("/transport/transporter")
public class TransportTransporterController extends AbstractController {

	@Autowired
	private TransportService	transportService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<Transport> transports = this.transportService.getLoggedTransporterTransportsFromCurrentDate();
			result = new ModelAndView("transport/transporter/list");
			result.addObject("transports", transports);
			result.addObject("requestURI", "transport/transporter/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		try {
			final Collection<Transport> transports = this.transportService.getLoggedTransporterTransports();
			result = new ModelAndView("transport/transporter/listAll");
			result.addObject("transports", transports);
			result.addObject("requestURI", "/transport/transporter/listAll.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false, value = "transportId") final Integer transportId) {
		if (transportId == null)
			return new ModelAndView("redirect:/welcome/index.do");

		ModelAndView result;
		try {
			final Transport transport = this.transportService.getLoggedTransporterTransport(transportId);
			result = new ModelAndView("transport/transporter/show");
			result.addObject("transport", transport);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		try {
			final Transport t = this.transportService.create();
			result = new ModelAndView("transport/transporter/create");
			result.addObject("transport", t);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/createMultiple", method = RequestMethod.GET)
	public ModelAndView createMultiple() {
		ModelAndView result;
		try {
			result = new ModelAndView("transport/transporter/createMultiple");
			result.addObject("transportForm", new TransportForm());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/createMultiple", method = RequestMethod.POST)
	public ModelAndView createMultiplePost(final TransportForm transportForm, final BindingResult binding) {
		try {
			this.transportService.validateTransportForm(transportForm, binding);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			return new ModelAndView("redirect:/welcome/index.do");
		}

		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("transport/transporter/createMultiple");
			result.addObject("transportForm", transportForm);
		} else
			try {
				this.transportService.saveMultiple(transportForm);
				result = new ModelAndView("redirect:/transport/transporter/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editGet(@RequestParam(required = false, value = "transportId") final Integer transportId) {
		if (transportId == null)
			return new ModelAndView("redirect:/welcome/index.do");

		ModelAndView result;
		try {
			final Transport transport = this.transportService.getLoggedTransporterTransportForEdit(transportId);

			result = new ModelAndView("transport/transporter/create");
			result.addObject("transport", transport);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(final Transport transport, final BindingResult binding) {
		Transport t = null;
		try {
			t = this.transportService.reconstruct(transport, binding);
		} catch (final Throwable oops) {
			Log.log.severe("Fallo en reconstruct");
			return new ModelAndView("redirect:/welcome/index.do");
		}

		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("transport/transporter/create");
			result.addObject("transport", t);
		} else
			try {
				this.transportService.save(t);
				result = new ModelAndView("redirect:/transport/transporter/list.do");
			} catch (final Throwable oops) {
				Log.log.severe("Fallo al guardar");
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = false, value = "transportId") final Integer transportId) {
		if (transportId == null)
			return new ModelAndView("redirect:/welcome/index.do");

		ModelAndView result;
		try {
			this.transportService.delete(transportId);
			result = new ModelAndView("redirect:/transport/transporter/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
}
