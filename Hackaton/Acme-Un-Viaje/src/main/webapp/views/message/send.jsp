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


<body>
		<hr>
		<div class="container-fluid"  style="padding-left: 2.5em" >
				<div class="col-md-6" style="padding-left: 2.5em">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="msg" action="message/edit.do">
				<form:hidden path="id" />
				<fieldset>
					<acme:textbox code="msg.subject" path="subject"
						 cssError="col-md-6" cssLabel="col-md-2"
						cssInput="col-md-4" />

					<acme:textbox code="msg.body" path="body"
						cssError="col-md-6" cssLabel="col-md-2" cssInput="col-md-4" />
						
					<acme:textbox code="msg.tags" path="tags"
						cssError="col-md-6" cssLabel="col-md-2" cssInput="col-md-4" />

					<form:label path="emailReceiver">
						<spring:message code="receiver.email" />:
					</form:label>
					<form:select multiple="true" path="emailReceiver">
   		 				<form:options items="${emails}" />
					</form:select>
					<form:errors cssClass="error" path="emailReceiver" />
					
				</fieldset>
				<br>
				<div class="row">
					<div class="col-md-8">
						<acme:submit name="save" code="actor.save" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
	
</body>