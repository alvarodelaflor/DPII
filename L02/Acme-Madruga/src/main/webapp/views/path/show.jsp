
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jstl:if test="${owner == false}]">
<ol>
<jstl:forEach var="segment" items="${segments}">
	<li> <jstl:out value=" Origen: (${segment.latitude}, ${segment.longitude}); Destino: (${segment.destination.latitude}, ${segment.destination.longitude})"/> </li>
</jstl:forEach>
</ol>
</jstl:if>

<jstl:if test="${owner == true}]">
<ol>
<jstl:forEach var="segment" items="${segments}">
	<li> <jstl:out value=" Origen: (${segment.latitude}, ${segment.longitude}); Destino: (${segment.destination.latitude}, ${segment.destination.longitude})"/> <a href="/path/edit.do?segmentId=${segment.id}"></a> </li>
</jstl:forEach>
</ol>
</jstl:if>
<div>
	<form method="get" action="#">
		<button type="submit">
			<spring:message code="back" />
		</button>
	</form>
</div>
