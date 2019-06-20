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
		<tr><td><strong><spring:message code="problem.title" />: </strong> <jstl:out	value="${problem.title}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="problem.statement" />: </strong> <jstl:out value="${problem.statement}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="problem.hint" />: </strong> <jstl:out value="${problem.hint}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="problem.statement" />: </strong> <jstl:out value="${problem.statement}"></jstl:out></td></tr>
	</table>
</div>
<input type="button" value="back" name="problem.back" onclick="history.back()" />
