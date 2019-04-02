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
				modelAttribute="form"  onsubmit="return phonenumberval();" action="administrator/save.do">
				
				<acme:textbox code="company.name" path="name" />
				<acme:textbox code="company.surname" path="surname" />
				<acme:textbox code="company.username" path="userName" />
				<acme:password code="company.password" path="password" />
				<acme:password code="company.passwordC" path="confirmPassword" />
				<acme:textbox code="company.address" path="address" />
				<acme:textbox code="company.email" path="email" />
				<acme:textbox code="company.photo" path="photo" />
				<acme:phonebox code="company.phone" path="phone" />
				
				<br>

				<spring:message code="company.conditions" var="termsAndConditions"/>
				<form:checkbox path="accept" label="${termsAndConditions}"/>
				<a href="company/conditions.do" target="_blank"><spring:message code="company.conditions1" /></a>
				<form:errors path="${accept}" cssClass="error" />
				
				<br>
				<br>
				<acme:submit name="save" code="company.save"/>
				<acme:cancel url=" " code="company.cancel"/>
			</form:form>
		</div>
	</article>
</section>
