<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<img src="<jstl:out value='${brotherhood.photo}' />" alt="<jstl:out value='${brotherhood.photo}' />" />

<p><spring:message code="brotherhood.name" /> : <jstl:out value="${brotherhood.name}" /></p>
<p><spring:message code="brotherhood.middlename" /> : <jstl:out value="${brotherhood.middleName}" /></p>
<p><spring:message code="brotherhood.surname" /> : <jstl:out value="${brotherhood.surname}" /></p>
<p><spring:message code="brotherhood.email" /> : <jstl:out value="${brotherhood.email}" /></p>
<p><spring:message code="brotherhood.phone" /> : <jstl:out value="${brotherhood.phone}" /></p>
<p><spring:message code="brotherhood.address" /> : <jstl:out value="${brotherhood.address}" /></p>
<p><spring:message code="brotherhood.spammer" /> : <jstl:out value="${brotherhood.spammer}" /></p>
<p><spring:message code="brotherhood.banned" /> : <jstl:out value="${brotherhood.banned}" /></p>
<p><spring:message code="brotherhood.score" /> : <jstl:out value="${brotherhood.score}" /></p>
<p><spring:message code="brotherhood.title" /> : <jstl:out value="${brotherhood.title}" /></p>
<fmt:formatDate pattern='yyyy-MM-dd' value='${brotherhood.establishedMoment}' var="establishedMoment" />
<p><spring:message code="brotherhood.establishedMoment" /> : <jstl:out value="${establishedMoment}" /></p>

<fieldset><legend><spring:message code="brotherhood.pictures" /></legend>
	<jstl:forEach items="${brotherhoodPictures}" var="picture">
		<img src="<jstl:out value='${picture.url}' />" alt="<jstl:out value='${picture.url}' />" />
	</jstl:forEach>
</fieldset>

<jstl:if test="${isPrincipalAuthorizedEdit}">
	<a href="brotherhood/brotherhood/edit.do?brotherhoodId=${brotherhood.id}"><spring:message code="brotherhood.edit" /></a>
</jstl:if>

