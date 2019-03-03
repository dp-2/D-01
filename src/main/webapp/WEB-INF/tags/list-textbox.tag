<%--
 * textbox.tag
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="items" required="true" %>
<%@ attribute name="fieldsetMessage" required="true" %>
<%@ attribute name="removeCode" required="true" %>
<%@ attribute name="addCode" required="true" %>
<%@ attribute name="urlRemove" required="true" %>
<%@ attribute name="urlAdd" required="true" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>

<%-- Definition --%>

<div>
	<fieldset><legend><spring:message code="${fieldsetMessage}" /></legend>
		<jstl:forEach begin="0" end="${items.size}" var="iter" varStatus="iterStatus">
			<acme:textbox path="${path}[${iterStatus.index}]" code="${code}" />
			<button onclick="${urlRemove + '?position=' + iter}" >${removeCode}</button>
			<br>
		</jstl:forEach>
		<button onclick="${urlAdd}" >${addCode}</button>
	</fieldset>
</div>
