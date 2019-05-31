<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<div class="row">

		<div class="col-md-12">
			<div class="row">
				<strong><spring:message code="complaint.customer" />:&nbsp;</strong>
				<jstl:out value="${complaint.customer.name}"></jstl:out>
			</div>
			<div class="row">
				<strong><spring:message code="complaint.travelAgency" />:&nbsp;</strong>
				<jstl:out value="${complaint.travelAgency.name}"></jstl:out>
			</div>
			<div class="row">
				<strong><spring:message code="complaint.host" />:&nbsp;</strong>
				<jstl:out value="${complaint.host.name}"></jstl:out>
			</div>
			<div class="row">
				<strong><spring:message code="complaint.transporter" />:&nbsp;</strong>
				<jstl:out value="${complaint.transporter.name}"></jstl:out>
			</div>
			<div class="row">
				<strong><spring:message code="complaint.moment" />:&nbsp;</strong>
				<jstl:out value="${complaint.moment}"></jstl:out>
			</div>
			<div class="row">
				<strong><spring:message code="complaint.description" />:&nbsp;</strong>
				<jstl:out value="${complaint.description}"></jstl:out>
			</div>
		</div>
	</div>



	<div class="row">
		<div class="col-md-6">
		<br>
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="review"
				action="review/create.do?complaintId=${complaint.id}">
				<form:hidden path="id" />
				<fieldset>
					<acme:textbox code="review.description" path="description"
						cssError="col-md-6" cssLabel="col-md-2" cssInput="col-md-4" />
				</fieldset>
				<br>
				<div class="row">
					<div class="col-md-6">
						<acme:submit name="save" code="actor.save" />
						<span style="padding-left: 0.5em"> <acme:cancel url="/review/list.do"
							code="back" />
						</span>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
