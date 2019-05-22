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
<div class="container-fluid">
	<form:form class="formularioEdicion" method="POST"
		modelAttribute="registerActor" onsubmit="return phonenumberval();"
		action="host/create.do">
		<div class="row" style="padding-left: 2.5em">
			<div class="col-md-6">
				<fieldset>
					<h2>
						<spring:message code="actor.personalData" />
						&nbsp;<i class="fas fa-mobile-alt icon-gradient"></i>
					</h2>
					<hr>
					<acme:textbox code="actor.name" path="name" cssLabel="col-md-3"
						placeholder="Carmen" cssInput="col-md-5" cssError="col-md-4" />
					<acme:textbox code="actor.surname" path="surname"
						placeholder="Fernández Benjumea" cssLabel="col-md-3"
						cssInput="col-md-5" cssError="col-md-4" />
					<acme:textbox code="actor.username" path="userName"
						placeholder="carferben" cssLabel="col-md-3" cssError="col-md-4"
						cssInput="col-md-5" />
					<acme:password code="actor.password" path="password"
						placeholder="********" cssLabel="col-md-3" cssInput="col-md-5"
						cssError="col-md-4" />
					<acme:password code="actor.passwordC" path="confirmPassword"
						placeholder="********" cssLabel="col-md-3" cssInput="col-md-5"
						cssError="col-md-4" />
					<acme:textbox code="actor.birthDate" path="birthDate"
						placeholder="1998/11/12" cssLabel="col-md-3" cssInput="col-md-5"
						cssError="col-md-4" />
					<acme:textbox code="actor.email" path="email"
						placeholder="ej: viaje@gmail.com" cssLabel="col-xs-3 col-md-3"
						cssError="col-md-4" cssInput="col-md-5" />
					<acme:textbox code="actor.photo" path="photo" cssLabel="col-md-3"
						cssError="col-md-4" cssInput="col-md-5" placeholder="http://" />
					<acme:phonebox code="actor.phone" path="phone" cssLabel="col-md-3"
						cssError="col-md-4" placeholder="678543267" cssInput="col-md-5" />
				</fieldset>
			</div>

			<div class="col-md-6">
				<fieldset>
					<h2>
						<spring:message code="actor.creditCard" />
						&nbsp;<i class="far fa-credit-card icon-gradient"></i>
					</h2>
					<hr>
					<acme:textbox code="actor.holder" path="holder"
						placeholder="Sr. Benjumea" cssLabel="col-md-3" cssInput="col-md-4"
						cssError="col-md-4" />

					<div class="row">
						<div class="col-md-3">
							<form:label path="make">
								<spring:message code="actor.make" />:
							</form:label>
						</div>
						<div class="col-md-4">
							<form:select multiple="false" path="make" cssStyle="width: 100%">
								<form:options items="${makes}" required="required" />
							</form:select>
						</div>
					</div>

					<acme:textbox code="actor.number" path="number"
						placeholder="1234567890987654" cssLabel="col-md-3"
						cssError="col-md-4" cssInput="col-md-4" />
					<acme:textbox code="actor.CVV" path="CVV" placeholder="123"
						cssError="col-md-4" cssLabel="col-md-3" cssInput="col-md-4" />
					<acme:textboxExpiration code="actor.expiration" path="expiration"
						cssError="col-md-4" cssLabel="col-md-3" cssInput="col-md-4" />
				</fieldset>
			</div>
		</div>

		<br>

		<div class="row" style="padding-left: 2.5em">
			<div class="col-md-12">
				<fieldset>
					<spring:message code="actor.conditions" var="termsAndConditions" />
					<form:checkbox path="accept" label="${termsAndConditions}" />
					<a href="actor/conditions.do" target="_blank"><spring:message
							code="actor.conditions1" /></a>
					<form:errors path="accept" cssClass="error" />
				</fieldset>
			</div>
		</div>
		<div class="row">
			<div class="col-md-3">
				<span style="padding-left: 2.5em"> <acme:submit name="save"
						code="actor.save" />
				</span> <span style="padding-left: 0.5em"> <acme:cancel url=""
						code="actor.cancel" />
				</span>
			</div>
		</div>
	</form:form>
</div>
