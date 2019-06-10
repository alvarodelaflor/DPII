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


<section id="main-content">
	<article>
		<div class="content">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="problem" action="problem/company/create.do">
				
				<acme:textbox code="problem.title" path="title" />
				<acme:textbox code="problem.statement" path="statement" />
				<acme:textbox code="problem.hint" path="hint"/>
				<acme:textbox code="problem.attachments" path="attachments"/>

				<br>
				<acme:submit name="save" code="problem.save"/>
				<input type="button" value="<spring:message code='problem.cancel' />" onclick="window.location = 'problem/company/list.do'" />
			</form:form>
		</div>
	</article>
</section>
