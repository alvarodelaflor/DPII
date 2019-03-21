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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<security:authorize access="hasRole('BROTHERHOOD')"> 

<head>
<style>
	 img {
	 width: 40%;
	 height: 40%;
	 }
	</style>
</head>

<div class="content">
	
	<img alt="" src="${banner}"  width="2">

	<table>
		
		<c:choose>
    		<c:when test="${brotherhood.photo==''}">
				<tr><td><spring:message code="brotherhood.photo" /><br>
    		</c:when>    
    		<c:otherwise>
				<tr><td><spring:message code="brotherhood.photo" /><br>
				<img width="95" src="${brotherhood.photo}" alt="ERROR"/></td></tr>
    		</c:otherwise>
		</c:choose>
		<tr><td><spring:message code="brotherhood.name" /> <jstl:out value="${brotherhood.name}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.middleName" /> <jstl:out value="${brotherhood.middleName}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.surname" /> <jstl:out value="${brotherhood.surname}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.address" /> <jstl:out value="${brotherhood.address}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.phone" /> <jstl:out value="${brotherhood.phone}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.email" /> <jstl:out value="${brotherhood.email}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.username" /> <jstl:out value="${brotherhood.userAccount.username}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.title" /> <jstl:out value="${brotherhood.title}"></jstl:out></td></tr>
		<tr><td><spring:message code="brotherhood.establishmentDate" /> <jstl:out value="${brotherhood.establishmentDate}"></jstl:out></td></tr>
	</table>
</div>

<form:form class="formularioEdicion" method="GET" action="brotherhood/addPhoto.do?picture='${picture}'.do">		
		<acme:picture code="brotherhood.pictures" path="${picture}"/>
		<input type="submit" value=<spring:message code="save" /> />
</form:form>
</security:authorize>

<br>

<display:table pagesize="5" name="${pictures}" id="picture"
	requestURI="${requestURI}">
	<display:column titleKey="brotherhood.pictures">
		<img width="300" src="${picture.trim()}" alt="Error" >
		<a href="brotherhood/delete.do?url=${picture}"><spring:message code="brotherhood.delete" /> </a>
	</display:column>
</display:table>
	
	<br>
	<br>
<acme:cancel url=" " code="back"/>
<acme:cancel url="/brotherhood/export.do?id=${brotherhood.id}" code="export"/>


