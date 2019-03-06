/*
 * ProfileController.java
 *
 * Copyright (C) 2018 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import security.LoginService;
import services.ActorService;
import services.ConfigurationService;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		Assert.notNull(a);

		result = this.createEditModelAndView(a);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Actor actor, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				this.actorService.update(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actor, "actor.commit.error");

			}
		return result;
	}

	// CreateModelAndView

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "actor/edit.do");
		result.addObject("banner", this.configurationService.findOne().getBanner());

		return result;
	}

}
