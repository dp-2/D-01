<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<a href="finder/handy/update.do"> <spring:message
		code="finder.update" />
</a><br/>

<display:table name="processions" id="row" requestURI="${requestURI}"
	pagesize="" class="displaytag">

	<display:column property="ticker" titleKey="procession.ticker" />

	<display:column titleKey=".category">
		<jstl:forEach var="entry" items="${row.category.name}">
			<jstl:if test="${lang==entry.key}">
				${entry.value}
			</jstl:if>
		</jstl:forEach>
	</display:column>

	<display:column titleKey="fixuptask.details">
		<a href="fixUpTask/customer/show.do?fixUpTaskId=${row.id}"> <spring:message
				code="fixuptask.show" />
		</a>
	</display:column>
<display:column titleKey="fixUpTask.customer">
			<a
				href="handyWorker/viewProfileCustomer.do?customerId=${row.customer.id}">
				<spring:message code="fixUpTask.viewProfile" />
			</a>
		</display:column>

		<display:column titleKey="fixUpTask.application.create">
			<jstl:if
				test="${applicationService.findApplicationByHandyWorkerIdAndTaskId(handyId, row.id) == null and applicationService.findApplicationAcceptedByFixUpTaskId(row.id) == null}">
				<a href="application/handyworker/create.do?fixUpTaskId=${row.id}"><spring:message
						code="fixUpTask.application.create" /></a>
			</jstl:if>
		</display:column>


</display:table>


