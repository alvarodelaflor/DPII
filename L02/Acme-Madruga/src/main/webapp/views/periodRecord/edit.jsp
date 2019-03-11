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
    	<form:form class="formularioEdicion" method="POST" modelAttribute="periodRecord" action="history/periodRecord/edit.do">
          	<form:hidden path="id"/>
          	<form:hidden path="version"/>
          	<acme:textbox code="periodRecord.title" path="title"/>
          	<acme:textbox code="periodRecord.description" path="description"/>
          	<acme:textbox code="periodRecord.photos" path="photos"/>
          	<acme:numberbox code="periodRecord.startYear" path="startYear"/>
          	<acme:numberbox code="periodRecord.endYear" path="endYear"/>
          	<acme:cancel url="history/show.do?brotherhoodId=${brotherhoodId}" code="cancel"/>
          	<acme:submit name="save" code="send"/>
		</form:form>
	</div>
</body>
