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

<div class="content">
	<table>
		<c:choose>
    		<c:when test="${administrator.photo==''}">
				<tr><td><spring:message code="admin.photo" /><br>
    		</c:when>    
    		<c:otherwise>
				<tr><td><spring:message code="admin.photo" /><br>
				<img width="95" src="${administrator.photo}" alt="ERROR"/></td></tr>
    		</c:otherwise>
		</c:choose>
		<tr><td><strong><spring:message code="admin.name" /></strong> <jstl:out	value="${administrator.name}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="admin.surname" /></strong> <jstl:out value="${administrator.surname}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="admin.address" /></strong> <jstl:out value="${administrator.address}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="admin.email" /></strong> <jstl:out value="${administrator.email}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="admin.phone" /></strong> <jstl:out value="${administrator.phone}"></jstl:out></td></tr>
	</table>
</div>
<input type="button" value="<spring:message code="admin.cancel" />" name="admin.cancel" onclick="history.back()" />

