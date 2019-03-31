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
		<tr><td><strong><spring:message code="position.ticker" />: </strong><jstl:out	value="${position.ticker}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="position.desdline" />: </strong><jstl:out value="${position.deadline}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="position.profile" />: </strong><jstl:out value="${position.profile}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="position.skills" />: </strong><jstl:out value="${position.skills}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="position.techs" />: </strong><jstl:out value="${position.techs}"></jstl:out></td></tr>
		<tr><td><strong><spring:message code="position.title" />: </strong><jstl:out value="${position.title}"></jstl:out></td></tr>
	</table>
</div>
<input type="button" value="back" name="position.cancel" onclick="history.back()" />
