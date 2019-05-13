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

<br>
<div class="container-fluid">
	<form:form class="formularioEdicion" method="POST"
		modelAttribute="registerActor" onsubmit="return phonenumberval();"
		action="cleaner/create.do">
		<div class="row" style="padding-left: 40px">
			<div class="col-md-5">
				<fieldset>

					<h2>
						<spring:message code="actor.personalData" />
					</h2>
					<acme:textbox code="actor.name" path="name" cssLabel="col-md-3" placeholder="Carmen"
						cssInput="col-md-6" />
					<acme:textbox code="actor.surname" path="surname" placeholder="Fernández Benjumea"
						cssLabel="col-md-3" cssInput="col-md-6" />
					<acme:textbox code="actor.username" path="userName" placeholder="carferben"
						cssLabel="col-md-3" cssInput="col-md-6" />
					<acme:password code="actor.password" path="password" 
						cssLabel="col-md-3" cssInput="col-md-6" />
					<acme:password code="actor.passwordC" path="confirmPassword" 
						cssLabel="col-md-3" cssInput="col-md-6" />
					<acme:textbox code="actor.birthDate" path="birthDate"
						placeholder="1998/11/12" cssLabel="col-md-3" cssInput="col-md-6" />
					<acme:textbox code="actor.email" path="email"
						placeholder="ej: viaje@gmail.com" cssLabel="col-xs-3 col-md-3"
						cssInput="col-md-6" />
					<acme:textbox code="actor.photo" path="photo" cssLabel="col-md-3"
						cssInput="col-md-6" placeholder="http://"/>
					<acme:phonebox code="actor.phone" path="phone" cssLabel="col-md-3"
						cssInput="col-md-6"  />
					<br>

				</fieldset>
			</div>

			<div class="col-md-7">
				<fieldset>
					<h2>
						<spring:message code="actor.creditCard" />
					</h2>
					<acme:textbox code="actor.holder" path="holder"
						placeholder="Sr. Benjumea" cssLabel="col-md-3" cssInput="col-md-6" />
					<acme:textbox code="actor.make" path="make" placeholder="VISA"
						cssLabel="col-md-3" cssInput="col-md-6" />
					<acme:textbox code="actor.number" path="number"
						placeholder="1234567890987654" cssLabel="col-md-3"
						cssInput="col-md-6" />
					<acme:textbox code="actor.CVV" path="CVV" placeholder="123"
						cssLabel="col-md-3" cssInput="col-md-6" />
					<acme:textboxExpiration code="actor.expiration" path="expiration"
						cssLabel="col-md-3" cssInput="col-md-6" />
					<br>
				</fieldset>
				<br> <br>
			</div>
			<fieldset>
				<spring:message code="actor.conditions" var="termsAndConditions" />
				<form:checkbox path="accept" label="${termsAndConditions}" />
				<a href="actor/conditions.do" target="_blank"><spring:message
						code="actor.conditions1" /></a>
				<form:errors path="accept" cssClass="error" />
			</fieldset>
			<br>
		</div>
		<div class="row" style="padding-left: 50px">
			<acme:submit name="save" code="actor.save" />
			<acme:cancel url=" " code="actor.cancel" />
		</div>
	</form:form>
</div>
