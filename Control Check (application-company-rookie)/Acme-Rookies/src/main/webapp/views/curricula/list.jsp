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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jstl:if test="${rookieLogger==true}">
	<form method="get" action="curricula/rookie/create.do">
		<button>
			<spring:message code="createCurricula" />
		</button>
	</form>
	<br>
</jstl:if>

<display:table name="curriculas" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<jstl:if test="${rookieLogger==true}">
		<display:column titleKey="curricula.edit">
			<a href="curricula/rookie/edit.do?curriculaId=${row.id}"><img width="35" height="35" src="./images/edit.png" alt="${row.id}" /></a>	
		</display:column>
		<display:column titleKey="curricula.delete">
			<a href="curricula/rookie/delete.do?curriculaId=${row.id}"><img width="35" height="35" src="./images/delete.png" alt="${row.id}" /></a>	
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
<c:choose>
	<c:when test="${rookieLogger==true}">
		<acme:cancel url="rookie/show.do?rookieId=${curricula.rookie.id}" code="back"/>
	</c:when>
	<c:otherwise>
		<input type="button" value=<spring:message code="curricula.back" /> name="curricula.back" onclick="history.back()" />
	</c:otherwise>
</c:choose>