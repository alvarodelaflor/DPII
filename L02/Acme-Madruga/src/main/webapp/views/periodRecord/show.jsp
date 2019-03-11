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

<div>
	<table>
    		<tr><td><spring:message code="periodRecord.title" /><jstl:out value="${periodRecord.title}"></jstl:out></td></tr>
    		<tr><td><spring:message code="periodRecord.description" /><jstl:out value="${period.description}"></jstl:out></td></tr>
    		<tr><td><spring:message code="periodRecord.photos" /><jstl:out value="${periodRecord.photos}"></jstl:out></td></tr>
    		<tr><td><spring:message code="periodRecord.startYear" /><jstl:out value=" ${periodRecord.startYear}"></jstl:out></td></tr>
    		<tr><td><spring:message code="periodRecord.endYear" /><jstl:out value=" ${periodRecord.endYear}"></jstl:out></td></tr>
	</table>
</div>
<div>      
	<form>
		<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
	</form>
</div>