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
			<form:form class="formularioEdicion" method="POST" modelAttribute="transport" action="transport/transporter/edit.do">
				<form:hidden path="id" />
				<fieldset>
					<hr>
					<acme:textbox code="transport.numberOfPlaces" path="numberOfPlaces" placeholder="4" cssError="col-md-5" cssLabel="col-md-3" cssInput="col-md-4" />

					<acme:textbox code="transport.price" path="price" placeholder="25" cssError="col-md-5" cssLabel="col-md-3" cssInput="col-md-4" />

					<acme:textbox code="transport.date" path="date" placeholder="2021/12/11 13:30" cssError="col-md-5" cssLabel="col-md-3" cssInput="col-md-4" />

					<acme:textbox code="transport.vehicleType" path="vehicleType" placeholder="Car" cssError="col-md-5" cssLabel="col-md-3" cssInput="col-md-4" />

					<acme:textbox code="transport.origin" path="origin" placeholder="Sevilla" cssError="col-md-5" cssLabel="col-md-3" cssInput="col-md-4" />

					<acme:textbox code="transport.destination" path="destination" placeholder="Valencia" cssError="col-md-5" cssLabel="col-md-3" cssInput="col-md-4" />
				</fieldset>
				<br>
				<div class="row">
					<span><acme:submit name="save" code="actor.save" /></span> <span style="padding-left: 0.5em"> <acme:cancel url="/transport/transporter/list.do" code="actor.back" />
					</span>
				</div>
			</form:form>
		</div>
	</div>
	<br>
</div>