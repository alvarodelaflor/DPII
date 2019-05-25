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


		<c:choose>
			<c:when
				test="${accomodation.pictures == null or accomodation.pictures=='' }">
				<div class="col-md-3">
					<div class="card">
						<img class="card-img-top"
							src="https://image.flaticon.com/icons/png/512/8/8721.png"
							alt="ERROR">
						<div class="card-body">
							<h4 class="card-title">${accomodation.place}</h4>
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="col-md-3">
					<div class="card">
						<img class="card-img-top" src="${accomodation.pictures}"
							alt="ERROR">
						<div class="card-body">
							<h4 class="card-title">${accomodation.place}</h4>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>

		<div class="col-md-6">
			<fieldset>
				<h2>
					<spring:message code="accomodation.information" />
					&nbsp;<i class="fas fa-home icon-gradient"></i>
				</h2>
				<hr>

				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="accomodation.place" />:&nbsp;</strong>
					<jstl:out value="${accomodation.place}"></jstl:out>
				</div>

				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="accomodation.address" />:&nbsp;</strong>
					<jstl:out value="${accomodation.address}"></jstl:out>
				</div>

				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="accomodation.description" />:&nbsp;</strong>
					<jstl:out value="${accomodation.description}"></jstl:out>
				</div>

				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="accomodation.maxPeople" />:&nbsp;</strong>
					<jstl:out value="${accomodation.maxPeople}"></jstl:out>
				</div>

				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="accomodation.pricePerNight" />:&nbsp;</strong>
					<jstl:out value="${accomodation.pricePerNight}"></jstl:out>
				</div>

				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="accomodation.rating" />:&nbsp;</strong>
					<jstl:out value="${accomodation.rating}"></jstl:out>
				</div>
			</fieldset>
		</div>
	</div>

	<br>

	<div class="row">
		<div class="col-md-6">
			<span style="padding-left: 0.5em"> <acme:cancel
					url="host/customerList.do?accomodationId=${accomodation.id}"
					code="myCustomers" />
			</span>
			<span style="padding-left: 0.5em"> <acme:cancel
					url="host/cleanerList.do?accomodationId=${accomodation.id}"
					code="myCleaners" />
			</span>
			<c:choose>
				<c:when test="${not res}">
					<span style="padding-left: 0.5em"> <acme:create
							url="accomodation/host/edit.do?accomodationId=${accomodation.id}"
							code="accomodation.edit" name="delete" />
					</span>
					<span style="padding-left: 0.5em"> <acme:delete
							url="accomodation/host/delete.do?accomodationId=${accomodation.id}"
							code="accomodation.delete" name="delete" />
					</span>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			<span style="padding-left: 0.5em"> <acme:cancel
					url="accomodation/host/list.do" code="actor.back" />
			</span>
		</div>
	</div>
</div>
