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
			<form:form class="formularioEdicion" method="POST" modelAttribute="form" action="bookingAccomodation/travelAgency/create.do">
				<form:hidden path="accomodation" />
				<fieldset>
					<hr>
					<acme:textbox code="baccomodation.startDate" path="startDate" placeholder ="yyyy/MM/dd" cssError="col-md-5" cssLabel="col-md-3" cssInput="col-md-4" />
					<acme:textbox code="baccomodation.endDate" path="endDate" placeholder ="yyyy/MM/dd" cssError="col-md-5" cssLabel="col-md-3" cssInput="col-md-4" />
					<form:label path="travelPackId">
						<spring:message code="baccomodation.travelPack" />:
					</form:label>
					<form:select itemLabel="name" itemValue="id" multiple="false" path="travelPackId">
	   		 		<form:options items="${packs}" itemLabel="name" itemValue="id" required="required"/>
					</form:select>
					<form:errors cssClass="error" path="travelPackId" />
				</fieldset>
				<br>
				<div class="row">
					<span><acme:submit name="save" code="actor.save" /></span> <span style="padding-left: 0.5em"> <acme:cancel url="/finder/travelAgency/show.do" code="actor.back" />
					</span>
				</div>
			</form:form>
		</div>
	</div>
	<br>
</div>