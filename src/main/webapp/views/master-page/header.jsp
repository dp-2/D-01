<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!--//BLOQUE COOKIES-->
<div id="barraaceptacion">
	<div class="inner">
		<spring:message code="aviso.cookies" />
		<a href="javascript:void(0);" class="ok" onclick="PonerCookie();"><b>OK</b></a>
		<a href="law/politicaCookies.do" target="_blank" class="info"><spring:message
				code="aviso.cookies.information" /></a>
	</div>
</div>

<script>
	function getCookie(c_name) {
		var c_value = document.cookie;
		var c_start = c_value.indexOf(" " + c_name + "=");
		if (c_start == -1) {
			c_start = c_value.indexOf(c_name + "=");
		}
		if (c_start == -1) {
			c_value = null;
		} else {
			c_start = c_value.indexOf("=", c_start) + 1;
			var c_end = c_value.indexOf(";", c_start);
			if (c_end == -1) {
				c_end = c_value.length;
			}
			c_value = unescape(c_value.substring(c_start, c_end));
		}
		return c_value;
	}

	function setCookie(c_name, value, exdays) {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + exdays);
		var c_value = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
		document.cookie = c_name + "=" + c_value;
	}

	if (getCookie('tiendaaviso') != "1") {
		document.getElementById("barraaceptacion").style.display = "block";
	} else {
		document.getElementById("barraaceptacion").style.display = "none";
	}

	function PonerCookie() {
		setCookie('tiendaaviso', '1', 365);
		document.getElementById("barraaceptacion").style.display = "none";
	}
</script>
<!--//FIN BLOQUE COOKIES-->


<div align="center">
	<a href="#"><img src="${banner}" alt="Acme-Madruga Co., Inc."
		width="450px" height="200px" /></a>
</div>

<form:form action="${requestURI}" modelAttribute="warning">
	<security:authorize access="isAuthenticated()">
		<jstl:if test="${warning.isWarning == true}">
			<img
				src="https://www.pngkey.com/png/full/133-1338651_svg-black-and-white-library-icon-clip-art.png"
				height="50px" width="50px" />

			<h3>
				<span style='font-family: "Arial", "sans-serif"; color: black'><spring:message
						code="master.page.warning" /> </span>

			</h3>
		</jstl:if>



	</security:authorize>
</form:form>

<div align="center">
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv" href="configuration/administrator/list.do"><spring:message
						code="master.page.configuration" /></a></li>
			<li><a class="fNiv" href="administrator/dashboard.do"><spring:message
						code="master.page.dashboard" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/scores.do"><spring:message
								code="master.page.administrator.calculateScores" /></a></li>
								
					<li><a href="administrator/spammers.do"><spring:message
								code="master.page.administrator.calculateSpammers" /></a></li>
				</ul></li>
			<li><a href="actor/administrator/list.do"><spring:message
						code="master.page.administrator.actors" /></a></li>
			<li><a href="register/administrator/newActor.do?authority=ADMIN"><spring:message
						code="master.page.register.admin" /></a></li>
			<li><a class="fNiv" href="position/administrator/list.do"><spring:message
						code="master.page.position" /></a></li>
			<li><a class="fNiv" href="area/list.do"><spring:message
						code="master.page.area" /></a></li>
			<li><a class="fNiv"
				href="message/actor/create.do?isBroadcast=true"><spring:message
						code="master.page.broadcast" /></a></li>
			<jstl:if test="${warning.isWarning == true}">
				<li><a href="administrator/adviseFalse.do"><spring:message
							code="advise.unBreach" /></a></li>

			</jstl:if>

			<jstl:if test="${warning.isWarning == false}">
				<li><a href="administrator/adviseTrue.do"><spring:message
							code="advise.breach" /></a></li>


			</jstl:if>



		</security:authorize>

		<security:authorize access="hasRole('MEMBER')">

			<li><a class="fNiv" href="march/member/list.do"><spring:message
						code="master.page.march" /></a></li>
			<li><a class="fNiv" href="enroll/member/list.do"><spring:message
						code="master.page.enroll" /></a></li>

			<li><a class="fNiv"><spring:message
						code="master.page.finder" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="finder/member/update.do"><spring:message
								code="master.page.finder.update" /></a></li>
					<li><a href="finder/member/list.do"><spring:message
								code="master.page.finder.result" /></a></li>

				</ul></li>

		</security:authorize>

		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv" href="area/list.do"><spring:message
						code="master.page.area" /></a></li>
			<li><a class="fNiv" href="enroll/brotherhood/list.do"><spring:message
						code="master.page.enroll" /></a></li>
			<li><a class="fNiv" href="dfloat/brotherhood/list.do"><spring:message
						code="master.page.dfloat" /></a></li>



			<li><a class="fNiv" href="brotherhood/brotherhood/display.do"><spring:message
						code="brotherhood.display" /></a></li>
		</security:authorize>


		<security:authorize access="permitAll">
			<li><a href="brotherhood/any/list.do"><spring:message
						code="master.page.listbrotherhood" /></a>
				<ul>
					<security:authorize access="hasRole('MEMBER')">
						<li class="arrow"></li>
						<li><a href="brotherhood/member/myList.do"><spring:message
									code="master.page.mybrotherhood" /></a>
					</security:authorize>
				</ul></li>
			<security:authorize access="hasRole('BROTHERHOOD')">
				<li><a class="fNiv" href="procession/list.do"><spring:message
							code="master.page.processions" /></a>
					<ul>

						<li class="arrow"></li>
						<li><a href="procession/brotherhood/myList.do"><spring:message
									code="master.page.processions.brotherhood" /></a></li>


					</ul>
			</security:authorize>
		</security:authorize>


		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="register/actor.do?authority=MEMBER"><spring:message
								code="master.page.register.member" /></a></li>


					<li><a href="brotherhood/none/create.do"><spring:message
								code="master.page.register.brotherhood" /></a></li>


				</ul></li>

		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a href="socialProfile/list.do"> <spring:message
						code="master.page.socialProfile" />
			</a>
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="box/actor/list.do"><spring:message
								code="master.page.listboxes" /> </a></li>
					<li><a href="message/actor/create.do"><spring:message
								code="master.page.messagecreate" /></a></li>
					<security:authorize access="hasRole('MEMBER')">
						<li><a href="actor/edit.do"><spring:message
									code="master.page.profile.edit" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="actor/edit.do"><spring:message
									code="master.page.profile.edit" /></a></li>
					</security:authorize>
					
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div align="center">
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

