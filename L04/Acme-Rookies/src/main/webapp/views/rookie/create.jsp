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
				modelAttribute="registrationForm"  onsubmit="return phonenumberval();" action="rookie/create.do">

				<fieldset>
				<legend>
					<spring:message code="actor.personalDate" />
				</legend>
				<acme:textbox code="rookie.name" path="name"/>
				<acme:textbox code="rookie.surname" path="surname" />
				<acme:textbox code="rookie.username" path="userName" />
				<acme:password code="rookie.password" path="password" />
				<acme:password code="rookie.passwordC" path="confirmPassword" />
				<acme:textbox code="rookie.address" path="address" />
				<acme:textbox code="rookie.email" path="email" placeholder="ej: benju12@gmail.com" />
				<acme:textbox code="rookie.photo" path="photo" />
				<acme:phonebox code="rookie.phone" path="phone" />
				</fieldset>
				<br>
				<fieldset>
				<legend>
					<spring:message code="actor.creditCard" />
				</legend>
				<acme:textbox code="rookie.holder" path="holder" placeholder="Do�a Benjumea"/>
				<acme:textbox code="rookie.make" path="make" placeholder="VISA"/>
				<acme:textbox code="rookie.number" path="number" placeholder="1234567890987654" />
				<acme:textbox code="rookie.CVV" path="CVV" placeholder="123"/>
				<acme:textboxExpiration code="rookie.expiration" path="expiration" />
				</fieldset>
				<br>
				<fieldset>
				<legend>
					<spring:message code="actor.vatNumer" />
				</legend>
				<acme:textbox code="actor.vat" path="vatNumber" placeholder="CA1234567r"/>	
				</fieldset>
				<br>
				<spring:message code="rookie.conditions" var="termsAndConditions"/>
				<form:checkbox path="accept" label="${termsAndConditions}"/>
				<a href="actor/conditions.do" target="_blank"><spring:message code="rookie.conditions1" /></a>
				<form:errors path="${accept}" cssClass="error" />
				<br>
				<br>					
				<acme:submit name="save" code="rookie.save"/>
				<acme:cancel url=" " code="rookie.cancel"/>
			</form:form>
		</div>
	</article>
</section>