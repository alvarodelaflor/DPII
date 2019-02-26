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

<security:authorize access="hasRole('MEMBER')"> 
<div class="content">
	<table>
		<tr><td><spring:message code="member.photo" /><br>
		<img width="95" src="${member.photo}" alt="Error" /></td></tr>
		<tr><td><spring:message code="member.name" /> <jstl:out	value="${member.name}"></jstl:out></td></tr>
		<tr><td><spring:message code="member.middleName" /> <jstl:out	value="${member.middleName}"></jstl:out></td></tr>
		<tr><td><spring:message code="member.surname" /> <jstl:out value="${member.surname}"></jstl:out></td></tr>
		<tr><td><spring:message code="member.address" /> <jstl:out value="${member.address}"></jstl:out></td></tr>
		<tr><td><spring:message code="member.phone" /> <jstl:out value="${member.phone}"></jstl:out></td></tr>
		<tr><td><spring:message code="member.email" /> <jstl:out value="${member.email}"></jstl:out></td></tr>
		<tr><td><spring:message code="member.username" /> <jstl:out value="${member.userAccount.username}"></jstl:out></td></tr>
	</table>
</div>
</security:authorize>
<acme:cancel url=" " code="cancel"/>


