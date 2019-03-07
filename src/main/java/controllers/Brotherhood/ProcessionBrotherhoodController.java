
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

import security.LoginService;
import services.ActorService;
import services.AreaService;
import services.BrotherhoodService;
import services.ConfigurationService;
import services.DFloatService;
import services.ProcessionService;
import controllers.AbstractController;
import domain.Actor;
import domain.DFloat;
import domain.Procession;
import forms.ProcessionFloatForm;

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

	@Autowired
	private DFloatService			dFloatService;

	@Autowired
	private ActorService			actorService;


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

	@RequestMapping("addFloat")
	public ModelAndView addFloatEdit(@RequestParam(required = true) final Integer processionId) {
		ModelAndView res = null;
		final Procession procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);
		res = this.createAddFloatModelAndView(procession, null);
		return res;
	}

	@RequestMapping("removeFloat")
	public ModelAndView removeFloatEdit(@RequestParam(required = true) final Integer processionId) {
		ModelAndView res = null;
		final Procession procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);
		res = this.createRemoveFloatModelAndView(procession, null);
		return res;
	}

	@RequestMapping(value = "addFloat", method = RequestMethod.POST, params = "add")
	public ModelAndView addFloatSave(@Valid final ProcessionFloatForm processionFloatForm, final BindingResult binding) {
		ModelAndView res = null;
		try {
			final DFloat dFloat = processionFloatForm.getDFloat();
			final Procession procession = processionFloatForm.getProcession();
			if (!(dFloat.getProcessions() == null))
				dFloat.setProcessions(new ArrayList<Procession>());
			if (!dFloat.getProcessions().contains(procession)) {
				final Collection<Procession> processions = new ArrayList<Procession>();
				processions.addAll(dFloat.getProcessions());
				processions.add(procession);
				dFloat.setProcessions(processions);
			}
			this.dFloatService.save(dFloat);
			res = new ModelAndView("redirect:/procession/list.do");
		} catch (final Throwable oops) {
			res = this.createAddFloatModelAndView(processionFloatForm.getProcession(), "cannot.commit.error");
		}
		return res;
	}
	@RequestMapping(value = "removeFloat", method = RequestMethod.POST, params = "remove")
	public ModelAndView removeFloatSave(@Valid final ProcessionFloatForm processionFloatForm, final BindingResult binding) {
		ModelAndView res = null;
		try {
			final DFloat dFloat = processionFloatForm.getDFloat();
			final Procession procession = processionFloatForm.getProcession();
			if (dFloat.getProcessions().contains(procession)) {
				final Collection<Procession> processions = new ArrayList<Procession>();
				processions.addAll(dFloat.getProcessions());
				processions.remove(procession);
				dFloat.setProcessions(processions);
			}
			this.dFloatService.save(dFloat);
			res = new ModelAndView("redirect:/procession/list.do");
		} catch (final Throwable oops) {
			res = this.createAddFloatModelAndView(processionFloatForm.getProcession(), "cannot.commit.error");
		}
		return res;
	}

	protected ModelAndView createAddFloatModelAndView(final Procession procession, final String message) {
		ModelAndView result;

		final ProcessionFloatForm processionFloatForm = new ProcessionFloatForm();
		processionFloatForm.setProcession(procession);

		result = new ModelAndView("procession/add");
		result.addObject("procession", procession);
		result.addObject("processionFloatForm", processionFloatForm);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "procession/brotherhood/addFloat.do");
		result.addObject("isPrincipalAuthorizedEdit", this.isPrincipalAuthorizedEdit(procession));
		Actor principal = null;
		try {
			principal = this.actorService.findPrincipal();
		} catch (final Throwable oops) {
			principal = null;
		}
		result.addObject("floatsForAdd", this.dFloatService.searchFloatNotInProcessionByIdByActorId(procession, principal));
		return result;
	}
	protected ModelAndView createRemoveFloatModelAndView(final Procession procession, final String message) {
		ModelAndView result;

		final ProcessionFloatForm processionFloatForm = new ProcessionFloatForm();
		processionFloatForm.setProcession(procession);

		result = new ModelAndView("procession/remove");
		result.addObject("procession", procession);
		result.addObject("processionFloatForm", processionFloatForm);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "procession/brotherhood/addFloat.do");
		result.addObject("isPrincipalAuthorizedEdit", this.isPrincipalAuthorizedEdit(procession));
		Actor principal = null;
		try {
			principal = this.actorService.findPrincipal();
		} catch (final Throwable oops) {
			principal = null;
		}
		result.addObject("floatsForRemove", this.dFloatService.searchFloatInProcessionByIdByActorId(procession, principal));
		return result;
	}

	private Boolean isPrincipalAuthorizedEdit(final Procession procession) {
		Boolean res = false;
		Actor principal = null;
		try {
			principal = this.actorService.findPrincipal();
		} catch (final Throwable oops) {
			principal = null;
		}
		if (principal.equals(procession.getBrotherhood()) && !procession.isFfinal())
			res = true;
		return res;
	}

}
