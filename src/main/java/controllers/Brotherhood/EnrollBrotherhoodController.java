
package controllers.Brotherhood;

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

import controllers.AbstractController;
import domain.Actor;
import domain.Enroll;
import domain.Member;
import domain.Position;
import security.LoginService;
import services.ActorService;
import services.AreaService;
import services.ConfigurationService;
import services.EnrollService;
import services.MemberService;
import services.PositionService;

@Controller
@RequestMapping("/enroll/brotherhood")
public class EnrollBrotherhoodController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private EnrollService			enrollService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private AreaService				areaService;

	@Autowired
	private ConfigurationService	configurationService;


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
		//		hasArea = this.areaService.findAreaByBrotherhoodId(a.getId());

		result = new ModelAndView("enroll/list");
		result.addObject("enrolls", enrolls);
		result.addObject("areaService", this.areaService);
		result.addObject("requestURI", "enroll/brotherhood/list.do");
		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("brotherhoodId", a.getId());
		//		result.addObject("memberId", m.getId());
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
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(enroll, "enroll.commit.error");
				System.out.println(oops.getMessage());

			}
		return result;
	}

	// kickOut
	@RequestMapping(value = "/kickOut", method = RequestMethod.GET)
	public ModelAndView goOut(@RequestParam final int enrollId) {

		ModelAndView result;
		this.enrollService.goOut(enrollId);
		result = this.list();

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
		final Collection<Position> positions;
		final Collection<Member> members;
		final String idioma;
		//		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		idioma = LocaleContextHolder.getLocale().getLanguage().toLowerCase();
		positions = this.positionService.findAll();
		members = this.memberService.findAll();

		result = new ModelAndView("enroll/edit");
		result.addObject("enroll", enroll);
		result.addObject("positions", positions);
		result.addObject("idioma", idioma);
		result.addObject("members", members);
		result.addObject("message", message);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("isRead", false);
		//		result.addObject("brotherhoodId", a.getId());
		result.addObject("requestURI", "enroll/brotherhood/edit.do");

		return result;
	}
}
