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
				modelAttribute="registrationForm"  onsubmit="return phonenumberval();" action="company/create.do">
				
				<fieldset>
				<legend>
					<spring:message code="actor.personalDate" />
				</legend>
				<acme:textbox code="company.name" path="name" />
				<acme:textbox code="company.surname" path="surname" />
				<acme:textbox code="company.username" path="userName" />
				<acme:password code="company.password" path="password" />
				<acme:password code="company.passwordC" path="confirmPassword" />
				<acme:textbox code="company.address" path="address" />
				<acme:textbox code="company.email" path="email" />
				<acme:textbox code="company.photo" path="photo" />
				<acme:phonebox code="company.phone" path="phone" />
				<acme:phonebox code="company.comercialName" path="companyName" />
				</fieldset>
				<br>
				<fieldset>
				<legend>
					<spring:message code="actor.creditCard" />
				</legend>
				<acme:textbox code="company.holder" path="holder" />
				<acme:textbox code="company.make" path="make" />
				<acme:textbox code="company.number" path="number" />
				<acme:textbox code="company.CVV" path="CVV" />
				<acme:textboxExpiration code="company.expiration" path="expiration" />
				</fieldset>
				<br>
				<spring:message code="company.conditions" var="termsAndConditions"/>
				<form:checkbox path="accept" label="${termsAndConditions}"/>
				<a href="actor/conditions.do" target="_blank"><spring:message code="company.conditions1" /></a>
				<form:errors path="${accept}" cssClass="error" />
				<br>
				<br>
				<acme:submit name="save" code="company.save"/>
				<acme:cancel url=" " code="company.cancel"/>
			</form:form>
		</div>
	</article>
</section>