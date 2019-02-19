
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.MemberService;

@Controller
@RequestMapping("/enrolled/brotherhood")
public class MemberController extends AbstractController {

	@Autowired
	private MemberService	memberService;

}
