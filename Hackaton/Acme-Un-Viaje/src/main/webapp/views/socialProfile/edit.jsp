<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
<div class="container-fluid"  style="padding-left: 2.5em" >
	<div class="row">
	
		<div class="col-md-6">
			<display:table name="socialProfiles" id="row"
				requestURI="${requestURI}" pagesize="5"
				class="displaytag table table-hover">
				<fieldset>
					<display:column titleKey="socialProfile.name">
						<a href="socialProfile/show.do?socialProfileId=${row.id}"><jstl:out
								value="${row.name}"></jstl:out> </a>
					</display:column>
					<display:column titleKey="socialProfile.delete">
						<a href="socialProfile/delete.do?socialProfileId=${row.id}"><spring:message
								code="socialProfile.deleteR" /></a>
					</display:column>
					<display:column titleKey="socialProfile.edit">
						<a href="socialProfile/edit.do?socialProfileId=${row.id}"><spring:message
								code="socialProfile.editR" /></a>
					</display:column>
				</fieldset>
			</display:table>
		</div>

		<div class="col-md-6" style="padding-left: 2.5em">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="socialProfile" action="socialProfile/edit.do">
				<form:hidden path="id" />
				<fieldset>

					<h2>
						<spring:message code="actor.editCreateSocialProfile" />
						&nbsp;<i class="fab fa-twitter-square icon-gradient"></i>
					</h2>
					<hr>
					<acme:textbox code="actor.name" path="name"
						placeholder="Sr. Benjumea" cssError="col-md-6" cssLabel="col-md-2"
						cssInput="col-md-4" />

					<acme:textbox code="actor.nick" path="nick" placeholder="carferben"
						cssError="col-md-6" cssLabel="col-md-2" cssInput="col-md-4" />

					<acme:textbox code="actor.link" path="link" placeholder="http://"
						cssError="col-md-6" cssLabel="col-md-2" cssInput="col-md-4" />
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
</div>
<br>
<div class="row">
	<div class="col-md-3">
		<span style="padding-left: 2.5em"> <acme:create
				url="socialProfile/create.do" name="buttonSocialProfile"
				code="socialProfile.create" />
		</span> <span style="padding-left: 0.5em"> <acme:cancel url="socialProfile/list.do"
				code="actor.back" />
		</span>
	</div>
</div>
</div>