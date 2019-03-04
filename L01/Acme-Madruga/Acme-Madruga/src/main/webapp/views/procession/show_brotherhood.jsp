<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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

<body>
	<security:authorize access="hasRole('MEMBER')">
		<jstl:if test="${validMember}">
			<form method="get" action="/Acme-Madruga/request/member/create.do">
				<button name="processionId" value="${procession.id}"><spring:message code="createRequest"/></button>
			</form>	
		</jstl:if>
	</security:authorize>
	<div>
	
	</div>
	<div>
		<table>
    		<tr><td><spring:message code="procession.title" /><jstl:out value="${procession.title}"></jstl:out></td></tr>
    		<tr><td><spring:message code="procession.description" /><jstl:out value="${procession.description}"></jstl:out></td></tr>
    		<tr><td><spring:message code="procession.moment" /><jstl:out value="${procession.moment}"></jstl:out></td></tr>
    		<tr><td><spring:message code="procession.brotherhood" /><jstl:out value="${procession.brotherhood.name} ${procession.brotherhood.surname}"></jstl:out></td></tr>
    		<tr><td><spring:message code="procession.ticker" /><jstl:out value="${procession.ticker}"></jstl:out></td></tr>
    		<tr><td><spring:message code="procession.maxRow" /><jstl:out value="${procession.maxRow}"></jstl:out></td></tr>
    		<tr><td><spring:message code="procession.maxColum" /><jstl:out value="${procession.maxColum}"></jstl:out></td></tr>
    		<tr><td><spring:message code="procession.floatBro" /><jstl:out value="${procession.floatBro.title}"></jstl:out></td></tr>
		</table>
	</div>
	<div>      
		<form>
			<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
		</form>
	</div>
</body>