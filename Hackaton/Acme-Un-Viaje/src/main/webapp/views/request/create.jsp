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
	<form:form class="formularioEdicion" method="POST"
		modelAttribute="request" action="request/customer/create.do">
		<div class="row">
			<div class="col-md-6">
				<fieldset>

					<acme:textbox code="request.place" path="place" cssLabel="col-md-3"
						cssInput="col-md-5" cssError="col-md-4" placeholder="Carmona"/>
					<acme:textbox code="request.numberOfPeople" path="numberOfPeople"
						 cssLabel="col-md-3" placeholder="4"
						cssInput="col-md-5" cssError="col-md-4" />
					<acme:textbox code="request.maxPrice" path="maxPrice" placeholder="2000"
						 cssLabel="col-md-3" cssError="col-md-4"
						cssInput="col-md-5" />
					<acme:textbox code="request.startDate" path="startDate"
						placeholder="2022/12/12" cssLabel="col-md-3" cssInput="col-md-5"
						cssError="col-md-4" />
					<acme:textbox code="request.endDate" path="endDate"
						placeholder="2023/01/03" cssLabel="col-md-3" cssInput="col-md-5"
						cssError="col-md-4" />
				</fieldset>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-3">
				<acme:submit name="save" code="actor.save" />
				<acme:cancel url="" code="actor.cancel" />
			</div>
		</div>
	</form:form>
</div>
