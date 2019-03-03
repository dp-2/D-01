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

<%-- <form:hidden path="id" />
<form:hidden path="version" /> --%>
<%-- <form:hidden path="brotherhood" />
 --%>
<acme:out code="area.title" value="${area.name}"/>
<%-- <acme:out code="area.pictures" value="${area.pictures}"/>
 --%>
	<jstl:forEach items="${pictures}" var="picture">
	  <br />
	      <div class="inline-block">
	  
  		<img src="${picture.url} "  style="width:600px;height:400px; display: inline-block;">
  		    </div>
  		
  	   <br />
  	  
	</jstl:forEach>

  	  <br />
  	  <br />
  	  <br />



 <security:authentication property="principal.username" var="username" />
	<jstl:if test='${brotherhood.userAccount.username == username || brotherhood.id == 0}'>
			<jstl:if test='${area.brotherhood eq null}'>
		
		
<input type="button" name="create" value="<spring:message code="area.create"></spring:message>" onclick="javascript:relativeRedir('area/edit.do?areaId=${area.id}')"/>	
		</jstl:if>
	</jstl:if>











