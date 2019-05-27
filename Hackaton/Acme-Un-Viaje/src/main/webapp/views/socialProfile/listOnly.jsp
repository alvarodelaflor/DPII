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
						<jstl:out value="${row.name}"></jstl:out>
					</display:column>

					<display:column titleKey="actor.nick">
						<jstl:out value="${row.nick}"></jstl:out>
					</display:column>

					<display:column titleKey="actor.link">
						<jstl:out value="${row.link}"></jstl:out>
					</display:column>
				</fieldset>
			</div>
		</div>
	</display:table>
</div>
<br>

<div class="row" >
	<div class="col-md-3"  style="padding-left: 2.5em">
		<acme:historyBack/>
	</div>
</div>

