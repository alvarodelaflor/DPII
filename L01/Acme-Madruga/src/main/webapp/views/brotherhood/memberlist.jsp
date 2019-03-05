<!-- 
	HIPONA 25-02-19 9:27 Creado
 -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h2><spring:message code="member.activeBrotherhoods" /></h2>
<display:table name="memberActiveBrotherhoods" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column titleKey="brotherhood.title"><a href="brotherhood/showBrotherhood.do?id=${row.id}"><jstl:out value="${row.title}"/></a></display:column>
	<display:column property="surname" titleKey="brotherhood.surname"></display:column>
	<display:column property="name" titleKey="brotherhood.name"></display:column>
	<display:column titleKey="showFloat" ><a href="float/listFloat.do?id=${row.id}"><spring:message code="floats" /></a></display:column>
	<display:column titleKey="showProcessions" ><a href="procession/listProcessions.do?id=${row.id}"><spring:message code="procession" /></a></display:column>
	<display:column titleKey="showEnrolleds" ><a href="member/listMembers.do?id=${row.id}"><spring:message code="memebers" /></a></display:column>
</display:table>

<h2><spring:message code="member.inactiveBrotherhoods" /></h2>
<display:table name="memberInactiveBrotherhoods" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column titleKey="brotherhood.title"><a href="brotherhood/showBrotherhood.do?id=${row.id}"><jstl:out value="${row.title}"/></a></display:column>
	<display:column property="surname" titleKey="brotherhood.surname"></display:column>
	<display:column property="name" titleKey="brotherhood.name"></display:column>

	<display:column titleKey="showFloat" ><a href="float/listFloat.do?id=${row.id}"><spring:message code="floats" /></a></display:column>
	<display:column titleKey="showProcessions" ><a href="procession/listProcessions.do?id=${row.id}"><spring:message code="procession" /></a></display:column>
	<display:column titleKey="showEnrolleds" ><a href="member/listMembers.do?id=${row.id}"><spring:message code="memebers" /></a></display:column>
</display:table>

<form method="get">
	<button formaction="#"><spring:message code="back"/></button>
</form>

