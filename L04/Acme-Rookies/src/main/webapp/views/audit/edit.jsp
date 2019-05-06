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

<body>

	<div>
    	<form:form class="formularioEdicion" method="POST" onsubmit="return phonenumberval();" modelAttribute="audit" action="audit/auditor/edit.do">
          	<form:hidden path="id"/>
          	<form:hidden path="version"/>
		    <fieldset>
				<legend>
					<spring:message code="audit.data" />
				</legend>
          		<acme:textarea path="text" code="audit.text" placeholder="Lorem ipsum dolor sit amet."/>
          		<acme:numberbox code="audit.score" path="score" step="0.01" placeholder="4.99"/>
          		<br>
          		<i><spring:message code="audit.verifyPosition" /></i>
				<acme:select items="${posFinal}" itemLabel="ticker" code="audit.position" path="position"/>
			</fieldset>
			<fieldset>
				<legend>
					<spring:message code="audit.configuration" />
				</legend>
				<i><spring:message code="audit.configuration.statusMode" /></i>
				<br><br>
				<acme:selectTrueFalse code="audit" path="status"/>
			</fieldset>
			<acme:submit name="save" code="save"/>
			<acme:cancel url="audit/list.do?" code="back"/>

		</form:form>
	</div>
</body>
