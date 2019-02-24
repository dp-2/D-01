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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="configuration">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<jstl:if test="${isRead==true}">
		<img src="${configuration.banner}" height="250px" width="350px" />
		<br />
	</jstl:if>

	<jstl:if test="${isRead==false}">
		<acme:textbox code="conf.banner" path="banner" />
	</jstl:if>


	<acme:textbox code="conf.nameSys" path="nameSys" readonly="${isRead}" />
	<acme:textbox code="conf.countryCode" path="countryCode"
		readonly="${isRead}" />
	<acme:textbox code="conf.cacheFinder" path="cacheFinder"
		readonly="${isRead}" />
	<acme:textbox code="conf.numResults" path="numResults"
		readonly="${isRead}" />
	<acme:textbox code="conf.welcomeMessageEN" path="welcomeMessageEN"
		readonly="${isRead}" />
	<acme:textbox code="conf.welcomeMessageES" path="welcomeMessageES"
		readonly="${isRead}" />
	<acme:textbox code="conf.spamEN" path="spamWordsEN"
		readonly="${isRead}" />
	<acme:textbox code="conf.spamES" path="spamWordsES"
		readonly="${isRead}" />
	<acme:textbox code="conf.negativeEN" path="negativeWordsEN"
		readonly="${isRead}" />
	<acme:textbox code="conf.negativeES" path="negativeWordsES"
		readonly="${isRead}" />
	<acme:textbox code="conf.positiveEN" path="positiveWordsEN"
		readonly="${isRead}" />
	<acme:textbox code="conf.positiveES" path="positiveWordsES"
		readonly="${isRead}" />


	<jstl:if test="${isRead == false}">

		<input type="submit" name="save"
			value="<spring:message code="conf.save" />" />

		<acme:cancel url="configuration/administrator/list.do"
			code="conf.cancel" />
	</jstl:if>

	<jstl:if test="${isRead == true}">
		<acme:cancel url="configuration/administrator/list.do"
			code="conf.back" />
	</jstl:if>
</form:form>
