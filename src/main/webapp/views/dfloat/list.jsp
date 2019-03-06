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



<display:table name="dfloats" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

	

<security:authorize access="hasRole('BROTHERHOOD')">
		<%--  La columna que va a la vista edit de las dfloat --%>
		<display:column>
			<a href="dfloat/brotherhood/edit.do?dfloatId=${row.id}"><spring:message
					code="dfloat.edit"></spring:message></a>
		</display:column>


		<%--  La columna que va a la vista display de las dfloat --%>
		<display:column>
			<a href="dfloat/brotherhood/display.do?dfloatId=${row.id}"><spring:message
					code="dfloat.display"></spring:message></a>
		</display:column>
</security:authorize>


		<acme:column code="dfloat.title" value="${ row.title}"></acme:column>
		<acme:column code="dfloat.description" value="${ row.description}"></acme:column>
		<acme:column code="dfloat.pictures" value="${ row.pictures}"></acme:column>
		<acme:column code="dfloat.brotherhood" value="${row.brotherhood.title}"></acme:column>
		



</display:table>


<%--  Boton de creacion --%>
<security:authorize access="hasRole('BROTHERHOOD')">

	<input type="button" name="create"
		value="<spring:message code="dfloat.create"></spring:message>"
		onclick="javascript:relativeRedir('dfloat/brotherhood/create.do')" />
</security:authorize>




