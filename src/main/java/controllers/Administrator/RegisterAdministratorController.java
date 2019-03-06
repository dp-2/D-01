
package controllers.Administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import security.Authority;
import security.UserAccount;
import services.ActorService;
import services.ConfigurationService;

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

			modelAndView = this.createEditModelAndView(actor);

		} catch (final Exception e) {
			modelAndView = new ModelAndView("redirect:/welcome/index.do");
		}

		return modelAndView;
	}

	//Save
	@RequestMapping(value = "/newActor", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Actor actor, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				final UserAccount userAccount = actor.getUserAccount();

				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				userAccount.setPassword(encoder.encodePassword(userAccount.getPassword(), null));
				userAccount.setEnabled(true);
				actor.setUserAccount(userAccount);
				this.actorService.update(actor);

				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				System.out.println("=======" + oops.getMessage() + "=======");
				final Actor test = this.actorService.findActorByUsername(actor.getUserAccount().getUsername());
				if (test != null)
					result = this.createEditModelAndView(actor, "actor.userExists");
				else
					result = this.createEditModelAndView(actor, "message.commit.error");
			}
		return result;
	}

	//CreateModelAndView
	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result = null;

		//TODO faltan actores
		final Collection<Authority> authorities = actor.getUserAccount().getAuthorities();
		final Authority admin = new Authority();
		admin.setAuthority("ADMIN");

		if (authorities.contains(admin))
			result = new ModelAndView("administrator/admin");
		else
			throw new NullPointerException();

		result.addObject("actor", actor);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		return result;
	}

}
