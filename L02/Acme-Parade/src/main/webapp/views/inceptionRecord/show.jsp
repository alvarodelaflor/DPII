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
		<legend>
			<spring:message code="inceptionRecord.dates" />
		</legend>
    		<h3><spring:message code="inceptionRecord.title" /></h3><jstl:out value=" ${inceptionRecord.title}"></jstl:out>
    		<h3><spring:message code="inceptionRecord.description" /></h3><jstl:out value=" ${inceptionRecord.description}"></jstl:out>
	</fieldset>
	<fieldset>
		<legend>
			<spring:message code="inceptionRecord.photos" />
		</legend>
			<jstl:forEach var ="res" items="${photos}">
				<p><img src="${res}" border="1" alt="Error" width="400" height="300"></p>
			</jstl:forEach>
	</fieldset>
</div>
<div>      
	<form>
		<br>
		<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
	</form>
</div>