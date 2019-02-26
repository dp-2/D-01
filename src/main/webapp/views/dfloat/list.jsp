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



<display:table name="dfloats" id="dfloat"
	requestURI="dfloat/brotherhood/list.do" pagesize="5"
	class="displaytag">

	<%--  Primero compruebo que es un brotherhood --%>
	<security:authorize access="hasRole('BROTHERHOOD')">


		<%--  La columna que va a la vista edit de las dfloat --%>
		<display:column>
			<a
				href="dfloat/brotherhood/edit.do?dfloatId=${dfloat.id}"><spring:message
					code="dfloat.edit"></spring:message></a>
		</display:column>


		<%--  La columna que va a la vista display de las dfloat --%>
		<display:column>
			<a
				href="dfloat/brotherhood/display.do?dfloatId=${dfloat.id}"><spring:message
					code="dfloat.display"></spring:message></a>
		</display:column>

		<spring:message code="dfloat.title"
			var="dfloat.title"></spring:message>
		<display:column property="title"
			title="${dfloat.title}" sortable="true" />




	</security:authorize>
</display:table>


<%--  Boton de creacion --%>
<security:authorize access="hasRole('BROTHERHOOD')">

	<input type="button" name="create"
		value="<spring:message code="dfloat.create"></spring:message>"
		onclick="javascript:relativeRedir('dfloat/brotherhood/create.do')" />
</security:authorize>

<%--  Boton de ATRAS --%>
<security:authorize access="hasRole('BROTHERHOOD')">

	<input type="button" name="back"
		value="<spring:message code="dfloat.back"></spring:message>"
		onclick="javascript:relativeRedir('curriculum/brotherhood/display.do')" />
</security:authorize>


