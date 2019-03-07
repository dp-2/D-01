/*
 * WelcomeController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.WarningService;
import domain.Configuration;
import domain.Warning;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	//Service------------------------------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	public WarningService			warningService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		final Configuration configuration = this.configurationService.findOne();

		final String nameSys = configuration.getNameSys();
		final String banner = configuration.getBanner();

		final String welcomeMessage = this.configurationService.internacionalizcionWelcome();

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		final Warning warning = this.warningService.giveWarning();

		result = new ModelAndView("welcome/index");
		result.addObject("nameSys", nameSys);
		result.addObject("welcomeMessage", welcomeMessage);
		result.addObject("banner", banner);
		result.addObject("warning", warning);
		result.addObject("moment", moment);

		return result;
	}
}
