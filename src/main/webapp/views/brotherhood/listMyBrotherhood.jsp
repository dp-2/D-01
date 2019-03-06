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

<display:table name="brotherhoods" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<acme:column value="${row.title}" code="brotherhood.title"
		sortable="true" />

	<acme:column value="${row.establishedMoment}"
		code="brotherhood.establishedMoment" />

	<acme:column value="${row.email}" code="brotherhood.email" />

	<acme:column value="${row.photo}" image="true" alt="${row.photo}" />

	<acme:column value="${row.phone}" code="brotherhood.phone" />

	<acme:column value="${row.address}" code="brotherhood.address" />

	<spring:message code='brotherhood.display'
		var="titleDisplayBrotherhood" />
	<acme:column value="brotherhood/any/display.do?brotherhoodId=${row.id}"
		url="true" alt="${titleDisplayBrotherhood}" />

	<spring:message code='brotherhood.listmembers' var="titleListMembers" />
	<acme:column value="member/list.do?brotherhoodId=${row.id}" url="true"
		alt="${titleListMembers}" />

	<security:authorize access="hasRole('MEMBER')">
		<spring:message code='brotherhood.listMyprocessions'
			var="titleListProcessions" />
		<acme:column
			value="procession/member/list.do?brotherhoodId=${row.id}"
			url="true" alt="${titleListProcessions}" />
	</security:authorize>

	<spring:message code='brotherhood.listfloats' var="titleListFloats" />
	<acme:column value="dfloat/list.do?brotherhoodId=${row.id}" url="true"
		alt="${titleListFloats}" />
</display:table>