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

<jstl:if test="${not adding and not finalMode}">
<input type="button" value="<spring:message code='problem.addNew' />" onclick="window.location = 'position/company/addProblemView.do?positionId=${positionId}'" />
<br>
</jstl:if>
<display:table name="problems" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column property="title" titleKey="problem.title"></display:column>
	<display:column property="statement" titleKey="problem.statement"/>
	<display:column property="hint" titleKey="problem.hint"/>
	<display:column property="attachments" titleKey="problem.statement"/>
	<display:column titleKey="problem.finalMode">
		<spring:message code="problem.finalMode.${row.finalMode}"/>
	</display:column>
	<jstl:if test="${not finalMode}">
	<display:column>
	<jstl:if test="${adding }">
		<a href="position/company/addProblemAction.do?problemId=${row.id}&positionId=${positionId}"><spring:message code="problem.add"/></a>
	</jstl:if>
	<jstl:if test="${not adding}">
		<a href="position/company/detachProblem.do?problemId=${row.id}&positionId=${positionId}"><spring:message code="problem.detach"/></a>
	</jstl:if>
	</display:column>
	</jstl:if>
	<display:column titleKey="problem.show">
		<a href="problem/company/show.do?problemId=${row.id}"><spring:message code="problem.show"/></a>
	</display:column>
</display:table>
<br>
<jstl:if test="${not adding }">		
<input type="button" value="<spring:message code='problem.back' />" onclick="window.location = 'position/company/show.do?positionId=${positionId}'" />
</jstl:if>
<jstl:if test="${ adding }">		
<input type="button" value="<spring:message code='problem.back' />" onclick="window.location = 'position/company/problemList.do?positionId=${positionId}'" />
</jstl:if>