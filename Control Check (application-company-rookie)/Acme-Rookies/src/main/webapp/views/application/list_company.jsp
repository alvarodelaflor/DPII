<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- NO ESTÁ ACABADO, CARMEN -->


<h2><spring:message code="application.submitted_applications"/></h2>
<display:table name="submittedApplications" id="row1" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="application.Show">
		<a href="application/company/show.do?applicationId=${row1.id}"><spring:message
				code="application.show" /></a>
	</display:column>

	<display:column property="status" titleKey="application.status"></display:column>

	<display:column titleKey="application.position">
		<a href="position/company/show.do?positionId=${row1.position.id}"><jstl:out
				value="${row1.position.ticker}" /></a>
	</display:column>

	<display:column titleKey="application.curricula.show">
		<a href="curricula/show.do?curriculaId=${row1.curricula.id}"><jstl:out
				value="${row1.curricula.name}" /></a>
	</display:column>

	<display:column titleKey="application.problem">
		<a href="problem/company/show.do?problemId=${row1.problem.id}"><jstl:out
				value="${row1.problem.title}" /></a>
	</display:column>

</display:table>

<h2><spring:message code="application.accepted_applications"/></h2>
<display:table name="acceptedApplications" id="row2" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="application.Show">
		<a href="application/company/show.do?applicationId=${row2.id}"><spring:message
				code="application.show" /></a>
	</display:column>

	<display:column property="status" titleKey="application.status"></display:column>

	<display:column titleKey="application.position">
		<a href="position/company/show.do?positionId=${row2.position.id}"><jstl:out
				value="${row2.position.ticker}" /></a>
	</display:column>

	<display:column titleKey="application.curricula.show">
		<a href="curricula/show.do?curriculaId=${row2.curricula.id}"><jstl:out
				value="${row2.curricula.name}" /></a>
	</display:column>

	<display:column titleKey="application.problem">
		<a href="problem/company/show.do?problemId=${row2.problem.id}"><jstl:out
				value="${row2.problem.title}" /></a>
	</display:column>
	
	<display:column titleKey="quolet.list">
		<input type="button" value="<spring:message code='quolet.list' />" onclick="window.location = 'quolet/company/list.do?applicationId=${row2.id}'" />
	</display:column>

</display:table>

<h2><spring:message code="application.rejected_applications"/></h2>
<display:table name="rejectedApplications" id="row3" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column titleKey="application.Show">
		<a href="application/company/show.do?applicationId=${row3.id}"><spring:message
				code="application.show" /></a>
	</display:column>

	<display:column property="status" titleKey="application.status"></display:column>

	<display:column titleKey="application.position">
		<a href="position/company/show.do?positionId=${row3.position.id}"><jstl:out
				value="${row3.position.ticker}" /></a>
	</display:column>

	<display:column titleKey="application.curricula.show">
		<a href="curricula/show.do?curriculaId=${row3.curricula.id}"><jstl:out
				value="${row3.curricula.name}" /></a>
	</display:column>

	<display:column titleKey="application.problem">
		<a href="problem/company/show.do?problemId=${row3.problem.id}"><jstl:out
				value="${row3.problem.title}" /></a>
	</display:column>

</display:table>

<acme:cancel url=" " code="application.back" />

