
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

import security.LoginService;
import services.ActorService;
import services.DFloatService;
import domain.Brotherhood;
import domain.DFloat;

@Controller
@RequestMapping("/dfloat/brotherhood")
public class DFloatController extends AbstractController {

	//-----------------Services-------------------------
	@Autowired
	DFloatService	dfloatService;

	@Autowired
	ActorService	actorService;


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
		result.addObject("requestURI", "dfloat/handyWorker/list.do");

		return result;
	}

	//-----------------Display-------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int dfloatId) {
		ModelAndView result;
		DFloat dfloat;
		dfloat = this.dfloatService.findOne(dfloatId);
		result = new ModelAndView("dfloat/display");
		result.addObject("dfloat", dfloat);

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

		er = this.dfloatService.findOne(dfloatId);
		Assert.notNull(er);
		result = this.createEditModelAndView(er);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final DFloat dfloat, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(dfloat);
		else
			try {
				this.dfloatService.save(dfloat);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(dfloat, "dfloat.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final DFloat dfloat, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("dfloat/edit");
		result.addObject("dfloat", dfloat);
		result.addObject("message", messageCode);

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
