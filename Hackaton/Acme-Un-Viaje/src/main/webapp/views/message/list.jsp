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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<hr>  	
  	<div class="container-fluid" style="padding-left: 2.5em">
	<display:table name="msgs" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag table table-hover">
		<div class="row" style="padding-left: 2.5em">
			<div class="col-md-6">
				<fieldset>
						<display:column titleKey="administrator.showMessage"> 
		<a href="message/show.do?messageId=${row.id}&mailboxId=${mailboxId}"><spring:message code="showMessage" /></a>
	</display:column>
		<display:column titleKey="administrator.editMessage"> 
		<a href="message/editMailbox.do?msgId=${row.id}"><spring:message code="editMessage" /></a>
	</display:column>
  	<display:column property="subject" titleKey="msg.subject"/>
  	<display:column property="body" titleKey="msg.body"/>
  	<display:column property="moment" titleKey="msg.moment"/>
				</fieldset>
			</div>
		</div>
	</display:table>
	<div class="row">
		<div class="col-md-3">
			 <span> <acme:cancel url=""
					code="actor.back" />
			</span>
		</div>
	</div>
	
</div>
  	  		