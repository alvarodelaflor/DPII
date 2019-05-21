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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="jumbotron" style="width: 100%">
	<div class="row">
		<div class="col-md-3">
			<c:choose>
				<c:when test="${curricula.cleaner.photo == null or curricula.cleaner.photo=='' }">
					<div class="row">
					<div class="card">
						<img class="card-img-top" src="images/registerPhoto.png" alt="ERROR">
						<div class="card-body">
							<h4 class="card-title">${curricula.cleaner.name}${curricula.cleaner.surname}</h4>
						</div>
					</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="card">
						<img class="card-img-top" src="${curricula.cleaner.photo}"
							alt="ERROR">
						<div class="card-body">
							<h4 class="card-title">${curricula.cleaner.name}${curricula.cleaner.surname}</h4>
						</div>
					</div>
						<jstl:if test="${cleanerLogin==true}">
							<p>
								<acme:delete name = "" url="curricula/cleaner/delete.do?curriculaId=${curricula.id}" code="curricula.delete"/>
								<acme:create name = "" url="curricula/cleaner/edit.do?curriculaId=${curricula.id}" code="curricula.edit"/> 
							</p>
						</jstl:if>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="col-md-9">
			<h1 class="display-3">
				<jstl:out value="${curricula.name}"></jstl:out>
			</h1>
			<p>
				<jstl:out value="${curricula.statement}"></jstl:out>
			</p>
			<p>
				<spring:message code="numberMoreInfo" /><jstl:out value="${curricula.phone}"></jstl:out>
			</p>
			<p>
				<spring:message code="linkLinkedinMoreInfo" /><jstl:out value="${curricula.linkLinkedin}"></jstl:out>
			</p>
		</div>
	</div>
</div>

