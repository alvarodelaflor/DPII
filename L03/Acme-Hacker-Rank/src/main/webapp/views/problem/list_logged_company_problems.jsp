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


<input type="button" value="<spring:message code='problem.create' />" onclick="window.location = 'problem/company/create.do'" />

<display:table name="problems" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column property="title" titleKey="problem.title"></display:column>
	<display:column property="statement" titleKey="problem.statement"/>
	<display:column property="hint" titleKey="problem.hint"/>
	<display:column property="attachments" titleKey="problem.statement"/>
	<display:column titleKey="problem.finalMode">
		<spring:message code="problem.finalMode.${row.finalMode}"/>
	</display:column>
	
	<display:column titleKey="problem.show">
		<a href="problem/company/show.do?problemId=${row.id}"><spring:message code="problem.show"/></a>
	</display:column>
</display:table>
					
<input type="button" value="<spring:message code='problem.back' />" onclick="window.location = 'welcome/index.do'" />

