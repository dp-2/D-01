
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
import domain.Configuration;
import services.ConfigurationService;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationAdministratorController extends AbstractController {

	//Service----------------------------------------------------------------

	@Autowired
	private ConfigurationService configurationService;


	//Constructor------------------------------------------------------------

	public ConfigurationAdministratorController() {
		super();
	}

	//List-------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Configuration configuration = this.configurationService.findOne();

		result = new ModelAndView("configuration/list");
		result.addObject("configuration", configuration);
		result.addObject("banner", configuration.getBanner());
		result.addObject("requestURI", "configuration/administrator/list.do");
		return result;
	}

	//Edit-------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int configurationId) {
		final ModelAndView result;

		final Configuration configuration = this.configurationService.findOne();
		Assert.notNull(configuration);

		result = this.createEditModelAndView(configuration);

		return result;
	}

	//Show-------------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int configurationId) {
		final ModelAndView result;

		final Configuration configuration = this.configurationService.findOne();
		Assert.notNull(configuration);

		result = this.createEditModelAndView(configuration);
		result.addObject("isRead", true);
		result.addObject("requestURI", "configuration/administrator/show.do?configurationId=" + configurationId);

		return result;
	}

	//Save-------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Configuration configuration, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(configuration);
		else
			try {
				this.configurationService.save(configuration);
				result = new ModelAndView("redirect:/configuration/administrator/list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("noAdmin"))
					result = this.createEditModelAndView(configuration, "conf.noAdmin");
				else
					result = this.createEditModelAndView(configuration, "conf.commint.error");
			}
		return result;
	}

	//ModelAndView-----------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Configuration configuration) {
		ModelAndView result;

		result = this.createEditModelAndView(configuration, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Configuration configuration, final String message) {
		ModelAndView result;

		result = new ModelAndView("configuration/edit");
		result.addObject("configuration", configuration);
		result.addObject("banner", configuration.getBanner());
		result.addObject("message", message);
		result.addObject("isRead", false);

		result.addObject("requestURI", "configuration/administrator/edit.do");

		return result;
	}

}
