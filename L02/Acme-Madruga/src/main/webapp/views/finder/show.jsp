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

<form:form class="formularioEdicion" modelAttribute="finder" method="POST" action="finder/member/refresh.do">
	<form:hidden path="id" />
	<strong>
		<form:label path="keyword"><spring:message code="finder.keyword" />:</form:label>
	</strong>
	<form:input path="keyword" />	
	<form:errors path="keyword" cssClass="error" />
	
	<strong>
	<form:label path="minDate"><spring:message code="finder.minDate" />:</form:label>
	</strong>
	<form:input path="minDate" placeholder="2015/02/03 15:00"/>	
	<form:errors path="minDate" cssClass="error" />
	
	<strong>
	<form:label path="maxDate"><spring:message code="finder.maxDate" />:</form:label>
	</strong>	
	<form:input path="maxDate" placeholder="2025/02/03 15:00"/>	
	<form:errors path="maxDate" cssClass="error" />
	
	<strong>
	<form:label path="area"><spring:message code="finder.area" />:</form:label>
	</strong>
	<form:select path="area">
	   <form:option value="" label="------"/>
	   <jstl:forEach var="area" items="${areas}">
	   	<form:option value="${area}" label="${area.name}"/>
	   </jstl:forEach>
	</form:select>
	<button type="submit" class="btn btn-primary">
		<spring:message code="finder.refresh" />
	</button>
</form:form>
<jstl:if test="${finderError == 'finderError'}">
	<p class="error"><spring:message code="finder.error"/></p>
</jstl:if>
<display:table name="finder.parades" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column property="title" titleKey="parade.title"></display:column>
	<display:column property="moment" titleKey="parade.moment"></display:column>
	<display:column property="description" titleKey="parade.description"></display:column>
	<display:column titleKey="parade.ticker">
		<a href="parade/show.do?paradeId=${row.id}">${row.ticker}</a>
	</display:column>
	
</display:table>

<acme:cancel url="brotherhood/list.do" code="back" />