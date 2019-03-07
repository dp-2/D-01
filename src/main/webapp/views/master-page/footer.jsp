<%--
 * footer.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<jsp:useBean id="date" class="java.util.Date" />

<hr />

<div>
	<a href="law/terminosYCondiciones.do"><spring:message
			code="master.page.terminosYCondiciones" /></a> <img
		src="images/padlock.ico" alt="Sample-Project-1.9 Co., Inc." width="15"
		height="15" /> <a href="law/politicaCookies.do"><spring:message
			code="master.page.politicaCookies" /></a> <img src="images/cookie.ico"
		width="20" height="20" /> <a href="law/avisoLegal.do"><spring:message
			code="master.page.avisoLegal" /></a> <img src="images/law.ico"
		width="15" height="15" />
		
	<a href="law/normativaGDPR.do"><spring:message
			code="master.page.normativaGDPR" /></a> <img src="images/GDPR.ico"
		width="15" height="15" />

</div>

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" />
	Acme-Madruga Co., Inc.
</b>