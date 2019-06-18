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

<div class="content">
	
			<h3><spring:message code="sentMessages" /></h3>
			<display:table name="msgsSend" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
				<display:column titleKey="show"> 
					<a href="message/show.do?messageId=${row.id}">${row.subject}</a>
				</display:column>
				<display:column property="subject" titleKey="subjectMessage"></display:column>
				<display:column property="tags" titleKey="tag"></display:column>
			</display:table>	
			
			<br>
			
			<h3><spring:message code="recievedMessages" /></h3>
			<display:table name="msgsReceive" id="row1" requestURI="${requestURI}" pagesize="5" class="displaytag">
				<display:column titleKey="show"> 
					<a href="message/show.do?messageId=${row1.id}">${row1.subject}</a>
				</display:column>
				<display:column property="subject" titleKey="subjectMessage"></display:column>
				<display:column property="tags" titleKey="tag"></display:column>
			</display:table>	
			
			<br />
			<br />
			
  		<a href="message/create.do"><spring:message code="send.msg" /></a>
  	
  	<br>
  	
  	<security:authorize access="hasRole('ADMIN')">
  		<a href="message/sendNoti.do"><spring:message code="send.msg.broadcast" /></a>
	</security:authorize>

	<br />
	<div>
		<form method="get" action="#">
			<button type="submit">
				<spring:message code="back" />
			</button>
		</form>
	</div>
</div>