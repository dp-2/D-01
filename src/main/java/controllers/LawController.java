/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;

@Controller
@RequestMapping("/law")
public class LawController extends AbstractController {

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public LawController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------

	@RequestMapping("/terminosYCondiciones")
	public ModelAndView terminosYCondiciones() {
		ModelAndView result;

		result = new ModelAndView("law/terminosYCondiciones");
		result.addObject("banner", this.configurationService.findOne().getBanner());

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/politicaCookies")
	public ModelAndView politicaCookies() {
		ModelAndView result;

		result = new ModelAndView("law/politicaCookies");
		result.addObject("banner", this.configurationService.findOne().getBanner());

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/avisoLegal")
	public ModelAndView avisoLegal() {
		ModelAndView result;

		result = new ModelAndView("law/avisoLegal");
		result.addObject("banner", this.configurationService.findOne().getBanner());

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/normativaGDPR")
	public ModelAndView normativaGDPR() {
		ModelAndView result;

		result = new ModelAndView("law/normativaGDPR");

		return result;
	}

}
