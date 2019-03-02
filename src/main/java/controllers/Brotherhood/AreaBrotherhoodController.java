
package controllers.Brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.AreaService;
import controllers.AbstractController;
import domain.Area;
import domain.Brotherhood;
import domain.Url;

@Controller
@RequestMapping("/area")
public class AreaBrotherhoodController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AreaBrotherhoodController() {
		super();
	}


	//-----------------Services-------------------------

	@Autowired
	AreaService		areaService;

	@Autowired
	ActorService	actorService;


	//display creado para mostrar al member logueado
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Brotherhood actor;
		Area area;
		actor = (Brotherhood) this.actorService.findByUserAccount(LoginService.getPrincipal());

		area = this.areaService.findAreaByBrotherhoodId(actor.getId());
		final Collection<Url> pictures = area.getPictures();

		result = new ModelAndView("area/display");
		result.addObject("area", area);
		result.addObject("pictures", pictures);

		return result;
	}

	//-------------------------- List ----------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Area> areas;

		areas = this.areaService.findAll();
		result = new ModelAndView("area/list");
		result.addObject("areas", areas);
		result.addObject("requestURI", "area/list.do");

		return result;
	}
}
