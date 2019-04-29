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

<jstl:if test="${providerLogger==true}">
	<form method="get" action="item/provider/create.do">
		<button>
			<spring:message code="createCurricula" />
		</button>
	</form>
	<br>
</jstl:if>

<display:table name="items" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<jstl:if test="${providerLogger==true}">
		<display:column titleKey="item.edit">
			<a href="item/provider/edit.do?itemId=${row.id}"><img width="35" height="35" src="./images/edit.png" alt="${row.id}" /></a>	
		</display:column>
		<display:column titleKey="item.delete">
			<a href="item/provider/delete.do?itemId=${row.id}"><img width="35" height="35" src="./images/delete.png" alt="${row.id}" /></a>	
		</display:column>
	</jstl:if>
	<display:column titleKey="item.show">
		<a href="item/show.do?itemId=${row.id}"><img width="35" height="35" src="./images/show.png" alt="${row.id}" /></a>	
	</display:column>
	<display:column property="name" titleKey="item.name"></display:column>
	<display:column property="statement" titleKey="item.statement"></display:column>
</display:table>
<br>
<br>
<c:choose>
	<c:when test="${providerLogger==true}">
		<acme:cancel url="provider/show.do?providerId=${item.provider.id}" code="back"/>
	</c:when>
	<c:otherwise>
		<input type="button" value=<spring:message code="item.back" /> name="item.back" onclick="history.back()" />
	</c:otherwise>
</c:choose>