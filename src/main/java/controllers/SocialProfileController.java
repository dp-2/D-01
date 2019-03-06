/*
 * SocialProfileController.java
 *
 * Copyright (C) 2018 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

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

import domain.Actor;
import domain.SocialProfile;
import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import services.SocialProfileService;

@Controller
@RequestMapping("/socialProfile")
public class SocialProfileController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	//List------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelAndView;

		final Collection<SocialProfile> socialProfiles = this.socialProfileService.findAll();
		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());

		modelAndView = new ModelAndView("socialProfile/list");
		modelAndView.addObject("socialProfiles", socialProfiles);
		modelAndView.addObject("requestURI", "/socialProfile/list.do");
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());
		modelAndView.addObject("username", a.getUserAccount().getUsername());

		return modelAndView;

	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialProfile socialProfile;
		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		socialProfile = this.socialProfileService.create(a.getId());
		result = this.createEditModelAndView(socialProfile);

		return result;
	}

	//Show------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int socialProfileId) {
		final ModelAndView modelAndView = new ModelAndView("socialProfile/edit");

		final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);

		modelAndView.addObject("socialProfile", socialProfile);
		modelAndView.addObject("isRead", true);
		modelAndView.addObject("requestURI", "/socialProfile/show.do?socialProfileId=" + socialProfileId);

		return modelAndView;

	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {
		final ModelAndView result;
		SocialProfile socialProfile;
		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		socialProfile = this.socialProfileService.findOne(socialProfileId);
		Assert.notNull(socialProfile);
		result = this.createEditModelAndView(socialProfile);
		result.addObject("actorId", a.getId());
		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialProfile socialProfile, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialProfile);
		else
			try {
				this.socialProfileService.save(socialProfile);
				result = new ModelAndView("redirect:/socialProfile/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialProfile, "profile.commit.error");

			}
		return result;
	}

	// Delete----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;

		try {
			this.socialProfileService.delete(socialProfile);
			result = new ModelAndView("redirect:/socialProfile/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialProfile, "profile.commit.error");
		}
		return result;
	}

	//CreateModelAndView

	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile) {
		ModelAndView result;

		result = this.createEditModelAndView(socialProfile, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile, final String message) {
		final ModelAndView result;

		result = new ModelAndView("socialProfile/edit");
		result.addObject("socialProfile", socialProfile);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("banner", this.configurationService.findOne().getBanner());

		result.addObject("requestURI", "socialProfile/edit.do");

		return result;
	}

}
