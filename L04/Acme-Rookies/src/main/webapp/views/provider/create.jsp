<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>	
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<section id="main-content">
	<article>
		<div class="content">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="registrationForm"  onsubmit="return phonenumberval();" action="provider/create.do">

				<fieldset>
				<legend>
					<spring:message code="actor.personalDate" />
				</legend>
				<acme:textbox code="provider.name" path="name"/>
				<acme:textbox code="provider.surname" path="surname" />
				<acme:textbox code="provider.username" path="userName" />
				<acme:password code="provider.password" path="password" />
				<acme:password code="provider.passwordC" path="confirmPassword" />
				<acme:textbox code="provider.comercialName" path="companyName" />
				<acme:textbox code="provider.address" path="address" />
				<acme:textbox code="provider.email" path="email" placeholder="ej: benju12@gmail.com"/>
				<acme:textbox code="provider.photo" path="photo" />
				<acme:phonebox code="provider.phone" path="phone" />
				</fieldset>
				<br>
				<fieldset>
				<legend>
					<spring:message code="actor.creditCard" />
				</legend>
				<acme:textbox code="provider.holder" path="holder" placeholder="Doña Benjumea"/>
				<acme:textbox code="provider.make" path="make" placeholder="VISA"/>
				<acme:textbox code="provider.number" path="number" placeholder="1234567890987654"/>
				<acme:textbox code="provider.CVV" path="CVV" placeholder="123"/>
				<acme:textboxExpiration code="provider.expiration" path="expiration" />
				</fieldset>
				<br>
				<fieldset>
				<legend>
					<spring:message code="actor.vatNumer" />
				</legend>
				<acme:textbox code="actor.vat" path="vatNumber" placeholder="CA1234567r"/>	
				</fieldset>
				<br>
				<spring:message code="provider.conditions" var="termsAndConditions"/>
				<form:checkbox path="accept" label="${termsAndConditions}"/>
				<a href="actor/conditions.do" target="_blank"><spring:message code="provider.conditions1" /></a>
				<form:errors path="${accept}" cssClass="error" />
				<br>
				<br>					
				<acme:submit name="save" code="provider.save"/>
				<acme:cancel url=" " code="provider.cancel"/>
			</form:form>
		</div>
	</article>
</section>