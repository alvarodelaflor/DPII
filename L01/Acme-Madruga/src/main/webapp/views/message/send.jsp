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
	<form:form action="message/edit.do" method="POST"	modelAttribute="msg">
		
		<form:hidden path="id" />
		<form:hidden path="version" />
		
				
		<form:label path="subject">
			<spring:message code="msg.subject" />:
		</form:label>
		<form:input path="subject" />
		<form:errors cssClass="error" path="subject" />
		
		<br />
		
		<form:label path="body">
			<spring:message code="msg.body" />:
		</form:label>
		<form:input path="body" />
		<form:errors cssClass="error" path="body" />
		
		<br />
		
		<form:label path="priority.value">
			<spring:message code="msg.priority" />:
		</form:label>
		<form:select path="priority.value" >
			<form:option value="HIGH"><spring:message code="high" /></form:option>
			<form:option value="NEUTRAL"><spring:message code="neutral" /></form:option>
			<form:option value="LOW"><spring:message code="low" /></form:option>
		</form:select>		
		<form:errors cssClass="error" path="priority.value" />
		
		<br />
		
		<form:label path="emailReceiver">
			<spring:message code="receiver.email" />:
		</form:label>
		<form:select multiple="true" path="emailReceiver">
   		 	<form:options items="${emails}" />
		</form:select>
		<form:errors cssClass="error" path="emailReceiver" />
		
		<br />
		
		
		<input type="submit" name="save" value="<spring:message code="editMessage" />" />
		
		</form:form>
		
		<input type="button" name="cancel" value="<spring:message code="msg.cancel"/>" onclick="javascript:relativeRedir('messageBox/list.do');"/>
</body>