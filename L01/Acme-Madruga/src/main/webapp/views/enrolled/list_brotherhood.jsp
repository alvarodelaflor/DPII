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
				<strong><p><spring:message code="enrolled.details.accepted"></spring:message></p></strong>
				<display:table name="enrolledsAccepted" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">				
					<display:column titleKey="enrolled.edit"> 
						<a href="enrolled/brotherhood/edit.do?id=${row.id}"><spring:message code="enrolled.edit"></spring:message></a>
					</display:column>
					<display:column titleKey="enrolled.delete"> 
						<a href="enrolled/brotherhood/delete.do?id=${row.id}"><spring:message code="enrolled.delete"></spring:message></a>
					</display:column>
					<display:column titleKey="enrolled.show"> 
						<a href="enrolled/brotherhood/show.do?enrolledId=${row.id}">${row.member.name} ${row.member.surname}</a>
					</display:column>
				</display:table>
			</div>
			<div>
				<strong><p><spring:message code="enrolled.details.rejected"></spring:message></p></strong>
				<display:table name="enrolledsRejected" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">				
					<display:column titleKey="enrolled.edit"> 
						<a href="enrolled/brotherhood/edit.do?id=${row.id}"><spring:message code="enrolled.edit"></spring:message></a>
					</display:column>
					<display:column titleKey="enrolled.delete"> 
						<a href="enrolled/brotherhood/delete.do?id=${row.id}"><spring:message code="enrolled.delete"></spring:message></a>
					</display:column>
					<display:column titleKey="enrolled.show"> 
						<a href="enrolled/brotherhood/show.do?enrolledId=${row.id}">${row.member.name} ${row.member.surname}</a>
					</display:column>
				</display:table>			
			</div>
			<div>
				<strong><p><spring:message code="enrolled.details.pending"></spring:message></p></strong>
				<display:table name="enrolledsPending" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">				
					<display:column titleKey="enrolled.edit"> 
						<a href="enrolled/brotherhood/edit.do?id=${row.id}"><spring:message code="enrolled.edit"></spring:message></a>
					</display:column>
					<display:column titleKey="enrolled.delete"> 
						<a href="enrolled/brotherhood/delete.do?id=${row.id}"><spring:message code="enrolled.delete"></spring:message></a>
					</display:column>
					<display:column titleKey="enrolled.show"> 
						<a href="enrolled/brotherhood/show.do?enrolledId=${row.id}">${row.member.name} ${row.member.surname}</a>
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