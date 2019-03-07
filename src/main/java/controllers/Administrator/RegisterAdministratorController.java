
package controllers.Administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import forms.ActorForm;

@Controller
@RequestMapping("/register/administrator")
public class RegisterAdministratorController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	//Register handyWorker
	@RequestMapping(value = "/newActor", method = RequestMethod.GET)
	public ModelAndView createHandyWorker(@RequestParam(required = false, defaultValue = "default") final String authority) {
		ModelAndView modelAndView;
		System.out.println("=====================  " + authority);
		try {
			Actor actor = null;
			// Faltan actores
			switch (authority) {
			case "ADMIN":
				actor = this.actorService.create("ADMIN");
				break;

			default:
				throw new NullPointerException();
			}

			final ActorForm actorForm = this.actorService.construct(actor);
			modelAndView = this.createEditModelAndView(actorForm);

		} catch (final Exception e) {
			modelAndView = new ModelAndView("redirect:/welcome/index.do");
		}

		return modelAndView;
	}

	//Save
	@RequestMapping(value = "/newActor", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ActorForm actorForm, final BindingResult binding) {

		ModelAndView result;

		this.actorService.validateForm(actorForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(actorForm);
		else
			try {
				final Actor actor = this.actorService.deconstruct(actorForm, binding);
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				actor.getUserAccount().setPassword(encoder.encodePassword(actor.getUserAccount().getPassword(), null));
				actor.getUserAccount().setEnabled(true);
				this.actorService.update(actor);

				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				System.out.println("=======" + oops.getMessage() + "=======");
				final Actor test = this.actorService.findActorByUsername(actorForm.getUsername());
				if (test != null)
					result = this.createEditModelAndView(actorForm, "actor.userExists");
				else
					result = this.createEditModelAndView(actorForm, "message.commit.error");
			}
		return result;
	}

	//CreateModelAndView
	protected ModelAndView createEditModelAndView(final ActorForm actorForm) {
		ModelAndView result;

		result = this.createEditModelAndView(actorForm, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final ActorForm actorForm, final String message) {
		ModelAndView result = null;

		//TODO faltan actores

		actorForm.setAuthority("ADMIN");

		if (actorForm.getAuthority().equals(Authority.ADMIN))
			result = new ModelAndView("administrator/admin");
		else
			throw new NullPointerException();

		result.addObject("actorForm", actorForm);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("isPrincipalAuthorizedEdit", this.isPrincipalAuthorizedEdit(actorForm));
		result.addObject("banner", this.configurationService.findOne().getBanner());
		return result;
	}

	private Boolean isPrincipalAuthorizedEdit(final ActorForm form) {
		Boolean res = false;
		Actor principal = null;
		try {
			principal = this.actorService.findPrincipal();
		} catch (final Throwable oops) {
			principal = null;
		}
		if (principal == null && form.getAuthority().equals(Authority.MEMBER))
			res = true;
		if (principal instanceof Administrator && form.getAuthority().equals(Authority.ADMIN))
			res = true;
		return res;
	}

}
