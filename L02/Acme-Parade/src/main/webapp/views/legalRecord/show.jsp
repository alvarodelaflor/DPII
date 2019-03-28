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
	<fieldset>
	    	<h3><spring:message code="legalRecord.title" /></h3><jstl:out value="${legalRecord.title}"></jstl:out>
    		<h3><spring:message code="legalRecord.description" /></h3><jstl:out value=" ${legalRecord.description}"></jstl:out>
    		<h3><spring:message code="legalRecord.legalName" /></h3><jstl:out value=" ${legalRecord.legalName}"></jstl:out>
    		<h3><spring:message code="legalRecord.vatNumber" /></h3><jstl:out value=" ${legalRecord.vatNumber}"></jstl:out>
    		<h3><spring:message code="legalRecord.laws" /></h3><jstl:out value=" ${legalRecord.laws}"></jstl:out>
	</fieldset>
</div>
<div>      
	<form>
		<br>
		<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
	</form>
</div>