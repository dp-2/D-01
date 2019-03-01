<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:out code="dfloat.title" value="${dfloat.title}"/>
<acme:out code="dfloat.description" value="${dfloat.description}"/>
<acme:out code="dfloat.pictures" value="${dfloat.pictures}"/>
<acme:out code="dfloat.brotherhood" value="${dfloat.title}"/>
<%-- <acme:out code="dfloat.procession" value="${dfloat.procession}"/> --%>

<fieldset>
				<legend>
					<b><spring:message code="dfloat.processions"></spring:message></b>
				</legend>
				<display:table name="myProcessions" id="processions" pagesize="5"
					class="displaytag">

					<spring:message code="dfloat.title" var="title"></spring:message>
					<display:column property="title" title="${title}" sortable="true" />

					<spring:message code="dfloat.brotherhood" var="brotherhood"></spring:message>
					<display:column property="brotherhood.title" title="${brotherhood}"
						sortable="true" />

					<spring:message code="dfloat.ticker" var="ticker"></spring:message>
					<display:column property="ticker" title="${ticker}" sortable="true" />

				</display:table>

</fieldset>


<input type="button" name="edit" value="<spring:message code="dfloat.edit"></spring:message>" onclick="javascript:relativeRedir('dfloat/brotherhood/edit.do?dfloatId=${dfloat.id}')"/>	
<input type="button" name="cancel" value="<spring:message code="dfloat.cancel"></spring:message>" onclick="javascript:relativeRedir('dfloat/brotherhood/list.do')" />	











