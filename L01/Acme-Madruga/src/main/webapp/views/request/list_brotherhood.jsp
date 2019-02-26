<%--
 * list_customer.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<!--
	CONTROL DE CAMBIOS list_customer.jsp FLOATBRO
  
	ALVARO 17/02/2019 19:28 CREACIÓN
-->

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
	<div>
		<security:authorize access="hasRole('BROTHERHOOD')"> 
			<div>
				<strong><p><spring:message code="request.details.accepted"></spring:message></p></strong>
				<display:table name="requestsAccepted" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">				
					<display:column titleKey="request.edit"> 
						<a href="request/brotherhood/edit.do?id=${row.id}"><spring:message code="request.edit"></spring:message></a>
					</display:column>
					<display:column titleKey="request.delete"> 
						<a href="request/brotherhood/delete.do?id=${row.id}"><spring:message code="request.delete"></spring:message></a>
					</display:column>
					<display:column titleKey="request.show"> 
						<a href="request/brotherhood/show.do?requestId=${row.id}">${row.id}</a>
					</display:column>
					<display:column titleKey="request.member"> 
						${row.member.name} ${row.member.surname}
					</display:column>
					<display:column titleKey="request.procession"> 
						${row.positionAux.procession.title}
					</display:column>
				</display:table>
			</div>
			<div>
				<strong><p><spring:message code="request.details.rejected"></spring:message></p></strong>
				<display:table name="requestsRejected" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">				
					<display:column titleKey="request.edit"> 
						<a href="request/brotherhood/edit.do?id=${row.id}"><spring:message code="request.edit"></spring:message></a>
					</display:column>
					<display:column titleKey="request.delete"> 
						<a href="request/brotherhood/delete.do?id=${row.id}"><spring:message code="request.delete"></spring:message></a>
					</display:column>
					<display:column titleKey="request.show"> 
						<a href="request/brotherhood/show.do?requestId=${row.id}">${row.id}</a>
					</display:column>
					<display:column titleKey="request.member"> 
						${row.member.name} ${row.member.surname}
					</display:column>
					<display:column titleKey="request.procession"> 
						${row.positionAux.procession.title}
					</display:column>
				</display:table>			
			</div>
			<div>
				<strong><p><spring:message code="request.details.pending"></spring:message></p></strong>
				<display:table name="requestsPending" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">				
					<display:column titleKey="request.edit"> 
						<a href="request/brotherhood/edit.do?id=${row.id}"><spring:message code="request.edit"></spring:message></a>
					</display:column>
					<display:column titleKey="request.delete"> 
						<a href="request/brotherhood/delete.do?id=${row.id}"><spring:message code="request.delete"></spring:message></a>
					</display:column>
					<display:column titleKey="request.show"> 
						<a href="request/brotherhood/show.do?requestId=${row.id}">${row.id}</a>
					</display:column>
					<display:column titleKey="request.member"> 
						${row.member.name} ${row.member.surname}
					</display:column>
					<display:column titleKey="request.procession"> 
						${row.positionAux.procession.title}
					</display:column>
				</display:table>
			</div>
		</security:authorize>
	</div>
	<div>
		<form method="get" action="#">
			<button type="submit">
				<spring:message code="back" />
			</button>
		</form>
	</div>
</body>