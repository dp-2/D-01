
package controllers.Member;

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

import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.Enroll;
import domain.Member;
import domain.Position;
import security.LoginService;
import services.ActorService;
import services.BrotherhoodService;
import services.ConfigurationService;
import services.EnrollService;
import services.MemberService;
import services.PositionService;

@Controller
@RequestMapping("/enroll/member")
public class EnrollMemberController extends AbstractController {

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
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ConfigurationService	configurationService;


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
		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("requestURI", "enroll/member/list.do");
		result.addObject("memberId", a.getId());
		return result;
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/listBrotherhood", method = RequestMethod.GET)
	public ModelAndView listBrotherhood() {
		ModelAndView result;
		final Collection<Brotherhood> brotherhoods, finalBrotherhoods;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final int memberId = a.getId();
		finalBrotherhoods = this.brotherhoodService.findAll();
		brotherhoods = this.enrollService.findBrotherhoodByMember(memberId);
		finalBrotherhoods.removeAll(brotherhoods);

		result = new ModelAndView("enroll/listBrotherhood");
		result.addObject("enrollBrotherhoods", finalBrotherhoods);
		result.addObject("requestURI", "enroll/member/listBrotherhood.do");
		result.addObject("memberId", memberId);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Enroll enroll;

		enroll = this.enrollService.create(brotherhoodId);
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
		enroll.setStatus("PENDING");
		if (binding.hasErrors()) {
			System.out.println(enroll.getStatus());
			result = this.createEditModelAndView(enroll, "enroll.commit.error");
		} else
			try {

				this.enrollService.save(enroll);
				result = this.list();
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(enroll, "enroll.commit.error");
				System.out.println(oops.getMessage());
			}
		return result;
	}
	// GoOut
	@RequestMapping(value = "/goOut", method = RequestMethod.GET)
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
			result = new ModelAndView("redirect:enroll/member/list.do");
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
		positions = this.positionService.findAll();
		members = this.memberService.findAll();

		result = new ModelAndView("enroll/edit");
		result.addObject("enroll", enroll);
		result.addObject("positions", positions);
		result.addObject("members", members);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "enroll/member/edit.do");
		result.addObject("banner", this.configurationService.findOne().getBanner());

		return result;
	}
}
