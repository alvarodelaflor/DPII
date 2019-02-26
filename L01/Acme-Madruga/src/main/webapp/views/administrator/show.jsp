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

<security:authorize access="hasRole('ADMIN')"> 
<div class="content">
	<table>
		<tr><td><spring:message code="admin.photo" /><br>
		<img width="95" src="${administrator.photo}" alt=<jstl:out value="${administrator.photo}"></jstl:out> /></td></tr>
		<tr><td><spring:message code="admin.name" /> <jstl:out	value="${administrator.name}"></jstl:out></td></tr>
		<tr><td><spring:message code="admin.middleName" /> <jstl:out	value="${administrator.middleName}"></jstl:out></td></tr>		
		<tr><td><spring:message code="admin.surname" /> <jstl:out value="${administrator.surname}"></jstl:out></td></tr>
		<tr><td><spring:message code="admin.address" /> <jstl:out value="${administrator.address}"></jstl:out></td></tr>
		<tr><td><spring:message code="admin.phone" /> <jstl:out value="${administrator.phone}"></jstl:out></td></tr>
		<tr><td><spring:message code="admin.email" /> <jstl:out value="${administrator.email}"></jstl:out></td></tr>
		<tr><td><spring:message code="admin.username" /> <jstl:out value="${administrator.userAccount.username}"></jstl:out></td></tr>
	</table>
</div>
</security:authorize>
<acme:cancel url=" " code="cancel"/> 


