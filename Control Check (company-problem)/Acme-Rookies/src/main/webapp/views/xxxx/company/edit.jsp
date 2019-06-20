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
			<form:form class="formularioEdicion" method="POST" modelAttribute="XXXX" action="${URI}">
				<form:input path="id" type="hidden" />

				<acme:textbox code="xxxx.body" path="body" />
				<acme:textbox code="xxxx.picture" path="picture" />
	
				<jstl:if test="${creating}">
					<acme:select items="${problems}" itemLabel="title" code="xxxx.problem" path="problem"/>
				</jstl:if>
				<table style="width: 3em">
					<tr>
						<th><spring:message code="xxxx.draftMode" /></th>
						<td><spring:message code="xxxx.draftMode.true" /> <form:radiobutton path="draftMode" value="true" checked="checked" /></td>
						<td><spring:message code="xxxx.draftMode.false" /> <form:radiobutton path="draftMode" value="false" /></td>
						<td><br> <input type="button" value="<spring:message code='problem.delete' />" name="problem.delete"
							onclick="window.location = 'xxxx/company/delete.do?xxxxId=${XXXX.id}'" /></td>
					</tr>
				</table>

				<acme:submit name="save" code="problem.save" />
				<input type="button" value="<spring:message code='problem.back' />" onclick="window.location = 'xxxx/company/list.do'" />
			</form:form>
		</div>
	</article>
</section>
