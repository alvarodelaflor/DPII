<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<display:table name="socialProfiles" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag table table-hover">
		<div class="row" style="padding-left: 2.5em">
			<div class="col-md-6">
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
			</div>
		</div>
	</display:table>
</div>
<br>

<div class="row">
	<div class="col-md-3">
		<span style="padding-left: 2.5em"> 
				<acme:create url="socialProfile/create.do" name="buttonSocialProfile" code="socialProfile.create"/>
		</span> <span style="padding-left: 0.5em"> <acme:cancel url=""
				code="actor.back" />
		</span>
	</div>
</div>

