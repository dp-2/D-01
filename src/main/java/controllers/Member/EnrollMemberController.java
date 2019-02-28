
package controllers.Member;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
import services.PositionService;
import controllers.AbstractController;
import domain.Actor;
import domain.Enroll;
import domain.Member;
import domain.Position;

@Controller
@RequestMapping("/enroll/member")
public class EnrollMemberController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private EnrollService	enrollService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private PositionService	positionService;

	@Autowired
	private MemberService	memberService;


	// Constructor---------------------------------------------------------

	public EnrollMemberController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Enroll> enrolls;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		enrolls = this.enrollService.findEnrollByMember(a.getId());

		result = new ModelAndView("enroll/list");
		result.addObject("enrolls", enrolls);
		result.addObject("requestURI", "/enroll/member/list.do");
		result.addObject("memberId", a.getId());
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

	// GoOut
	@RequestMapping(value = "/goOut", method = RequestMethod.GET, params = "goOut")
	public ModelAndView goOut(@RequestParam final int enrollId, final BindingResult binding) {

		ModelAndView result;
		final Enroll enroll = this.enrollService.findOne(enrollId);
		if (binding.hasErrors())
			result = this.createEditModelAndView(enroll, "enroll.commit.error");
		else
			try {

				this.enrollService.goOut(enrollId);
				result = new ModelAndView("redirect:enroll/member/list.do");
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
		final Collection<Position> positionsES;
		final Collection<Position> positionsEN;
		final Collection<Position> positions;
		final Collection<Member> members;
		final String idioma;
		//		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		idioma = LocaleContextHolder.getLocale().getLanguage().toLowerCase();
		positionsES = this.positionService.findPositionES();
		positionsEN = this.positionService.findPositionEN();
		positions = this.positionService.findAll();
		members = this.memberService.findAll();

		result = new ModelAndView("enroll/edit");
		result.addObject("enroll", enroll);
		result.addObject("positionsES", positionsES);
		result.addObject("positionsEN", positionsEN);
		result.addObject("positions", positions);
		result.addObject("idioma", idioma);
		result.addObject("members", members);
		result.addObject("message", message);
		result.addObject("isRead", false);
		//		result.addObject("brotherhoodId", a.getId());
		result.addObject("requestURI", "enroll/brotherhood/edit.do");

		return result;
	}
}
