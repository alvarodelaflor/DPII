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
				modelAttribute="warranty" action="warranty/travelAgency/edit.do">
				<form:hidden path="id" />
				<fieldset>
					<acme:textbox code="warranty.title" path="title"
						placeholder="garnatia0001" cssError="col-md-6" cssLabel="col-md-2"
						cssInput="col-md-4" />

					<acme:textbox code="warranty.terms" path="terms"
						placeholder="terminos00001" cssError="col-md-6"
						cssLabel="col-md-2" cssInput="col-md-4" />

					<spring:message code="warranty.finalMode" />
					&nbsp; &nbsp; &nbsp; &nbsp;
					<spring:message code="warranty.finalMode.true" />
					<form:radiobutton path="draftMode" value="true" />
					<spring:message code="warranty.finalMode.false" />
					<form:radiobutton path="draftMode" value="false" checked="checked" />

				</fieldset>
				<br>
				<div class="row">
					<div class="col-md-8">
						<acme:submit name="saveEdit" code="actor.save" />
						<acme:cancel url="/warranty/travelAgency/list.do"
							code="actor.cancel" />
					</div>

				</div>
			</form:form>
		</div>
	</div>
</div>
