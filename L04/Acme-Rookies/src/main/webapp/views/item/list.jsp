<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	

	<display:table name="items" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<display:column titleKey="item.name">
			<a href="item/show.do?id=${row.id}"><jstl:out value="${row.name}"/></a></display:column>
		<display:column titleKey="item.provider" ><a href="provider/show.do?id=${row.provider.id}"><spring:message code="item.provider.show" /></a></display:column>
	</display:table>
					
<input type="button" value=<spring:message code="item.back" /> name="item.back" onclick="history.back()" />
