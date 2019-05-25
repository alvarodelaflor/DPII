<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="styles/common.css" type="text/css">
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<div class="col-md-3">
	<div class="rounded float-left">
	    <img src="https://images.vexels.com/media/users/3/153887/isolated/preview/eb12eda1d7aeaab1b62f568831605d49---cono-de-contrato-firmado-by-vexels.png" alt="Curricula"  width="250"/>
	</div>
</div>

<form:form class="formularioEdicion" method="POST" onsubmit="return phonenumberval();" modelAttribute="jobApplication" action="jobApplication/cleaner/edit.do">
   	<form:hidden path="id"/>
   	<form:hidden path="version"/>
   	<form:hidden path="host"/>
   	
	<h3>
		<spring:message code="curricula.principalMessage" />
		<small class="text-muted">
			<spring:message code="jobApplication.secundaryMessage" />
		</small>
	</h3>
	<div class="row">
		<div class="col-md-12">
			<c:choose>
				<c:when test="${empty curriculas}">
					<br>
					<br>
					<p class="lead">
						<spring:message htmlEscape="false" code="jobApplication.emptyCurriculas"/>
						<a href="curricula/cleaner/create.do">
							<spring:message htmlEscape="false" code="createCurricula"/>
						</a>		
					</p>
					<br>
					<br>
					<br>
					<br>
					<br>
				</c:when>
				<c:otherwise>
					<div class="form-group">
						<spring:message htmlEscape="false" code="jobApplication.cleanerMessage" var="placeholder1" />
						<form:textarea class="form-control" path="cleanerMessage" placeholder="${placeholder1}"/>
						<form:errors path="cleanerMessage" cssClass="error" />
					</div>
					<div class="form-group">
					  <label for="exampleFormControlSelect1"><spring:message htmlEscape="false" code="jobApplication.curricula" /></label>
					  <form:select class="form-control" id="exampleFormControlSelect1" path="curricula">
					  	<form:options items="${curriculas}" itemValue="id" itemLabel="name" />
					  </form:select>
					  <form:errors path="curricula" cssClass="error" />
					</div>
					<div class="row" style="padding-left: 1.0em">
						<p class="lead">
							<spring:message htmlEscape="false" code="jobApplication.add" />
						</p>
					</div>
					<div class="row">
						<div class="col-md-6" style="padding-left: 0.0em">
							<span style="padding-left: 1.0em"> <acme:submit
									name="save" code="actor.save" />
							</span> 
							<c:choose>
								<c:when test="${toEdit}">
									<span style="padding-left: 0.5em"> <acme:cancel url="/jobApplication/cleaner/list.do"
											code="actor.cancel" />
									</span>								
								</c:when>
								<c:otherwise>
									<span style="padding-left: 0.5em"> <acme:cancel url="/host/show.do?hostId=${jobApplication.host.id}"
											code="actor.cancel" />
									</span>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	

</form:form>