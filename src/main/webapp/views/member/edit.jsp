<%--
 * action-2.jsp
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
<%@taglib prefix="acme" uri="/WEB-INF/tags" %>

<jstl:if test="${isPrincipalAuthorizedEdit}">
	<jstl:choose>
	<jstl:when test="${isBrotherhood}">
		<form:form action="member/edit.do" method="post" id="formEdit"
			name="formEdit" modelAttribute="memberForm">
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			
			<acme:userAccount code="member.userAccount" />
			<acme:userAccount code="member.password" />
			
			<acme:textbox path="name" code="member.name" />
			<acme:textbox path="middleName" code="member.middleName" />
			<acme:textbox path="surname" code="member.surname" />
			<acme:textbox path="photo" code="member.photo" />
			<acme:textbox path="email" code="member.email" />
			<acme:textbox path="phone" code="member.phone" />

			
			
		</form:form>
	</jstl:when>
	<jstl:when test="${isAdmin}">
	
	</jstl:when>
	</jstl:choose>
</jstl:if>