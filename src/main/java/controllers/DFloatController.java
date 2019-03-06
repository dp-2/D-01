
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.DFloatService;
import services.ProcessionService;
import domain.DFloat;

@Controller
@RequestMapping("/dfloat")
public class DFloatController extends AbstractController {

	//-----------------Services-------------------------
	@Autowired
	DFloatService		dfloatService;

	@Autowired
	ActorService		actorService;

	@Autowired
	ProcessionService	processionService;


	//-------------------------- List ----------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Collection<DFloat> dfloats;

		dfloats = this.dfloatService.SearchDFloatsByBrotherhood(brotherhoodId);

		result = new ModelAndView("dfloat/list");
		result.addObject("dfloats", dfloats);
		result.addObject("requestURI", "dfloat/list.do");

		return result;
	}

}
