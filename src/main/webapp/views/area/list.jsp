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



<display:table name="areas" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<%--  Primero compruebo que es un brotherhood --%>
	<security:authorize access="hasRole('ADMIN')">




		<display:column>
			<a href="area/administrator/edit.do?areaId=${row.id}"> <spring:message
					code="area.edit" />
			</a>
		</display:column>

		<acme:column code="area.title" value="${row.name}"></acme:column>
		<acme:column code="area.brotherhood" value="${row.brotherhood.title}"></acme:column>




	</security:authorize>
	<security:authorize access="hasRole('BROTHERHOOD')">
		<acme:column code="area.title" value="${row.name}"></acme:column>
		<acme:column code="area.brotherhood" value="${row.brotherhood.title}"></acme:column>
		<display:column>
			<jstl:if test="${row.brotherhood==null}">
				<a href="area/assign.do?areaId=${row.id}"> <spring:message
						code="area.assign" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>


<security:authorize access="hasRole('ADMIN')">
	<a href="area/administrator/create.do"> <spring:message
			code="area.create" />
	</a>
</security:authorize>




