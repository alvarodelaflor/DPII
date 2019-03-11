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

<display:table name="sponsorships" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
<display:column titleKey="sponsorship.target">
						<a href="sponsorship/show.do?sponsorshipId=${row.id}"><jstl:out
								value="${row.target}"></jstl:out> </a>
					</display:column>	
					<display:column titleKey="sponsorship.delete"><a href="sponsorship/delete.do?sponsorshipId=${row.id}"><spring:message code="sponsorship.delete" /></a></display:column>
	<display:column titleKey="sponsorship.edit"><a href="sponsorship/edit.do?sponsorshipId=${row.id}"><spring:message code="sponsorship.edit" /></a></display:column>
</display:table>

<p class="create"><input type="button" value=<spring:message code="createSponsorship" /> id="buttonSponsorship" name="buttonSponsorship"  onclick="location.href='sponsorship/create.do';"/></p>

<acme:cancel url=" " code="cancel"/>