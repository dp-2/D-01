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

<security:authorize access="hasRole('BROTHERHOOD')">
	<div>

		<form:form action="dfloat/brotherhood/edit.do" method="post"
			id="formCreate" name="formCreate" modelAttribute="dfloat">

			<!-- Atributos hidden-->

			<form:hidden path="id" />
			<form:hidden path="version" />


			<fieldset>
				<!-------------------Form ------------------------------------>
				<div>
					<form:label path="title">
						<spring:message code="dfloat.title"></spring:message>
					</form:label>
					<form:input path="title" id="title"
						name="title" />
					<form:errors cssClass="error" path="title" />
					<br />
				</div>
				
				<acme:labelForm code="dfloat.description" path="description"/>

				




			</fieldset>


			<!--  Los botones de crear y cancelar -->

			<input type="submit" name="save"
				value="<spring:message code="dfloat.save"></spring:message>" />

			<button type="button"
				onclick="javascript: relativeRedir('dfloat/brotherhood/list.do')">
				<spring:message code="dfloat.cancel" />
			</button>

			<jstl:if test="${brotherhood.id != 0}">
				<input type="submit" name="delete"
					value="<spring:message code="dfloat.delete" />"
					onclick="return confirm('<spring:message code="dfloat.confirm.delete" />')" />&nbsp;
	</jstl:if>






		</form:form>

	</div>





</security:authorize>