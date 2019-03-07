
package controllers.Administrator;

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
import domain.Area;
import services.AreaService;
import services.ConfigurationService;

@Controller
@RequestMapping("/area/administrator")
public class AreaAdministratorController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private AreaService				areaService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor---------------------------------------------------------

	public AreaAdministratorController() {
		super();
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Area area;

		area = this.areaService.create();
		result = this.createEditModelAndView(area);

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int areaId) {
		ModelAndView result;
		Area area;

		area = this.areaService.findOne(areaId);
		Assert.notNull(area);
		result = this.createEditModelAndView(area);
		result.addObject("requestURI", "area/administrator/edit.do");
		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Area area, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(area, "area.commit.error");
		else
			try {

				this.areaService.save(area);
				result = new ModelAndView("redirect:/area/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(area, "area.commit.error");

			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Area area, final BindingResult bindingResult) {
		ModelAndView modelAndView;

		if (bindingResult.hasErrors())
			modelAndView = this.createEditModelAndView(area);
		else
			try {
				this.areaService.delete(area);
				modelAndView = new ModelAndView("redirect:/area/list.do");
			} catch (final Throwable oops) {
				modelAndView = this.createEditModelAndView(area, "area.commit.error");

			}
		return modelAndView;
	}

	protected ModelAndView createEditModelAndView(final Area area) {
		ModelAndView result;

		result = this.createEditModelAndView(area, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Area area, final String message) {
		ModelAndView result;

		result = new ModelAndView("area/edit");
		result.addObject("area", area);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("banner", this.configurationService.findOne().getBanner());

		result.addObject("requestURI", "area/administrator/edit.do");

		return result;
	}
}
