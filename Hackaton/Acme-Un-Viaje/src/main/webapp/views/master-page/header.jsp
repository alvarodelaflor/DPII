<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div class="row">
	<div class="col-md-12">
		<nav class="navbar navbar-expand-sm bg-light navbar-light">
			<a href="#" class="navbar-brand">
				<img src="images/logo.png" style="width: 80px;">
			</a>

			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#uno">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="uno">
				<div class="col-md-8">
					<ul class="navbar-nav">

						<!-- ACTORES LOGUEADOS, DISTINGUIENDO POR ROL -->
						<security:authorize access="hasRole('ADMIN')">
							<li class="nav-item dropdown">
								<a href="#" class="nav-link dropdown-toggle"
									data-toggle="dropdown" id="navbardrop">
									<spring:message code="master.page.admin" />
								</a>
								<div class="dropdown-menu">
									<a href="admin/create.do" class="dropdown-item">
										<spring:message code="master.page.admin.create" />
									</a>
									<a href="admin/actorList.do" class="dropdown-item">
										<spring:message code="master.page.admin.actorList" />
									</a>
									<a href="admin/config.do" class="dropdown-item">
										<spring:message code="master.page.admin.config" />
									</a>
								</div>
							</li>
							<li class="nav-item dropdown">
								<a href="#" class="nav-link dropdown-toggle"
									data-toggle="dropdown" id="navbardrop">
									<spring:message code="master.page.actor.profile" />
								</a>
								<div class="dropdown-menu">
									<a href="admin/edit.do" class="dropdown-item">
										<spring:message code="master.page.actor.edit" />
									</a>
									<a href="admin/show.do" class="dropdown-item">
										<spring:message code="master.page.actor.show" />
									</a>
									<a href="socialProfile/list.do" class="dropdown-item">
										<spring:message code="master.page.socialProfile.list" />
									</a>
								</div>
							</li>
						</security:authorize>

						<security:authorize access="hasRole('CLEANER')">
							<li class="nav-item dropdown">
								<a href="#" class="nav-link dropdown-toggle"
									data-toggle="dropdown" id="navbardrop">
									<spring:message code="master.page.cleaner.employment" />
								</a>
								<div class="dropdown-menu">
									<a href="curricula/list.do" class="dropdown-item">
										<spring:message code="curricula" />
									</a>
								</div>
							</li>
							<li class="nav-item dropdown">
								<a href="#" class="nav-link dropdown-toggle"
									data-toggle="dropdown" id="navbardrop">
									<spring:message code="master.page.actor.profile" />
								</a>
								<div class="dropdown-menu">
									<a href="cleaner/edit.do" class="dropdown-item">
										<spring:message code="master.page.actor.edit" />
									</a>
									<a href="cleaner/show.do" class="dropdown-item">
										<spring:message code="master.page.actor.show" />
									</a>
									<a href="socialProfile/list.do" class="dropdown-item">
										<spring:message code="master.page.socialProfile.list" />
									</a>
								</div>
							</li>
						</security:authorize>

						<security:authorize access="hasRole('TRANSPORTER')">
							<li class="nav-item dropdown">
								<a href="#" class="nav-link dropdown-toggle"
									data-toggle="dropdown" id="navbardrop">
									<spring:message code="master.page.actor.profile" />
								</a>
								<div class="dropdown-menu">
									<a href="transporter/edit.do" class="dropdown-item">
										<spring:message code="master.page.actor.edit" />
									</a>
									<a href="transporter/show.do" class="dropdown-item">
										<spring:message code="master.page.actor.show" />
									</a>
									<a href="socialProfile/list.do" class="dropdown-item">
										<spring:message code="master.page.socialProfile.list" />
									</a>
								</div>
							</li>
							<li class="nav-item dropdown">
								<a href="#" class="nav-link dropdown-toggle"
									data-toggle="dropdown" id="navbardrop">
									<spring:message code="master.page.transporter.transports" />
								</a>
								<div class="dropdown-menu">
									<a href="transport/transporter/list.do" class="dropdown-item">
										<spring:message code="master.page.transporter.transport.list" />
									</a>
									<a href="transport/transporter/list.do" class="dropdown-item">
										<spring:message code="master.page.transporter.transport.list" />
									</a>
								</div>
							</li>
						</security:authorize>

						<security:authorize access="hasRole('REFEREE')">
							<li class="nav-item dropdown">
								<a href="#" class="nav-link dropdown-toggle"
									data-toggle="dropdown" id="navbardrop">
									<spring:message code="master.page.actor.profile" />
								</a>
								<div class="dropdown-menu">
									<a href="referee/edit.do" class="dropdown-item">
										<spring:message code="master.page.actor.edit" />
									</a>
									<a href="referee/show.do" class="dropdown-item">
										<spring:message code="master.page.actor.show" />
									</a>
									<a href="socialProfile/list.do" class="dropdown-item">
										<spring:message code="master.page.socialProfile.list" />
									</a>
								</div>
							</li>
						</security:authorize>

						<security:authorize access="hasRole('CUSTOMER')">
							<li class="nav-item dropdown">
								<a href="#" class="nav-link dropdown-toggle"
									data-toggle="dropdown" id="navbardrop">
									<spring:message code="master.page.customer" />
								</a>
								<div class="dropdown-menu">
									<a href="request/customer/list.do" class="dropdown-item">
										<spring:message code="master.page.request.customerList" />
									</a>
								</div>
							</li>
							<li class="nav-item dropdown">
								<a href="#" class="nav-link dropdown-toggle"
									data-toggle="dropdown" id="navbardrop">
									<spring:message code="master.page.actor.profile" />
								</a>
								<div class="dropdown-menu">
									<a href="customer/edit.do" class="dropdown-item">
										<spring:message code="master.page.actor.edit" />
									</a>
									<a href="customer/show.do" class="dropdown-item">
										<spring:message code="master.page.actor.show" />
									</a>
									<a href="socialProfile/list.do" class="dropdown-item">
										<spring:message code="master.page.socialProfile.list" />
									</a>
								</div>
							</li>
						</security:authorize>

						<security:authorize access="hasRole('TRAVELAGENCY')">
							<li class="nav-item dropdown">
								<a href="#" class="nav-link dropdown-toggle"
									data-toggle="dropdown" id="navbardrop">
									<spring:message code="master.page.travelAgency" />
								</a>
								<div class="dropdown-menu">
									<a href="warranty/travelAgency/list.do" class="dropdown-item">
										<spring:message code="master.page.warranty.travelAgencyList" />
									</a>
								</div>
							</li>
							<li class="nav-item dropdown">
								<a href="#" class="nav-link dropdown-toggle"
									data-toggle="dropdown" id="navbardrop">
									<spring:message code="master.page.actor.profile" />
								</a>
								<div class="dropdown-menu">
									<a href="travelAgency/edit.do" class="dropdown-item">
										<spring:message code="master.page.actor.edit" />
									</a>
									<a href="travelAgency/show.do" class="dropdown-item">
										<spring:message code="master.page.actor.show" />
									</a>
									<a href="socialProfile/list.do" class="dropdown-item">
										<spring:message code="master.page.socialProfile.list" />
									</a>
								</div>
							</li>
						</security:authorize>

						<security:authorize access="hasRole('HOST')">
							<li class="nav-item dropdown">
								<a href="#" class="nav-link dropdown-toggle"
									data-toggle="dropdown" id="navbardrop">
									<spring:message code="master.page.actor.profile" />
								</a>
								<div class="dropdown-menu">
									<a href="host/edit.do" class="dropdown-item">
										<spring:message code="master.page.actor.edit" />
									</a>
									<a href="host/show.do" class="dropdown-item">
										<spring:message code="master.page.actor.show" />
									</a>
									<a href="socialProfile/list.do" class="dropdown-item">
										<spring:message code="master.page.socialProfile.list" />
									</a>
								</div>
							</li>
						</security:authorize>

						<!-- ACTORES LOGUEADOS, DISTINGUIENDO POR ROL -->
					</ul>
				</div>

				<!-- PARA TODO AQUEL LOGUEADO -->
				<div class="col-md-4 text-right">
					<security:authorize access="isAuthenticated()">
						<div class="btn-group">
							<button type="button"
								class="btn btn-secondary dropdown-toggle dropdown-toggle-split"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">
								<spring:message code="master.page.config" />
								<span class="sr-only">Toggle Dropdown</span>
							</button>
							<div class="dropdown-menu">
								<a href="j_spring_security_logout" class="dropdown-item">
									<spring:message code="master.page.logout" />
								</a>
								<ul class="navbar-nav">
									<li class="nav-item">
										<a href="?language=en" class="dropdown-item">
											<img src="images/logoEN.png" style="width: 2em;">
										</a>
									</li>
									<li class="nav-item">
										<a href="?language=es" class="dropdown-item">
											<img src="images/logoES.png" style="width: 2em;">
										</a>
									</li>
								</ul>
							</div>
						</div>
					</security:authorize>
					<!-- PARA TODO AQUEL LOGUEADO -->

					<!-- SIN LOGUEAR -->
					<security:authorize access="isAnonymous()">

						<div class="btn-group">
							<a href="security/login.do" class="btn btn-secondary">
								<spring:message code="master.page.login" />
							</a>
							<button type="button" class="btn btn-secondary dropdown-toggle"
								data-toggle="dropdown">
								<spring:message code="master.page.config" />
								<span class="sr-only">Toggle Dropdown</span>
							</button>
							<div class="dropdown-menu">
								<ul class="navbar-nav">
									<li class="nav-item">
										<a href="?language=en" class="dropdown-item">
											<img src="images/logoEN.png" style="width: 2em;">
										</a>
									</li>
									<li class="nav-item">
										<a href="?language=es" class="dropdown-item">
											<img src="images/logoES.png" style="width: 2em;">
										</a>
									</li>
								</ul>
							</div>
						</div>

					</security:authorize>
					<!-- SIN LOGUEAR -->
				</div>
			</div>

		</nav>
	</div>
</div>
