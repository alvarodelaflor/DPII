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
			<c:choose>
				<c:when test="${empty jobApplications}">
					<br>
					<p class="lead"><spring:message code="jobApplications.empty" /></p>
				</c:when>
				<c:otherwise>
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
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="col-md-12">
			<div class="jumbotron jumbotron-fluid">
			  <div class="container">
			    <h1 class="display-4"><spring:message code="lookForJob1"/></h1>
			    <c:choose>
			    	<c:when test="${empty hosts}">
			    		<p class="lead"><spring:message code="jobApplications.emptyHost" /></p>
			    	</c:when>
			    	<c:otherwise>
			    		<p class="lead"><spring:message code="lookForJob2"/></p>
						<div id="myCarousel" class="carousel slide bg-inverse w-50 ml-auto mr-auto" data-ride="carousel">
							<ol class="carousel-indicators">
						    	<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
								<c:forEach var = "i" items="${numbers}">
									<jstl:if test="${i!=0}">
										<li data-target="#myCarousel" data-slide-to="${i}"></li>
									</jstl:if>
								</c:forEach>
						  	</ol>
						  	<div class="carousel-inner" role="listbox">
							  	<c:forEach var = "i" items="${hosts}">
									<c:choose>
										<c:when test="${i.name == hosts[0].name}">
										    <div class="carousel-item active">
												<c:choose>
													<c:when
														test="${i.photo == null or i.photo=='' }">
														<div class="col-md-3">
															<div class="card">
																<img class="card-img-top" src="images/registerPhoto.png"
																	alt="ERROR">
															</div>
														</div>
													</c:when>
													<c:otherwise>
														<div class="col-md-3">
															<div class="card">
																<img class="card-img-top" src="${i.photo}" alt="ERROR">
															</div>
														</div>
													</c:otherwise>
												</c:choose>
										        <div class="carousel-caption">
													<h4 style="color:rgb(55,55,55);"><b><jstl:out value="${i.name} ${i.surname}"></jstl:out></b></h4>
													<p><a href="host/show.do?hostId=${i.id}" class="lead"><spring:message code="jobApplication.contact"/></a></p>
										        </div>
										    </div>
										</c:when>
										<c:otherwise>
										    <div class="carousel-item">
												<c:choose>
													<c:when
														test="${i.photo == null or i.photo=='' }">
														<div class="col-md-3">
															<div class="card">
																<img class="card-img-top" src="images/registerPhoto.png"
																	alt="ERROR">
															</div>
														</div>
													</c:when>
													<c:otherwise>
														<div class="col-md-3">
															<div class="card">
																<img class="card-img-top" src="${i.photo}" alt="ERROR">
															</div>
														</div>
													</c:otherwise>
												</c:choose>
										        <div class="carousel-caption">
													<h4 style="color:rgb(55,55,55);"><b><jstl:out value="${i.name} ${i.surname}"></jstl:out></b></h4>
													<p><a href="host/show.do?hostId=${i.id}" class="lead"><spring:message code="jobApplication.contact"/></a></p>
										        </div>
										    </div>
										</c:otherwise>
									</c:choose>
							  	</c:forEach>
						  	</div>
						  	<a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
						    	<span class="carousel-control-prev-icon" aria-hidden="true"></span>
						    	<span class="sr-only">Previous</span>
						  </a>
						  <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
						    <span class="carousel-control-next-icon" aria-hidden="true"></span>
						    <span class="sr-only">Next</span>
						  </a>
						</div>				
						<!-- Initialize Bootstrap functionality -->
						<script>
						// Initialize tooltip component
						$(function () {
						  $('[data-toggle="tooltip"]').tooltip()
						})
						
						// Initialize popover component
						$(function () {
						  $('[data-toggle="popover"]').popover()
						})
						</script>
			    	</c:otherwise>
			    </c:choose>
			  </div>
			</div>		
		</div>
	</div>
</div>