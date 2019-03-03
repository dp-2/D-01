<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:hidden path="id" />
<form:hidden path="version" />
<form:hidden path="userAccount" />

<acme:out code="member.name" value="${member.name}"/>
<acme:out code="member.middleName" value="${member.middleName}"/>
<acme:out code="member.surname" value="${member.surname}"/>
<acme:out code="member.photo" value="${member.photo}"/>
<acme:out code="member.email" value="${member.email}"/>

<acme:out code="member.phone" value="${member.phone}"/>
<acme:out code="member.address" value="${member.address}"/>

<security:authorize access="hasRole('ADMIN')">
<acme:out code="member.spammer" value="${member.spammer}"/>
<acme:out code="member.score" value="${member.score}"/>
</security:authorize>




<security:authentication property="principal.username" var="username" />
	<jstl:if
		test='${customer.userAccount.username == username || customer.id == 0}'>
		
<input type="button" name="edit" value="<spring:message code="member.edit"></spring:message>" onclick="javascript:relativeRedir('member/brotherhood/edit.do?memberId=${member.id}')"/>	
	</jstl:if>

<input type="button" name="cancel" value="<spring:message code="member.cancel"></spring:message>" onclick="javascript:relativeRedir('member/brotherhood/list.do')" />	










