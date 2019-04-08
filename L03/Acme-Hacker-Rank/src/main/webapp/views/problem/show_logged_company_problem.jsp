<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${problem.finalMode}">
<div class="content">
	<table>
		<tr><td><strong><spring:message code="problem.title" />: </strong><jstl:out value="${problem.title}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="problem.statement" />: </strong><jstl:out value="${problem.statement}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="problem.hint" />: </strong><jstl:out value="${problem.hint}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="problem.attachments" />: </strong><jstl:out value="${problem.attachments}"/></td></tr>
		<tr><td><strong><spring:message code="problem.finalMode" />: </strong><jstl:out value="${problem.finalMode}"></jstl:out></td></tr>
	</table>
</div>
<input type="button" value="<spring:message code='problem.back' />" name="problem.back" onclick="window.location = 'problem/company/list.do'" />
</jstl:if>
<!-- FORM IN CASE THIS IS NOT IN FINAL MODE -->
<jstl:if test="${not problem.finalMode}">
	<section id="main-content">
		<article>
			<div class="content">
				<form:form class="formularioEdicion" method="POST"
					modelAttribute="problem" action="problem/company/edit.do">
					<form:input path="id" type="hidden"/>
					
					<acme:textbox code="problem.title" path="title" />
					<acme:textbox code="problem.statement" path="statement" />
					<acme:textbox code="problem.hint" path="hint"/>
					<acme:textbox code="problem.attachments" path="attachments"/>
					
					<table style="width: 3em">
						<tr>
							<th><spring:message code="problem.finalMode"/></th>
							<td>
							<spring:message code="problem.finalMode.true"/>
							<form:radiobutton path="finalMode" value="true"/> 
							</td>
							<td>
							<spring:message code="problem.finalMode.false"/>
							<form:radiobutton path="finalMode" value="false" checked="checked"/> 
							</td>
							<td>
							<br>
							<input type="button" value="<spring:message code='problem.delete' />" name="problem.delete" onclick="window.location = 'problem/company/delete.do?problemId=${problem.id}'" />
							</td>
						</tr>
					</table>
					
					<acme:submit name="save" code="problem.save"/>
					<input type="button" value="<spring:message code='problem.back' />" onclick="window.location = 'problem/company/list.do'" />
				</form:form>
			</div>
		</article>
	</section>
</jstl:if>