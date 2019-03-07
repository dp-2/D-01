
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.AreaService;
import services.BrotherhoodService;
import services.ConfigurationService;
import services.ProcessionService;
import domain.Brotherhood;
import domain.Procession;

@Controller
@RequestMapping("/procession")
public class ProcessionController extends AbstractController {

	//Services--------------------------------------------------------------------

	@Autowired
	private ProcessionService		processionService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private AreaService				areaService;

	@Autowired
	private BrotherhoodService		brotherhoodService;


	//Constructor-----------------------------------------------------------------

	public ProcessionController() {
		super();
	}

	//List of Procession all actors-----------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView modelAndView = new ModelAndView("procession/list");

		final SecurityContext context = SecurityContextHolder.getContext();
		Assert.notNull(context);
		final Authentication authentication = context.getAuthentication();

		if (!authentication.getPrincipal().equals("anonymousUser")) {
			final Authority authority = new Authority();
			authority.setAuthority("BROTHERHOOD");
			if (LoginService.getPrincipal().getAuthorities().contains(authority)) {
				final int brotherhoodId = this.brotherhoodService.findBrotherhoodByUserAcountId(LoginService.getPrincipal().getId()).getId();
				modelAndView.addObject("hasArea", this.areaService.findAreaByBrotherhoodId(brotherhoodId));
			}
		}

		final List<Procession> processions = this.processionService.findProcessionsFinal();

		modelAndView.addObject("processions", processions);
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());
		modelAndView.addObject("numResults", this.configurationService.findOne().getNumResults());
		modelAndView.addObject("requestURI", "procession/list.do");

		return modelAndView;
	}

	//List of Procession Navigation Brotherhood-----------------------------------------------
	@RequestMapping(value = "/listBrotherhood", method = RequestMethod.GET)
	public ModelAndView listBrotherhood(final int processionId) {
		final ModelAndView modelAndView = new ModelAndView("procession/list");

		final Brotherhood brotherhood = this.processionService.findOne(processionId).getBrotherhood();
		final List<Procession> processions = this.processionService.findProcessionsFinalByBrotherhoodId(brotherhood.getId());

		modelAndView.addObject("processions", processions);
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());
		//modelAndView.addObject("requestURI", "procession/listBrotherhood.do");

		return modelAndView;
	}

	@RequestMapping(value = "/listBrotherhoodAllUsers", method = RequestMethod.GET)
	public ModelAndView listBrotherhoodAllUsers(@RequestParam final int brotherhoodId) {
		final ModelAndView modelAndView;

		final List<Procession> processions = this.processionService.findProcessionsFinalByBrotherhoodId(brotherhoodId);

		modelAndView = new ModelAndView("procession/list");
		modelAndView.addObject("requestURI", "procession/listBrotherhoodAllUsers.do");
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());
		modelAndView.addObject("processions", processions);

		return modelAndView;
	}

}
