<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="requests" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column	titleKey="request.status">
		<jstl:if test="${row.status eq true }"><span class="approved"><spring:message code="request.approved"/></span></jstl:if>
		<jstl:if test="${row.status eq false }"><span class="rejected"><spring:message code="request.rejected"/></span></jstl:if>
		<jstl:if test="${row.status eq null}"><span class="pending"><spring:message code="request.pending"/></span></jstl:if>
	</display:column>
	<display:column property="procession.title" titleKey="procession.title"/>
	<display:column	titleKey="request.show">
		<a href="/Acme-Madruga/request/member/show.do?requestId=${row.id}"><spring:message code="request.show"/></a>
	</display:column>
	<display:column	titleKey="request.delete">
		<jstl:if test="${row.status eq pending}">
				<a href="/Acme-Madruga/request/member/delete.do?requestId=${row.id}"><spring:message code="request.delete"/></a>
		</jstl:if>
	</display:column>
</display:table>
