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
    		<tr><td><spring:message code="legalRecord.title" /><jstl:out value="${legalRecord.title}"></jstl:out></td></tr>
    		<tr><td><spring:message code="legalRecord.description" /><jstl:out value=" ${legalRecord.description}"></jstl:out></td></tr>
    		<tr><td><spring:message code="legalRecord.legalName" /><jstl:out value=" ${legalRecord.legalName}"></jstl:out></td></tr>
    		<tr><td><spring:message code="legalRecord.vatNumber" /><jstl:out value=" ${legalRecord.vatNumber}"></jstl:out></td></tr>
    		<tr><td><spring:message code="legalRecord.laws" /><jstl:out value=" ${legalRecord.laws}"></jstl:out></td></tr>
	</table>
</div>
<div>      
	<form>
		<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
	</form>
</div>