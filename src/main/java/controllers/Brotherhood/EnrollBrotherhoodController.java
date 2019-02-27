
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
import services.EnrollService;
import services.MemberService;
import controllers.AbstractController;
import domain.Actor;
import domain.Enroll;

@Controller
@RequestMapping("/enroll/brotherhood")
public class EnrollBrotherhoodController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private EnrollService	enrollService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private MemberService	memberService;


	// Constructor---------------------------------------------------------

	public EnrollBrotherhoodController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Enroll> enrolls;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		//		final Member m = this.memberService.findMemberByEnrollId(enrollId);
		enrolls = this.enrollService.findEnrollByBrotherhood(a.getId());

		result = new ModelAndView("enroll/list");
		result.addObject("enrolls", enrolls);
		result.addObject("requestURI", "/enroll/brotherhood/list.do");
		result.addObject("brotherhoodId", a.getId());
		//		result.addObject("memberId", m.getId());
		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Enroll enroll;

		enroll = this.enrollService.create();
		Assert.notNull(enroll);
		result = this.createEditModelAndView(enroll);

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int enrollId) {
		ModelAndView result;
		Enroll enroll;

		enroll = this.enrollService.findOne(enrollId);
		Assert.notNull(enroll);
		result = this.createEditModelAndView(enroll);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Enroll enroll, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(enroll, "enroll.commit.error");
		else
			try {

				this.enrollService.save(enroll);
				result = new ModelAndView("redirect:enroll/brotherhood/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(enroll, "enroll.commit.error");

			}
		return result;
	}

	// Delete----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Enroll enroll, final BindingResult binding) {
		ModelAndView result;

		try {
			this.enrollService.delete(enroll);
			result = new ModelAndView("redirect:enroll/brotherhood/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(enroll, "enroll.commit.error");
		}
		return result;
	}

	//Model and View-------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Enroll enroll) {
		ModelAndView result;

		result = this.createEditModelAndView(enroll, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Enroll enroll, final String message) {
		ModelAndView result;

		//		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		result = new ModelAndView("enroll/edit");
		result.addObject("enroll", enroll);
		result.addObject("message", message);
		result.addObject("isRead", false);
		//		result.addObject("brotherhoodId", a.getId());
		result.addObject("requestURI", "enroll/brotherhood/edit.do");

		return result;
	}
}
