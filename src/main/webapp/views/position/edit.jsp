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

<%

String languageValue;
try{
Cookie[] cookies = request.getCookies();
Cookie languageCookie = null;
for(Cookie c : cookies) {
	if(c.getName().equals("language")) {
		languageCookie = c;
	}
}

languageValue = languageCookie.getValue();}
catch(NullPointerException e){
	languageValue = "en";	
}

%>



<form:form action="${requestURI}" modelAttribute="position">
	<form:hidden path="id" />
	<form:hidden path="version" />


	<acme:textbox code="position.nameEnglish" path="nameEnglish" />
	<br />
	<acme:textbox code="position.nameSpanish" path="nameSpanish" />
	<br />

	<%-- <acme:select items="EN, ES" itemLabel="EN, ES" code="position.language" path="language"/>
	<br /> --%>

	<%-- <form:label path="language">
		<spring:message code="position.language"></spring:message>
	</form:label>
	<form:select id="language" path="language">
		<option value="EN">EN</option>
		<option value="ES">ES</option>

	</form:select>
	<br /><br /> --%>


	<acme:submit name="save" code="position.save" />
	<acme:cancel code="position.cancel"
		url="/position/administrator/list.do" />
	<jstl:if test="${position.id!=0}">
		<acme:submit code="position.delete" name="delete" />
	</jstl:if>



</form:form>


