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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${hackerLogger==true}">
	<form method="get" action="/Acme-Hacker-Rank/curricula/hacker/create.do">
		<button>
			<spring:message code="createCurricula" />
		</button>
	</form>
	<br>
</jstl:if>

<display:table name="curriculas" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<jstl:if test="${hackerLogger==true}">
		<display:column titleKey="curricula.edit">
			<a href="curricula/hacker/edit.do?curriculaId=${row.id}"><img width="35" height="35" src="./images/edit.png" alt="${row.id}" /></a>	
		</display:column>
		<display:column titleKey="curricula.delete">
			<a href="curricula/hacker/delete.do?curriculaId=${row.id}"><img width="35" height="35" src="./images/delete.png" alt="${row.id}" /></a>	
		</display:column>
	</jstl:if>
	<display:column titleKey="curricula.show">
		<a href="curricula/show.do?curriculaId=${row.id}"><img width="35" height="35" src="./images/show.png" alt="${row.id}" /></a>	
	</display:column>
	<display:column property="name" titleKey="curricula.name"></display:column>
	<display:column property="statement" titleKey="curricula.statement"></display:column>
</display:table>
<br>
<br>
<acme:cancel url="curricula/list.do?hackerId=${row.hacker.id}" code="back"/>