<%--
 * action-1.jsp
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

<spring:message code="confirm.phone" var="confirmPhoneMessage" />

<jstl:if test="${isPrincipalAuthorizedEdit}">
		<form:form action="register/actor.do" method="post" id="formEdit"
			name="formEdit" modelAttribute="actorForm">
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="authority" />
			
			<acme:textbox path="username" code="useraccount.username" />
			<acme:password path="password" code="useraccount.password" />
			<acme:password path="confirmPassword" code="useraccount.confirmPassword" />
			
			<acme:textbox path="name" code="brotherhood.name" />
			<acme:textbox path="middleName" code="brotherhood.middlename" />
			<acme:textbox path="surname" code="brotherhood.surname" />
			<acme:textbox path="email" code="brotherhood.email" />
			<acme:textbox path="phone" code="brotherhood.phone" id="phone" />
			<acme:textbox path="address" code="brotherhood.address" />
			<acme:textbox path="photo" code="brotherhood.photo" />
				
			<acme:checkbox code="brotherhood.accept" path="accept" />
				
			<input type="submit" name="save"
				value="<spring:message code="brotherhood.save"></spring:message>"
				onclick="return patternPhone(document.getElementById('phone').value, '${confirmPhoneMessage}');" />
				
		</form:form>
		
		<acme:cancel url="" code="brotherhood.cancel" />	
		
</jstl:if>