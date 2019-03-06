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

<display:table name="positions" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('ADMIN')">
			<display:column>

				<a href="position/administrator/edit.do?positionId=${row.id}"> <spring:message
						code="position.edit" />
				</a>

			</display:column>
	</security:authorize>
	
	<spring:message code="position.name" var="positionName"></spring:message>
		<display:column title="${positionName}" sortable="true" >
			<% if(languageValue.equals("en")) { %>
				<jstl:out value="${row.nameEnglish}" />
			<% } else if (languageValue.equals("es")) { %>
				<jstl:out value="${row.nameSpanish}" />
			<% } %>
		</display:column>



</display:table>

<a href="position/administrator/create.do"><spring:message
		code="position.create" />
</a>




