<%--
 * action-2.jsp
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

<form:form action="procession/brotherhood/edit.do"
	modelAttribute="procession">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="brotherhood" />
	<form:hidden path="ticker" />

	<acme:textbox code="procession.title" path="title" readonly="${isRead}" />
	<acme:textbox code="procession.description" path="description"
		readonly="${isRead}" />
	<acme:textbox code="procession.moment" path="momentOrganised"
		readonly="${isRead}" placeholder="yyyy/mm/dd" />




	<jstl:if test="${isRead == false}">
		<acme:checkbox code="procession.final" path="ffinal" />

		<input type="submit" name="save"
			value="<spring:message code="procession.save" />" />

		<jstl:if test="${procession.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="procession.delete" />" />
		</jstl:if>

		<acme:cancel url="procession/brotherhood/myList.do"
			code="procession.cancel" />
	</jstl:if>

	<jstl:if test="${isRead == true}">

		<acme:cancel url="procession/list.do" code="procession.back" />

	</jstl:if>


</form:form>


