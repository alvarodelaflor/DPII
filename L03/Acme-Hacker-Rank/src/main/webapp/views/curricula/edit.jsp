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
    	<form:form class="formularioEdicion" method="POST" modelAttribute="curricula" action="curricula/hacker/edit.do">
          	<form:hidden path="id"/>
          	<form:hidden path="version"/>
          	<acme:textbox path="name" code="curricula.name"/>
          	<acme:textbox path="statement" code="curricula.statement"/>
          	<acme:textbox path="phone" code="curricula.phone"/>
          	<acme:textbox path="linkGitHub" code="curricula.linkGitHub"/>
          	<acme:textbox path="linkLinkedin" code="curricula.linkLinkedin"/>
          	<acme:textbox path="miscellaneous" code="curricula.miscellaneous"/>
          	<acme:submit name="save" code="save"/>
          	<acme:cancel url="#" code="cancel"/>
		</form:form>
	</div>
</body>
