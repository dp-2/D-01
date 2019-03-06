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
import services.BrotherhoodService;
import services.EnrollService;
import services.MarchService;
import services.MemberService;
import services.PositionService;
import services.ProcessionService;
import domain.Actor;
import domain.Brotherhood;
import domain.Enroll;
import domain.Member;
import domain.Procession;

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

		final List<Enroll> enrollsSmall = (List<Enroll>) this.enrollService.findEnrollsAprovedByBrotherhood(largestBrotherhood.getId());
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

		result.addObject("positionCountTotal", positionCountTotal);
		result.addObject("positionCountPresident", positionCountPresident);
		result.addObject("positionCountVicepresident", positionCountVicepresident);
		result.addObject("positionCountSecretary", positionCountSecretary);
		result.addObject("positionCountTreasurer", positionCountTreasurer);
		result.addObject("positionCountHistorian", positionCountHistorian);
		result.addObject("positionCountOfficer", positionCountOfficer);

		//-----------------------Processions Statics
		final List<Procession> processionsIn30Days = this.processionService.findProcessionsIn30Days();
		result.addObject("processionsIn30Days", processionsIn30Days);

		//-----------------------March Statics
		final List<Member> members10RequestAccepted = this.marchService.members10PerMarchAccepted();
		result.addObject("members10RequestAccepted", members10RequestAccepted);

		return result;
	}
	//	@RequestMapping(value = "/administrator/dashboard", method = RequestMethod.GET)
	//	public ModelAndView dashboard() {
	//
	//		final ModelAndView result = new ModelAndView("administrator/dashboard");
	//
	//		//----------------------------------
	//
	//		//		final Double maxCuestionariosPerCustomer = this.cuestionarioService.cuestionariosStats().get("MAX");
	//		//		final Double minCuestionariosPerCustomer = this.cuestionarioService.cuestionariosStats().get("MIN");
	//		//		final Double avgCuestionariosPerCustomer = this.cuestionarioService.cuestionariosStats().get("AVG");
	//		//		final Double stdCuestionariosPerCustomer = this.cuestionarioService.cuestionariosStats().get("STD");
	//		//		final Double ratioPublishedCuestionarios = this.cuestionarioService.ratioPublishedCuestionarios();
	//		//		final Double ratioUnpublishedCuestionarios = this.cuestionarioService.ratioUnpublishedCuestionarios();
	//		//
	//		//		final Double maxFixupTaskPerUser = this.actorService.fixupTasksStats().get("MAX");
	//		//		final Double minFixupTaskPerUser = this.actorService.fixupTasksStats().get("MIN");
	//		//		final Double avgFixupTaskPerUser = this.actorService.fixupTasksStats().get("AVG");
	//		//		final Double stdFixupTaskPerUser = this.actorService.fixupTasksStats().get("STD");
	//		//
	//		//		final Double maxApplicationsPerFixupTask = this.fixupTaskService.appsStats().get("MAX");
	//		//		final Double minApplicationsPerFixupTask = this.fixupTaskService.appsStats().get("MIN");
	//		//		final Double avgApplicationsPerFixupTask = this.fixupTaskService.appsStats().get("AVG");
	//		//		final Double stdApplicationsPerFixupTask = this.fixupTaskService.appsStats().get("STD");
	//		//
	//		//		final Double maxMaximumPriceOfFixupTasks = this.fixupTaskService.maxFixupStaskStats().get("MAX");
	//		//		final Double minMaximumPriceOfFixupTasks = this.fixupTaskService.maxFixupStaskStats().get("MIN");
	//		//		final Double avgMaximumPriceOfFixupTasks = this.fixupTaskService.maxFixupStaskStats().get("AVG");
	//		//		final Double stdMaximumPriceOfFixupTasks = this.fixupTaskService.maxFixupStaskStats().get("STD");
	//		//
	//		//		final Double maxPriceOfApplications = this.applicationService.applicationPriceStats().get("MAX");
	//		//		final Double minPriceOfApplications = this.applicationService.applicationPriceStats().get("MIN");
	//		//		final Double avgPriceOfApplications = this.applicationService.applicationPriceStats().get("AVG");
	//		//		final Double stdPriceOfApplications = this.applicationService.applicationPriceStats().get("STD");
	//		//
	//		//		final Double ratioPendingApplications = this.applicationService.pendingRatio().get("Ratio");
	//		//		final Double ratioAcceptedApplications = this.applicationService.acceptedRatio().get("Ratio");
	//		//		final Double ratioRejectedApplications = this.applicationService.appsRejectedRatio().get("Ratio");
	//		//		final Double ratioLateApplications = this.applicationService.lateApplicationsRatio();
	//		//
	//		//		final Collection<Customer> customersMoreFixupTasksAverage = this.customerService.listCustomer10();
	//		//		final Collection<HandyWorker> handyWorkersMoreFixupTasksAverage = this.handyWorkerService.listHandyWorkerApplication();
	//		//
	//		//		final Double maxComplaintsPerFixupTask = this.fixupTaskService.fixupComplaintsStats().get("MAX");
	//		//		final Double minComplaintsPerFixupTask = this.fixupTaskService.fixupComplaintsStats().get("MIN");
	//		//		final Double avgComplaintsPerFixupTask = this.fixupTaskService.fixupComplaintsStats().get("AVG");
	//		//		final Double stdComplaintsPerFixupTask = this.fixupTaskService.fixupComplaintsStats().get("STD");
	//		//
	//		//		final Double maxNotesPerReport = this.reportService.refeeReportStats().get("MAX");
	//		//		final Double minNotesPerReport = this.reportService.refeeReportStats().get("MIN");
	//		//		final Double avgNotesPerReport = this.reportService.refeeReportStats().get("AVG");
	//		//		final Double stdNotesPerReport = this.reportService.refeeReportStats().get("STD");
	//		//
	//		//		final Double ratioFixupTasksWithComplaints = this.fixupTaskService.getRatioFixupTasksWithComplaints().get("Ratio");
	//		//
	//		//		final Collection<Customer> top3CustomersWithMoreComplaints = this.customerService.getTop3CustomerWithMoreComplaints();
	//		//		final Collection<HandyWorker> top3HandyWorkersWithMoreComplaints = this.handyWorkerService.getTop3HandyWorkerWithMoreComplaints();
	//		//
	//		//		result.addObject("minCuestionariosPerCustomer", minCuestionariosPerCustomer);
	//		//		result.addObject("maxCuestionariosPerCustomer", maxCuestionariosPerCustomer);
	//		//		result.addObject("avgCuestionariosPerCustomer", avgCuestionariosPerCustomer);
	//		//		result.addObject("stdCuestionariosPerCustomer", stdCuestionariosPerCustomer);
	//		//		result.addObject("ratioPublishedCuestionarios", ratioPublishedCuestionarios);
	//		//		result.addObject("ratioUnpublishedCuestionarios", ratioUnpublishedCuestionarios);
	//		//
	//		//		result.addObject("maxFixupTaskPerUser", maxFixupTaskPerUser);
	//		//		result.addObject("minFixupTaskPerUser", minFixupTaskPerUser);
	//		//		result.addObject("avgFixupTaskPerUser", avgFixupTaskPerUser);
	//		//		result.addObject("stdFixupTaskPerUser", stdFixupTaskPerUser);
	//		//
	//		//		result.addObject("maxApplicationsPerFixupTask", maxApplicationsPerFixupTask);
	//		//		result.addObject("minApplicationsPerFixupTask", minApplicationsPerFixupTask);
	//		//		result.addObject("avgApplicationsPerFixupTask", avgApplicationsPerFixupTask);
	//		//		result.addObject("stdApplicationsPerFixupTask", stdApplicationsPerFixupTask);
	//		//
	//		//		result.addObject("maxMaximumPriceOfFixupTasks", maxMaximumPriceOfFixupTasks);
	//		//		result.addObject("minMaximumPriceOfFixupTasks", minMaximumPriceOfFixupTasks);
	//		//		result.addObject("avgMaximumPriceOfFixupTasks", avgMaximumPriceOfFixupTasks);
	//		//		result.addObject("stdMaximumPriceOfFixupTasks", stdMaximumPriceOfFixupTasks);
	//		//
	//		//		result.addObject("maxPriceOfApplications", maxPriceOfApplications);
	//		//		result.addObject("minPriceOfApplications", minPriceOfApplications);
	//		//		result.addObject("avgPriceOfApplications", avgPriceOfApplications);
	//		//		result.addObject("stdPriceOfApplications", stdPriceOfApplications);
	//		//
	//		//		result.addObject("ratioPendingApplications", ratioPendingApplications);
	//		//		result.addObject("ratioAcceptedApplications", ratioAcceptedApplications);
	//		//		result.addObject("ratioRejectedApplications", ratioRejectedApplications);
	//		//		result.addObject("ratioLateApplications", ratioLateApplications);
	//		//
	//		//		result.addObject("customersMoreFixupTasksAverage", customersMoreFixupTasksAverage);
	//		//		result.addObject("handyWorkersMoreFixupTasksAverage", handyWorkersMoreFixupTasksAverage);
	//		//
	//		//		result.addObject("maxComplaintsPerFixupTask", maxComplaintsPerFixupTask);
	//		//		result.addObject("minComplaintsPerFixupTask", minComplaintsPerFixupTask);
	//		//		result.addObject("avgComplaintsPerFixupTask", avgComplaintsPerFixupTask);
	//		//		result.addObject("stdComplaintsPerFixupTask", stdComplaintsPerFixupTask);
	//		//
	//		//		result.addObject("maxNotesPerReport", maxNotesPerReport);
	//		//		result.addObject("minNotesPerReport", minNotesPerReport);
	//		//		result.addObject("avgNotesPerReport", avgNotesPerReport);
	//		//		result.addObject("stdNotesPerReport", stdNotesPerReport);
	//		//
	//		//		result.addObject("ratioFixupTasksWithComplaints", ratioFixupTasksWithComplaints);
	//		//
	//		//		result.addObject("top3CustomersWithMoreComplaints", top3CustomersWithMoreComplaints);
	//		//		result.addObject("top3HandyWorkerWithMoreComplaints", top3HandyWorkersWithMoreComplaints);
	//		//
	//		//		result.addObject("requestURI", "administrator/administrator/dashboard.do");
	//		//
	//		//		//---------------------------------
	//
	//		return result;
	//
	//	}

}
