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

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<div class="row">
		<div class="col-md-6">
			<form:form class="formularioEdicion" method="POST" modelAttribute="complaint" action="complaint/customer/create.do">
				<form:hidden path="id" />
				<input type="hidden" id="travelPackId" name="travelPackId" value="${travelPackId}" />
				<fieldset>
					<acme:textbox code="complaint.description" path="description" placeholder="This is a description" cssError="col-md-4" cssLabel="col-md-3" cssInput="col-md-5" />
					<div class="row">
						<div class="col-md-3">
							<spring:message code="complaint.travelAgency" />
						</div>
						<div class="col-md-5">
							<form:select multiple="false" path="travelAgency" cssStyle="width: 100%">
								<form:option value="0" label="-----------" />
								<form:option value="${travelAgency.id}" label="${travelAgency.name} ${travelAgency.surname}" />
							</form:select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3">
							<spring:message code="complaint.host" />
						</div>
						<div class="col-md-5">
							<form:select multiple="false" path="host" cssStyle="width: 100%">
								<form:option value="0" label="-----------" />
								<c:forEach var="host" items="${hosts}">
									<form:option value="${host.id}">
										<c:out value="${host.name} ${host.surname}" />
									</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3">
							<spring:message code="complaint.transporter" />
						</div>
						<div class="col-md-5">
							<form:select multiple="false" path="transporter" cssStyle="width: 100%">
								<form:option value="0" label="-----------" />
								<c:forEach var="transporter" items="${transporters}">
									<form:option value="${transporter.id}">
										<c:out value="${transporter.name} ${transporter.surname}" />
									</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</fieldset>
				<br>
				<div class="row" style="padding-left: 0.5em">
					<span>
						<acme:submit name="save" code="actor.save" />
					</span>
					<span style="padding-left: 0.5em">
						<acme:cancel url="/complaint/customer/list.do" code="actor.back" />
					</span>
				</div>
			</form:form>
		</div>
	</div>
	<br>
</div>