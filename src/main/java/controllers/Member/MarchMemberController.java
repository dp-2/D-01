
package controllers.Member;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.March;
import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import services.MarchService;

@Controller
@RequestMapping("/march/member")
public class MarchMemberController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private MarchService			marchService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor---------------------------------------------------------

	public MarchMemberController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<March> marchs;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		marchs = this.marchService.findMarchsByMember(a.getId());

		result = new ModelAndView("march/list");
		result.addObject("marchs", marchs);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("requestURI", "march/member/list.do?memberId=" + a.getId());
		result.addObject("memberId", a.getId());

		return result;
	}
	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int processionId) {
		ModelAndView result;
		March march;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		march = this.marchService.create(processionId, a.getId());
		result = this.createEditModelAndView(march);
		result.addObject("memberId", a.getId());

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

		final Map<Integer, Integer> map = march.getLocation().entrySet().stream().collect(Collectors.toMap(entry -> new Integer(entry.getKey()), entry -> new Integer(entry.getValue())));

		march.setLocation(map);
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(march, "message.commit.error");
		else
			try {
				final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
				this.marchService.save(march);
				result = new ModelAndView("redirect:/march/member/list.do?=" + a.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(march, "march.commit.error");

			}
		return result;
	}

	// DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.GET, params = "delete")
	public ModelAndView delete(@RequestParam final int marchId, final BindingResult binding) {

		ModelAndView result;
		final March march = this.marchService.findOne(marchId);
		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		try {
			this.marchService.delete(march);
			result = new ModelAndView("redirect:list.do?memberId=" + a.getId());
		} catch (final Throwable oops) {
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
		result.addObject("memberId", a.getId());
		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("requestURI", "march/member/edit.do");

		return result;
	}
}
