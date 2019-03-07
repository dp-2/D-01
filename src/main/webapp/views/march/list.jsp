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

<display:table name="marchs" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('MEMBER')">
		<jstl:if test="${memberId==row.member.id}">
			<display:column>
				<jstl:if test="${row.status=='PENDING'}">

					<a href="march/member/delete.do?marchId=${row.id}"> <spring:message
							code="march.delete" />
					</a>

				</jstl:if>

			</display:column>
		</jstl:if>
	</security:authorize>
	<security:authorize access="hasRole('BROTHERHOOD')">
		<jstl:if test="${brotherhoodId==row.procession.brotherhood.id}">
			<display:column>

				<a href="march/brotherhood/edit.do?marchId=${row.id}"> <spring:message
						code="march.edit" />
				</a>

			</display:column>
		</jstl:if>
	</security:authorize>
	<jstl:if test="${row.status=='PENDING'}">
		<display:column property="status" titleKey="march.status"
			style="background-color:Grey" sortable="true" />
	</jstl:if>

	<jstl:if test="${row.status=='APPROVED' }">
		<display:column property="status" titleKey="march.status"
			style="background-color:Green" sortable="true " />
	</jstl:if>

	<jstl:if test="${row.status=='REJECTED'}">
		<display:column property="status" titleKey="march.status"
			style="background-color:Orange" sortable="true" />
	</jstl:if>

	<display:column property="reason" titleKey="march.reason" />

	<display:column titleKey="march.location">
		<jstl:if test="${row.location.isEmpty() }">
			<acme:out code="location.noPosition" value=""/>
		</jstl:if>
		<jstl:if test="${!row.location.isEmpty()}">
		<acme:out code="location.row" value="${row.location.get(0)}"/>
		<acme:out code="location.column" value="${row.location.get(1)}"/>
		</jstl:if>
	</display:column>
		
		<display:column property="procession.title" titleKey="march.procession" />
		
	



</display:table>



