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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.CategoryService;
import services.FinderService;
import services.HandyWorkerService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Category;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Warranty;

@Controller
@RequestMapping("/finder/handy")
public class FinderMemberController extends AbstractController {

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private WarrantyService		warrantyService;

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------------------

	public FinderMemberController() {
		super();
	}

	// Update finder ---------------------------------------------------------------		

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateFinder() {
		ModelAndView result;

		final Finder finder = this.finderService.findFinder();
		Collection<Category> categories;
		//TODO Arreglar categoryTreeToPlain
		//categories = this.categoryService.categoryTreeToPlain();
		categories = this.categoryService.findAll();
		final Collection<Warranty> warranties = this.warrantyService.warrantiesFinalMode();
		final String lang = LocaleContextHolder.getLocale().getLanguage().toUpperCase();
		final Collection<String> nameCategories = new ArrayList<>();
		for (final Category category : categories)
			nameCategories.add(category.getName().get(lang));

		result = new ModelAndView("finder/handy/update");
		result.addObject("finder", finder);
		result.addObject("categories", nameCategories);
		result.addObject("warranties", warranties);
		result.addObject("lang", lang);

		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView updateFinder(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final Collection<Category> categories = this.categoryService.findAll();
			final Collection<Warranty> warranties = this.warrantyService.findAll();

			result = new ModelAndView("finder/handy/update");
			result.addObject("finder", finder);
			result.addObject("categories", categories);
			result.addObject("warranties", warranties);

		} else
			try {
				this.finderService.save(finder);
				result = new ModelAndView("redirect:listFixUpTasks.do");
			} catch (final Exception e) {
				final Collection<Category> categories = this.categoryService.findAll();
				final Collection<Warranty> warranties = this.warrantyService.findAll();
				System.out.println("====" + e.getMessage());
				result = new ModelAndView("finder/handy/update");
				result.addObject("finder", finder);
				result.addObject("categories", categories);
				result.addObject("message", "message.commit.error");
				result.addObject("warranties", warranties);
			}
		return result;
	}
	// List result finder ---------------------------------------------------------------		

	@RequestMapping("/listFixUpTasks")
	public ModelAndView listFixUpTasks() {
		ModelAndView result;
		Finder finder = this.finderService.findFinder();
		//Comprobar fecha ultima actualización
		finder = this.finderService.updateFinder(finder);
		//Obtener resultados fixuptasks de finder
		final Collection<FixUpTask> fixUpTasks = finder.getFixUpTasks();
		final String lang = LocaleContextHolder.getLocale().getLanguage().toUpperCase();
		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		result = new ModelAndView("finder/handy/listFixUpTasks");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("lang", lang);
		result.addObject("handyId", handyWorker.getId());
		result.addObject("applicationService", this.applicationService);
		result.addObject("requestURI", "finder/handy/listFixUpTasks.do");
		return result;
	}

}
