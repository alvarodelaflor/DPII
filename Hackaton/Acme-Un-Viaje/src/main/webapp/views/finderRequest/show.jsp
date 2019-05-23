<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid">
<form:form class="formularioEdicion" modelAttribute="finder" method="POST" action="finderRequest/travelAgency/refresh.do">
	<form:hidden path="id" />
	<strong>
		<form:label path="place"><spring:message code="finderRequest.place" />:</form:label>
	</strong>
	<form:input path="place" />	
	<form:errors path="place" cssClass="error" />
	
	<strong>
	<form:label path="price"><spring:message code="finderRequest.price" />:</form:label>
	</strong>
	<form:input type="number" path="price"/>	
	<form:errors path="price" cssClass="error" />
	
	<strong>
	<form:label path="startDate"><spring:message code="finder.startDate" />:</form:label>
	</strong>
	<form:input  path="startDate"/>	
	<form:errors path="startDate" cssClass="error" />
	
	
	<button type="submit" class="btn btn-primary">
		<spring:message code="finder.refresh" />
	</button>
</form:form>
<jstl:if test="${finderError == 'finderError'}">
	<p class="error"><spring:message code="finder.error"/></p>
</jstl:if>

<display:table name="requests" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag table table-hover">
		<div class="row" style="padding-left: 2.5em">
			<div class="col-md-6">
				<fieldset>
					<display:column titleKey="request.place">
						<jstl:out value="${row.destination}"></jstl:out>
					</display:column>
					<display:column titleKey="request.placeOrigin">
						<jstl:out value="${row.origin}"></jstl:out>
					</display:column>
					<display:column titleKey="request.numberOfPeople">
						<jstl:out value="${row.numberOfPeople}"></jstl:out>
					</display:column>
					<display:column titleKey="request.maxPrice">
						<jstl:out value="${row.maxPrice}"></jstl:out>
					</display:column>
					<display:column titleKey="request.startDate">
						<jstl:out value="${row.startDate}"></jstl:out>
					</display:column>
					<display:column titleKey="request.endDate">
						<jstl:out value="${row.endDate}"></jstl:out>
					</display:column>
				</fieldset>
			</div>
		</div>
	</display:table>
	<br>

	<div class="row">
		<div class="col-md-3">
			<span> <acme:cancel url="welcome/index.do" code="actor.back" />
			</span>
		</div>
	</div>
</div>