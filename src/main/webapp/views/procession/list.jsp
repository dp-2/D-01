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

<display:table name="processions" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('BROTHERHOOD')">

		<display:column>
			<jstl:if test="${row.ffinal == false}">
				<a href="procession/brotherhood/edit.do?processionId=${row.id}">
					<spring:message code="procession.edit" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column property="brotherhood.name" titleKey="procession.hood" />
	<display:column property="ticker" titleKey="procession.ticker" />

	<security:authorize access="isAuthenticated()">
		<display:column>

			<a href="procession/brotherhood/show.do?processionId=${row.id}">
				<spring:message code="procession.show" />
			</a>

		</display:column>
	</security:authorize>

</display:table>

<br />
<security:authorize access="hasRole('BROTHERHOOD')">
	<a href="procession/brotherhood/create.do"> <spring:message
			code="procession.create" />
	</a>
</security:authorize>




