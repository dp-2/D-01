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


<display:table name="socialProfiles" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="actor.userAccount.username"
		titleKey="socialProfile.username" />

	<display:column titleKey="socialProfile.social">
	
		<a href="socialProfile/show.do?socialProfileId=${row.id}"> <spring:message
				code="socialProfile.view" />
		</a>
	</display:column>
	
	
	<display:column>
		<jstl:if test="${username==row.actor.userAccount.username}">
		<a href="socialProfile/edit.do?socialProfileId=${row.id}"> <spring:message
				code="socialProfile.edit" />
		</a>
	</jstl:if>
	</display:column>
	
	
</display:table>

<a href="socialProfile/create.do"> <spring:message
			code="socialProfile.create" />
	</a>