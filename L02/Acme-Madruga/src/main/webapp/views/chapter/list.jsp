<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="chapter" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column titleKey="chapter.title2"><a href="chapter/show.do?id=${row.id}"><jstl:out value="${row.title}"/></a></display:column>
	<display:column property="name" titleKey="chapter.name2"></display:column>
	<display:column titleKey="chapter.area"><a href="area/areaChapter.do?id=${row.id}"><spring:message code="chapter.area" /></a></display:column>
</display:table>

<acme:cancel url=" " code="back"/>

