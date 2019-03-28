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
	<div>
		<fieldset>
			<legend>
				<strong><spring:message code="searchTerm"/></strong><i><spring:message code="help"/></i>				
			</legend>
			<br>
			<form:form class="formularioEdicion" modelAttribute="historyFinderForm" method="POST" action="history/list.do">
				<acme:textbox code="history.title" path="title"/>
				<acme:textbox code="history.name" path="name"/>
				<br>
				<div style="float:left;">
					<acme:submit name="save" code="search"/>
				</div>
			</form:form>
			<div style="float:left;">
				<jstl:if test="${brotherhoodId!=-1}">
					<form method="get" action="/Acme-Parade/history/show.do">
						<button name="brotherhoodId" value="${brotherhoodId}"><spring:message code="showMyHistory"/></button>
					</form>	
				</jstl:if>
			</div>
		</fieldset>
	</div>
	
	<h2><spring:message code="results"/></h2>
	<c:choose>
		<c:when test="${empty brotherhoods}">
			<div>
				<img alt="Error" src="./images/triste.png" width="50" height="50">
				<br>
				<spring:message code="notResults"/>
				<br>
				<br>
			</div>
		</c:when>
		<c:otherwise>
			<display:table name="brotherhoods" id="row1" requestURI="${requestURI}" pagesize="5" class="displaytag">
				<display:column titleKey="show"> 
					<a href="history/show.do?brotherhoodId=${row1.id}">${row1.title}</a>
				</display:column>
				<display:column property="name" titleKey="brotherhoodName"></display:column>
			</display:table>	
		</c:otherwise>	
	</c:choose>

	<div>
		<form method="get" action="#">
			<button type="submit">
				<spring:message code="back" />
			</button>
		</form>
	</div>
</div>