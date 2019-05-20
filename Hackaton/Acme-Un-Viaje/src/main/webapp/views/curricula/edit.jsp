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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<!------ Include the above in your HEAD tag ---------->


<div class="col-md-3">
	<div class="rounded float-left">
	    <img src="http://portalvirtualempleo.us.es/wp-content/uploads/2015/05/cvsinerrores_2.png" alt="Curricula"  width="250"/>
	</div>
</div>
<div class="container contact-form">
	<form:form class="formularioEdicion" method="POST" onsubmit="return phonenumberval();" modelAttribute="curricula" action="curricula/cleaner/edit.do">
        <form:hidden path="id"/>
		<form:hidden path="version"/>
    	<h3>
			<spring:message code="curricula.principalMessage" />
			<small class="text-muted">
				<spring:message code="curricula.secundaryMessage" />
			</small>
		</h3>
       	<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<spring:message htmlEscape="false" code="curricula.giveName" var="placeholder1" />
					<form:input class="form-control" path="name" placeholder="${placeholder1}"/>
					<form:errors path="name" cssClass="error" />
				</div>
                <div class="form-group">
                	<spring:message htmlEscape="false" code="curricula.givePhone" var="placeholder2" />
	            	<form:input class="form-control" path="phone" placeholder="${placeholder2}"/>
	            	<form:errors path="phone" cssClass="error" />
				</div>
	          <div class="form-group">
                	<spring:message htmlEscape="true" code="curricula.linkLinkedin" var="placeholder3" />
	            	<form:input class="form-control" path="linkLinkedin" placeholder="${placeholder3}"/>
	            	<form:errors path="linkLinkedin" cssClass="error" />
				</div>
			</div>
			<div class="col-md-6">
	            <div class="form-group">
	            	<spring:message htmlEscape="true" code="curricula.tellus" var="placeholder4" />
	            	<form:textarea class="form-control" rows="6" path="statement" placeholder="${placeholder4}"/>
	            	<form:errors path="statement" cssClass="error" />
	            </div>
            </div>
        </div>

		<div class="row" style="padding-left: 10.5em">
			<p class="lead">
				<spring:message htmlEscape="false" code="curricula.add" />
			</p>
            </div>
		    	<div class="row">
					<div class="col-md-3">
						<span style="padding-left: 9.5em"> <acme:submit
								name="save" code="actor.save" />
						</span> <span style="padding-left: 0.5em"> <acme:cancel url=""
								code="actor.cancel" />
						</span>
					</div>
				</div>
	</form:form>
            
</div>
