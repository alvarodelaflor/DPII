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

<br>
<section id="main-content">
	<article>
		<div class="content">
			<form:form class="formularioEdicion" method="POST" modelAttribute="registerActor"  onsubmit="return phonenumberval();" action="cleaner/create.do">
    			<div class="col-md-9">
       			 	<div class="row" style="padding-left:40px" >
       			 		<fieldset>
							<legend>
								<h2><spring:message code="actor.personalData" /></h2>
									<acme:textbox code="actor.name" path="name"/>
									<acme:textbox code="actor.surname" path="surname" />
									<acme:textbox code="actor.username" path="userName" />
									<acme:password code="actor.password" path="password" />
									<acme:password code="actor.passwordC" path="confirmPassword" />
									<acme:textbox code="actor.bornDate" path="bornDate" />
									<acme:textbox code="actor.email" path="email" />
									<acme:textbox code="actor.photo" path="photo" />
									<acme:phonebox code="actor.phone" path="phone" />
								<br>
							</legend>
						</fieldset>
					<div class="row align-items-center" style=" margin:50px">				
						<fieldset>
							<legend>
								<h2><spring:message code="actor.creditCard" /></h2>
									<acme:textbox code="actor.holder" path="holder" />
									<acme:textbox code="actor.make" path="make" />
									<acme:textbox code="actor.number" path="number" />
									<acme:textbox code="actor.CVV" path="CVV" />
									<acme:textboxExpiration code="actor.expiration" path="expiration" />
								<br>
							</legend>
						</fieldset>
					</div>
				<br>
				<br>
				</div>
					<fieldset>
						<legend>
							<spring:message code="actor.conditions" var="termsAndConditions"/>
							<form:checkbox path="accept" label="${termsAndConditions}"/>
							<a href="actor/conditions.do" target="_blank"><spring:message code="actor.conditions1" /></a>
							<form:errors path="${accept}" cssClass="error" />
						</legend>
					</fieldset>
				<br>
					<acme:submit name="save" code="actor.save"/>
					<acme:cancel url=" " code="actor.cancel"/>
				</div>
			</form:form>
		</div>
	</article>
</section>
