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
		<div class="col-md-12">
			<div class="jumbotron jumbotron-fluid">
			  <div class="container">
			    <h1 class="display-4"><spring:message code="host.lookForJob1"/><jstl:out value="${pending[0].cleaner.name}"></jstl:out>!</h1>
			    <c:choose>
			    	<c:when test="${empty pending}">
			    		<p class="lead"><spring:message code="jobApplications.emptycleaner" /></p>
			    	</c:when>
			    	<c:otherwise>
			    		<p class="lead"><spring:message code="host.lookForJob2"/></p>
						<div id="myCarousel" class="carousel slide bg-inverse w-90 ml-auto mr-auto" data-ride="carousel">
							<ol class="carousel-indicators">
						    	<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
								<c:forEach var = "i" items="${numberPending}">
									<jstl:if test="${i!=0}">
										<li data-target="#myCarousel" data-slide-to="${i}"></li>
									</jstl:if>
								</c:forEach>
						  	</ol>
						  	<div class="carousel-inner" role="listbox">
							  	<c:forEach var = "i" items="${pending}">
									<c:choose>
										<c:when test="${i.cleaner.name == pending[0].cleaner.name}">
										    <div class="carousel-item active">
												<c:choose>
													<c:when
														test="${i.cleaner.photo == null or i.cleaner.photo=='' }">
														<center>
															<div class="col-md-3 col-center">
																<div class="card">
																	<img class="card-img-top" src="images/registerPhoto.png" alt="ERROR">
																	<div class="card-body">
																		<h4 class="card-title">${i.cleaner.name} <br>${i.cleaner.surname}</h4>
																		<div class="row">
																			<div class="col-md-12 col-center">
																				<acme:create name = "" url="jobApplication/host/edit.do?jobApplicationId=${i.id}" code="jobApplication.acceptCleaner"/>
																				<acme:delete name = "" url="jobApplication/host/reject.do?jobApplicationId=${i.id}" code="jobApplication.rejectCleaner"/>
																			</div>
																			<br>
																			<div class="col-md-12 col-center">
																				<br>
																				<acme:cancel url="curricula/show.do?curriculaId=${i.curricula.id}" code="jobApplication.seeCurricula" />
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</center>
													</c:when>
													<c:otherwise>
														<center>
															<div class="col-md-3 col-center">
																<div class="card">
																	<img class="card-img-top" src="${i.cleaner.photo}" alt="ERROR">
																	<div class="card-body">
																		<h4 class="card-title">${i.cleaner.name} <br>${i.cleaner.surname}</h4>
																		<div class="row">
																			<div class="col-md-12 col-center">
																				<acme:create name = "" url="jobApplication/host/edit.do?jobApplicationId=${i.id}" code="jobApplication.acceptCleaner"/>
																				<acme:delete name = "" url="jobApplication/host/reject.do?jobApplicationId=${i.id}" code="jobApplication.rejectCleaner"/>
																			</div>
																			<br>
																			<div class="col-md-12 col-center">
																				<br>
																				<acme:cancel url="curricula/show.do?curriculaId=${i.curricula.id}" code="jobApplication.seeCurricula" />
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</center>
													</c:otherwise>
												</c:choose>
										    </div>
										</c:when>
										<c:otherwise>
										    <div class="carousel-item">
												<c:choose>
													<c:when
														test="${i.cleaner.photo == null or i.cleaner.photo=='' }">
														<center>
															<div class="col-md-3 col-center">
																<div class="card">
																	<img class="card-img-top" src="images/registerPhoto.png" alt="ERROR">
																	<div class="card-body">
																		<h4 class="card-title">${i.cleaner.name} <br>${i.cleaner.surname}</h4>
																		<div class="row">
																			<div class="col-md-12 col-center">
																				<acme:create name = "" url="jobApplication/host/edit.do?jobApplicationId=${i.id}" code="jobApplication.acceptCleaner"/>
																				<acme:delete name = "" url="jobApplication/host/reject.do?jobApplicationId=${i.id}" code="jobApplication.rejectCleaner"/>
																			</div>
																			<br>
																			<div class="col-md-12 col-center">
																				<br>
																				<acme:cancel url="curricula/show.do?curriculaId=${i.curricula.id}" code="jobApplication.seeCurricula" />
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</center>
													</c:when>
													<c:otherwise>
														<center>
															<div class="col-md-3 col-center">
																<div class="card">
																	<img class="card-img-top" src="${i.cleaner.photo}" alt="ERROR">
																	<div class="card-body">
																		<h4 class="card-title">${i.cleaner.name} <br>${i.cleaner.surname}</h4>
																		<div class="row">
																			<div class="col-md-12 col-center">
																				<acme:create name = "" url="jobApplication/host/edit.do?jobApplicationId=${i.id}" code="jobApplication.acceptCleaner"/>
																				<acme:delete name = "" url="jobApplication/host/reject.do?jobApplicationId=${i.id}" code="jobApplication.rejectCleaner"/>
																			</div>
																			<br>
																			<div class="col-md-12 col-center">
																				<br>
																				<acme:cancel url="curricula/show.do?curriculaId=${i.curricula.id}" code="jobApplication.seeCurricula" />
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</center>
													</c:otherwise>
												</c:choose>
										    </div>
										</c:otherwise>
									</c:choose>
							  	</c:forEach>
						  	</div>
						  	<br>
						  	<br>
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
	<div class="row">
		<c:choose>
			<c:when test="${empty accepted}">
			
			</c:when>
			<c:otherwise>
				<div class="col-md-12">
					<h1 class="display-4"><strong><spring:message code="jobApplication.accepted" /></strong></h1>
					<p class="lead"><spring:message code="jobApplications.howDrop" /></p>
					<br>
					<display:table name="accepted" id="row3" requestURI="${requestURI}" pagesize="5" class="displaytag table table-hover">
						<display:column titleKey="actor.name">
							<a href="curricula/show.do?curriculaId=${row3.curricula.id}"><jstl:out value="${row3.cleaner.name} ${row3.cleaner.surname}"></jstl:out></a>			
						</display:column>
						<display:column property="cleanerMessage" titleKey="jobApplication.firstMessage"></display:column>
							<display:column titleKey="none">
								<acme:delete name = "" url="jobApplication/host/drop.do?jobApplicationId=${row3.id}" code="curricula.delete"/>
							</display:column>
					</display:table>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>