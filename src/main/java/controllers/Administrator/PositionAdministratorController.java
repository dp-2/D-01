
package controllers.Administrator;

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

import controllers.AbstractController;
import domain.Position;
import services.ConfigurationService;
import services.PositionService;

@Controller
@RequestMapping("/position/administrator")
public class PositionAdministratorController extends AbstractController {

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ConfigurationService	configurationService;


	//List------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelAndView;

		final Collection<Position> positions = this.positionService.findAll();

		modelAndView = new ModelAndView("position/list");
		modelAndView.addObject("positions", positions);
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());
		modelAndView.addObject("requestURI", "/position/administrator/list.do");

		return modelAndView;

	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Position position;
		position = this.positionService.create();
		result = this.createEditModelAndView(position);

		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionId) {
		final ModelAndView result;
		Position position;
		position = this.positionService.findOne(positionId);
		Assert.notNull(position);
		result = this.createEditModelAndView(position);
		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Position position, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(position);
		else
			try {
				this.positionService.save(position);
				result = new ModelAndView("redirect:/position/administrator/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(position, "position.commit.error");

			}
		return result;
	}

	// Delete----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Position position, final BindingResult binding) {
		ModelAndView result;

		try {
			this.positionService.delete(position);
			result = new ModelAndView("redirect:/position/administrator/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(position, "position.commit.error");
		}
		return result;
	}

	//CreateModelAndView

	protected ModelAndView createEditModelAndView(final Position position) {
		ModelAndView result;

		result = this.createEditModelAndView(position, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Position position, final String message) {
		final ModelAndView result;

		//		final String s = LocaleContextHolder.getLocale().getLanguage().toLowerCase();
		//		final Collection<Position> lista = this.positionService.findPositionByLanguage(s);

		result = new ModelAndView("position/edit");
		result.addObject("position", position);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		//		result.addObject("position", lista);

		result.addObject("requestURI", "position/administrator/edit.do");

		return result;
	}
}
