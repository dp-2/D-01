<%--
 * create.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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

<security:authentication property="principal.username" var="username" />
<jstl:if
		test='${dfloat.brotherhood.userAccount.username == username || cuestionario.id == 0}'>
		

<security:authorize access="hasRole('BROTHERHOOD')">
	<div>

		<form:form action="dfloat/brotherhood/edit.do" method="post"
			id="formCreate" name="formCreate" modelAttribute="dfloat">

			<!-- Atributos hidden-->

			<form:hidden path="id" />
			<form:hidden path="version" />

			<form:hidden path="brotherhood" />
			<form:hidden path="processions" />


			<fieldset>
				<!-------------------Form ------------------------------------>
				<acme:labelForm code="dfloat.title" path="title" />
				<acme:textarea code="dfloat.description" path="description" />
				<acme:labelForm code="dfloat.pictures" path="pictures" />	



			</fieldset>

			<%-- <fieldset>
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

				<spring:message code="curriculum.seeProfessionalRecords" var="seeProfessionalRecords"></spring:message>
			<input type="button" name="seeProfessionalRecords" value="${seeProfessionalRecords}"
				onclick="javascript:relativeRedir('professionalRecord/handyWorker/list.do')" />

				<fieldset>
					<legend>
						<b><spring:message code="dfloat.processions"></spring:message></b>
					</legend>
					<display:table name="allProcessions" id="processions" pagesize="5"
						class="displaytag">

						<spring:message code="dfloat.title" var="title"></spring:message>
						<display:column property="title" title="${title}" sortable="true" />

						<spring:message code="dfloat.brotherhood" var="brotherhood"></spring:message>
						<display:column property="brotherhood.title"
							title="${brotherhood}" sortable="true" />

						<spring:message code="dfloat.ticker" var="ticker"></spring:message>
						<display:column property="ticker" title="${ticker}"
							sortable="true" />

					</display:table>

					<spring:message code="curriculum.seeProfessionalRecords" var="seeProfessionalRecords"></spring:message>
			<input type="button" name="seeProfessionalRecords" value="${seeProfessionalRecords}"
				onclick="javascript:relativeRedir('professionalRecord/handyWorker/list.do')" />



				</fieldset>

			</fieldset> --%>





			<!--  Los botones de crear y cancelar -->

			<input type="submit" name="save"
				value="<spring:message code="dfloat.save"></spring:message>" />

			<button type="button"
				onclick="javascript: relativeRedir('dfloat/brotherhood/list.do')">
				<spring:message code="dfloat.cancel" />
			</button>

			<jstl:if test="${dfloat.id != 0}">
				<input type="submit" name="delete"
					value="<spring:message code="dfloat.delete" />"
					onclick="return confirm('<spring:message code="dfloat.confirm.delete" />')" />&nbsp;
	</jstl:if>






		</form:form>

	</div>





</security:authorize>
</jstl:if>
<jstl:if
	test='${dfloat.brotherhood.userAccount.username != username && dfloat.id != 0}'>
	<h1 style="color: red;">
		<b><spring:message code="dfloat.permissions"></spring:message></b>
	</h1>
	
	<img src="https://cdn.shopify.com/s/files/1/1061/1924/products/Very_Angry_Emoji_7f7bb8df-d9dc-4cda-b79f-5453e764d4ea_large.png?v=1480481058" alt="Cuestionario Picture"
			style="width: 10%; height: 10%;">

		<br />
		<br />
	
	<button type="button"
				onclick="javascript: relativeRedir('dfloat/brotherhood/list.do')">
				<spring:message code="dfloat.cancel" />
			</button>
</jstl:if>