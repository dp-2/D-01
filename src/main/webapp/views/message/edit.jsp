<%--
 * create.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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

<jstl:if test="${isPrincipalAuthorizedEdit}">
<security:authentication property="principal.username" var="username" />
<jstl:if test='${message.box.actor.userAccount.username == username || message.id == 0}'>

	<div>
	<jstl:if test="${message.id == 0}">
		<form:form action="message/actor/edit.do" method="post" id="formEdit"
			name="formEdit" modelAttribute="message">
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			
			<div>
					<form:label path="subject">
						<spring:message code="message.subject"></spring:message>
					</form:label>
					<form:input path="subject" />
					<form:errors cssClass="error" path="subject"></form:errors>
				</div>
				
				<div class="form-group">
					<form:label path="body">
						<spring:message code="message.body"></spring:message>
					</form:label>
					<form:textarea path="body" />
					<form:errors cssClass="error" path="body"></form:errors>
				</div>
				
				<div>
					<form:label path="priority">
						<spring:message code="message.priority"></spring:message>
					</form:label>
					<form:select path="priority">
						<form:option value="">-----</form:option>
						<form:option value="HIGH"><spring:message code="message.HIGH" /></form:option>
						<form:option value="NEUTRAL"><spring:message code="message.NEUTRAL" /></form:option>
						<form:option value="LOW"><spring:message code="message.LOW" /></form:option>
					</form:select>
					<form:errors cssClass="error" path="priority"></form:errors>
				</div>
				
				<div>
					<form:label path="tags">
						<spring:message code="message.tags"></spring:message>
					</form:label>
					<form:input path="tags" />
					<form:errors cssClass="error" path="tags"></form:errors>
				</div>
				
				<jstl:if test="${isBroadcast == false or isBroadcast == null}">
					<div>
						<form:label path="recipient">
							<spring:message code="message.receiver"></spring:message>
						</form:label>
						<form:select path="recipient">
							<form:option value="0">-----</form:option>
							<jstl:forEach items="${actors}" var="actor">
								<form:option value="${actor.id}"><jstl:out value="${actor.name} ${actor.surname}" /></form:option>
							</jstl:forEach>
						</form:select>
						<form:errors cssClass="error" path="recipient"></form:errors>
					</div>
				</jstl:if>
		
			
			<jstl:choose>
				<jstl:when test="${isBroadcast}">
					<input type="submit" name="broadcast"
						value="<spring:message code="message.broadcast"></spring:message>" />
				</jstl:when>
				<jstl:otherwise>
					<input type="submit" name="save"
						value="<spring:message code="message.save"></spring:message>" />
				</jstl:otherwise>
			</jstl:choose>
			
		</form:form>
	</jstl:if>
		
	<jstl:if test="${message.id > 0}">
		<form:form action="message/actor/edit.do" method="post" id="formEdit"
			name="formEdit" modelAttribute="message">
				
				<form:hidden path="id" />
				<form:hidden path="version" />
				
				<div>
					<form:label path="box">
						<spring:message code="message.box" />
					</form:label>
					<form:select path="box" >
						<form:option value="0">-----</form:option>
						<jstl:forEach items="${boxs}" var="box">
							<form:option value="${box.id}"><jstl:out value="${box.name}" /></form:option>
						</jstl:forEach>
					</form:select>
					<form:errors cssClass="error" path="box" />
				</div>
								
			<input type="submit" name="save" value="<spring:message code="message.save"></spring:message>" />
			<jstl:if test="${message.id > 0}">
				<input type="submit" name="delete"
					value="<spring:message code="message.delete"></spring:message>" />
			</jstl:if>
		</form:form>
	</jstl:if>
	</div>

</jstl:if>

<spring:message code="message.cancel" var="cancel"></spring:message>
<button name="cancel" value="${cancel}" onclick="javascript:relativeRedir('')" >${cancel}</button>
</jstl:if>

<jstl:if test='${message.box.actor.userAccount.username != username && message.id != 0}'>
	<h1>
		<b><spring:message code="message.permissions"></spring:message></b>
	</h1>
</jstl:if>
