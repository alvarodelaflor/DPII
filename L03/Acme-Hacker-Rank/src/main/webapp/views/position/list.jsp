<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>	
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


	<form:form class="formularioEdicion" method="GET" action="position/newPalabra.do?newPalabra='${newPalabra}'.do">		
		<spring:message code="position.word" />
		<input name="newPalabra"/>
		<input type="submit" value=<spring:message code="position.search" /> />
	</form:form>
		<acme:cancel url="position/list.do" code="position.clear"/>
	
	<br>

	<display:table name="positions" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<display:column titleKey="position.ticker">
			<a href="position/show.do?id=${row.id}"><jstl:out value="${row.ticker}"/></a></display:column>
		<display:column titleKey="position.company" ><a href="company/show.do?id=${row.company.id}"><spring:message code="position.company.show" /></a></display:column>
	</display:table>
					
	<acme:cancel url=" " code="position.cancel"/>

