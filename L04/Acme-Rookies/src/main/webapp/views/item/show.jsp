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

<security:authorize access="hasRole('PROVIDER')">
	<style>
		.linea
		{
		    float: left;
		}
	</style>

	<div>
		<jstl:if test="${providerLogin==true}">
			<form method="get" action="item/provider/delete.do">
				<button class="linea" name="itemId" value="${item.id}"><spring:message code="item.delete"/></button>
			</form>
			<form method="get" action="item/provider/edit.do">
				<button name="itemId" value="${item.id}"><spring:message code="item.edit"/></button>
			</form>
			<br>	
		</jstl:if>
	</div>
</security:authorize> 
<div class="content">
	
		<p><strong><spring:message code="item.name" /></strong><jstl:out value="${item.name}"></jstl:out></p>
		<p><strong><spring:message code="item.description" /></strong><jstl:out value="${item.description}"></jstl:out></p>
		<fieldset>
			<legend>
				<i><spring:message code="item.link" /></i><a href="${item.link}"><jstl:out value= "${item.link }"/></a>
			</legend>
			<p><strong>Provider <spring:message code="provider.name" /></strong><jstl:out value="${item.provider.name}"></jstl:out></p>
		</fieldset>
	<fieldset>
		
		<br>
		
	</fieldset>
</div>

<br>

		<c:choose>
    		<c:when test="${providerLogin==true}">
    			<acme:cancel url="item/listProvider.do?providerId=${item.provider.id}" code="back"/>  		
    		</c:when>   
    		<c:otherwise>
				<input type="button" value="back" name="back" onclick="history.back()" />
    		</c:otherwise>
        </c:choose>	
    			