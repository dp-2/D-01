
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Box;
import services.ActorService;
import services.BoxService;
import services.ConfigurationService;

@Controller
@RequestMapping("box/actor")
public class BoxController extends AbstractController {

	// Services

	@Autowired
	private BoxService				boxService;
	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// List

	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = false) final Integer rootId) {
		final ModelAndView res = new ModelAndView("box/list");
		final Actor actor = this.actorService.findPrincipal();
		Collection<Box> boxs = null;
		if (rootId == null) {
			boxs = this.boxService.findByActorWithoutRoot(actor);
			res.addObject("showBack", false);
			res.addObject("isPrincipalAuthorizedEdit", true);

		} else {
			final Box root = this.boxService.findOne(rootId);
			boxs = this.boxService.findByActorAndRoot(actor, root);
			if (!root.getRootBox().equals(root))
				res.addObject("backBoxId", root.getRootBox().getId());
			res.addObject("showBack", true);
			this.isPrincipalAuthorizedEdit(res, root, false);
		}
		res.addObject("boxs", boxs);
		res.addObject("banner", this.configurationService.findOne().getBanner());

		if (rootId == null)
			res.addObject("requestURI", "box/actor/list.do");
		else
			res.addObject("requestURI", "box/actor/list.do?rootId=" + new Integer(rootId).toString());
		return res;
	}

	// Create

	@SuppressWarnings("unused")
	@RequestMapping("create")
	private ModelAndView create() {
		final Actor actor = this.actorService.findPrincipal();
		final Box box = this.boxService.create(actor);
		final ModelAndView res = this.createEditModelAndView(box);
		return res;
	}

	// Edit

	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit(@RequestParam(required = true) final Integer boxId) {
		final Box box = this.boxService.findOne(boxId);
		Assert.notNull(box);
		final ModelAndView res = this.createEditModelAndView(box);
		return res;
	}

	// Save

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(Box box, final BindingResult binding) {
		ModelAndView res = null;
		box = this.boxService.deconstruct(box, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(box);
		else
			try {
				this.boxService.save(box);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable t) {
				res = this.createEditModelAndView(box, "cannot.commit.error");
			}
		return res;
	}
	// Delete

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	private ModelAndView delete(Box box, final BindingResult binding) {
		ModelAndView res = null;
		box = this.boxService.deconstruct(box, binding);
		try {
			this.boxService.delete(box);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable t) {
			res = this.createEditModelAndView(box, "cannot.commit.error");
		}
		return res;
	}

	// Ancillary methods

	private ModelAndView createEditModelAndView(final Box box) {
		return this.createEditModelAndView(box, null);
	}

	private ModelAndView createEditModelAndView(final Box box, final String message) {
		final ModelAndView res = new ModelAndView("box/edit");
		final Actor principal = this.actorService.findPrincipal();
		final Collection<Box> boxs = this.boxService.findAllByActor(principal);
		boxs.remove(box);
		res.addObject("box", box);
		res.addObject("message", message);
		res.addObject("boxs", boxs);
		res.addObject("banner", this.configurationService.findOne().getBanner());
		this.isPrincipalAuthorizedEdit(res, box, true);
		return res;
	}

	private void isPrincipalAuthorizedEdit(final ModelAndView modelAndView, final Box inputBox, final Boolean isEdit) {
		Boolean res = false;
		if (inputBox.getId() > 0) {
			final Box box = this.boxService.findOne(inputBox.getId());
			final Actor principal = this.actorService.findPrincipal();
			if (box.getActor().equals(principal) && !box.getIsSystem() && isEdit)
				res = true;
			else if (box.getActor().equals(principal) && !isEdit)
				res = true;
		} else if (inputBox.getId() == 0)
			res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}

}
