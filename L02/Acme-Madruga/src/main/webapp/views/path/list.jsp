
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

<display:table name="paths" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<display:column titleKey="path.parade">
		<a href="parade/show.do?paradeId=${row.parade.id}"><jstl:out value="${row.parade.title}"/></a>
	</display:column>
	<display:column titleKey="path.origin"><jstl:out value="(${row.origin.latitude}, ${row.origin.longitude})"/></display:column>
</display:table>

<div>
	<form method="get" action="#">
		<button type="submit">
			<spring:message code="back" />
		</button>
	</form>
</div>
