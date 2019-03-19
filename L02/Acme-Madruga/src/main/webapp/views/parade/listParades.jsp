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

<display:table name="parade" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<!-- We are showing this publicly so we only want to show the accepted ones -->
	<display:column titleKey="parade.title"> 
	<jstl:if test="${row.isFinal and (row.status eq 'ACCEPTED')}">
		<jstl:out value="title"></jstl:out> 
	</jstl:if>
	</display:column>
	
	<display:column titleKey="parade.moment"> 
	<jstl:if test="${row.isFinal and (row.status eq 'ACCEPTED')}">
		<jstl:out value="moment"></jstl:out> 
	</jstl:if>
	</display:column>
	
	<display:column titleKey="parade.description"> 
	<jstl:if test="${row.isFinal and (row.status eq 'ACCEPTED')}">
		<jstl:out value="description"></jstl:out> 
	</jstl:if>
	</display:column>
	
	<display:column titleKey="parade.ticker"> 
	<jstl:if test="${row.isFinal and (row.status eq 'ACCEPTED')}">
		<a href="parade/show.do?paradeId=${row.id}">${row.ticker}</a>
	</jstl:if>
	</display:column>

	<display:column titleKey="parade.path"> 
	<jstl:if test="${row.isFinal and (row.status eq 'ACCEPTED')}">
		<a href="path/show.do?paradeId=${row.id}"><spring:message code="parade.path" /></a>
	</jstl:if>
	</display:column>
</display:table>

<input type="button" value="back" name="back" onclick="history.back()" />
