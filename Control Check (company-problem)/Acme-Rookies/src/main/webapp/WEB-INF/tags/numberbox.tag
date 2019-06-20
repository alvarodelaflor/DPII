<%--
 * textbox.tag
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>

<%@ attribute name="readonly" required="false" %>
<%@ attribute name="step" required="false" %>
<%@ attribute name="placeholder" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>
<jstl:if test="${step == null}">
	<jstl:set var="step" value="1" />
</jstl:if>
<jstl:if test="${placeholder == null}">
	<jstl:set var="placeholder" value="" />
</jstl:if>
<%-- Definition --%>

<div>
	<form:label path="${path}">
		<spring:message code="${code}" />
	</form:label>	
	<form:input type="number" path="${path}" readonly="${readonly}" step="${step}" placeholder="${placeholder}"/>	
	<form:errors path="${path}" cssClass="error" />
</div>	
