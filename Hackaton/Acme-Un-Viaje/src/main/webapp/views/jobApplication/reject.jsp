<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="styles/common.css" type="text/css">
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<div class="col-md-3">
	<div class="rounded float-left">
	    <img src="https://www.onlygfx.com/wp-content/uploads/2016/09/red-rejected-stamp-1.png" alt="Curricula"  width="250"/>
	</div>
</div>

<form:form class="formularioEdicion" method="POST" onsubmit="return phonenumberval();" modelAttribute="jobApplication" action="jobApplication/host/reject.do">
   	<form:hidden path="id"/>
   	<form:hidden path="version"/>
	<h3>
		<spring:message code="jobApplication.principalMessage1" /><jstl:out value="${host.name}"></jstl:out>!
		<small class="text-muted">
			<spring:message code="jobApplication.rejectReasonMessage" />
		</small>
	</h3>
	<div class="row">
		<div class="col-md-12">
			<div class="form-group">
				<spring:message htmlEscape="false" code="jobApplication.rejectMessage" var="placeholder5" />
				<form:textarea class="form-control" path="rejectMessage" placeholder="${placeholder5}"/>
				<form:errors path="rejectMessage" cssClass="error" />
			</div>
		</div>
	</div>
	
	<div class="row" style="padding-left: 1.0em">
		<p class="lead">
			<spring:message htmlEscape="false" code="jobApplication.thanks" />
		</p>
	</div>
	<div class="row">
		<div class="col-md-12">
			<span style="padding-left: 0.0em"> <acme:submit
					name="save" code="actor.save" />
			</span> <span style="padding-left: 0.5em"> <acme:cancel url="jobApplication/host/list.do"
					code="actor.cancel" />
			</span>
		</div>
	</div>

</form:form>