/*
 * CustomerController.java
 *
 * Copyright (C) 2018 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.Member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Area;
import domain.Finder;
import domain.Procession;
import services.AreaService;
import services.ConfigurationService;
import services.FinderService;
import services.MemberService;
import services.ProcessionService;

@Controller
@RequestMapping("/finder/member")
public class FinderMemberController extends AbstractController {

	@Autowired
	private MemberService			memberService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private AreaService				areaService;

	@Autowired
	private ProcessionService		processionService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public FinderMemberController() {
		super();
	}

	// Update finder ---------------------------------------------------------------

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateFinder() {
		ModelAndView result;

		final Finder finder = this.finderService.findOneByPrincipal();

		result = this.createEditModelAndView(finder);

		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView updateFinder(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				this.finderService.save(finder);
				result = this.list();
			} catch (final Exception e) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}

	// List result finder ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView modelAndView = new ModelAndView("procession/list");
		final Finder finder = this.finderService.findOneByPrincipal();
		List<Procession> processions = new ArrayList<>();

		if (finder == null)
			processions = this.processionService.findProcessionsFinal();
		else if (this.finderService.checkCache(finder) == true)
			processions = this.processionService.findProcessionsFinal();
		else
			processions = finder.getProcessions();

		modelAndView.addObject("processions", processions);
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());
		modelAndView.addObject("numResults", this.configurationService.findOne().getNumResults());
		modelAndView.addObject("requestURI", "finder/member/list.do");

		return modelAndView;
	}

	//ModelAndView--------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;
		final Collection<Area> areas = this.areaService.findAll();

		result = new ModelAndView("finder/update");
		result.addObject("finder", finder);
		result.addObject("areas", areas);
		result.addObject("banner", this.configurationService.findOne().getBanner());

		return result;
	}

}
