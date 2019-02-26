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

<form:form action="${requestURI}" modelAttribute="position">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<acme:select itemLabel="positions" items="${position}" code="position.name" path="name"/>
	<br />
	
	<acme:textbox code="position.language" path="language"/>
	<br />
	
	<acme:submit name="save" code="position.save"/>
	<acme:cancel code="position.cancel" url="/position/administrator/list.do" />
		<jstl:if test="${position.id!=0}">
			<acme:submit code="position.delete" name="delete" />
		</jstl:if>
	
	

</form:form>


