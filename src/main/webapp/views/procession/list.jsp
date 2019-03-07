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
	pagesize="${numResults}" class="displaytag">

	<security:authorize access="hasRole('BROTHERHOOD')">

		<display:column>
			<jstl:if test="${row.ffinal == false}">
				<a href="procession/brotherhood/edit.do?processionId=${row.id}">
					<spring:message code="procession.edit" />
				</a>
				<br>
				<a href="procession/brotherhood/addFloat.do?processionId=${row.id}">
					<spring:message code="procession.addFloat" />
				</a>
				<br>
				<a href="procession/brotherhood/removeFloat.do?processionId=${row.id}">
					<spring:message code="procession.removeFloat" />
				</a>
				
			</jstl:if>
		</display:column>
	</security:authorize>



	<display:column titleKey="procession.hood">
		<a href="procession/listBrotherhood.do?processionId=${row.id}"> <jstl:out
				value="${row.brotherhood.name}" />
		</a>
	</display:column>

	<display:column property="ticker" titleKey="procession.ticker" />
	<security:authorize access="hasRole('BROTHERHOOD')">

		<display:column titleKey="procession.member">

			<a href="march/brotherhood/list.do?processionId=${row.id}"> <spring:message
					code="procession.list" />
			</a>

		</display:column>
	</security:authorize>

	

	<display:column>

		<a href="procession/brotherhood/show.do?processionId=${row.id}"> <spring:message
				code="procession.show" />
		</a>

	</display:column>



	<security:authorize access="hasRole('MEMBER')">
	
		<display:column>
		<jstl:if test="${marchService.findMatchByProcessionidAndMemberid(row.id,memberId)==0}">
			<a href="march/member/create.do?processionId=${row.id}"> <spring:message
					code="procession.createMarch" />
			</a>
		</jstl:if>
		</display:column>

	</security:authorize>

</display:table>

<br />
<security:authorize access="hasRole('BROTHERHOOD')">
	<jstl:if test="${hasArea != null}">
		<a href="procession/brotherhood/create.do"> <spring:message
				code="procession.create" />
		</a>
	</jstl:if>

	<jstl:if test="${hasArea == null}">
		<p>
			<spring:message code="procession.area.null" />
		</p>
	</jstl:if>
</security:authorize>




