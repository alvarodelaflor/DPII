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

<p><spring:message code="editMessage" /></p>
<body>
	<form:form action="message/editMailbox.do" method="POST"	modelAttribute="msg">
		
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="moment" />
		<form:hidden path="subject" />
		<form:hidden path="body" />
		<form:hidden path="priority.value" />
		<form:hidden path="emailReceiver" />
		
		<form:label path="mailboxes">
			<spring:message code="msg.mailboxes" />:
		</form:label>
		<form:select path="mailboxes">
   		 	<form:options items="${nameMailbox}" />
		</form:select>
		<form:errors cssClass="error" path="mailboxes" />
		
		<br />
		
				
		
		
		<input type="submit" name="save" value="<spring:message code="editMessage" />" />
		
		</form:form>
		
		<input type="button" name="cancel" value="<spring:message code="msg.cancel"/>" onclick="javascript:relativeRedir('mailbox/list.do');"/>
</body>