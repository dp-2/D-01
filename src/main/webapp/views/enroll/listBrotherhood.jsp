<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="enrolls2" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
		<display:column property="brotherhood.name"
			titleKey="enroll.brotherhood" />


		<jstl:if test="${row.endMoment == null}">
			<display:column titleKey="enroll.out">
				<a href="enroll/member/goOut.do?enrollId=${row.id}"><spring:message
						code="enroll.getout" /></a>
			</display:column>
		</jstl:if>
</display:table>
<br />
<br />



