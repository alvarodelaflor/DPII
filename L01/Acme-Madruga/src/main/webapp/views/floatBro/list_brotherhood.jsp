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
	CONTROL DE CAMBIOS list_customer.jsp FLOATBRO
  
	ALVARO 17/02/2019 19:28 CREACIÓN
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
			<p class="create"><input type="button" value=<spring:message code="brotherhood.createFloatBro" /> id="buttonCreateFloatBro" name="buttonCreateFloatBro"  onclick="location.href='floatBro/brotherhood/create.do';"/></p>
			<display:table name="floatBros" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">				
				<display:column titleKey="floatBro.edit"> 
					<a href="floatBro/brotherhood/edit.do?id=${row.id}"><spring:message code="floatBro.edit"></spring:message></a>
				</display:column>
				<display:column titleKey="floatBro.delete"> 
					<a href="floatBro/brotherhood/delete.do?id=${row.id}"><spring:message code="floatBro.delete"></spring:message></a>
				</display:column>
				<display:column titleKey="floatBro.show"> 
					<a href="floatBro/brotherhood/show.do?floatBroId=${row.id}">${row.title}</a>
				</display:column>
				<display:column property="description" titleKey="floatBro.description"></display:column>
			</display:table>
		</security:authorize>
	</div>
	<div>
		<form method="get" action="#">
			<button type="submit">
				<spring:message code="back" />
			</button>
		</form>
	</div>
</body>