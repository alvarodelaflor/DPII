<!-- /*
 * CONTROL DE CAMBIOS list.jsp
 * FRAN 19/02/2019 13:16 CREACIÓN DE LA VISTA
  -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
	<div>
		<security:authorize access="hasRole('ADMIN')">
			<div>
			<p><h2><spring:message code="companies"></spring:message></h2></p>
				<display:table name="companies" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag">
					<display:column property="name" titleKey="company.commercialName"></display:column>
					
					<c:choose>
					<c:when test="${row.userAccount.spammerFlag == false}">
						<display:column  titleKey="banned">	<!-- B123123u123g123ge23dDe1231231tect13213123ed -->					
						<spring:message code="spammer.show.isFinal.NO" />
						</display:column>
					</c:when>
					<c:otherwise>
						<display:column  titleKey="banned">						
						<spring:message	code="spammer.show.isFinal.SI" />
						</display:column>
					</c:otherwise>
					</c:choose>
					
					<%-- <display:column property="userAccount.polarity" titleKey="polarityScore" /> --%>
					
					<c:choose>
					<c:when test="${row.userAccount.banned == false}">
						<display:column  titleKey="spammer">						
						<spring:message code="spammer.show.isFinal.NO" />
						</display:column>
					</c:when>
					<c:otherwise>
						<display:column  titleKey="spammer">						
						<spring:message	code="spammer.show.isFinal.SI" />
						</display:column>
					</c:otherwise>
					</c:choose>
					
					<c:choose>
					<c:when test="${row.userAccount.banned == false}">
						<display:column  titleKey="ban?">							
						<a href="administrator/banCompany.do?actorId=${row.id}"><spring:message	code="admin.ban" /></a>
						</display:column>
					</c:when>
					<c:otherwise>
						<display:column  titleKey="ban?">						
						<a href="administrator/banCompany.do?actorId=${row.id}"><spring:message	code="admin.Unban" /></a>
						</display:column>
					</c:otherwise>
					</c:choose>
					
				</display:table>
			</div>
			<div>
			<p><h2><spring:message code="rookies"></spring:message></h2></p>
				<display:table name="rookies" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag">
					<display:column property="name" titleKey="rookie.name"></display:column>
					
					<c:choose>
					<c:when test="${row.userAccount.spammerFlag == false}">
						<display:column  titleKey="spammer">						
						<spring:message code="spammer.show.isFinal.NO" />
						</display:column>
					</c:when>
					<c:otherwise>
						<display:column  titleKey="spammer">						
						<spring:message	code="spammer.show.isFinal.SI" />
						</display:column>
					</c:otherwise>
					</c:choose>
					
					<%-- <display:column property="userAccount.polarity" titleKey="polarityScore" /> --%>
					
					<c:choose>
					<c:when test="${row.userAccount.banned == false}">
						<display:column  titleKey="banned">						
						<spring:message code="spammer.show.isFinal.NO" />
						</display:column>
					</c:when>
					<c:otherwise>
						<display:column  titleKey="banned">						
						<spring:message	code="spammer.show.isFinal.SI" />
						</display:column>
					</c:otherwise>
					</c:choose>
					
					<c:choose>
					<c:when test="${row.userAccount.banned == false}">
						<display:column  titleKey="ban?">							
						<a href="administrator/banRookie.do?actorId=${row.id}"><spring:message	code="admin.ban" /></a>
						</display:column>
					</c:when>
					<c:otherwise>
						<display:column  titleKey="ban?">						
						<a href="administrator/banRookie.do?actorId=${row.id}"><spring:message	code="admin.Unban" /></a>
						</display:column>
					</c:otherwise>
					</c:choose>
		</display:table>
					
				<br />
	
	<acme:cancel url="administrator/actorList.do" code="refresh" />		
	<acme:cancel url=" " code="Back" />
		
		</security:authorize>
	</div>
</body>