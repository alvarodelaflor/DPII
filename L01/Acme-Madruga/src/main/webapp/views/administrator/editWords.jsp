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
<security:authorize access="hasRole('ADMIN')"> 
		<div class="content">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="wordList" action="administrator/editWords.do">
			
				<form:hidden path="negWords"/>
				<form:hidden path="negWordsEs"/>
			
				<acme:textarea code="enWords" path="posWords" />
				<acme:textarea code="esWords" path="posWordsEs" />
				
				<acme:submit name="save" code="save"/>
				<acme:cancel url="administrator/actorList" code="back"/>
			</form:form>
		</div>
</security:authorize>
	</article>
</section>

