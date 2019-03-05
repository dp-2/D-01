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

<form:form action="${requestURI}" modelAttribute="enroll">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="member" />
	<form:hidden path="brotherhood" />
	<form:hidden path="startMoment" />
	<form:hidden path="endMoment" />

	<security:authorize access="hasRole('BROTHERHOOD')">

		<%-- <jstl:if test="${idioma}=='es'">
			<form:label path="position">
				<spring:message code="enroll.position" />:
  		</form:label>
			<form:select id="positions" path="position">
				<form:options items="${positionsES}" itemValue="id" itemLabel="name" />
			</form:select>
			<form:errors cssClass="error" path="position" />
			<br />
			<br />
		</jstl:if>

		<jstl:if test="${idioma}=='en'">
			<form:label path="position">
				<spring:message code="enroll.position" />:
  		</form:label>
			<form:select id="positions" path="position">
				<form:options items="${positionsEN}" itemValue="id" itemLabel="name" />
			</form:select>
			<form:errors cssClass="error" path="position" />
			<br />
			<br />
		</jstl:if> --%>


		<%-- <form:label path="position">
			<spring:message code="enroll.position" />:
  		</form:label>
		<form:select id="positions" path="position">
			<form:options items="${positions}" itemValue="id" itemLabel="name" />
		</form:select>
		<form:errors cssClass="error" path="position" /> --%>
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
			</jstl:if>
			<br />

			<jstl:if test="${enroll.status=='REJECTED'}">
				<form:hidden path="status" />
				<br />
			</jstl:if>

			<jstl:if test="${enroll.status=='APPROVED'}">

				<acme:select code="enroll.position" path="position"
					items="${positions}" itemLabel="name" />
				<br />
			</jstl:if>


		</security:authorize>


	</security:authorize>

	<acme:submit name="save" code="enroll.save" />
	<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:cancel code="enroll.cancel" url="/enroll/brotherhood/list.do" />
	</security:authorize>
	
	<security:authorize access="hasRole('MEMBER')">
	<acme:cancel code="enroll.cancel" url="/enroll/member/list.do" />
	</security:authorize>
	<%-- <jstl:if test="${position.id!=0}">
		<acme:submit code="enroll.delete" name="delete" />
	</jstl:if>
 --%>
</form:form>

