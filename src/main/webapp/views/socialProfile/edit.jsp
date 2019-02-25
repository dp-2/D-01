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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="socialProfile">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actor" />



	<form:label path="nick">
		<spring:message code="profile.nick" />
	</form:label>
	<form:input path="nick" readonly="${isRead }"/>
	<form:errors cssClass="error" path="nick" />
	<br />

	<form:label path="nameSN">
		<spring:message code="profile.name" />
	</form:label>
	<form:input path="nameSN" readonly="${isRead }"/>
	<form:errors cssClass="error" path="nameSN" />
	<br />

	<form:label path="linkSN">
		<spring:message code="profile.link" />
	</form:label>
	<form:input path="linkSN" readonly="${isRead }"/>
	<form:errors cssClass="error" path="linkSN" />
	<br />

	<jstl:if test="${isRead==false }">		
		<acme:submit code="profile.save" name="save"/>
	
		<br />

		<acme:cancel code="profile.cancel" url="/socialProfile/list.do"/>
	
		<br />
		
	</jstl:if>

	<jstl:if test="${isRead == true }">
		<acme:cancel code="profile.cancel" url="/socialProfile/list.do"/>
	</jstl:if>

</form:form>