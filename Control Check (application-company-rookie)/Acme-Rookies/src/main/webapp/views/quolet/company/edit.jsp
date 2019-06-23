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

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<section id="main-content">
	<article>
		<div class="content">
			<form:form class="formularioEdicion" method="POST" modelAttribute="quolet" action="${URI}">
				<form:hidden path="id" />
				<form:hidden path="version" />
				<form:hidden path="application"/>

				<acme:textbox code="quolet.body" path="body" />
				<acme:textbox code="quolet.picture" path="picture" />

				<table style="width: 3em">
					<tr>
						<th><spring:message code="quolet.draftMode" /></th>
						<td><spring:message code="quolet.draftMode.true" /> <form:radiobutton path="draftMode" value="true" checked="checked" /></td>
						<td><spring:message code="quolet.draftMode.false" /> <form:radiobutton path="draftMode" value="false" /></td>
						<jstl:if test="${quolet.id != 0}">
							<td><br> <input type="button" value="<spring:message code='problem.delete' />" name="problem.delete"
								onclick="window.location = 'quolet/company/delete.do?quoletId=${quolet.id}'" /></td>
						</jstl:if>
					</tr>
				</table>

				<acme:submit name="save" code="quolet.save" />
				<input type="button" value="<spring:message code='problem.back' />" onclick="window.location = 'quolet/company/list.do?applicationId=${quolet.application.id}'" />
			</form:form>
		</div>
	</article>
</section>
