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
			<spring:message code="dashboard.membersPerBrotherhood">
			</spring:message>
		</legend>
		<acme:out code="dashboard.membersPerBrotherhood.max" value="${maxMembersPerBrotherhood}" />
		<acme:out code="dashboard.membersPerBrotherhood.min" value="${minMembersPerBrotherhood}" />
		<acme:out code="dashboard.membersPerBrotherhood.avg" value="${avgMembersPerBrotherhood}" />
		<acme:out code="dashboard.membersPerBrotherhood.stdev" value="${stdevMembersPerBrotherhood}" />
	</fieldset>
	
	<fieldset>
		<legend>
		<spring:message code="dashboard.largestSmallestBrotherhood">
			</spring:message>
		</legend>
		<acme:out code="dashboard.largestBrotherhood" value="${largestBrotherhood.title}" />
		<acme:out code="dashboard.numberOfMembers" value="${largestBrotherhoodNumMembers}" />
		<br/>
		<br/>
		<acme:out code="dashboard.smallestBrotherhood" value="${smallestBrotherhood.title}" />
		<acme:out code="dashboard.numberOfMembers" value="${smallestBrotherhoodNumMembers}" />
	</fieldset>

		<fieldset>
			<legend>
				<spring:message code="dashboard.positionStats">
				</spring:message>
			</legend>

			<acme:out code="dashboard.positionStats.positionCountTotal" value="${positionCountTotal}" />
			<acme:out code="dashboard.positionStats.positionCountPresident" value="${positionCountPresident}" />
			<acme:out code="dashboard.positionStats.positionCountVicepresident" value="${positionCountVicepresident}" />
			<acme:out code="dashboard.positionStats.positionCountSecretary" value="${positionCountSecretary}" />
			<acme:out code="dashboard.positionStats.positionCountTreasurer" value="${positionCountTreasurer}" />
			<acme:out code="dashboard.positionStats.positionCountHistorian" value="${positionCountHistorian}" />
			<acme:out code="dashboard.positionStats.positionCountOfficer" value="${positionCountOfficer}" />

		</fieldset>



		<fieldset>
			<legend>
				<spring:message code="dashboard.processionsIn30Days">
				</spring:message>
			</legend>

			<display:table name="processionsIn30Days" id="procession">
				<acme:column code="dashboard.procession.title"
					value="${ procession.title}"></acme:column>
				<acme:column code="dashboard.procession.momentOrganised"
					value="${ procession.momentOrganised}"></acme:column>
			</display:table>
			
		</fieldset>
		<fieldset>
			<legend>
				<spring:message code="dashboard.members10RequestAccepted">
				</spring:message>
			</legend>

			<display:table name="members10RequestAccepted" id="member">
				<acme:column code="dashboard.march.member"
					value="${member.name}"></acme:column>
				<acme:column code="dashboard.march.position"
					value="${ member.position}"></acme:column>
			</display:table>
			
		</fieldset>
</security:authorize>