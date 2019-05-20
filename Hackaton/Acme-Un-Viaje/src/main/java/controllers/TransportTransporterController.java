
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TransportService;
import domain.Transport;

@Controller
@RequestMapping("/transport/transporter")
public class TransportTransporterController extends AbstractController {

	@Autowired
	private TransportService	transportService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<Transport> transports = this.transportService.getLoggedTransporterTransports();
			result = new ModelAndView("transport/transporter/list");
			result.addObject("transports", transports);
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
}
