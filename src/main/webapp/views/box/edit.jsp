
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="box/actor/edit.do" modelAttribute="box">
<security:authentication property="principal.username" var="username" />
<jstl:if test='${box.actor.userAccount.username == username || box.id == 0}'>
	
	<jstl:if test="${isPrincipalAuthorizedEdit}">
		
		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<div>
			<form:label path="name">
				<spring:message code="box.name"/>
			</form:label>
			<form:input path="name" id="name" name="name"/>
			<form:errors path="name" class="error" />
		</div>
		
		<div>
			<form:label path="rootBox">
				<spring:message code="box.rootbox"/>
			</form:label>
			<form:select path="rootBox">
				<form:option value="${box.id}">-----</form:option>
				<jstl:forEach items="${boxs}" var="boxVar">
					<form:option value="${boxVar.id}">${boxVar.name}</form:option>
				</jstl:forEach>
			</form:select>
			<form:errors path="rootBox" class="error" />
		</div>
		
		<div>
			<spring:message code="box.save" var="saveHeader"></spring:message>
			<input type="submit" name="save" value="${saveHeader}"/>
			<jstl:if test="${box.id > 0}">
				<spring:message code="box.delete" var="deleteHeader"></spring:message>
				<input type="submit" name="delete" value="${deleteHeader}" />
			</jstl:if>
		</div>
	</jstl:if>
	
	</jstl:if>
	
</form:form>

<div>
	<spring:message code="box.cancel" var="cancelHeader"></spring:message>
	<button name="cancel" value="${cancelHeader}" onClick="javascript:relativeRedir('box/actor/list.do');" >${cancelHeader}</button>
</div>

<jstl:if test='${box.actor.userAccount.username != username && box.id != 0}'>
	<h1>
		<b><spring:message code="box.permissions"></spring:message></b>
	</h1>
</jstl:if>