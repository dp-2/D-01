<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${isPrincipalAuthorizedEdit}">

	<display:table name="boxs" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">
	
		<acme:column value="${row.name}" code="box.name" sortable="false" />

		<spring:message code='box.listsubboxs' var="altListSubboxes" />
		<acme:column value="box/actor/list.do?rootId=${row.id}" alt="${altListSubboxes}" url="true" />
		
		<spring:message code='box.listmessages' var="altListMessages" />
		<acme:column value="message/actor/list.do?boxId=${row.id}" alt="${altListMessages}" url="true" />
		
		<spring:message code='box.edit' var="altBoxEdit" />
		<acme:column value="box/actor/edit.do?boxId=${row.id}" alt="${altBoxEdit}" 
			url="true" test="${row.isSystem == false}" />
		
	</display:table>
	
	<a href="box/actor/create.do"> <spring:message
			code="box.create"></spring:message></a>
	
	<jstl:choose>
		<jstl:when test="${not (backBoxId eq null)}">
			<a href="box/actor/list.do?rootBoxId=${backBoxId}"> 
				<spring:message code="box.back"></spring:message>
			</a>
		</jstl:when>
		<jstl:when test="${(rootBox.id eq rootBox.rootBox.id) and showBack}">
			<a href="box/actor/list.do"> 
				<spring:message code="box.back"></spring:message>
			</a>
		</jstl:when>
	</jstl:choose>	

</jstl:if>	