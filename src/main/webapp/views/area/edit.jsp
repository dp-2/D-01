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

<form:form action="${requestURI}" modelAttribute="area">

	<form:hidden path="id" />
	<form:hidden path="version" />


	<acme:textbox path="name" code="area.title" />

	<acme:textarea code="area.pictures" path="pictures"/>




</form:form>
<acme:submit name="save" code="area.save" />

<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${area.id != 0 && area.brotherhood==null}">
		<acme:submit name="delete" code="area.delete" />
	</jstl:if>
	<acme:cancel url="/area/list.do" code="area.cancel" />

	<br />
</security:authorize>

<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:cancel url="/area/list.do}" code="area.cancel" />

	<br />
</security:authorize>

<br />

