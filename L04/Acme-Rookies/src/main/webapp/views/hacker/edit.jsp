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
		<security:authorize access="hasRole('HACKER')"> 
		<div class="content">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="hacker"  onsubmit="return phonenumberval();" action="hacker/edit.do">
				
				<form:hidden path="id" />
				

				
				<acme:textbox code="hacker.name" path="name" />
				<acme:textbox code="hacker.surname" path="surname" />
				<acme:textbox code="hacker.address" path="address" />
				<acme:textbox code="hacker.email" path="email" />
				<acme:textbox code="hacker.photo" path="photo" />
				<acme:phonebox code="hacker.phone" path="phone" />
					
				<acme:submit name="saveEdit" code="hacker.save"/>
				<acme:cancel url=" " code="hacker.cancel"/>
			</form:form>
		</div>
		</security:authorize>
	</article>
</section>
