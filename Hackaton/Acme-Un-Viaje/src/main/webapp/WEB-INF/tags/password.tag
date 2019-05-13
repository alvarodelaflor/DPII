<%--
 * password.tag
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Attributes --%>

<%@ attribute name="path" required="true" rtexprvalue="true"%>
<%@ attribute name="code" required="true" rtexprvalue="true"%>
<%@ attribute name="cssLabel" required="false"%>
<%@ attribute name="cssInput" required="false"%>

<%-- Definition --%>
<jstl:if test="${cssLabel == null}">
	<jstl:set var="cssLabel" value="" />
</jstl:if>
<jstl:if test="${cssInput == null}">
	<jstl:set var="cssInput" value="" />
</jstl:if>
<jstl:if test="${cssInput != null or cssLabel != null}">
	<jstl:set var="row" value="row" />
</jstl:if>

<div class="${row}">
	<div class="${cssLabel}">
		<form:label path="${path}">
			<spring:message code="${code}" />
		</form:label>
	</div>
	<div class="${cssInput}">
		<form:password path="${path}" style="width:100%"/>
	</div>
	<form:errors path="${path}" cssClass="error" />
</div>
