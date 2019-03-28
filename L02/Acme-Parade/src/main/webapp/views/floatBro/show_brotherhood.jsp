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
    		<tr><td><spring:message code="floatBro.title" /><jstl:out value="${floatBro.title}"></jstl:out></td></tr>
    		<tr><td><spring:message code="floatBro.description" /><jstl:out value="${floatBro.description}"></jstl:out></td></tr>
    		<tr><td><spring:message code="floatBro.brotherhood" /><jstl:out value="${floatBro.brotherhood.name} ${floatBro.brotherhood.surname}"></jstl:out></td></tr>
		</table>
	</div>
	<div>
		<table>
			<tr><td><spring:message code="floatBro.pictures" /></td></tr>
			<jstl:forEach var ="res" items="${pictures}">
				<tr><td><img src="${res}" border="1" alt="Error" width="400" height="300"></td></tr>
			</jstl:forEach>
		</table>
	</div>
	<div>      
		<form>
			<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
		</form>
	</div>
</body>