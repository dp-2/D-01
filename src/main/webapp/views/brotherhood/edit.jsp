<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<spring:message code="confirm.phone" var="confirmPhoneMessage" />

<jstl:if test="${isPrincipalAuthorizedEdit}">
		<form:form action="brotherhood/brotherhood-none/edit.do" method="post" id="formEdit"
			name="formEdit" modelAttribute="brotherhoodForm">
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			
			<acme:textbox path="username" code="useraccount.username" />
			<acme:password path="password" code="useraccount.password" />
			<acme:password path="confirmPassword" code="useraccount.confirmPassword" />
			
			<acme:textbox path="name" code="brotherhood.name" />
			<acme:textbox path="middleName" code="brotherhood.middlename" />
			<acme:textbox path="surname" code="brotherhood.surname" />
			<acme:textbox path="email" code="brotherhood.email" />
			<acme:textbox path="phone" code="brotherhood.phone" id="phone" />
			<acme:textbox path="address" code="brotherhood.address" />
			<acme:textbox path="photo" code="brotherhood.photo" />
			<acme:textbox path="title" code="brotherhood.title" />
				
			<div>
				<fieldset><legend><spring:message code="brotherhood.pictures" /></legend>
					<jstl:forEach items="${brotherhoodForm.pictures}" var="iter" varStatus="iterStatus">
						<acme:textbox path="pictures[${iterStatus.index}].url" code="brotherhood.picture" />
						<br>
					</jstl:forEach>
					<acme:submit name="addPicture" code="brotherhood.addPicture"/>
					<jstl:if test="${brotherhoodForm.pictures.size() > 0}" >
						<acme:submit name="removePicture" code="brotherhood.removePicture"/>
					</jstl:if>
				</fieldset>
			</div>
				
			<acme:checkbox code="brotherhood.accept" path="accept" />
				
			<input type="submit" name="save"
				value="<spring:message code="brotherhood.save"></spring:message>"
				onclick="return patternPhone(document.getElementById('phone').value, '${confirmPhoneMessage}');" />
			<a href="law/terminosYCondiciones.do"><spring:message code="brotherhood.consultTermsAndConditions" /></a><br/><br/> 
				
		</form:form>
		
		<acme:cancel url="" code="brotherhood.cancel" />	
		
</jstl:if>