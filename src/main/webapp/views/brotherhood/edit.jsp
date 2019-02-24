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
		<form:form action="brotherhood/brotherhood-none/edit.do" method="post" id="formEdit"
			name="formEdit" modelAttribute="brotherhoodForm">
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			
			<acme:userAccount code="actor.userAccount" />
			
			<acme:textbox path="name" code="brotherhood.name" />
			<acme:textbox path="surname" code="brotherhood.surname" />
			<acme:textbox path="email" code="brotherhood.email" />
			<acme:textbox path="phone" code="brotherhood.phone" />
			<acme:textbox path="photo" code="brotherhood.photo" />
			<acme:textbox path="title" code="brotherhood.title" />

			<acme:list-textbox items="${brotherhoodForm.pictures}" path="picture" 
				code="brotherhood.picture" fieldsetMessage="brotherhood.pictures" 
				addCode="brotherhood.addPicture" removeCode="brotherhood.removePicture" />
				
			
			
		</form:form>
	</jstl:when>
	<jstl:when test="${isAdmin}">
	
	</jstl:when>
	</jstl:choose>
</jstl:if>