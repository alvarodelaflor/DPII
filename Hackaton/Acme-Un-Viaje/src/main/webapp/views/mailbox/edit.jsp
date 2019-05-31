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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
	<div class="container-fluid">
		<form:form action="mailbox/edit.do" method="POST"
			modelAttribute="mailbox">

			<form:hidden path="id" />
			<form:hidden path="version" />

			<form:label path="name">
				<spring:message code="mailbox.name" />:
		</form:label>
			<form:input path="name" />
			<form:errors cssClass="error" path="name" />

			<br />

				
			<div class="row">
					<div class="col-md-8">
						<acme:submit name="save" code="actor.save" />
						<acme:cancel url="/mailbox/list.do"	code="back" />					
					</div>
				</div>

		</form:form>
</div>