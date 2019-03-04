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

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private PositionService	positionService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView action1() {
		ModelAndView result;
		Map<String, Double> statistics;

		statistics = this.positionService.computeStatistics();

		result = new ModelAndView("administrator/dashboard");
		result.addObject("statistics", statistics);

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-2");

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
