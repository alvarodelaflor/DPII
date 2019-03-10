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

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<div class="content">
	<div>
		<form:form class="formularioEdicion" modelAttribute="historyFinderForm" method="POST" action="history/list.do">	
			<acme:textbox code="history.title" path="title"/>
			<acme:textbox code="history.name" path="name"/>
			
			<acme:submit name="save" code="send"/>
		</form:form>
	</div>
	<display:table name="brotherhoods" id="row1" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<display:column titleKey="show"> 
			<a href="history/show.do?brotherhoodId=${row1.id}">${row1.title}</a>
		</display:column>
		<display:column property="name" titleKey="brotherhoodName"></display:column>
	</display:table>
	<div>
		<form method="get" action="#">
			<button type="submit">
				<spring:message code="back" />
			</button>
		</form>
	</div>
</div>