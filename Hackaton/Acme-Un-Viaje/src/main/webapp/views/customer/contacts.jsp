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
	<h3>
	<spring:message code="Hosts" />
	</h3>
	<br />
	<display:table name="hosts" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag table table-hover">
		<div class="row" style="padding-left: 2.5em">
			<div class="col-md-6">
				<fieldset>
					<display:column property="name" titleKey="name" />
					<display:column property="surname" titleKey="surname" />
					<display:column property="email" titleKey="email" />
					<display:column property="phone" titleKey="phone" />
					<display:column>
						<a href="customer/rateHost.do?hostId=${row.id}"><spring:message
								code="valorate" /></a>
					</display:column>
				</fieldset>
			</div>
		</div>
	</display:table>
	<h3>
	<spring:message code="Transporters" />
	</h3>
	<br />
	<display:table name="transporters" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag table table-hover">
		<div class="row" style="padding-left: 2.5em">
			<div class="col-md-6">
				<fieldset>
					<display:column property="name" titleKey="name" />
					<display:column property="surname" titleKey="surname" />
					<display:column property="email" titleKey="email" />
					<display:column property="phone" titleKey="phone" />
					<display:column>
						<a href="customer/rateTransporter.do?transporterId=${row.id}"><spring:message
								code="valorate" /></a>
					</display:column>
				</fieldset>
			</div>
		</div>
	</display:table>
	<h3>
	<spring:message code="TravelAgencies" />
	</h3>
	<br />
	<display:table name="travelAgencies" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag table table-hover">
		<div class="row" style="padding-left: 2.5em">
			<div class="col-md-6">
				<fieldset>
					<display:column property="name" titleKey="name" />
					<display:column property="surname" titleKey="surname" />
					<display:column property="email" titleKey="email" />
					<display:column property="phone" titleKey="phone" />
					<display:column>
						<a href="customer/rateTravelAgency.do?travelAgencyId=${row.id}"><spring:message
								code="valorate" /></a>
					</display:column>
				</fieldset>
			</div>
		</div>
	</display:table>

	<div class="row">
		<div class="col-md-3">
			<span style="padding-left: 0.5em"> <acme:cancel
					url=" " code="Back" />
			</span>
		</div>
	</div>
</div>
<br>