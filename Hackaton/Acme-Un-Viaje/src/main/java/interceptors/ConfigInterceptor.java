
package interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import services.ConfigService;

public class ConfigInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ConfigService	configService;


	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
		// super.postHandle(request, response, handler, modelAndView);

		modelAndView.addObject("bannerLogo", this.configService.getBannerLogo());
	}

}
