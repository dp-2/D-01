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


<display:table name="profiles" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="actor.userAccount.username"
		titleKey="profile.username" />

	<display:column titleKey="profile.social">
	
		<a href="profile/show.do?profileId=${row.id}"> <spring:message
				code="profile.view" />
		</a>
	</display:column>
	
	
	<display:column>
		<jstl:if test="${username==row.actor.userAccount.username}">
		<a href="profile/edit.do?profileId=${row.id}"> <spring:message
				code="profile.edit" />
		</a>
	</jstl:if>
	</display:column>
	
	
</display:table>

<a href="profile/create.do"> <spring:message
			code="profile.create" />
	</a>