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
				modelAttribute="registrationForm"  onsubmit="return phonenumberval();" action="auditor/create.do">
				
				<fieldset>
				<legend>
					<spring:message code="actor.personalDate" />
				</legend>
				<acme:textbox code="auditor.name" path="name" />
				<acme:textbox code="auditor.surname" path="surname" />
				<acme:textbox code="auditor.username" path="userName" />
				<acme:password code="auditor.password" path="password" />
				<acme:password code="auditor.passwordC" path="confirmPassword" />
				<acme:textbox code="auditor.address" path="address" />
				<acme:textbox code="auditor.email" path="email" />
				<acme:textbox code="auditor.photo" path="photo" />
				<acme:phonebox code="auditor.phone" path="phone" />
				</fieldset>
				<br>
				<fieldset>
				<legend>
					<spring:message code="actor.creditCard" />
				</legend>
				<acme:textbox code="auditor.holder" path="holder" />
				<acme:textbox code="auditor.make" path="make" />
				<acme:textbox code="auditor.number" path="number" />
				<acme:textbox code="auditor.CVV" path="CVV" />
				<acme:textboxExpiration code="auditor.expiration" path="expiration" />
				</fieldset>
				<br>
				<fieldset>
				<legend>
					<spring:message code="actor.vatNumer" />
				</legend>
				<acme:textbox code="actor.vat" path="vatNumber" placeholder="CA1234567r"/>	
				</fieldset>
				<br>
				<spring:message code="auditor.conditions" var="termsAndConditions"/>
				<form:checkbox path="accept" label="${termsAndConditions}"/>
				<a href="actor/conditions.do" target="_blank"><spring:message code="auditor.conditions1" /></a>
				<form:errors path="${accept}" cssClass="error" />
				<br>
				<br>
				<acme:submit name="save" code="auditor.save"/>
				<acme:cancel url=" " code="auditor.cancel"/>
			</form:form>
		</div>
	</article>
</section>