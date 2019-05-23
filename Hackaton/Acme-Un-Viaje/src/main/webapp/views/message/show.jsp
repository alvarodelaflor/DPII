<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

      			
    <div class="container-fluid" style="padding-left: 2.5em">
    	<div class="col-md-6" style="padding-left: 2.5em">
			<fieldset>
				<h2>
					<spring:message code="msg.show" />
					&nbsp;
				</h2>
				<hr>
				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="msg.show.subject" />:&nbsp;</strong>
					<jstl:out value="${msg.subject}"></jstl:out>
				</div>

				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="msg.show.body" />:&nbsp;</strong>
					<jstl:out value="${msg.body}"></jstl:out>
				</div>
				
				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="msg.show.tags" />:&nbsp;</strong>
					<jstl:out value="${tags}"></jstl:out>
				</div>
				
					<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="msg.show.sender" />:&nbsp;</strong>
					<jstl:out value="${msg.sender}"></jstl:out>
				</div>
				
				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="msg.show.emailReceiver" />:&nbsp;</strong>
					<jstl:out value="${msg.emailReceiver}"></jstl:out>
				</div>

				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="msg.show.moment" />:&nbsp;</strong>
					<jstl:out value="${msg.moment}"></jstl:out>
				</div>
			</fieldset>
		</div>
			<div class="row">
			<div class="col-md-3">
				<span> <acme:create url="message/delete.do?id=${msg.id}&mailboxId=${mailboxId}"
					name="buttonDelete" code="buttonDelete" />
				</span> 
			</div>
			</div>
			
			<br>
			
			<div class="row">
		<div class="col-md-3">
			 <span> <acme:cancel url="history.back()"
					code="actor.back" />
			</span>
		</div>
	</div>

	</div>
    
