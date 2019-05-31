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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid" style="padding-left: 2.5em">
	<h3>
		<spring:message code="curricula.principalMessage" />
		<small class="text-muted">
			<c:choose>
				<c:when test="${empty curriculas}">
					<spring:message htmlEscape="false" code="oopsNoCurriculas"/>
				</c:when>
				<c:otherwise>				
					<spring:message code="curricula.moreInfo" />
				</c:otherwise>
			</c:choose>
		</small>
	</h3>
	<jstl:if test="${cleanerLogger==true}">
		<p class="lead">
			<spring:message code="jobApplications.wantCreateCrurricula" />
			<a href="curricula/cleaner/create.do">
				<spring:message htmlEscape="false" code="showDetailsCurricula"/>
			</a>
		</p>
	</jstl:if>
	<div id="accordion">
	<c:forEach var = "i" items="${numbers}">
			<c:choose>
				<c:when test="${i==0}">
					<div class="card">
				    	<div class="card-header" id="heading0">
							<h5 class="mb-0">
				        		<button class="btn btn-link" data-toggle="collapse" data-target="#collapse0" aria-expanded="true" aria-controls="collapse0">
				          			<jstl:out value="${curriculas[i].name}"></jstl:out>
				        		</button>
				      		</h5>
				    	</div>
				    	<div id="collapse0" class="collapse show" aria-labelledby="heading0" data-parent="#accordion">
				      		<div class="card-body">
				      			<b>
					        		<jstl:out value="${curriculas[i].statement}"></jstl:out><br>
				        		</b>
				        		<br>
					        	<spring:message htmlEscape="false" code="curricula.moreDetails"/>
								<a href="curricula/show.do?curriculaId=${curriculas[i].id}">
									<spring:message htmlEscape="false" code="showDetailsCurricula"/>
								</a>
				      		</div>
				    	</div>
				  	</div>
				</c:when>
				<c:otherwise>
				  	<div class="card">
				    	<div class="card-header" id="heading${i}">
				      		<h5 class="mb-0">
				        		<button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapse${i}" aria-expanded="false" aria-controls="collapse${i}">
				          			<jstl:out value="${curriculas[i].name}"></jstl:out>
				        		</button>
				      		</h5>
				    	</div>
				    	<div id="collapse${i}" class="collapse" aria-labelledby="heading${i}" data-parent="#accordion">
				      		<div class="card-body">
				      			<b>
					        		<jstl:out value="${curriculas[i].statement}"></jstl:out><br>
				        		</b>
				        		<br>
					        	<spring:message htmlEscape="false" code="curricula.moreDetails"/>
								<a href="curricula/show.do?curriculaId=${curriculas[i].id}">
									<spring:message htmlEscape="false" code="showDetailsCurricula"/>
								</a>
				      		</div>
				    	</div>
				  	</div>
				</c:otherwise>
			</c:choose>
	</c:forEach>
	</div>
	<br>
	<acme:cancel url="#" code="back"/>
</div>