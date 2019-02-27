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

<display:table name="enrolls" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('BROTHERHOOD')">
		<jstl:if test="${brotherhoodId==row.brotherhood.id}">
			<display:column>

				<a href="enroll/brotherhood/edit.do?enrollId=${row.id}"> <spring:message
						code="enroll.edit" />
				</a>

			</display:column>
		</jstl:if>
	</security:authorize>
	
	<display:column property="startMoment" titleKey="enroll.start" />
	<display:column property="endMoment" titleKey="enroll.end" />
	<display:column property="member.name" titleKey="enroll.member" />


</display:table>
<security:authorize access="hasRole('BROTHERHOOD')">
	<a href="enroll/brotherhood/create.do"> <spring:message
			code="enroll.create" />
	</a>
</security:authorize>
<br />

<br />

