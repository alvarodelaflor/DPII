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
	

			<display:table name="msgs" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
				<display:column titleKey="show"> 
					<a href="message/show.do?messageId=${row.id}">${row.subject}</a>
				</display:column>
				<display:column property="subject" titleKey="subjectMessage"></display:column>
			</display:table>	

	<div>
		<form method="get" action="#">
			<button type="submit">
				<spring:message code="back" />
			</button>
		</form>
	</div>
</div>