
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

import services.ActorService;
import services.BoxService;
import services.ConfigurationService;
import services.MessageService;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Message;

@Controller
@RequestMapping("message")
public class MessageController extends AbstractController {

	// Services

	@Autowired
	private MessageService			messageService;
	@Autowired
	private BoxService				boxService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;


	// List

	@RequestMapping("actor/list")
	public ModelAndView list(@RequestParam(required = true) final Integer boxId) {
		final ModelAndView res = new ModelAndView("message/list");
		final Box box = this.boxService.findOne(boxId);
		final Collection<Message> messages = this.messageService.findByBox(box);
		res.addObject("messages", messages);
		System.out.println(messages);
		res.addObject("requestURI", "message/actor/list.do");
		res.addObject("banner", this.configurationService.findOne().getBanner());
		this.isPrincipalAuthorizedEdit(res, box);
		return res;
	}

	// Create

	@SuppressWarnings("unused")
	@RequestMapping("actor/create")
	private ModelAndView create(@RequestParam(required = false) final Boolean isBroadcast) {
		final Actor principal = this.actorService.findPrincipal();
		final Box box = this.boxService.findBoxByActorAndName(principal, "notificationBox");
		final Message message = this.messageService.create(box);
		Boolean broadcast = false;
		if (isBroadcast != null)
			broadcast = isBroadcast;
		final ModelAndView res = this.createEditModelAndView(message, broadcast);
		return res;
	}

	// Edit

	@SuppressWarnings("unused")
	@RequestMapping("actor/edit")
	private ModelAndView edit(@RequestParam(required = true) final Integer messageId) {
		final Message message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		final ModelAndView res = this.createEditModelAndView(message, false);
		return res;
	}
	@SuppressWarnings("unused")
	@RequestMapping(value = "actor/edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(Message message, final BindingResult binding) {
		ModelAndView res = null;
		message = this.messageService.deconstruct(message, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(message, false);
		else
			try {
				this.messageService.save(message, false);
				res = new ModelAndView("redirect:/box/actor/list.do");
			} catch (final Throwable t) {
				res = this.createEditModelAndView(message, "cannot.commit.error", false);
			}
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "actor/edit", method = RequestMethod.POST, params = "broadcast")
	private ModelAndView broadcast(Message message, final BindingResult binding) {
		ModelAndView res = null;
		message = this.messageService.deconstruct(message, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(message, true);
		else
			try {
				this.messageService.broadcast(message);
				res = new ModelAndView("redirect:list.do?boxId=" + String.valueOf(message.getBox().getId()));
			} catch (final Throwable t) {
				res = this.createEditModelAndView(message, "cannot.commit.error", true);
			}
		return res;
	}

	// Delete

	@SuppressWarnings("unused")
	@RequestMapping(value = "actor/edit", method = RequestMethod.POST, params = "delete")
	private ModelAndView delete(Message message, final BindingResult binding) {
		ModelAndView res = null;
		message = this.messageService.deconstruct(message, binding);
		try {
			this.messageService.delete(message);
			res = new ModelAndView("redirect:/box/actor/list.do");
		} catch (final Throwable t) {
			res = this.createEditModelAndView(message, "cannot.commit.error", false);
		}
		return res;
	}

	// Ancillary methods

	private ModelAndView createEditModelAndView(final Message messageObject, final Boolean isBroadcast) {
		return this.createEditModelAndView(messageObject, null, isBroadcast);
	}

	private ModelAndView createEditModelAndView(final Message message, final String messageError, Boolean isBroadcast) {
		final ModelAndView res = new ModelAndView("message/edit");
		final Actor principal = this.actorService.findPrincipal();
		res.addObject("message", message);
		res.addObject("messageError", messageError);
		res.addObject("actors", this.actorService.findAll());
		res.addObject("boxs", this.boxService.findAllByActor(principal));
		res.addObject("notUseMessage", 0);
		res.addObject("banner", this.configurationService.findOne().getBanner());
		if (!(this.actorService.findPrincipal() instanceof Administrator))
			isBroadcast = false;
		res.addObject("isBroadcast", isBroadcast);
		this.isPrincipalAuthorizedEdit(res, message.getBox());
		return res;
	}
	private void isPrincipalAuthorizedEdit(final ModelAndView modelAndView, final Box box) {
		Boolean res = false;
		final Actor principal = this.actorService.findPrincipal();
		if (box == null)
			res = true;
		else if (box.getActor().equals(principal))
			res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}

}
