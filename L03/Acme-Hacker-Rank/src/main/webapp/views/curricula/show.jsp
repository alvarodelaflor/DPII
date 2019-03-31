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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('HACKER')">
<style>
	.linea
	{
	    float: left;
	}
</style>

	<div>
		<jstl:if test="${hackerLogin==true}">
			<form method="get" action="/Acme-Hacker-Rank/curricula/hacker/delete.do">
				<button class="linea" name="curriculaId" value="${curricula.id}"><spring:message code="curricula.delete"/></button>
			</form>
			<form method="get" action="/Acme-Hacker-Rank/curricula/hacker/edit.do">
				<button name="curriculaId" value="${curricula.id}"><spring:message code="curricula.edit"/></button>
			</form>
			<br>	
		</jstl:if>
	</div>
</security:authorize> 
<div class="content">
	<fieldset>
		<legend>
			<spring:message code="curricula.data" />
		</legend>
		<c:choose>
    		<c:when test="${curricula.hacker.photo==''}">
				
    		</c:when>    
    		<c:otherwise>
				<img width="95" src="${curricula.hacker.photo}" alt="ERROR"/>
    		</c:otherwise>
		</c:choose>
		<p><strong><spring:message code="curricula.name" /></strong><jstl:out value="${curricula.name}"></jstl:out></p>
		<p><strong><spring:message code="curricula.statement" /></strong><jstl:out value="${curricula.statement}"></jstl:out></p>
		<p><strong><spring:message code="curricula.phone" /></strong><jstl:out value="${curricula.phone}"></jstl:out></p>
		<p><strong><spring:message code="curricula.linkGitHub" /></strong><a href =<jstl:out value="${curricula.linkGitHub}"></jstl:out>><jstl:out value="${curricula.linkGitHub}"></jstl:out></a></p>
		<p><strong><spring:message code="curricula.linkLinkedin" /></strong><a href =<jstl:out value="${curricula.linkLinkedin}"></jstl:out>><jstl:out value="${curricula.linkLinkedin}"></jstl:out></a></p>
		<p><strong><spring:message code="hacker.name" /></strong><jstl:out value="${curricula.hacker}"></jstl:out></p>
	</fieldset>
	<fieldset>
		<legend>
			<spring:message code="curricula.attachments" />
		</legend>
		<strong><spring:message code="curricula.positionData" /></strong>
		<jstl:if test="${hackerLogin==true}">
			<form method="get" action="/Acme-Hacker-Rank/positionData/hacker/create.do">
				<button  name="curriculaId" value="${curricula.id}"><spring:message code="positionData.create"/></button>
			</form>
		</jstl:if>
		<br>
			<display:table name="positionDatas" id="row1" requestURI="${requestURI}" pagesize="5" class="displaytag">
				<c:choose>
					<c:when test="${hackerLogin==true}">
						<display:column titleKey="curricula.edit">
							<a href="positionData/hacker/edit.do?positionDataId=${row1.id}"><img width="35" height="35" src="./images/edit.png" alt="${row1.id}" /></a>	
						</display:column>
						<display:column titleKey="curricula.delete">
							<a href="positionData/hacker/delete.do?positionDataId=${row1.id}"><img width="35" height="35" src="./images/delete.png" alt="${row1.id}" /></a>	
						</display:column>
					</c:when>
				</c:choose>
				<display:column titleKey="curricula.show"> 
					<a href="positionData/show.do?positionDataId=${row1.id}"><img width="35" height="35" src="./images/show.png" alt="${row1.id}" /></a>
				</display:column>
				<display:column property="title" titleKey="curricula.positonData.title"></display:column>
				<display:column property="description" titleKey="curricula.positinoData.description"></display:column>
			</display:table>
			<br>
			<br>
			<strong><spring:message code="curricula.educationalData" /></strong>
			<br>
			<jstl:if test="${hackerLogin==true}">
				<form method="get" action="/Acme-Hacker-Rank/educationalData/hacker/create.do">
					<button name="curriculaId" value="${curricula.id}"><spring:message code="educationalData.create"/></button>
				</form>
			</jstl:if>
			<display:table name="educationalDatas" id="row2" requestURI="${requestURI}" pagesize="5" class="displaytag">
				<c:choose>
					<c:when test="${hackerLogin==true}">
						<display:column titleKey="curricula.edit">
							<a href="educationalData/hacker/edit.do?educationalDataId=${row2.id}"><img width="35" height="35" src="./images/edit.png" alt="${row2.id}" /></a>	
						</display:column>
						<display:column titleKey="curricula.delete">
							<a href="educationalData/hacker/delete.do?educationalDataId=${row2.id}"><img width="35" height="35" src="./images/delete.png" alt="${row2.id}" /></a>	
						</display:column>
					</c:when>
				</c:choose>
				<display:column titleKey="curricula.show"> 
					<a href="educationalData/show.do?educationalDataId=${row2.id}"><img width="35" height="35" src="./images/show.png" alt="${row2.id}" /></a>
				</display:column>
				<display:column property="degree" titleKey="curricula.educationalData.degree"></display:column>
				<display:column property="institution" titleKey="curricula.educationalData.institution"></display:column>
			</display:table>
		<br>
	</fieldset>
</div>

<br>
<acme:cancel url="curricula/list.do?hackerId=${curricula.hacker.id}" code="back"/>
