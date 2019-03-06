
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.DFloat;
import services.ActorService;
import services.ConfigurationService;
import services.DFloatService;
import services.ProcessionService;

@Controller
@RequestMapping("/dfloat")
public class DFloatController extends AbstractController {

	//-----------------Services-------------------------
	@Autowired
	DFloatService					dfloatService;

	@Autowired
	ActorService					actorService;

	@Autowired
	ProcessionService				processionService;

	@Autowired
	private ConfigurationService	configurationService;


	//-------------------------- List ----------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Collection<DFloat> dfloats;

		dfloats = this.dfloatService.SearchDFloatsByBrotherhood(brotherhoodId);

		result = new ModelAndView("dfloat/list");
		result.addObject("dfloats", dfloats);
		result.addObject("requestURI", "dfloat/list.do");
		result.addObject("banner", this.configurationService.findOne().getBanner());

		return result;
	}

}
