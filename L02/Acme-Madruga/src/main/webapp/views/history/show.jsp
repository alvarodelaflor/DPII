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

<div class="content">
	<c:choose>
		<c:when test="${ownerBrotherhood==true and empty history.inceptionRecord}">
			<form method="get" action="/Acme-Madruga/history/inceptionRecord/create.do">
					<spring:message code="history.empty.inceptionRecord"/>
					<button><spring:message code="createInceptionRecord"/></button>
			</form>
		</c:when>
		<c:otherwise>
			<display:table name="history.inceptionRecord" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
				<c:choose>
					<c:when test="${ownerBrotherhood==true}">
						<display:column titleKey="inceptionRecord.edit">
							<a href="history/inceptionRecord/edit.do?inceptionRecordId=${row.id}">${row.id}</a>	
						</display:column>
					</c:when>
				</c:choose>
				<display:column titleKey="inceptionRecord.show"> 
					<a href="history/inceptionRecord/show.do?inceptionRecordId=${row.id}">${row.title}</a>
				</display:column>
				<display:column property="description" titleKey="inceptionRecord.description"></display:column>
			</display:table>
		</c:otherwise>
	</c:choose>
</div>