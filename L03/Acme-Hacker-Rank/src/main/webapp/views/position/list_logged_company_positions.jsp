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


<display:table name="positions" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column property="title" titleKey="position.title"></display:column>
	<display:column property="salary" titleKey="position.salary"></display:column>
	<display:column titleKey="position.status">
		<spring:message code="position.status.${row.status}"/>
	</display:column>
	<display:column property="deadline" titleKey="position.deadline"></display:column>
	<display:column property="skills" titleKey="position.skills"></display:column>
	<display:column property="techs" titleKey="position.techs"></display:column>
	<display:column property="ticker" titleKey="position.ticker"></display:column>
	<!-- TODO: Descripción en el show -->
	<display:column titleKey="position.show">
		<a href="position/company/show.do?positionId=${row.id}"><spring:message code="position.show"/></a>
	</display:column>
	
</display:table>
					
<input type="button" value="back" name="position.cancel" onclick="window.location = 'welcome/indexz.do'" />

