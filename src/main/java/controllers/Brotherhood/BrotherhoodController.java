
package controllers.Brotherhood;

import java.util.ArrayList;
import java.util.Collection;
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

import services.ActorService;
import services.BrotherhoodService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.Url;
import forms.BrotherhoodForm;

@Controller
@RequestMapping("brotherhood")
public class BrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping("any/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("brotherhood/list");
		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		res.addObject("brotherhoods", brotherhoods);
		res.addObject("banner", this.configurationService.findOne().getBanner());
		return res;
	}

	@RequestMapping("none/create")
	public ModelAndView create() {
		final Brotherhood brotherhood = this.brotherhoodService.create();
		final BrotherhoodForm brotherhoodForm = this.brotherhoodService.construct(brotherhood);
		return this.createEditModelAndView(brotherhoodForm);
	}

	@RequestMapping("brotherhood/edit")
	public ModelAndView edit() {
		final Actor principal = this.actorService.findPrincipal();
		final Brotherhood brotherhood = this.brotherhoodService.findOne(principal.getId());
		Assert.notNull(brotherhood);
		final BrotherhoodForm brotherhoodForm = this.brotherhoodService.construct(brotherhood);
		return this.createEditModelAndView(brotherhoodForm);
	}

	@RequestMapping(value = "brotherhood-none/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		ModelAndView res = null;
		this.brotherhoodService.validateForm(brotherhoodForm, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(brotherhoodForm);
		else
			try {
				final Brotherhood brotherhood = this.brotherhoodService.deconstruct(brotherhoodForm);
				this.brotherhoodService.save(brotherhood);
				res = new ModelAndView("redirect:/brotherhood/brotherhood/display.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(brotherhoodForm, "cannot.commit.error");
			}
		return res;
	}
	@RequestMapping(value = "brotherhood-none/edit", method = RequestMethod.POST, params = "addPicture")
	public ModelAndView addPicture(final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		ModelAndView res = null;
		if (brotherhoodForm.getPictures() == null)
			brotherhoodForm.setPictures(new ArrayList<Url>());
		brotherhoodForm.getPictures().add(new Url());
		res = this.createEditModelAndView(brotherhoodForm);
		return res;
	}

	@RequestMapping(value = "brotherhood-none/edit", method = RequestMethod.POST, params = "removePicture")
	public ModelAndView removePicture(final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		ModelAndView res = null;
		final List<Url> pictures = new ArrayList<Url>();
		pictures.addAll(brotherhoodForm.getPictures());
		brotherhoodForm.getPictures().remove(pictures.get(pictures.size() - 1));
		res = this.createEditModelAndView(brotherhoodForm);
		return res;
	}

	@RequestMapping("any/display")
	public ModelAndView display(@RequestParam(required = true) final Integer brotherhoodId) {
		final ModelAndView res = new ModelAndView("brotherhood/display");
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		Collection<Url> brotherhoodPictures = brotherhood.getPictures();
		if (brotherhoodPictures == null)
			brotherhoodPictures = new ArrayList<Url>();
		res.addObject("brotherhood", brotherhood);
		res.addObject("brotherhoodPictures", brotherhoodPictures);
		res.addObject("isPrincipalAuthorizedEdit", this.isPrincipalAuthorizedEdit(brotherhood));
		res.addObject("banner", this.configurationService.findOne().getBanner());
		return res;
	}

	@RequestMapping("brotherhood/display")
	public ModelAndView display() {
		final ModelAndView res = new ModelAndView("brotherhood/display");
		final Brotherhood brotherhood = (Brotherhood) this.actorService.findPrincipal();
		res.addObject("brotherhood", brotherhood);
		res.addObject("isPrincipalAuthorizedEdit", this.isPrincipalAuthorizedEdit(brotherhood));
		res.addObject("banner", this.configurationService.findOne().getBanner());
		return res;
	}

	private ModelAndView createEditModelAndView(final BrotherhoodForm brotherhoodForm) {
		return this.createEditModelAndView(brotherhoodForm, null);
	}

	private ModelAndView createEditModelAndView(final BrotherhoodForm brotherhoodForm, final String message) {
		final ModelAndView res = new ModelAndView("brotherhood/edit");
		final Boolean isPrincipalAuthorizedEdit = this.isPrincipalAuthorizedEdit(brotherhoodForm);
		res.addObject("brotherhoodForm", brotherhoodForm);
		res.addObject("message", message);
		res.addObject("banner", this.configurationService.findOne().getBanner());
		res.addObject("isPrincipalAuthorizedEdit", isPrincipalAuthorizedEdit);
		return res;
	}

	private Boolean isPrincipalAuthorizedEdit(final BrotherhoodForm brotherhoodForm) {
		Boolean res = false;
		Actor principal = null;
		try {
			principal = this.actorService.findPrincipal();
		} catch (final IllegalArgumentException e) {
			principal = null;
		}
		if (brotherhoodForm.getId() > 0)
			res = principal.getId() == brotherhoodForm.getId();
		else if (brotherhoodForm.getId() == 0)
			res = principal == null;
		return res;
	}

	private Boolean isPrincipalAuthorizedEdit(final Brotherhood brotherhood) {
		Boolean res = false;
		Actor principal = null;
		try {
			principal = this.actorService.findPrincipal();
		} catch (final IllegalArgumentException e) {
			principal = null;
		}
		if (brotherhood.getId() > 0 && principal != null)
			res = principal.getId() == brotherhood.getId();
		else if (brotherhood.getId() == 0)
			res = principal == null;
		return res;
	}
}
