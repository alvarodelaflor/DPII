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
	<div class="row">
		<div class="col-md-3">
	    		<img src="https://koe.cl/uploads/carta-presentacion-en-ingles/carta-presentacion-en-ingles.png" alt="Curricula"  width="250"/>
		</div>
		<div class="col-md-9">
		    <h3>
				<spring:message code="jobApplication.principalMessage1" /><jstl:out value=" ${cleaner.name}!"></jstl:out>
				<small class="text-muted">
					<spring:message code="jobApplication.secundaryMessage1" />
				</small>
			</h3>
			<display:table name="jobApplications" id="row2" requestURI="${requestURI}" pagesize="5" class="displaytag table table-hover">
				<display:column titleKey="jobApplication.host">
					<a href="host/show.do?hostId=${row2.host.id}"><jstl:out value="${row2.host.name} ${row2.host.surname}"></jstl:out></a>			
				</display:column>
				<display:column titleKey="jobApplication.status">
					<c:choose>
						<c:when test="${row2.status==null or row2.status==''}">
							<spring:message code="null" />
						</c:when>
						<c:otherwise>
							<c:when test="${row2.status==true}">
								<spring:message code="true" />
							</c:when>
							<c:otherwise>
								<spring:message code="false" />
							</c:otherwise>
						</c:otherwise>
					</c:choose>			
				</display:column>
				<display:column property="cleanerMessage" titleKey="jobApplication.cleanerMessage2"></display:column>
				<display:column property="rejectMessage" titleKey="jobApplication.rejectMessage"></display:column>
				<c:choose>
					<c:when test="${cleanerLogger==true and row2.status==null}">
						<display:column titleKey="none">
							<acme:create name = "" url="jobApplication/cleaner/edit.do?jobApplicationId=${row2.id}" code="curricula.edit"/>	
						</display:column>
						<display:column titleKey="none">
							<acme:delete name = "" url="jobApplication/cleaner/delete.do?jobApplicationId=${row2.id}" code="curricula.delete"/>
						</display:column>
					</c:when>
				</c:choose>
			</display:table>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="col-md-12">
			<div class="jumbotron jumbotron-fluid">
			  <div class="container">
			    <h1 class="display-4"><spring:message code="lookForJob1"/></h1>
			    <p class="lead"><spring:message code="lookForJob2"/></p>
				<display:table name="hosts" id="row3" requestURI="${requestURI}" pagesize="1" class="displaytag table table-hover">
					<display:column titleKey="none">
						<center><c:choose>
							<c:when
								test="${row3.photo == null or row3.photo=='' }">
								<div class="col-md-3">
									<div class="card">
										<img class="card-img-top" src="images/registerPhoto.png"
											alt="ERROR">
										<div class="card-body">
											<h4 class="card-title"><a href="host/show.do?hostId=${row3.id}"><jstl:out value="${row3.name}${row3.surname}"></jstl:out></a></h4>
										</div>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="col-md-3">
									<div class="card">
										<img class="card-img-top" src="${row3.photo}" alt="ERROR">
										<div class="card-body">
											<h4 class="card-title"><a href="host/show.do?hostId=${row3.id}"><jstl:out value="${row3.name} ${row3.surname}"></jstl:out></a></h4>
										</div>
									</div>
								</div>
							</c:otherwise>
						</c:choose></center>
					</display:column>
				</display:table>
			  </div>
			</div>		
		</div>
	</div>
</div>