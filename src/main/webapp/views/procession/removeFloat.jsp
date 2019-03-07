<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<jstl:if test="${isPrincipalAuthorizedEdit}">
	
	<form:form action="procession/brotherhood/removeFloat.do" modelAttribute="processionFloatForm">
		
		<form:hidden path="procession" /> 
			
		<acme:select code="float" itemLabel="title" items="${floatsForRemove}" path="dFloat" />
			
		<acme:submit code="procession.removeFloat" name="remove" />
		<acme:cancel url="procession/list.do" code="procession.back" />
	
	</form:form>

</jstl:if>
