/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.AreaService;
import services.BrotherhoodService;
import services.ConfigurationService;
import services.EnrollService;
import services.FinderService;
import services.MarchService;
import services.MemberService;
import services.PositionService;
import services.ProcessionService;
import services.WarningService;
import domain.Actor;
import domain.Brotherhood;
import domain.Enroll;
import domain.Member;
import domain.Procession;
import domain.Warning;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ProcessionService		processionService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private EnrollService			enrollService;

	@Autowired
	private MarchService			marchService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private AreaService				areaService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	public WarningService			warningService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping(value = "/scores", method = RequestMethod.GET)
	public ModelAndView scores() {
		ModelAndView result;
		this.administratorService.generateAllScore();
		result = new ModelAndView("administrator/scores");
		final Collection<Actor> actors = this.actorService.findAllTypes();
		result.addObject("actors", actors);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		return result;
	}

	@RequestMapping(value = "/spammers", method = RequestMethod.GET)
	public ModelAndView spammers() {
		ModelAndView result;
		this.administratorService.generateAllSpammers();
		result = new ModelAndView("administrator/spammers");
		final Collection<Actor> actors = this.actorService.findAllTypes();
		result.addObject("actors", actors);
		result.addObject("banner", this.configurationService.findOne().getBanner());
		return result;
	}
	// Dashboard---------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView action1() throws ParseException {
		ModelAndView result;
		result = new ModelAndView("administrator/dashboard");
		Map<String, Double> statistics;

		statistics = this.positionService.computeStatistics();
		result.addObject("statistics", statistics);
		result.addObject("banner", this.configurationService.findOne().getBanner());

		//-----------------------Members per brotherhood

		final Double maxMembersPerBrotherhood = this.memberService.membersBrotherhoodStats().get("MAX");
		final Double minMembersPerBrotherhood = this.memberService.membersBrotherhoodStats().get("MIN");
		final Double avgMembersPerBrotherhood = this.memberService.membersBrotherhoodStats().get("AVG");
		final Double stdevMembersPerBrotherhood = this.memberService.membersBrotherhoodStats().get("STD");

		result.addObject("maxMembersPerBrotherhood", maxMembersPerBrotherhood);
		result.addObject("minMembersPerBrotherhood", minMembersPerBrotherhood);
		result.addObject("avgMembersPerBrotherhood", avgMembersPerBrotherhood);
		result.addObject("stdevMembersPerBrotherhood", stdevMembersPerBrotherhood);

		//--------------Largest and smallest brotherhood
		final Brotherhood largestBrotherhood = this.brotherhoodService.BrotherhoodWithMoreMembers();
		final Brotherhood smallestBrotherhood = this.brotherhoodService.BrotherhoodWithLessMembers();

		final List<Enroll> enrollsLarg = (List<Enroll>) this.enrollService.findEnrollsAprovedByBrotherhood(largestBrotherhood.getId());
		final int largestBrotherhoodNumMembers = enrollsLarg.size();

		final List<Enroll> enrollsSmall = (List<Enroll>) this.enrollService.findEnrollsAprovedByBrotherhood(smallestBrotherhood.getId());
		final int smallestBrotherhoodNumMembers = enrollsSmall.size();

		result.addObject("largestBrotherhood", largestBrotherhood);
		result.addObject("smallestBrotherhood", smallestBrotherhood);
		result.addObject("largestBrotherhoodNumMembers", largestBrotherhoodNumMembers);
		result.addObject("smallestBrotherhoodNumMembers", smallestBrotherhoodNumMembers);

		//-----------------------Position Statics
		final Double positionCountTotal = this.positionService.computeStatistics().get("count.total");
		final Double positionCountPresident = this.positionService.computeStatistics().get("count.president");
		final Double positionCountVicepresident = this.positionService.computeStatistics().get("count.vicepresident");
		final Double positionCountSecretary = this.positionService.computeStatistics().get("count.secretary");
		final Double positionCountTreasurer = this.positionService.computeStatistics().get("count.treasurer");
		final Double positionCountHistorian = this.positionService.computeStatistics().get("count.historian");
		final Double positionCountOfficer = this.positionService.computeStatistics().get("count.officer");
		final Double positionCountVocal = this.positionService.computeStatistics().get("count.vocal");

		result.addObject("positionCountTotal", positionCountTotal);
		result.addObject("positionCountPresident", positionCountPresident);
		result.addObject("positionCountVicepresident", positionCountVicepresident);
		result.addObject("positionCountSecretary", positionCountSecretary);
		result.addObject("positionCountTreasurer", positionCountTreasurer);
		result.addObject("positionCountHistorian", positionCountHistorian);
		result.addObject("positionCountOfficer", positionCountOfficer);
		result.addObject("positionCountVocal", positionCountVocal);

		//-----------------------Processions Statics
		final List<Procession> processionsIn30Days = this.processionService.findProcessionsIn30Days();
		result.addObject("processionsIn30Days", processionsIn30Days);

		//-----------------------March Statics
		final List<Member> members10RequestAccepted = this.marchService.members10PerMarchAccepted();
		final Double ratioRequestByStatusAPPROVED = this.marchService.ratioRequestByStatus().get(0);
		final Double ratioRequestByStatusPENDING = this.marchService.ratioRequestByStatus().get(1);
		final Double ratioRequestByStatusREJECTED = this.marchService.ratioRequestByStatus().get(2);
		result.addObject("members10RequestAccepted", members10RequestAccepted);
		result.addObject("ratioRequestByStatusAPPROVED", ratioRequestByStatusAPPROVED);
		result.addObject("ratioRequestByStatusPENDING", ratioRequestByStatusPENDING);
		result.addObject("ratioRequestByStatusREJECTED", ratioRequestByStatusREJECTED);

		//-----------------------Brotherhoods per area
		final Double countHermandadesPorArea = this.areaService.statsBrotherhoodPerArea().get("COUNT");
		final Double maxHermandadesPorArea = this.areaService.statsBrotherhoodPerArea().get("MAX");
		final Double minHermandadesPorArea = this.areaService.statsBrotherhoodPerArea().get("MIN");
		final Double avgHermandadesPorArea = this.areaService.statsBrotherhoodPerArea().get("AVG");
		final Double stddevHermandadesPorArea = this.areaService.statsBrotherhoodPerArea().get("STD");

		result.addObject("countHermandadesPorArea", countHermandadesPorArea);
		result.addObject("maxHermandadesPorArea", maxHermandadesPorArea);
		result.addObject("minHermandadesPorArea", minHermandadesPorArea);
		result.addObject("avgHermandadesPorArea", avgHermandadesPorArea);
		result.addObject("stddevHermandadesPorArea", stddevHermandadesPorArea);

		//-----------------------Results in finder
		final Double minResultsInFinder = this.finderService.finderStats().get(0);
		final Double maxResultsInFinder = this.finderService.finderStats().get(1);
		final Double avgResultsInFinder = this.finderService.finderStats().get(2);
		final Double stdResultsInFinder = this.finderService.finderStats().get(3);
		final Double emptyVSNonEmptyFinder = this.finderService.finderStats().get(4);
		System.out.println("--------------->" + emptyVSNonEmptyFinder);

		result.addObject("minResultsInFinder", minResultsInFinder);
		result.addObject("maxResultsInFinder", maxResultsInFinder);
		result.addObject("avgResultsInFinder", avgResultsInFinder);
		result.addObject("stdResultsInFinder", stdResultsInFinder);
		result.addObject("emptyVSNonEmptyFinder", emptyVSNonEmptyFinder);

		return result;
	}

	@RequestMapping("/adviseTrue")
	public ModelAndView adviseTrue() {
		ModelAndView result;
		Warning war = this.warningService.giveWarning();
		war = this.warningService.setWarningTrue();
		result = new ModelAndView("welcome/index");
		System.out.println("Se ha alertado de una brecha?" + war.getIsWarning());

		return result;
	}

	@RequestMapping("/adviseFalse")
	public ModelAndView adviseFalse() {
		ModelAndView result;
		Warning war = this.warningService.giveWarning();
		war = this.warningService.setWarningFalse();
		result = new ModelAndView("welcome/index");
		System.out.println("Se ha alertado de una brecha?" + war.getIsWarning());

		return result;
	}
	//
	protected ModelAndView createEditModelAndView2(final Warning war) {
		ModelAndView result;

		result = this.createEditModelAndView2(war, null);

		return result;
	}

	protected ModelAndView createEditModelAndView2(final Warning warning, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("welcome/index");
		result.addObject("warning", warning);
		result.addObject("message", messageCode);

		return result;
	}

}
