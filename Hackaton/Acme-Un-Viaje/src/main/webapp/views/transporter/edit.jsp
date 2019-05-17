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
		modelAttribute="transporter" onsubmit="return phonenumberval();"
		action="transporter/edit.do">


		<form:hidden path="id" />

		<div class="row" style="padding-left: 2.5em">
			<div class="col-md-6">
				<fieldset>
					<h2>
						<spring:message code="actor.personalData" />&nbsp;<i class="fas fa-mobile-alt icon-gradient"></i>
					</h2>
					<hr>
					<acme:textbox code="actor.name" path="name" cssLabel="col-md-3"
						placeholder="Carmen" cssInput="col-md-5" cssError="col-md-4" />
					<acme:textbox code="actor.surname" path="surname"
						placeholder="Fernández Benjumea" cssLabel="col-md-3"
						cssInput="col-md-5" cssError="col-md-4" />
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
						<spring:message code="actor.creditCard" />&nbsp;<i class="far fa-credit-card icon-gradient"></i>
					</h2>
					<hr>
					<acme:textbox code="actor.holder" path="creditCard.holder"
						placeholder="Sr. Benjumea" cssLabel="col-md-3" cssInput="col-md-4"
						cssError="col-md-4" />
					<acme:textbox code="actor.make" path="creditCard.make" placeholder="VISA"
						cssLabel="col-md-3" cssInput="col-md-4" cssError="col-md-4" />
					<acme:textbox code="actor.number" path="creditCard.number"
						placeholder="1234567890987654" cssLabel="col-md-3"
						cssError="col-md-4" cssInput="col-md-4" />
					<acme:textbox code="actor.CVV" path="creditCard.CVV" placeholder="123"
						cssError="col-md-4" cssLabel="col-md-3" cssInput="col-md-4" />
					<acme:textboxExpiration code="actor.expiration" path="creditCard.expiration"
						cssError="col-md-4" cssLabel="col-md-3" cssInput="col-md-4" />
				</fieldset>
			</div>
		</div>

		<br>

		<div class="row">
			<div class="col-md-3">
				<span style="padding-left: 2.5em"> <acme:submit
						name="saveEdit" code="actor.save" />
				</span> <span style="padding-left: 0.5em"> <acme:cancel url=""
						code="actor.cancel" />
				</span>
			</div>
		</div>
	</form:form>

</div>
