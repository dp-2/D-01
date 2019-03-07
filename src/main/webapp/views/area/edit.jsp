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

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="area">

	<form:hidden path="id" />
	<form:hidden path="version" />



	<acme:textbox code="area.title" path="name" />




	<jstl:if test="${isRead == false}">
		<acme:textarea code="area.pictures" path="pictures" />
	</jstl:if>

	<jstl:if test="${isRead == true}">
		<jstl:forEach var="pic" items="${tutorial.pictures}">
			<img src="${pic}" height="100px" width="100px" />
		</jstl:forEach>
		<br />
	</jstl:if>

	<acme:submit name="save" code="area.save" />

	<jstl:if test="${area.id != 0 && area.brotherhood==null}">
		<acme:submit name="delete" code="area.delete" />
	</jstl:if>
	<acme:cancel url="/area/list.do" code="area.cancel" />

	<br />


</form:form>


<br />

