<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<section id="main-content">
	<article>
		<div class="content">
				<form:form class="formularioEdicion" method="POST"
				modelAttribute="registrationForm" onsubmit="return phonenumberval();"  action="sponsor/create.do">

				<acme:textbox code="sponsor.name" path="name" />
				<acme:textbox code="sponsor.surname" path="surname" />
				<acme:textbox code="sponsor.middleName" path="middleName" />
				<acme:textbox code="sponsor.username" path="userName" />
				<acme:password code="sponsor.password" path="password" />
				<acme:password code="sponsor.passwordC" path="confirmPassword" />
				<acme:textbox code="sponsor.address" path="address" />
				<acme:textbox code="sponsor.email" path="email" /> 					
				<acme:textbox code="sponsor.photo" path="photo" />
				<acme:phonebox code="sponsor.phone" path="phone"/>
				
				<br>
				
				<spring:message code="conditions" var="termsAndConditions"/>
				<form:checkbox path="accept" label="${termsAndConditions}"/>
				<a href="brotherhood/conditions.do" target="_blank"><spring:message code="conditions1" /></a>
				<form:errors path="${accept}" cssClass="error" />
				
				<br>
				<br>												
				<acme:submit name="save" code="save"/>
				<acme:cancel url=" " code="cancel"/>
				
				
			</form:form>
		</div>
	</article>
</section>

