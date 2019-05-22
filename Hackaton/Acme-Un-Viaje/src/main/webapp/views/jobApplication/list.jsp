<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid" style="padding-left: 2.5em">
	<div class="row">
		<div class="col-md-12">
			<h3><strong><spring:message code="jobApplication.hello" /></strong></h3>
			<br>
			<display:table name="jobApplications" id="row2" requestURI="${requestURI}" pagesize="5" class="displaytag table table-hover">
				<display:column titleKey="jobApplication.host">
					<a href="host/show.do?hostId=${row2.host.id}"><jstl:out value="${row2.host.name} ${row2.host.surname}"></jstl:out></a>			
				</display:column>
				<display:column titleKey="jobApplication.status">
					<c:choose>
						<c:when test="${row2.status==null or row2.status==''}">
							<spring:message code="null" />
						</c:when>
						<c:otherwise>
							<c:when test="${row2.status==true}">
								<spring:message code="true" />
							</c:when>
							<c:otherwise>
								<spring:message code="false" />
							</c:otherwise>
						</c:otherwise>
					</c:choose>			
				</display:column>
				<display:column property="cleanerMessage" titleKey="jobApplication.cleanerMessage2"></display:column>
				<display:column property="rejectMessage" titleKey="jobApplication.rejectMessage"></display:column>
				<c:choose>
					<c:when test="${cleanerLogger==true and row2.status==null}">
						<display:column titleKey="none">
							<acme:create name = "" url="jobApplication/cleaner/edit.do?jobApplicationId=${row2.id}" code="curricula.edit"/>	
						</display:column>
						<display:column titleKey="none">
							<acme:delete name = "" url="jobApplication/cleaner/delete.do?jobApplicationId=${row2.id}" code="curricula.delete"/>
						</display:column>
					</c:when>
				</c:choose>
			</display:table>
		</div>
	</div>
</div>