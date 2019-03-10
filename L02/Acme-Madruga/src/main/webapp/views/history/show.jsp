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
			<div>
				<h2><spring:message code="history.inceptionRecord"/></h2>
				<display:table name="history.inceptionRecord" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
					<c:choose>
						<c:when test="${ownerBrotherhood==true}">
							<display:column titleKey="edit">
								<a href="history/inceptionRecord/edit.do?inceptionRecordId=${row.id}">${row.id}</a>	
							</display:column>
						</c:when>
					</c:choose>
					<display:column titleKey="show"> 
						<a href="history/inceptionRecord/show.do?inceptionRecordId=${row.id}">${row.title}</a>
					</display:column>
					<display:column property="description" titleKey="description"></display:column>
				</display:table>			
			</div>
			<div>
				<h2><spring:message code="history.periodRecord"/></h2>
				<display:table name="history.periodRecord" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
					<c:choose>
						<c:when test="${ownerBrotherhood==true}">
							<display:column titleKey="edit">
								<a href="history/periodRecord/edit.do?periodRecordId=${row.id}">${row.id}</a>	
							</display:column>
							<display:column titleKey="delete">
								<a href="history/periodRecord/delete.do?periodRecordId=${row.id}">${row.id}</a>	
							</display:column>
						</c:when>
					</c:choose>
					<display:column titleKey="show"> 
						<a href="history/periodRecord/show.do?periodRecordId=${row.id}">${row.title}</a>
					</display:column>
					<display:column property="description" titleKey="description"></display:column>
				</display:table>
				<c:choose>
					<c:when test="${ownerBrotherhood==true}">
						<form method="get" action="/Acme-Madruga/history/periodRecord/create.do">
							<button><spring:message code="createPeriodRecord"/></button>
						</form>
					</c:when>
				</c:choose>			
			</div>
		</c:otherwise>
	</c:choose>
</div>