<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<h1>
		<spring:message code="complaint.unassigned" />
	</h1>
	<display:table name="complaintsUnassigned" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag table table-hover">
		<div class="row">
			<div class="col-md-12">
				<fieldset>
					<display:column titleKey="complaint.moment" property="moment" />
					<display:column titleKey="complaint.description" property="description" />

					<display:column titleKey="complaint.actor">
						<jstl:if test="${row.host != null}">
							<jstl:out value="[${row.host.name} ${row.host.surname}]" />
						</jstl:if>
						<jstl:if test="${row.transporter != null}">
							<jstl:out value="[${row.transporter.name} ${row.transporter.surname}]" />
						</jstl:if>
						<jstl:if test="${row.travelAgency != null}">
							<jstl:out value="[${row.travelAgency.name} ${row.travelAgency.surname}]" />
						</jstl:if>
					</display:column>

					<display:column titleKey="complaint.edit">
						<a href="complaint/customer/edit.do?complaintId=${row.id}">
							<spring:message code="transport.edit" />
						</a>
					</display:column>
					<display:column titleKey="complaint.delete">
						<a href="complaint/customer/delete.do?complaintId=${row.id}">
							<spring:message code="complaint.delete" />
						</a>
					</display:column>
				</fieldset>
			</div>
		</div>
	</display:table>
	<h1>
		<spring:message code="complaint.assigned" />
	</h1>
	<display:table name="complaintsAssigned" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag table table-hover">
		<div class="row">
			<div class="col-md-12">
				<fieldset>
					<display:column titleKey="complaint.moment" property="moment" />
					<display:column titleKey="complaint.description" property="description" />
				</fieldset>
			</div>
		</div>
	</display:table>

	<br>

	<div class="row">
		<div class="col-md-12">
			<span>
				<acme:cancel url="" code="actor.back" />
			</span>
		</div>
	</div>
</div>

