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
				modelAttribute="actorForm"  onsubmit="return phonenumberval();" action="administrator/create.do">
				
				<fieldset>
				<legend>
					<spring:message code="actor.personalDate" />
				</legend>
				<acme:textbox code="admin.name" path="name" />
				<acme:textbox code="admin.surname" path="surname" />
				<acme:textbox code="admin.username" path="userName" />
				<acme:password code="admin.password" path="password" />
				<acme:password code="admin.passwordC" path="confirmPassword" />
				<acme:textbox code="admin.address" path="address" />
				<acme:textbox code="admin.email" path="email" />
				<acme:textbox code="admin.photo" path="photo" />
				<acme:phonebox code="admin.phone" path="phone" />
				<br>
				</fieldset>
				<fieldset>
				<legend>
					<spring:message code="actor.creditCard" />
				</legend>
				<acme:textbox code="admin.holder" path="holder" />
				<acme:textbox code="admin.make" path="make" />
				<acme:textbox code="admin.number" path="number" />
				<acme:textbox code="admin.CVV" path="CVV" />
				<acme:textboxExpiration code="admin.expiration" path="expiration" />
				</fieldset>
				<br>
				<fieldset>
				<legend>
					<spring:message code="actor.vatNumer" />
				</legend>
				<acme:textbox code="actor.vat" path="vatNumber" placeholder="CAR1234567r"/>	
				</fieldset>
				<br>
				<spring:message code="admin.conditions" var="termsAndConditions"/>
				<form:checkbox path="accept" label="${termsAndConditions}"/>
				<a href="actor/conditions.do" target="_blank"><spring:message code="admin.conditions1" /></a>
				<form:errors path="${accept}" cssClass="error" />
				<br>
				<br>
				<acme:submit name="save" code="admin.save"/>
				<acme:cancel url=" " code="admin.cancel"/>
			</form:form>
		</div>
	</article>
</section>
