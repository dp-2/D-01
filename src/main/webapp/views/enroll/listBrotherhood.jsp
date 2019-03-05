<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="enrollBrotherhoods" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">

	<display:column property="name" titleKey="enroll.brotherhood" />
	<display:column titleKey="enroll.unir">
		<a href="enroll/member/create.do?brotherhoodId=${row.id}"><spring:message
				code="enroll.unirse" /></a>

	</display:column>

</display:table>
<br />
<br />



