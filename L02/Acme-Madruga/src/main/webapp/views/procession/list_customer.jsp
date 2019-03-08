<%--
 * list_customer.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<!--
	CONTROL DE CAMBIOS list_customer.jsp PROCESSION
  
	ALVARO 17/02/2019 12:54 CREACIÓN
-->

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
	<div>
		<security:authorize access="hasRole('BROTHERHOOD')"> 
			<p class="create"><input type="button" value=<spring:message code="brotherhood.createProcession" /> id="buttonCreateProcession" name="buttonCreateProcession"  onclick="location.href='procession/brotherhood/create.do';"/></p>
			<display:table name="processions" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
				<display:column titleKey="procession.ticker"> 
					<a href="procession/brotherhood/show.do?processionId=${row.id}">${row.ticker}</a>
				</display:column>
				<display:column property="title" titleKey="procession.title"></display:column>
			</display:table>
		</security:authorize>
	</div>
	<div>
		<form method="get" action=" ">
			<button type="submit">
				<spring:message code="back" />
			</button>
		</form>
	</div>
</body>