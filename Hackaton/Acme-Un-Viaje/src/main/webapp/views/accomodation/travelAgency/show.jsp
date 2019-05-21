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
		<div class="col-md-4">
			<fieldset>
				<div class="row">
					<spring:message code="accomodation.host" />
					:
					<jstl:out value="${accomodation.host.name}"></jstl:out>
				</div>
				<div class="row">
					<spring:message code="accomodation.pricePerNight" />
					:
					<jstl:out value="${accomodation.pricePerNight}"></jstl:out>
					EUR
				</div>
				<div class="row">
					<spring:message code="accomodation.description" />
					:
					<jstl:out value="${accomodation.description}"></jstl:out>
				</div>
				
				<div class="row">
					<spring:message code="accomodation.address" />
					:
					<jstl:out value="${accomodation.address}"></jstl:out>
				</div>
				<div class="row">
					<display:table pagesize="5" name="${pictures}" id="picture"
						requestURI="${requestURI}">
						<display:column titleKey="accomodation.pictures">
							<img width="300" src="${picture.trim()}" alt="<spring:message code = 'error.pictures' />" />
						</display:column>
					</display:table>
				</div>
			</fieldset>
		</div>
	</div>

	<br>

	<div class="row">
		<div class="col-md-4">
			<acme:cancel url="accomodation/travelAgency/list.do" code="actor.back" />
		</div>
	</div>
</div>
