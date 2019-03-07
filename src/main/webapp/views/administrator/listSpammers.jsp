
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">

<display:table name="actors" id="row" requestURI="${requestURI}"
	pagesize="20" class="displaytag">

	<display:column property="userAccount.username"
		titleKey="actor.username" />


	<acme:column code="actor.name" value="${ row.name}"></acme:column>
	
		<acme:column code="actor.spammer" value="${ row.spammer}"></acme:column>
		
	
</display:table>

<input type="button" name="refresh" value="<spring:message code="administrator.refresh"></spring:message>" onclick="javascript:relativeRedir('administrator/spammers.do')"/>	
</security:authorize>