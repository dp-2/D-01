
package controllers.Brotherhood;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import services.MarchService;
import controllers.AbstractController;
import domain.Actor;
import domain.March;

@Controller
@RequestMapping("/march/brotherhood")
public class MarchBrotherhoodController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private MarchService			marchService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor---------------------------------------------------------

	public MarchBrotherhoodController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int processionId) {
		ModelAndView result;
		Collection<March> marchs;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		marchs = this.marchService.findMarchsByProcession(processionId);
		result = new ModelAndView("march/list");
		result.addObject("marchs", marchs);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("requestURI", "march/brotherhood/list.do?processionId=" + processionId);
		result.addObject("brotherhoodId", a.getId());
		return result;
	}
	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int marchId) {
		ModelAndView result;
		March march;

		march = this.marchService.findOne(marchId);
		Assert.notNull(march);
		result = this.createEditModelAndView(march);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final March march, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(march, "message.commit.error");
		else
			try {

				this.marchService.save(march);
				final int processionId = march.getProcession().getId();
				result = new ModelAndView("redirect:list.do?processionId=" + processionId);
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("errorposition"))
					result = this.createEditModelAndView(march, "march.commit.errorposition");
				else
					result = this.createEditModelAndView(march, "march.commit.error");

			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final March march) {
		ModelAndView result;

		result = this.createEditModelAndView(march, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final March march, final String message) {
		ModelAndView result;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		result = new ModelAndView("march/edit");
		result.addObject("march", march);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("brotherhoodId", a.getId());
		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("requestURI", "march/brotherhood/edit.do");

		return result;
	}
}
