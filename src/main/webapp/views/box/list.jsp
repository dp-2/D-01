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

<jstl:if test="${isPrincipalAuthorizedEdit}">

	<display:table name="folders" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">
	
		<display:column property="name" titleKey="folder.name" sortable="false"></display:column>
	
		<display:column>
			<a href="folder/actor/list.do?rootFolderId=${row.id}" >
				<spring:message code="folder.listsubfolders" />
			</a>	
		</display:column>
	
		<display:column>
			<a href='message/actor/list.do?folderId=${row.id}' >
				<spring:message code="folder.listmessages" />
			</a>
		</display:column>
		
		<display:column>
			<jstl:if test="${row.system == false}">
				<a href="folder/actor/edit.do?folderId=${row.id}">
					<spring:message code="folder.edit" />
				</a>
			</jstl:if>
		</display:column>
		
	</display:table>
	
	<a href="folder/actor/create.do"> <spring:message
			code="folder.create"></spring:message></a>
	
	<jstl:choose>
		<jstl:when test="${not (backFolderId eq null)}">
			<a href="folder/actor/list.do?rootFolderId=${backFolderId}"> 
				<spring:message code="folder.back"></spring:message>
			</a>
		</jstl:when>
		<jstl:when test="${(rootFolder.id eq rootFolder.rootFolder.id) and showBack}">
			<a href="folder/actor/list.do"> 
				<spring:message code="folder.back"></spring:message>
			</a>
		</jstl:when>
	</jstl:choose>	

</jstl:if>	