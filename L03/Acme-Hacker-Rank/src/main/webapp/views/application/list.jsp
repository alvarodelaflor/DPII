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
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>	
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- NO ESTÁ ACABADO, CARMEN -->


	<display:table name="applications" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
		<display:column titleKey="application.Show">
			<a href="application/hacker/show.do?id=${row.id}"><spring:message code="application.show" /></a></display:column>
			
			
		<c:choose>
    		<c:when test="${row.status == 'PENDING'}">
 					<display:column titleKey="application.edit">
						<a href="application/hacker/edit.do?applicationid=${row.id}"><spring:message code="application.edit" /></a>	
					</display:column>	
    		</c:when>    
    		<c:otherwise>
					<display:column titleKey="application.edit">
					</display:column>
    		</c:otherwise>
        </c:choose>	
		
			
		
		
		<display:column property="status" titleKey="application.status"></display:column>
		
		<display:column titleKey="application.position">
			<a href="position/show.do?id=${row.position.id}"><jstl:out value="${row.position.ticker}"/></a></display:column>
			
		<display:column titleKey="application.curricula.show">
			<a href="curricula/show.do?curriculaId=${row.curricula.id}"><jstl:out value="${row.curricula.name}"/></a></display:column>
		
		<display:column titleKey="application.problem">
			<a href="problem/hacker/show.do?problemId=${row.problem.id}"><jstl:out value="${row.problem.title}"/></a></display:column>
	
	</display:table>
	
					
	<acme:cancel url=" " code="application.back"/>

