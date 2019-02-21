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

	<div>
		<p>
			<label class="strong"><spring:message code="request.status"/>: </label>
			<jstl:if test="${request.status eq true }"><span class="approved"><spring:message code="request.approved"/></span></jstl:if>
			<jstl:if test="${request.status eq false }"><span class="rejected"><spring:message code="request.rejected"/></span></jstl:if>
			<jstl:if test="${request.status eq null}"><span class="pending"><spring:message code="request.pending"/></span></jstl:if>
		</p>
	</div>

	<div>
		<p>
			<label class="strong"><spring:message code="request.procession"/>: </label>
			<jstl:out value="${request.procession.title}"/>
		</p>
	</div>
	
	<jstl:if test="${request.status eq true }">
	<div>
		<p>
			<label class="strong"><spring:message code="request.row"/>: </label>
			<jstl:out value="${request.row}"/>
		</p>
		<p>
			<label class="strong"><spring:message code="request.col"/>: </label>
			<jstl:out value="${request.col}"/>
		</p>
	</div>
	</jstl:if>
	
	<jstl:if test="${request.status eq false }">
	<div>
		<p>
			<label class="strong"><spring:message code="request.rejectionReason"/>: </label>
			<jstl:out value="${request.rejectionReason}"/>
		</p>		
	</div>
	</jstl:if>
	<jstl:if test="${request.status eq null}">
		<div>
			<a href="/Acme-Madruga/request/member/delete.do?requestId=${request.id}"><spring:message code="request.delete"/></a>
		</div>
	</jstl:if>
