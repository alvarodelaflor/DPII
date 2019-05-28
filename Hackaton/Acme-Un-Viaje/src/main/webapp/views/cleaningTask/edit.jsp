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
		<div class="col-md-6" style="padding-left: 2.5em">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="cleaningTask" action="cleaningTask/create.do">
				<form:hidden path="id" />
				<fieldset>

					<acme:textbox code="cleaningTask.description" path="description"
						cssError="col-md-6" cssLabel="col-md-2" cssInput="col-md-4" />

					<form:label path="cleaner">
						<spring:message code="cleaningTask.cleaner" />:
					</form:label>
					<form:select multiple="true" path="cleaner">

						<form:options items="${cleaners}" />
					</form:select>
					<form:errors cssClass="error" path="cleaner" />

					<br>

					<form:label path="accomodation">
						<spring:message code="cleaningTask.accomodation" />:
					</form:label>
					<form:select multiple="true" path="accomodation">
						<form:options items="${accomodations}" />
					</form:select>
					<form:errors cssClass="error" path="accomodation" />

					<acme:textbox code="cleaningTask.startMoment" path="startMoment"
						placeholder="2025/02/03 15:00" cssError="col-md-6"
						cssLabel="col-md-2" cssInput="col-md-4" />

					<acme:textbox code="cleaningTask.endMoment" path="endMoment"
						placeholder="2025/02/04 15:00" cssError="col-md-6"
						cssLabel="col-md-2" cssInput="col-md-4" />


				</fieldset>
				<br>
				<div class="row">
					<div class="col-md-8">
						<acme:submit name="save" code="actor.save" />
						<span style="padding-left: 0.5em"> <acme:cancel url=""
								code="actor.back" />
						</span>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
