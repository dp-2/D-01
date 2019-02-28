<%--
 * edit.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="finder/handy/update.do" modelAttribute="finder">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="lastUpdate" />
	<form:hidden path="fixUpTasks" />




	<form:label path="keyword">
		<spring:message code="finder.keyword" />
	</form:label>
	<form:input path="keyword" />
	<form:errors cssClass="error" path="keyword" />
	<br />

	<form:label path="priceMin">
		<spring:message code="finder.priceMin" />
	</form:label>
	<form:input path="priceMin" />
	<form:errors cssClass="error" path="priceMin" />
	<br />

	<form:label path="priceMax">
		<spring:message code="finder.priceMax" />
	</form:label>
	<form:input path="priceMax" />
	<form:errors cssClass="error" path="priceMax" />
	<br />

	<form:label path="dateMin">
		<spring:message code="finder.dateMin" />
	</form:label>
	<form:input path="dateMin" placeholder="yyyy/mm/dd" />
	<form:errors cssClass="error" path="dateMin" />
	<br />

	<form:label path="dateMax">
		<spring:message code="finder.dateMax" />
	</form:label>
	<form:input path="dateMax" placeholder="yyyy/mm/dd" />
	<form:errors cssClass="error" path="dateMax" />
	<br />

	<form:label path="namecategory">
		<spring:message code="finder.namecategory" />
	</form:label>
	<form:select id="categories" path="namecategory">
		<form:option value="" label="------" />
		<form:options items="${categories}" />
	</form:select>

	<form:label path="namewarranty">
		<spring:message code="finder.namewarranty" />
	</form:label>
	<form:select id="warranties" path="namewarranty">
		<form:option value="" label="------" />
		<form:options items="${warranties}" itemLabel="title"
			itemValue="title" />
	</form:select>

	<input type="submit" name="save"
		value="<spring:message code="finder.save"/>" />

	<input type="button" name="cancel"
		value="<spring:message code="finder.cancel" />"
		onclick="javascript: relativeRedir('finder/handy/listFixUpTasks.do');" />

	<br />


</form:form>