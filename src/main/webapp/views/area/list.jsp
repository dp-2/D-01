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



<display:table name="areas" id="area"
	requestURI="area/list.do" pagesize="5"
	class="displaytag">

	<%--  Primero compruebo que es un brotherhood --%>
	<security:authorize access="hasRole('ADMIN')">


		<%--  La columna que va a la vista edit de las area --%>
<%-- 		<display:column>
			<a
				href="area/brotherhood/edit.do?areaId=${area.id}"><spring:message
					code="area.edit"></spring:message></a>
		</display:column> --%>
		

		<%--  La columna que va a la vista display de las area --%>
<%-- 		<display:column>
			<a
				href="area/display.do?areaId=${area.id}"><spring:message
					code="area.display"></spring:message></a>
		</display:column> --%>

	
			
		<acme:column code="area.title" value="${ area.name}"></acme:column>	
		<acme:column code="area.brotherhood" value="${ area.brotherhood.title}" ></acme:column>
		<%-- <acme:column code="area.procession" value="${ area.procession.title}" ></acme:column> --%>
	
	</security:authorize>
</display:table>


<%--  Boton de creacion --%>
<%-- <security:authorize access="hasRole('BROTHERHOOD')">

	<input type="button" name="create"
		value="<spring:message code="area.create"></spring:message>"
		onclick="javascript:relativeRedir('area/brotherhood/create.do')" />
</security:authorize> --%>




