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

<body>

<div class="content">
				<table>
					<tr><td><strong><spring:message code="msg.show" /></strong></td></tr>
    						
    				<tr><td><spring:message code="msg.show.subject" /><jstl:out value="${msg.subject}"></jstl:out></td></tr>
    							
    				<tr><td><spring:message code="msg.show.body" /><jstl:out value="${msg.body}"></jstl:out></td></tr>
    				  			          			
          			<tr><td><spring:message code="msg.show.moment" /><jstl:out value="${msg.moment}"></jstl:out></td></tr>				
          			
          			<tr><td><spring:message code="msg.show.tag" /><jstl:out value="${tag.tag}"></jstl:out></td></tr>															          			          														          			
          			</table>
          			
		<a href="message/delete.do?id=${msg.id}" ><input type="button" value="<spring:message code='delete'></spring:message>"></a>
          			<form>
	<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
</form>
          			
      			</div>