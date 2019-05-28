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

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<div class="row">
		<div class="col-md-6">
			<form:form class="formularioEdicion" method="POST" modelAttribute="travelPack" action="travelPack/travelAgency/create.do">
				<form:hidden path="id" />
				<form:hidden path="customer" />
				<fieldset>
					<hr>
					<acme:textbox code="travelPack.name" path="name" cssError="col-md-5" cssLabel="col-md-3" cssInput="col-md-4" />

					<spring:message code="warranty.finalMode" />
					&nbsp; &nbsp; &nbsp; &nbsp;
					<spring:message code="travelPack.draft.true" />
					<form:radiobutton path="draft" value="true"  checked="checked" />
					<spring:message code="travelPack.draft.false" />
					<form:radiobutton path="draft" value="false" />
					<form:select itemLabel="title" multiple="false" path="warranty">
	   		 		<form:options items="${warranties}" itemLabel="title" required="required"/>
					</form:select>
				</fieldset>
				<br>
				<div class="row">
					<span><acme:submit name="save" code="actor.save" /></span> <span style="padding-left: 0.5em"> <acme:cancel url="/travelPack/travelAgency/list.do" code="actor.back" />
					</span>
				</div>
			</form:form>
		</div>
	</div>
	<br>
</div>