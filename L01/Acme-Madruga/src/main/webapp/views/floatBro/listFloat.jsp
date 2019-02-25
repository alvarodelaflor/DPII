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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="floatbro" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column property="title" titleKey="floatbro.title"></display:column>
	<display:column property="description" titleKey="floatbro.description"></display:column>
	<display:column titleKey="floatbro.pictures"><a href="floatBro/showPictureFloat.do?id=${row.id}"><spring:message code="float.pictures" /></a></display:column>
</display:table>

<acme:cancel url="brotherhood/list.do" code="back"/>