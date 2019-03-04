<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="requests" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="startMoment" titleKey="enroll.start" />
	<display:column property="endMoment" titleKey="enroll.end" />	
	
	<jstl:if test="${row.status=='PENDING'}">
		<display:column property="status" titleKey="enroll.status"
			style="background-color:Yellow" sortable="true" />
	</jstl:if>

	<jstl:if test="${row.status=='APPROVED' }">
		<display:column property="status" titleKey="march.status"
			style="background-color:Blue" sortable="true " />
	</jstl:if>

	<jstl:if test="${row.status=='REJECTED'}">
		<display:column property="status" titleKey="march.status"
			style="background-color:Red" sortable="true" />
	</jstl:if>
	

	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column property="member.name" titleKey="enroll.member" />
	</security:authorize>


</display:table>
<br />
<br />



