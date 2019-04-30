
package interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import services.ConfigurationService;

public class UserInterceptor extends HandlerInterceptorAdapter {

	private static Logger			log	= LoggerFactory.getLogger(UserInterceptor.class);

	@Autowired
	private ConfigurationService	configurationService;


	@Override
	public void postHandle(final HttpServletRequest req, final HttpServletResponse res, final Object o, final ModelAndView model) throws Exception {

		model.addObject("logo", this.getLogo());
		model.addObject("system", this.getSystem());

	}

	private String getLogo() {
		return this.configurationService.getConfiguration().getBanner();
	}

	private String getSystem() {
		return this.configurationService.getConfiguration().getSystemName();
	}

}
