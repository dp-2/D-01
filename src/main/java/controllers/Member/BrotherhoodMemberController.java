
package controllers.Member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import services.EnrollService;
import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;

@Controller
@RequestMapping("/brotherhood/member")
public class BrotherhoodMemberController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private EnrollService			enrollService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor---------------------------------------------------------

	public BrotherhoodMemberController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView myList() {
		ModelAndView result;
		final Collection<Brotherhood> brotherhoods;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		brotherhoods = this.enrollService.findBrotherhoodByMemberId(a.getId());

		result = new ModelAndView("brotherhood/listMyBrotherhood");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("requestURI", "brotherhood/member/myList.do");
		result.addObject("memberId", a.getId());
		return result;
	}
}
