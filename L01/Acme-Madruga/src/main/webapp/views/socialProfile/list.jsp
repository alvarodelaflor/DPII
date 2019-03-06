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

<display:table name="socialProfiles" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
<display:column titleKey="socialProfile.name">
						<a href="socialProfile/show.do?socialProfileId=${row.id}"><jstl:out
								value="${row.name}"></jstl:out> </a>
					</display:column>	<display:column titleKey="socialProfile.delete"><a href="socialProfile/delete.do?socialProfileId=${row.id}"><spring:message code="socialProfile.delete" /></a></display:column>
	<display:column titleKey="socialProfile.edit"><a href="socialProfile/edit.do?socialProfileId=${row.id}"><spring:message code="socialProfile.edit" /></a></display:column>
</display:table>

<p class="create"><input type="button" value=<spring:message code="createSocialProfile" /> id="buttonSocialProfile" name="buttonSocialProfile"  onclick="location.href='socialProfile/create.do';"/></p>

<acme:cancel url=" " code="cancel"/>