
package controllers.Member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import services.ProcessionService;
import controllers.AbstractController;
import domain.Actor;
import domain.Procession;

@Controller
@RequestMapping("/procession/member")
public class ProcessionMemberController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private ProcessionService		processionService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;


	// Constructor---------------------------------------------------------

	public ProcessionMemberController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView modelAndView = new ModelAndView("procession/list");
		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final List<Procession> processions = this.processionService.findProcessionOfMember(a.getId());

		modelAndView.addObject("processions", processions);
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());
		modelAndView.addObject("requestURI", "procession/list.do");

		return modelAndView;
	}

}
