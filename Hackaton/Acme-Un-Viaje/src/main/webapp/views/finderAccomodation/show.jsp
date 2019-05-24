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

<div class="container-fluid">
<form:form class="formularioEdicion" modelAttribute="finder" method="POST" action="finder/travelAgency/refresh.do">
	<form:hidden path="id" />
	<strong>
		<form:label path="keyword"><spring:message code="finder.keyword" />:</form:label>
	</strong>
	<form:input path="keyword" />	
	<form:errors path="keyword" cssClass="error" />
	
	<strong>
	<form:label path="price"><spring:message code="finder.price" />:</form:label>
	</strong>
	<form:input type="number" path="price"/>	
	<form:errors path="price" cssClass="error" />
	
	
	<strong>
	<form:label path="people"><spring:message code="finder.people" />:</form:label>
	</strong>
	<form:input type="number" path="people"/>	
	<form:errors path="people" cssClass="error" />
	
	
	<strong>
	<form:label path="address"><spring:message code="finder.address" />:</form:label>
	</strong>	
	<form:input path="address"/>	
	<form:errors path="address" cssClass="error" />
	
	
	<button type="submit" class="btn btn-primary">
		<spring:message code="finder.refresh" />
	</button>
</form:form>
<jstl:if test="${finderError == 'finderError'}">
	<p class="error"><spring:message code="finder.error"/></p>
</jstl:if>

	<display:table name="finder.accomodations" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag table table-hover">
		<div class="row">
			<div class="col-md-12">
				<fieldset>
					<display:column titleKey="accomodation.place"
						property="place" />
					<display:column titleKey="accomodation.pricePerNight" property="pricePerNight" />
					<display:column titleKey="accomodation.rating" property="rating" />
					<display:column titleKey="accomodation.maxPeople" property="maxPeople" />
					<display:column titleKey="transport.show">
						<a href="accomodation/travelAgency/show.do?accomodationId=${row.id}">
							<spring:message code="transport.show" />
						</a>
					</display:column>
					<display:column titleKey="baccomodation.book">
						<a href="bookingAccomodation/travelAgency/create.do?accomodationId=${row.id}">
							<spring:message code="baccomodation.book" />
						</a>
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