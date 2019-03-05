<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%-- Attributes --%>

<%@ attribute name="code" required="false"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="url" required="false" %>
<%@ attribute name="image" required="false" %>
<%@ attribute name="alt" required="false" %>
<%@ attribute name="sortable" required="false" %>
<%@ attribute name="test" required="false" %>
<%@ attribute name="formatDate" required="false" %>

<%@ attribute name="readonly" required="false"%>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>
<jstl:if test="${url == null}">
	<jstl:set var="url" value="false" />
</jstl:if>
<jstl:if test="${image == null}">
	<jstl:set var="image" value="false" />
</jstl:if>
<jstl:if test="${sortable == null}">
	<jstl:set var="sortable" value="false" />
</jstl:if>
<jstl:if test="${test == null}">
	<jstl:set var="test" value="true" />
</jstl:if>

<%-- Definition --%>

<jstl:if test="${code != null}">
	<spring:message code="${code}" var="titleKey" />
</jstl:if>
	<jstl:choose>
		<jstl:when test="${image}">
			<display:column title="${titleKey}" sortable="${sortable}">
				<jstl:if test="${test}">
					<img src="${value}" alt='<jstl:out value="${alt}" />' />
				</jstl:if>
			</display:column>
		</jstl:when>
		<jstl:when test="${url}">
			<display:column title="${titleKey}" sortable="${sortable}">
				<jstl:if test="${test}">
					<a href='<jstl:out value="${value}" />'><jstl:out value="${alt}" /></a>
				</jstl:if>
			</display:column>
		</jstl:when>
		<jstl:when test="${formatDate != null}">
			<display:column title="${titleKey}" sortable="${sortable}">
				<jstl:if test="${test}">
					<fmt:formatDate value="${value}" pattern="${formatDate}" var="dateValue" />
				</jstl:if>
			</display:column>
		</jstl:when>
		<jstl:otherwise>
			<display:column title="${titleKey}" sortable="${sortable}">
				<jstl:if test="${test}">
					<jstl:out value="${value}" />
				</jstl:if>
			</display:column>
		</jstl:otherwise>
	</jstl:choose>
	
<!-- 
	<jstl:if test="${formatDate != null}">
						<fmt:formatDate pattern = '${formatDate}' value = '${value}' />
					</jstl:if>
 -->
