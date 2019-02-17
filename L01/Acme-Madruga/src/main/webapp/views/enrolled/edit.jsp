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
    	<form:form class="formularioEdicion" method="POST" modelAttribute="enrolled" action="enrolled/brotherhood/edit.do">
          	<form:hidden path="id"/>
          	
          	<form:label path="state">
			<spring:message code="enrolled.state" />:
			</form:label>
			<form:select path="state" >
				<form:option value="false"><spring:message code="enrolled.false"/></form:option>
				<form:option value="true"><spring:message code="enrolled.true"/></form:option>
			</form:select>
			<form:errors cssClass="error" path="state" />
			<br/>
          	
          	<acme:cancel url=" " code="cancel"/>
          	<input type="submit" name="save" value=<spring:message code="send" />/>
		</form:form>
	</div>
    <div>  
		<form method="get" action="enrolled/brotherhood/list.do">
    		<button type="submit"><spring:message code="back" /></button>
		</form>
	</div>
</body>
