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


	<fieldset>
		<legend>
			<spring:message code="dashboard.positionStats"> </spring:message>
		</legend>
		
		<acme:out code="dashboard.positionStats.positionCountTotal" value="${positionCountTotal}"/>
		<acme:out code="dashboard.positionStats.positionCountPresident" value="${positionCountPresident}"/>
		<acme:out code="dashboard.positionStats.positionCountVicepresident" value="${positionCountVicepresident}"/>
		<acme:out code="dashboard.positionStats.positionCountSecretary" value="${positionCountSecretary}"/>
		<acme:out code="dashboard.positionStats.positionCountTreasurer" value="${positionCountTreasurer}"/>
		<acme:out code="dashboard.positionStats.positionCountHistorian" value="${positionCountHistorian}"/>
		<acme:out code="dashboard.positionStats.positionCountOfficer" value="${positionCountOfficer}"/>
		
	</fieldset>
	</security:authorize>