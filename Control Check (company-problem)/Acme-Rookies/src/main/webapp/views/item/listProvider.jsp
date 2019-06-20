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


	<form method="get" action="item/provider/create.do">
		<button>
			<spring:message code="createItem" />
		</button>
	</form>
	<br>

<display:table name="items" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<display:column titleKey="item.edit">
			<a href="item/provider/edit.do?itemId=${row.id}"><img width="35" height="35" src="./images/edit.png" alt="${row.id}" /></a>	
		</display:column>
		<display:column titleKey="item.delete">
			<a href="item/provider/delete.do?itemId=${row.id}"><img width="35" height="35" src="./images/delete.png" alt="${row.id}" /></a>	
		</display:column>
	<display:column titleKey="item.show">
		<a href="item/show.do?itemId=${row.id}"><img width="35" height="35" src="./images/show.png" alt="${row.id}" /></a>	
	</display:column>
	<display:column property="name" titleKey="item.name"></display:column>
	<display:column property="description" titleKey="item.description"></display:column>
</display:table>
<br>
<br>
<c:choose>
		<acme:cancel url="/welcome/index.do" code="back"/>
</c:choose>