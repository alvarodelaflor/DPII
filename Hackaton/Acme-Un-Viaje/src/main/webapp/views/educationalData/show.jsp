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

<div class="jumbotron" style="width: 100%">
	<div class="row">
		<div class="col-md-12">
			<h3 class="display-3">
				<jstl:out value="${educationalData.degree}"></jstl:out>
			</h3>
			<p>
				<spring:message code="whereStudy" /><jstl:out value="${educationalData.institution}"></jstl:out>
			</p>
			<br>
			<p class="lead">
				<jstl:out value="${educationalData.curricula.cleaner.name}"></jstl:out>
				<spring:message code="period" />
				<strong><b><jstl:out value="${educationalData.startDate} - ${educationalData.endDate}"></jstl:out></b></strong>
				<spring:message code="calification" />
				<strong><b><jstl:out value="${educationalData.mark}"></jstl:out></b></strong>
				<br>
				<br>
				<spring:message code="masInfo" />
				<a href="https://www.google.com/search?source=hp&ei=CjPoXLzZIZGZkwX09K1I&q=${educationalData.degree}">
					<spring:message htmlEscape="false" code="showDetailsCurricula"/>
				</a>		
			</p>
		</div>
	</div>
	<br>
	<span style="padding-left: 0.0em"> <acme:cancel url="/curricula/show.do?curriculaId=${educationalData.curricula.id}"
			code="back" />
	</span>
</div>
