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
  
   <display:table name="messageBoxes" id="row"  requestURI="${requestURI}"	pagesize="5" class="displaytag" >
  	
  	<display:column titleKey="administrator.showMessageBox"> 
          			<a href="message/list.do?messageBoxId=${row.id}"><spring:message code="seeMessages" /></a>
	</display:column>
  	<c:choose>
   		<c:when test="${row.isDefault != true}">
    <display:column titleKey="administrator.editMessageBox"> 
		<a href="messageBox/edit.do?messageBoxId=${row.id}"><spring:message code="editMessageBox" /></a>
	</display:column>
    	</c:when>    
    	<c:otherwise>
        	<display:column titleKey="administrator.editMessageBox"/>
    	</c:otherwise>
	</c:choose>
  	
  	<display:column property="name" titleKey="messageBox.name"/>
  	
  	<c:choose>
   		<c:when test="${row.isDefault != true}">
    <display:column titleKey="administrator.deleteMessageBox"> 
		<a href="messageBox/delete.do?messageBoxId=${row.id}"><spring:message code="deleteMessageBox" /></a>
	</display:column>
    	</c:when>    
    	<c:otherwise>
        	<display:column titleKey="administrator.deleteMessageBox"/>
    	</c:otherwise>
	</c:choose>
  	
  	</display:table>
  	
	<p class="create"><input type="button" value=<spring:message code="createMessageBox" /> id="buttonMessageBox" name="buttonMessageBox"  onclick="location.href='messageBox/create.do';"/></p>
  	
  	<br>
  	<a href="message/create.do"><spring:message code="send.msg" /></a>
  	
  	<br>
  	
  	<security:authorize access="hasRole('ADMIN')">
  		<a href="message/createNotification.do"><spring:message code="send.msg.notification" /></a>
	</security:authorize>

  			
			<form method="get" action=" ">
    			<button type="submit"><spring:message code="button.back" /></button>
			</form>
		
</body>