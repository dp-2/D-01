
package controllers.Brotherhood;

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
import services.ActorService;
import services.ConfigurationService;
import services.DFloatService;
import services.ProcessionService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.DFloat;
import domain.Procession;

@Controller
@RequestMapping("/dfloat/brotherhood")
public class DFloatBrotherhoodController extends AbstractController {

	//-----------------Services-------------------------
	@Autowired
	DFloatService			dfloatService;

	@Autowired
	ActorService			actorService;

	@Autowired
	ProcessionService		processionService;

	@Autowired
	ConfigurationService	configurationService;


	//-------------------------- List ----------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<DFloat> dfloats;
		Brotherhood brotherhood;

		brotherhood = (Brotherhood) this.actorService.findActorByUsername(LoginService.getPrincipal().getUsername());

		dfloats = this.dfloatService.findAllDFloatsByBrotherhood(brotherhood);

		result = new ModelAndView("dfloat/list");
		result.addObject("dfloats", dfloats);
		result.addObject("requestURI", "dfloat/brotherhood/list.do");
		result.addObject("banner", this.configurationService.findOne().getBanner());

		return result;
	}

	//-----------------Display-------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int dfloatId) {
		ModelAndView result;
		DFloat dfloat;
		Collection<Procession> myProcessions;
		dfloat = this.dfloatService.findOne(dfloatId);
		myProcessions = dfloat.getProcessions();
		result = new ModelAndView("dfloat/display");
		result.addObject("dfloat", dfloat);
		result.addObject("myProcessions", myProcessions);
		result.addObject("banner", this.configurationService.findOne().getBanner());

		return result;
	}

	//------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		DFloat er;
		er = this.dfloatService.create();
		result = this.createEditModelAndView(er);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int dfloatId) {
		ModelAndView result;
		DFloat er;

		Collection<Procession> allProcessions;
		final Collection<Procession> myProcessions;

		er = this.dfloatService.findOne(dfloatId);
		Assert.notNull(er);
		final Brotherhood br = er.getBrotherhood();
		allProcessions = this.processionService.findProcessionsByBrotherhoodId(br.getId());
		myProcessions = er.getProcessions();

		result = this.createEditModelAndView(er);
		result.addObject("allProcessions", allProcessions);
		result.addObject("myProcessions", myProcessions);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final DFloat dfloat, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(dfloat);
			System.out.println(binding.getAllErrors());
		} else
			try {
				this.dfloatService.save(dfloat);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(dfloat, "dfloat.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final DFloat dfloat, final String message) {
		final ModelAndView result;

		result = new ModelAndView("dfloat/edit");

		result.addObject("dfloat", dfloat);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("message", message);
		result.addObject("requestURI", "dfloat/brotherhood/edit.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final DFloat dfloat) {
		ModelAndView result;

		result = this.createEditModelAndView(dfloat, null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final DFloat dfloat, final BindingResult binding) {
		ModelAndView result;
		try {
			this.dfloatService.delete(dfloat);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(dfloat, "dfloat.commit.error");

		}
		return result;
	}

}
