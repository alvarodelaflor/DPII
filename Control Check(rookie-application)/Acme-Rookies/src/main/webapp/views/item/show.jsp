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

<div>
		<jstl:if test="${owner==true}">
			<acme:cancel url="item/provider/delete.do?itemId=${item.id}" code="item.delete"/>
			<form method="get" action="item/provider/edit.do">
				<button name="itemId" value="${item.id}"><spring:message code="item.edit"/></button>
			</form>
			<br>	
		</jstl:if>
	</div>

<div class="content">
	<table>
		<tr><td><strong><spring:message code="item.name" />:</strong> <jstl:out	value="${item.name}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="item.description" />:</strong> <jstl:out value="${item.description}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="item.link" />:</strong> <jstl:out value="${item.link}"></jstl:out></td></tr>
	</table>
</div>
<br>

<display:table pagesize="5" name="${pictures}" id="picture"
	requestURI="${requestURI}">
	<display:column titleKey="item.pictures">
		<img width="300" src="${picture.trim()}" alt="Error" >
	</display:column>
</display:table>
<input type="button" value=<spring:message code="item.back" /> name="item.back" onclick="history.back()" />

