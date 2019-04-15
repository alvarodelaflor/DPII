<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form class="formularioEdicion" modelAttribute="finder" method="POST" action="finder/hacker/refresh.do">
	<form:hidden path="id" />
	<strong>
		<form:label path="keyword"><spring:message code="finder.keyword" />:</form:label>
	</strong>
	<form:input path="keyword" />	
	<form:errors path="keyword" cssClass="error" />
	
	<strong>
	<form:label path="minSalary"><spring:message code="finder.minSalary" />:</form:label>
	</strong>
	<form:input path="minSalary"/>	
	<form:errors path="minSalary" cssClass="error" />
	
	<strong>
	<form:label path="maxSalary"><spring:message code="finder.maxSalary" />:</form:label>
	</strong>	
	<form:input path="maxSalary"/>	
	<form:errors path="maxSalary" cssClass="error" />
	
	<strong>
	<form:label path="deadline"><spring:message code="finder.deadline" />:</form:label>
	</strong>	
	<form:input path="deadline" placeholder="2025/02/03 15:00"/>	
	<form:errors path="deadline" cssClass="error" />
	
	
	<button type="submit" class="btn btn-primary">
		<spring:message code="finder.refresh" />
	</button>
</form:form>
<jstl:if test="${finderError == 'finderError'}">
	<p class="error"><spring:message code="finder.error"/></p>
</jstl:if>
<display:table name="finder.positions" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column property="title" titleKey="position.title"></display:column>
	<display:column property="deadline" titleKey="position.deadline"></display:column>
	<display:column property="description" titleKey="position.description"></display:column>
	<display:column property="salary" titleKey="position.salary"></display:column>
	<display:column titleKey="position.ticker">
		<a href="position/show.do?positionId=${row.id}">${row.ticker}</a>
	</display:column>
	
</display:table>

<acme:cancel url="position/list.do" code="back" />