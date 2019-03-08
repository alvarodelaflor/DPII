
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.BrotherhoodService;
import services.MemberService;
import services.MessageService;
import services.WelcomeService;
import domain.Brotherhood;
import domain.Member;
import domain.Message;

@Controller
@RequestMapping("/brotherhood/member")
public class BrotherhoodMemberController extends AbstractController {

	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private MessageService		messageService;
	@Autowired
	private MemberService		memberService;
	@Autowired
	private WelcomeService welcomeService;


	// HIPONA 25-02-19 9:48
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Brotherhood> memberActiveBrotherhoods;
		final Collection<Brotherhood> memberInactiveBrotherhoods;
		try {
			memberActiveBrotherhoods = this.brotherhoodService.findActiveFromLoggedMember();
			memberInactiveBrotherhoods = this.brotherhoodService.findInactiveFromLoggedMember();

			result = new ModelAndView("brotherhood/memberlist");
			result.addObject("memberActiveBrotherhoods", memberActiveBrotherhoods);
			result.addObject("memberInactiveBrotherhoods", memberInactiveBrotherhoods);
			result.addObject("requestURI", "/brotherhood/member/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/drop", method = RequestMethod.GET)
	public ModelAndView drop(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		final UserAccount user = LoginService.getPrincipal();
		final Member memberLogged = this.memberService.getMemberByUserAccountId(user.getId());
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		try {
			this.brotherhoodService.dropLogged(brotherhoodId);
			final Message msg = this.messageService.create();
			msg.setBody("El miembro " + memberLogged.getName() + " ha cancelado su inscripción a la hermandad");
			msg.setSubject("Notifación sobre cancelación de inscripción");
			final Collection<String> emails = new ArrayList<>();
			emails.add(brotherhood.getEmail());
			msg.setEmailReceiver(emails);
			final Message notification = this.messageService.sendNotification(msg, brotherhood);
			this.messageService.save(notification);
			result = new ModelAndView("redirect:/brotherhood/member/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
}
