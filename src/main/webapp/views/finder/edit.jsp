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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="finder/member/update.do" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="lastUpdate" />
	<form:hidden path="member" />
	<form:hidden path="processions" />

	<acme:textbox code="finder.keyword" path="keyword" />
	<acme:textbox code="finder.dateMin" path="minDate"
		placeholder="yyyy/MM/dd" />
	<acme:textbox code="finder.dateMax" path="maxDate"
		placeholder="yyyy/MM/dd" />

	<acme:select items="${areas}" itemLabel="name" code="procession.area"
		path="area" />

	<input type="submit" name="save"
		value="<spring:message code="finder.save"/>" />

	<acme:cancel url="finder/member/list.do" code="finder.cancel" />

	<br />

</form:form>