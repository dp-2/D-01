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

<display:table name="configuration" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('ADMIN')">


		<display:column titleKey="conf.edit">
			<a
				href="configuration/administrator/edit.do?configurationId=${row.id}">
				<spring:message code="conf.edit" />
			</a>
		</display:column>

	</security:authorize>

	<display:column titleKey="conf.details">
		<a
			href="configuration/administrator/show.do?configurationId=${row.id}">
			<spring:message code="conf.show" />
		</a>
	</display:column>


</display:table>