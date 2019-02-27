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



<display:table name="members" id="member"
	requestURI="member/brotherhood/list.do" pagesize="5"
	class="displaytag">


		<%--  La columna que va a la vista display de las member --%>
		<display:column>
			<a
				href="member/display.do?memberId=${member.id}"><spring:message
					code="member.display"></spring:message></a>
		</display:column>

	
			
		<acme:column code="member.name" value="${ member.name}"></acme:column>	
		<acme:column code="member.surname" value="${ member.surname}"></acme:column>
		<acme:column code="member.middleName" value="${ member.middleName}" ></acme:column>



</display:table>

		<acme:cancel url="procession/list.do" code="member.back" />






