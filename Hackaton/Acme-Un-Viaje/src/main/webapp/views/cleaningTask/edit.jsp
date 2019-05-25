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
<div class="container-fluid"  style="padding-left: 2.5em" >
	<div class="row">
	
		<div class="col-md-6" style="padding-left: 2.5em">
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="cleaningTask" action="cleaningTask/edit.do">
				<form:hidden path="id" />
				<fieldset>

					<h2>
						<spring:message code="host.editCreateCleaningTask" />
						&nbsp;
					</h2>
					<hr>
					<acme:textbox code="cleaningTask.description" path="description"
						 cssError="col-md-6" cssLabel="col-md-2"
						cssInput="col-md-4" />

					<acme:textbox code="cleaningTask.cleaner" path="cleaner" 
						cssError="col-md-6" cssLabel="col-md-2" cssInput="col-md-4" />

					<acme:textbox code="cleaningTask.accomodation" path="accomodation"
						cssError="col-md-6" cssLabel="col-md-2" cssInput="col-md-4" />
						
						<acme:textbox code="cleaningTask.startMoment" path="startMoment" placeholder="2019/12/11 11:00:00"
						cssError="col-md-6" cssLabel="col-md-2" cssInput="col-md-4" />
						
						<acme:textbox code="cleaningTask.endMoment" path="endMoment" placeholder="2019/12/11 11:00:00"
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
		 <span style="padding-left: 0.5em"> <acme:cancel url=""
				code="actor.back" />
		</span>
	</div>
</div>