<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<body>

<div class="content">
				<table>    						
    				<tr><td><spring:message code="socialProfile.show.name" /><jstl:out value="${socialProfile.name}"></jstl:out></td></tr>
    							
    				<tr><td><spring:message code="socialProfile.show.link" /><jstl:out value="${socialProfile.link}"></jstl:out></td></tr>
    				  		
      				<tr><td><spring:message code="socialProfile.show.nick" /><jstl:out value="${socialProfile.nick}"></jstl:out></td></tr>
  																          			
          			</table>
          			
          			<acme:cancel url=" " code="cancel"/>
      			</div>