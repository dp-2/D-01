
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Procession;
import services.ConfigurationService;
import services.ProcessionService;

@Controller
@RequestMapping("/procession")
public class ProcessionController extends AbstractController {

	//Services--------------------------------------------------------------------

	@Autowired
	private ProcessionService		processionService;

	@Autowired
	private ConfigurationService	configurationService;


	//Constructor-----------------------------------------------------------------

	public ProcessionController() {
		super();
	}

	//List of Procession all actors-----------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView modelAndView = new ModelAndView("procession/list");

		final List<Procession> processions = this.processionService.findProcessionsFinal();

		modelAndView.addObject("processions", processions);
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());
		modelAndView.addObject("requestURI", "procession/list.do");

		return modelAndView;
	}

	//List of Procession Navigation Brotherhood-----------------------------------------------
	@RequestMapping(value = "/listBrotherhood", method = RequestMethod.GET)
	public ModelAndView listBrotherhood(@RequestParam final int brotherhoodId) {
		final ModelAndView modelAndView = new ModelAndView("procession/list");

		final List<Procession> processions = this.processionService.findProcessionsFinalByBrotherhoodId(brotherhoodId);

		modelAndView.addObject("processions", processions);
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());
		modelAndView.addObject("requestURI", "procession/listBrotherhood.do");

		return modelAndView;
	}
}
