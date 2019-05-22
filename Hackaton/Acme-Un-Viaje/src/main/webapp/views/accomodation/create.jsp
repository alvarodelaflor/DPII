<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<div class="row">
		<div class="col-md-6" style="padding-left: 2.5em">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="accomodation" action="accomodation/host/create.do">
				<form:hidden path="id" />
				<fieldset>
					<acme:textbox code="accomodation.address" path="address"
						placeholder="C/Lora del rio" cssInput="col-md-5"
						cssError="col-md-4" cssLabel="col-md-3"/>

					<acme:textbox code="accomodation.description" path="description"
						placeholder="Dime algo" cssInput="col-md-5" cssError="col-md-4"
						cssLabel="col-md-3" />

					<acme:textbox code="accomodation.pictures" path="pictures"
						placeholder="http://" cssInput="col-md-5" cssError="col-md-4"
						cssLabel="col-md-3" />

					<acme:textbox code="accomodation.place" path="place"
						placeholder="Carmona" cssInput="col-md-5" cssError="col-md-4"
						cssLabel="col-md-3" />

					<acme:textbox code="accomodation.maxPeople" path="maxPeople"
						placeholder="4" cssInput="col-md-5" cssError="col-md-4"
						cssLabel="col-md-3" />

					<acme:textbox code="accomodation.rating" path="rating"
						placeholder="20.0" cssInput="col-md-5" cssError="col-md-4"
						cssLabel="col-md-3" />

					<acme:textbox code="accomodation.pricePerNight"
						path="pricePerNight" placeholder="200" cssInput="col-md-5"
						cssError="col-md-4" cssLabel="col-md-3" />

				</fieldset>
				<br>
				<div class="row">
					<div class="col-md-8">
						<acme:submit name="save" code="actor.save" />
						<acme:cancel url="/accomodation/host/list.do" code="actor.cancel" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
