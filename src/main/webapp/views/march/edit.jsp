<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="march">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="member" />
	<form:hidden path="procession" />
	<security:authorize access="hasRole('MEMBER')">
		<form:hidden path="status" />
		<h1>
			<b><spring:message code="march.create1"></spring:message>${march.procession.title}</b>
		</h1>
	</security:authorize>
	<security:authorize access="hasRole('BROTHERHOOD')">
		<jstl:if test="${march.status=='PENDING'}">
			<form:label path="status">
				<spring:message code="march.status"></spring:message>
			</form:label>
			<form:select id="status" path="status">
				<option value="PENDING">PENDING</option>
				<option value="APPROVED">APPROVED</option>
				<option value="REJECTED">REJECTED</option>

			</form:select>

		</jstl:if>
		<jstl:if test="${march.status!='PENDING'}">
			<form:hidden path="status" />
		</jstl:if>
		<br />
		<br />

		<jstl:if test="${march.status=='REJECTED'}">
			<acme:textbox code="march.reason" path="reason" />
			<br />
		</jstl:if>

		<jstl:if test="${march.status=='APPROVED'}">
			<acme:textbox code="march.location" path="location" />
			<br />
		</jstl:if>


	</security:authorize>

	<acme:submit name="save" code="march.save" />

	<security:authorize access="hasRole('MEMBER')">

		<acme:cancel url="/march/member/list.do" code="march.cancel" />

		<br />
	</security:authorize>

	<security:authorize access="hasRole('BROTHERHOOD')">
		<acme:cancel
			url="/march/brotherhood/list.do?processionId=${march.getProcession().getId()}"
			code="march.cancel" />

		<br />
	</security:authorize>

	<br />



</form:form>
