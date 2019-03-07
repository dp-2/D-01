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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<form:form action="${requestURI}" modelAttribute="actor">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="spammer" />
	<form:hidden path="userAccount" />
	


	<jstl:if test="${isRead==true}">
		<img src="${actor.photo}" height="200px" width="200px" />
		<br />
	</jstl:if>

	<acme:textbox code="actor.name" path="name" />
	<br />

	<acme:textbox code="actor.middleName" path="middleName" />
	<br />

	<acme:textbox code="actor.surname" path="surname" />
	<br />
	<jstl:if test="${isRead == false}">
	<acme:textbox code="actor.photo" path="photo" />
	<br />
	</jstl:if>

	<acme:textbox code="actor.email" path="email" />
	<br />

	

	<acme:textbox code="actor.phone" path="phone" />
	<br />

	<script type="text/javascript">
		function isValid() {
			var phoneRe = /^(((\+[1-9][0-9]{0,2}) \(([1-9][0-9]{0,2})\) (\d\d\d\d+))|((\+[1-9][0-9]{0,2}) (\d\d\d\d+))|((\d\d\d\d+)))$/;
			var digits = document.getElementById('tlf').value;
			var res = phoneRe.test(digits);
			if (res) {
				return true;
			} else {
				return confirm('<spring:message code="phone.confirm" />');
			}
		}
	</script>
	
	
	<jstl:if test="${isRead == true}">
		<jstl:if test="${score != null}">
			<h3>
				<spring:message code="actor.score" />
				:
				<jstl:out value="${score}" />
			</h3>
		</jstl:if>
	</jstl:if>


	<jstl:if test="${isRead == false}">
		<br />
		<acme:submit name="save" code="actor.save" />

		<acme:cancel url="welcome/index.do" code="actor.cancel" />
	</jstl:if>

	<jstl:if test="${isRead == true}">
		<acme:cancel url="welcome/index.do" code="actor.back" />
		<br />

	</jstl:if>

</form:form>



