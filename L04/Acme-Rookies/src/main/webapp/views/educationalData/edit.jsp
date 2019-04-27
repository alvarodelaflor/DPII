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
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<body>

	<div>
    	<form:form class="formularioEdicion" method="POST" modelAttribute="educationalData" action="educationalData/hacker/edit.do">
          	<form:hidden path="id"/>
          	<form:hidden path="version"/>
          	<form:hidden path="curricula"/>
          	<form:hidden path="isCopy"/>
          	<acme:textbox path="degree" code="educationalData.degree"/>
          	<acme:textbox path="institution" code="educationalData.institution"/>
          	<acme:textbox path="mark" code="educationalData.mark"/>
			<acme:textboxMoment code="educationalData.startDate" path="startDate"/>
			<acme:textboxMoment code="educationalData.endDate" path="endDate"/>
          	<acme:submit name="save" code="save"/>
          	<acme:cancel url="curricula/show.do?curriculaId=${curricula.id}" code="back"/>
		</form:form>
	</div>
</body>
