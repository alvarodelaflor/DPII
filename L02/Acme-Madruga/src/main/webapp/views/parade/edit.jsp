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
    	<form:form class="formularioEdicion" method="POST" modelAttribute="parade" action="parade/brotherhood/edit.do">
          	<form:hidden path="id"/>
          	
          	<acme:textbox code="parade.title" path="title"/>
          	<acme:textbox code="parade.description" path="description"/>
          	<acme:textboxMoment code="parade.moment" path="moment"/>
          	<acme:selectTrueFalse code="parade" path="isFinal"/>
          	<acme:numberbox code="parade.maxRow" path="maxRow"/>
          	<acme:numberbox code="parade.maxColum" path="maxColum"/>
          	<acme:select items="${floats}" itemLabel="title" code="parade.float" path="floatt"/>
          	
          	<acme:cancel url="parade/brotherhood/list.do" code="cancel"/>
          	<acme:submit name="save" code="send"/>
		</form:form>
	</div>
</body>
