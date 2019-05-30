<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<div class="row">
		<br>
		<div class="col-md-6" style="padding-left: 2.5em">
			<fieldset>
				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="cleaningTask.description" />:&nbsp;</strong>
					<jstl:out value="${cleaningTask.description}"></jstl:out>
				</div>
				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="cleaningTask.accomodation" />:&nbsp;</strong>
					<jstl:out value="${cleaningTask.accomodation.address}"></jstl:out>
				</div>
				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="cleaningTask.startMoment" />:&nbsp;</strong>
				<jstl:out value="${cleaningTask.startMoment}"></jstl:out>
				</div>
				<div class="row" style="padding-left: 1.5em">
					<strong><spring:message code="cleaningTask.endMoment" />:&nbsp;</strong>
				<jstl:out value="${cleaningTask.endMoment}"></jstl:out>
				</div>
			</fieldset>
		</div>
	</div>
	<div class="row">
		<div class="col-md-3">
			 <span><acme:historyBack />
			</span>
		</div>
	</div>
</div>
