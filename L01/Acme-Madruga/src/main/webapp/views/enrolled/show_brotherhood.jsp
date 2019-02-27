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
	<div>
		<table>
    		<tr><td><spring:message code="enrolled.member" /><jstl:out value="${enrolled.member.name} ${enrolled.member.surname}"></jstl:out></td></tr>
    		<tr><td><spring:message code="enrolled.brotherhood" /><jstl:out value="${enrolled.brotherhood.name} ${enrolled.brotherhood.surname}"></jstl:out></td></tr>
    		<c:choose>
    			<c:when test="${not empty enrolled.dropMoment}">
		    		<tr><td><spring:message code="enrolled.dropMoment" /><jstl:out value="${enrolled.dropMoment}"></jstl:out></td></tr>    		
    			</c:when>
    		</c:choose>
		</table>
	</div>
	<div>      
		<form>
			<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
		</form>
	</div>
</body>