
package controllers.Brotherhood;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Procession;
import security.LoginService;
import services.AreaService;
import services.BrotherhoodService;
import services.ConfigurationService;
import services.ProcessionService;

@Controller
@RequestMapping("/procession/brotherhood")
public class ProcessionBrotherhoodController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private ProcessionService		processionService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private AreaService				areaService;


	//Constructor---------------------------------------------------------

	public ProcessionBrotherhoodController() {
		super();
	}

	//List of all procession by principal brotherhood---------------------

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView myList() {
		final ModelAndView modelAndView = new ModelAndView("procession/list");
		final int brotherhoodId = this.brotherhoodService.findBrotherhoodByUserAcountId(LoginService.getPrincipal().getId()).getId();

		final List<Procession> processions = this.processionService.findProcessionsByBrotherhoodId(brotherhoodId);

		modelAndView.addObject("processions", processions);
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());
		modelAndView.addObject("requestURI", "procession/brotherhood/myList.do");
		modelAndView.addObject("numResults", this.configurationService.findOne().getNumResults());
		modelAndView.addObject("hasArea", this.areaService.findAreaByBrotherhoodId(brotherhoodId));
		modelAndView.addObject("brotherhoodId", brotherhoodId);

		return modelAndView;
	}

	//Create a Procession------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView modelAndView;

		final Procession procession = this.processionService.create();
		Assert.notNull(procession);

		modelAndView = this.createEditModelAndView(procession);

		return modelAndView;

	}

	//Show a Procession------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int processionId) {
		final ModelAndView modelAndView;

		final Procession procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);

		modelAndView = this.createEditModelAndView(procession);
		modelAndView.addObject("requestURI", "procession/brotherhood/show.do");
		modelAndView.addObject("isRead", true);

		return modelAndView;

	}

	//Edit a Procession------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int processionId) {
		final ModelAndView modelAndView;

		final Procession procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);

		modelAndView = this.createEditModelAndView(procession);
		modelAndView.addObject("requestURI", "procession/brotherhood/edit.do");

		return modelAndView;

	}

	//Save a Procession-----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Procession procession, final BindingResult bindingResult) {
		ModelAndView modelAndView;

		if (bindingResult.hasErrors())
			modelAndView = this.createEditModelAndView(procession);
		else
			try {

				this.processionService.save(procession);
				modelAndView = this.myList();
			} catch (final Exception e) {
				if (e.getMessage().equals("noOwner"))
					modelAndView = this.createEditModelAndView(procession, "procession.error.owner");
				else if (e.getMessage().equals("noFinal"))
					modelAndView = this.createEditModelAndView(procession, "procession.error.final");
				else if (e.getMessage().equals("noFuture"))
					modelAndView = this.createEditModelAndView(procession, "procession.error.moment");
				else
					modelAndView = this.createEditModelAndView(procession, "procession.commit.error");
			}
		return modelAndView;
	}

	//Save a Procession-----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Procession procession, final BindingResult bindingResult) {
		ModelAndView modelAndView;

		if (bindingResult.hasErrors())
			modelAndView = this.createEditModelAndView(procession);
		else
			try {
				this.processionService.delete(procession);
				modelAndView = this.myList();
			} catch (final Exception e) {
				if (e.getMessage().equals("noOwner"))
					modelAndView = this.createEditModelAndView(procession, "procession.error.owner");
				else if (e.getMessage().equals("noFinal"))
					modelAndView = this.createEditModelAndView(procession, "procession.error.final");
				else
					modelAndView = this.createEditModelAndView(procession, "procession.commit.error");
			}
		return modelAndView;
	}
	//ModelAndView-----------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Procession procession) {
		ModelAndView result;

		result = this.createEditModelAndView(procession, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Procession procession, final String message) {
		ModelAndView result;

		result = new ModelAndView("procession/edit");
		result.addObject("procession", procession);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("message", message);
		result.addObject("isRead", false);

		result.addObject("requestURI", "procession/brotherhood/edit.do");

		return result;
	}

}
