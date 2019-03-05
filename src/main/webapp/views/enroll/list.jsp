<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="enrolls" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column>
			<jstl:if
				test="${brotherhoodId==row.brotherhood.id && (row.status=='APPROVED'||row.status=='PENDING') && areaService.findAreaByBrotherhoodId(row.brotherhood.id)!=null}">

				<a href="enroll/brotherhood/edit.do?enrollId=${row.id}"> <spring:message
						code="enroll.edit" />
				</a>


			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column property="startMoment" titleKey="enroll.start" />
	<display:column property="endMoment" titleKey="enroll.end" />
	<display:column property="position.name" titleKey="enroll.position" />

	<jstl:if test="${row.status=='PENDING'}">
		<display:column property="status" titleKey="enroll.status"
			style="background-color:Yellow" sortable="true" />
	</jstl:if>

	<jstl:if test="${row.status=='APPROVED' }">
		<display:column property="status" titleKey="enroll.status"
			style="background-color:Blue" sortable="true " />
	</jstl:if>

	<jstl:if test="${row.status=='REJECTED'}">
		<display:column property="status" titleKey="enroll.status"
			style="background-color:Red" sortable="true" />
	</jstl:if>

	<jstl:if test="${row.status=='OUT'}">
		<display:column property="status" titleKey="enroll.status"
			style="background-color:VIOLET" sortable="true" />
	</jstl:if>


	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column property="member.name" titleKey="enroll.member" />

		<display:column titleKey="enroll.out">
			<jstl:if test="${row.status == 'APPROVED' && row.endMoment == null}">

				<a href="enroll/brotherhood/kickOut.do?enrollId=${row.id}"><spring:message
						code="enroll.kickout" /></a>

			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('MEMBER')">
		<display:column property="brotherhood.name"
			titleKey="enroll.brotherhood" />

		<display:column titleKey="enroll.out">
			<jstl:if test="${row.status == 'APPROVED' && row.endMoment == null}">

				<a href="enroll/member/goOut.do?enrollId=${row.id}"><spring:message
						code="enroll.getout" /></a>
			</jstl:if>
		</display:column>

	</security:authorize>
</display:table>
<security:authorize access="hasRole('MEMBER')">
	<a href="enroll/member/listBrotherhood.do"> <spring:message
			code="enroll.enroll" />
	</a>
</security:authorize>
<br />
<br />



