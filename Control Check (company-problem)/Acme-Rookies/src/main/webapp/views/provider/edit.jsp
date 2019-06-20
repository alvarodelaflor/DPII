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
		<security:authorize access="hasRole('PROVIDER')"> 
		<div class="content">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="provider"  onsubmit="return phonenumberval();" action="provider/edit.do">
				
				<form:hidden path="id" />
				
				<acme:textbox code="provider.name" path="name" />
				<acme:textbox code="provider.surname" path="surname" />
				<acme:textbox code="provider.address" path="address" />
				<acme:textbox code="provider.email" path="email" />
				<acme:textbox code="provider.photo" path="photo" />
				<acme:phonebox code="provider.phone" path="phone" />
				<acme:textbox code="provider.comercialName" path="commercialName" />
				
					
				<acme:submit name="saveEdit" code="provider.save"/>
				<acme:cancel url=" " code="provider.cancel"/>
			</form:form>
		</div>
		</security:authorize>
	</article>
</section>
