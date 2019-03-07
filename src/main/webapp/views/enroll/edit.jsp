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


<form:form action="${requestURI}" modelAttribute="enroll">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="member" />
	<form:hidden path="brotherhood" />
	<form:hidden path="startMoment" />
	<form:hidden path="endMoment" />
	<security:authorize access="hasRole('MEMBER')">
		<h1>
			<b><spring:message code="enroll.create1"></spring:message>${enroll.brotherhood.title}</b>
		</h1>
	</security:authorize>
	<security:authorize access="hasRole('BROTHERHOOD')">
		<jstl:if test="${enroll.status=='PENDING'}">
			<form:label path="status">
				<spring:message code="enroll.status"></spring:message>
			</form:label>
			<form:select id="status" path="status">
				<option value="PENDING">PENDING</option>
				<option value="APPROVED">APPROVED</option>
				<option value="REJECTED">REJECTED</option>

			</form:select>
		</jstl:if>

		<jstl:if test="${enroll.status!='PENDING'}">
			<form:hidden path="status" />
			<br />
		</jstl:if>

		<jstl:if test="${enroll.status=='APPROVED'}">
				
				<form:label path="position">
					<b><spring:message code="enroll.position"></spring:message>:</b>
				</form:label>
				<form:select id="position" path="position">
					<form:option value="${positions}" label="------"></form:option>

					<%
						if (languageValue.equals("en")) {
					%>
					<form:options items="${positions}" itemLabel="nameEnglish"
						itemValue="id" />
					<%
						} else if (languageValue.equals("es")) {
					%>
					<form:options items="${positions}" itemLabel="nameSpanish"
						itemValue="id" />
					<%
						}
					%>

				</form:select>
			<br />
		</jstl:if>


	</security:authorize>
	<br />
	<acme:submit name="save" code="enroll.save" />
	<security:authorize access="hasRole('BROTHERHOOD')">
		<acme:cancel code="enroll.cancel" url="enroll/brotherhood/list.do" />
	</security:authorize>

	<security:authorize access="hasRole('MEMBER')">
		<acme:cancel code="enroll.cancel" url="enroll/member/list.do" />
	</security:authorize>
	<%-- <jstl:if test="${position.id!=0}">
		<acme:submit code="enroll.delete" name="delete" />
	</jstl:if>
 --%>
</form:form>

