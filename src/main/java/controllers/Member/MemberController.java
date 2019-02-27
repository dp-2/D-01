
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

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.MemberService;
import services.SocialProfileService;
import controllers.AbstractController;
import domain.Member;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public MemberController() {
		super();
	}


	//-----------------Services-------------------------

	@Autowired
	MemberService			memberService;

	@Autowired
	ActorService			actorService;

	@Autowired
	SocialProfileService	socialProfileService;


	//	@Autowired
	//	UserAccount				userAccountService;

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("member/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("member/action-2");

		return result;
	}

	//-----------------Display-------------------------

	//display creado para mostrar al member logueado
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Member member;

		member = (Member) this.actorService.findByUserAccount(LoginService.getPrincipal());
		result = new ModelAndView("member/edit");
		result.addObject("member", member);

		return result;
	}

	//------------------Edit---------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int memberId) {
		ModelAndView result;
		Member member;

		member = this.memberService.findOne(memberId);
		Assert.notNull(member);
		result = this.createEditModelAndView(member);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Member member, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(member);
			result.addObject("message", "member.commit.error");
		} else

			try {

				this.memberService.save(member);
				result = new ModelAndView("redirect:display.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(member, "member.commit.error");
			}

		return result;
	}

	//---------------------------create------------------------------------------------------
	// Creation
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Member member;

		member = this.memberService.create();

		result = new ModelAndView("member/create");
		result.addObject("member", member);
		return result;
	}

	//-----------------List----------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Member> members;

		members = this.memberService.findAll();

		result = new ModelAndView("member/list");
		result.addObject("requestURI", "member/list.do");
		result.addObject("members", members);

		return result;
	}

	//---------------------------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Member member) {
		ModelAndView result;

		result = this.createEditModelAndView(member, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Member member, final String messageCode) {
		final ModelAndView result;
		UserAccount userAccount = new UserAccount();
		userAccount = member.getUserAccount();

		result = new ModelAndView("member/edit");
		result.addObject("member", member);
		result.addObject("userAccount", userAccount);
		result.addObject("message", messageCode);

		return result;
	}

}
