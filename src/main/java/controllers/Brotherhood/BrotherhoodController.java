
package controllers.Brotherhood;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Brotherhood;
import forms.BrotherhoodForm;

@Controller
@RequestMapping("brotherhood")
public class BrotherhoodController extends AbstractController {

	private BrotherhoodService	brotherhoodService;


	@RequestMapping("any/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("brotherhood/list");
		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		res.addObject("brotherhoods", brotherhoods);
		return res;
	}

	@RequestMapping("none/create")
	public ModelAndView create() {
		final Brotherhood brotherhood = this.brotherhoodService.create();
		final BrotherhoodForm brotherhoodForm = this.brotherhoodService.construct(brotherhood);
		return this.createEditModelAndView(brotherhoodForm);
	}

	@RequestMapping("brotherhood/edit")
	public ModelAndView edit(@RequestParam(required = true) final Integer brotherhoodId) {
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		Assert.notNull(brotherhood);
		final BrotherhoodForm brotherhoodForm = this.brotherhoodService.construct(brotherhood);
		return this.createEditModelAndView(brotherhoodForm);
	}

	@RequestMapping(value = "none/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAsNone(final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		ModelAndView res = null;
		final Brotherhood brotherhood = this.brotherhoodService.deconstruct(brotherhoodForm, binding);
		if (binding.hasErrors())
			this.createEditModelAndView(brotherhoodForm);
		else
			try {
				this.brotherhoodService.save(brotherhood);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				this.createEditModelAndView(brotherhoodForm, "cannot.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAsBrotherhood(final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		ModelAndView res = null;
		final Brotherhood brotherhood = this.brotherhoodService.deconstruct(brotherhoodForm, binding);
		if (binding.hasErrors())
			this.createEditModelAndView(brotherhoodForm);
		else
			try {
				this.brotherhoodService.save(brotherhood);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				this.createEditModelAndView(brotherhoodForm, "cannot.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "brotherhood/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteAsBrotherhood(final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		ModelAndView res = null;
		final Brotherhood brotherhood = this.brotherhoodService.deconstruct(brotherhoodForm, binding);
		try {
			this.brotherhoodService.delete(brotherhood);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			this.createEditModelAndView(brotherhoodForm, "cannot.commit.error");
		}
		return res;
	}

	private ModelAndView createEditModelAndView(final BrotherhoodForm brotherhoodForm) {
		return this.createEditModelAndView(brotherhoodForm, null);
	}

	private ModelAndView createEditModelAndView(final BrotherhoodForm brotherhoodForm, final String message) {
		final ModelAndView res = new ModelAndView("brotherhood/edit");
		res.addObject("brotherhoodForm", brotherhoodForm);
		res.addObject("message", message);
		return res;
	}

}
