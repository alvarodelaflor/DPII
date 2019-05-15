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

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<nav class="navbar navbar-expand-sm bg-light navbar-light">
	<a href="#" class="navbar-brand"><img src="images/logo.png"
		style="width: 80px;"></a>

	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#uno">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="uno">
		<ul class="navbar-nav">

			<!-- ACTORES LOGUEADOS, DISTINGUIENDO POR ROL -->
			<security:authorize access="hasRole('ADMIN')">
				<li class="nav-item"><a href="#" class="nav-link">Link 1</a></li>
				<li class="nav-item dropdown"><a href="#"
					class="nav-link dropdown-toggle" data-toggle="dropdown"
					id="navbardrop"><spring:message
							code="master.page.administrator" /></a>
					<div class="dropdown-menu">
						<a href="administrator/action-1.do" class="dropdown-item"><spring:message
								code="master.page.administrator.action.1" /></a> <a
							href="administrator/action-2.do" class="dropdown-item"><spring:message
								code="master.page.administrator.action.2" /></a>
					</div></li>
			</security:authorize>

			<security:authorize access="hasRole('CLEANER')">
				<li class="nav-item"><a href="#" class="nav-link">Link 1</a></li>
				<li class="nav-item dropdown"><a href="#"
					class="nav-link dropdown-toggle" data-toggle="dropdown"
					id="navbardrop"><spring:message
							code="master.page.cleaner.profile" /></a>
					<div class="dropdown-menu">
						<a href="cleaner/edit.do" class="dropdown-item"><spring:message
								code="master.page.cleaner.edit" /></a> <a href="cleaner/show.do"
							class="dropdown-item"><spring:message
								code="master.page.cleaner.show" /></a>
					</div></li>
			</security:authorize>

			<security:authorize access="hasRole('TRASNSPORTER')">
				<li class="nav-item"><a href="#" class="nav-link">Link 1</a></li>
				<li class="nav-item dropdown"><a href="#"
					class="nav-link dropdown-toggle" data-toggle="dropdown"
					id="navbardrop"><spring:message
							code="master.page.cleaner.profile" /></a>
					<div class="dropdown-menu">
						<a href="transporter/edit.do" class="dropdown-item"><spring:message
								code="master.page.cleaner.edit" /></a> <a
							href="transporter/show.do" class="dropdown-item"><spring:message
								code="master.page.cleaner.show" /></a>
					</div></li>
			</security:authorize>


			<!-- ACTORES LOGUEADOS, DISTINGUIENDO POR ROL -->
		</ul>

		<!-- PARA TODO AQUEL LOGUEADO -->

		<security:authorize access="isAuthenticated()">
			<div class="btn-group ml-auto">
				<button type="button"
					class="btn btn-secondary dropdown-toggle dropdown-toggle-split"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					<spring:message code="master.page.config" />
					<span class="sr-only">Toggle Dropdown</span>
				</button>
				<div class="dropdown-menu">
					<a href="j_spring_security_logout" class="dropdown-item"><spring:message
							code="master.page.logout" /></a>
					<ul class="navbar-nav">
						<li class="nav-item"><a href="?language=en"
							class="dropdown-item"><img src="images/logoEN.png"
								style="width: 30px;"></a></li>
						<li class="nav-item"><a href="?language=es"
							class="dropdown-item"><img src="images/logoES.png"
								style="width: 40px;"></a></li>
					</ul>
				</div>
			</div>
		</security:authorize>
		<!-- PARA TODO AQUEL LOGUEADO -->

		<!-- SIN LOGUEAR -->
		<security:authorize access="isAnonymous()">

			<div class="btn-group ml-auto">

				<a href="security/login.do" class="btn btn-secondary"><spring:message
						code="master.page.login" /></a>
				<button type="button"
					class="btn btn-secondary dropdown-toggle dropdown-toggle-split"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					<spring:message code="master.page.config" />
					<span class="sr-only">Toggle Dropdown</span>
				</button>
				<div class="dropdown-menu">
					<ul class="navbar-nav">
						<li class="nav-item"><a href="?language=en"
							class="dropdown-item"><img src="images/logoEN.png"
								style="width: 30px;"></a></li>
						<li class="nav-item"><a href="?language=es"
							class="dropdown-item"><img src="images/logoES.png"
								style="width: 40px;"></a></li>
					</ul>
				</div>
			</div>
		</security:authorize>
		<!-- SIN LOGUEAR -->
	</div>
</nav>