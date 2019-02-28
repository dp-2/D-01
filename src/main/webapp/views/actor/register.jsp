<%--
 * action-1.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="register/actor.do" modelAttribute="actor">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="spammer" />
	<form:hidden path="banned" />
	<form:hidden path="userAccount.authorities" />
	<form:hidden path="userAccount.enabled" />

	<acme:textbox code="actor.userAccount.username"
		path="userAccount.username" />
	<br />

	<acme:password code="actor.userAccount.password"
		path="userAccount.password" />
	<br />

	<acme:textbox code="actor.name" path="name" />
	<br />

	<acme:textbox code="actor.middleName" path="middleName" />
	<br />

	<acme:textbox code="actor.surname" path="surname" />
	<br />

	<acme:textbox code="actor.photo" path="photo" />
	<br />

	<acme:textbox code="actor.email" path="email" />
	<br />
	
	<acme:textbox code="actor.address" path="address" />
	<br />

	<acme:textbox code="actor.phone" path="phone" id="tlf" />
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




	<input type="submit" name="save"
		value='<spring:message code="actor.save"/>'
		onclick=" javascript: return isValid();">

	<acme:cancel url="welcome/index.do" code="actor.cancel" />


</form:form>