<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<section id="main-content">
	<article>
		<div class="content">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="position" action="position/company/create.do">
				
				<acme:textbox code="position.title" path="title" />
				<acme:textbox code="position.description" path="description" />
				<acme:numberbox code="position.salary" path="salary"/>
				<acme:textbox code="position.deadline" path="deadline" placeholder="2021/12/25 15:30"/>
				<acme:textbox code="position.profile" path="profile" />
				<acme:textbox code="position.skills" path="skills" />
				<acme:textbox code="position.techs" path="techs" />
				
				<br>
				<acme:submit name="save" code="position.save"/>
				<input type="button" value="<spring:message code='position.cancel' />" onclick="window.location = 'position/company/list.do'" />
			</form:form>
		</div>
	</article>
</section>
