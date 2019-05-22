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

<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<display:table name="accomodations" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag table table-hover">
		<div class="row" style="padding-left: 2.5em">
			<div class="col-md-6">
				<fieldset>
					<display:column titleKey="accomodation.address">
						<jstl:out value="${row.address}"></jstl:out>
					</display:column>

					<display:column titleKey="accomodation.description">
						<jstl:out value="${row.description}"></jstl:out>
					</display:column>

					<display:column titleKey="accomodation.maxPeople">
						<jstl:out value="${row.maxPeople}"></jstl:out>
					</display:column>

					<display:column titleKey="accomodation.place">
						<jstl:out value="${row.place}"></jstl:out>
					</display:column>

					<display:column titleKey="accomodation.pricePerNight">
						<jstl:out value="${row.pricePerNight}"></jstl:out>
					</display:column>

					<display:column titleKey="accomodation.rating">
						<jstl:out value="${row.rating}"></jstl:out>
					</display:column>

					<display:column titleKey="accomodation.show">
								<a href="accomodation/host/show.do?accomodationId=${row.id}"><spring:message
										code="accomodation.show" /></a>
					</display:column>
				</fieldset>
			</div>
		</div>
	</display:table>
</div>
<br>

<div class="row">
	<div class="col-md-3">
		<span style="padding-left: 2.5em"> <acme:create
				url="accomodation/host/create.do" name="buttonWarranty"
				code="accomodation.create" />
		</span> <span style="padding-left: 0.5em"> <acme:cancel url=""
				code="actor.back" />
		</span>
	</div>
</div>

