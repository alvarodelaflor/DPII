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
<div class="row" style="padding-left: 2em">
	<div class="col-md-3">
		<div class="card">
			<img class="card-img-top" src="${registerActor.photo}" alt="ERROR">
			<div class="card-body">
				<h4 class="card-title">${registerActor.name}
					${registerActor.surname}</h4>
			</div>
		</div>
	</div>

	<div class="col-md-4">
		<fieldset>
			<h2>
				<spring:message code="actor.personalData" />
			</h2>
			<hr>

			<div class="row"  style="padding-left: 1.5em">
				<strong><spring:message code="actor.name"/>:&nbsp;</strong>
				<jstl:out value="${registerActor.name}"></jstl:out>
			</div>

			<div class="row" style="padding-left: 1.5em">
				<strong><spring:message code="actor.surname" />:&nbsp;</strong>
				<jstl:out value="${registerActor.surname}"></jstl:out>
			</div>

			<div class="row" style="padding-left: 1.5em">
				<strong><spring:message code="actor.email" />:&nbsp;</strong>
				<jstl:out value="${registerActor.email}"></jstl:out>
			</div>

			<div class="row" style="padding-left: 1.5em">
				<strong><spring:message code="actor.phone" />:&nbsp;</strong>
				<jstl:out value="${registerActor.phone}"></jstl:out>
			</div>

			<div class="row" style="padding-left: 1.5em">
				<acme:displayDate date="${registerActor.birthDate}" code="actor.birthDate"/>
			</div>
		</fieldset>
	</div>

	<div class="col-md-4">
		<fieldset>
			<h2>
				<spring:message code="actor.creditCard" />
			</h2>
			<hr>
			<div class="row" style="padding-left: 1.5em">
				<strong><spring:message code="actor.holder" />:&nbsp;</strong>
				<jstl:out value="${registerActor.creditCard.holder}"></jstl:out>
			</div>

			<div class="row" style="padding-left: 1.5em">
				<strong><spring:message code="actor.make" />:&nbsp;</strong>
				<jstl:out value="${registerActor.creditCard.make}"></jstl:out>
			</div>
			<div class="row" style="padding-left: 1.5em">
				<strong><spring:message code="actor.number" />:&nbsp;</strong>
				<jstl:out value="${registerActor.creditCard.number}"></jstl:out>
			</div>

			<div class="row" style="padding-left: 1.5em">
				<strong><spring:message code="actor.CVV" />:&nbsp;</strong>
				<jstl:out value="${registerActor.creditCard.CVV}"></jstl:out>
			</div>
			<div class="row" style="padding-left: 1.5em">
				<strong><spring:message code="actor.expiration" />:&nbsp;</strong>
				<jstl:out value="${registerActor.creditCard.expiration}"></jstl:out>
			</div>
		</fieldset>
	</div>
</div>


<br>

<div class="row">
	<div class="col-md-4">
		<span style="padding-left: 2.5em"> <acme:cancel url=""
				code="actor.cancel" />
		</span>
	</div>
</div>

