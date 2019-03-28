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
			<p><h2><spring:message code="members"></spring:message></h2></p>
				<display:table name="members" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag">
					<display:column property="name" titleKey="member.name"></display:column>
					
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
					
					<display:column property="userAccount.polarity" titleKey="polarityScore" />
					
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
						<a href="administrator/banMember.do?actorId=${row.id}"><spring:message	code="admin.ban" /></a>
						</display:column>
					</c:when>
					<c:otherwise>
						<display:column  titleKey="ban?">						
						<a href="administrator/banMember.do?actorId=${row.id}"><spring:message	code="admin.Unban" /></a>
						</display:column>
					</c:otherwise>
					</c:choose>
					
				</display:table>
			</div>
			<div>
			<p><h2><spring:message code="brotherhoods"></spring:message></h2></p>
				<display:table name="brotherhoods" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag">
					<display:column property="name" titleKey="brotherhood.name"></display:column>
					
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
					
					<display:column property="userAccount.polarity" titleKey="polarityScore" />
					
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
						<a href="administrator/banBrotherhood.do?actorId=${row.id}"><spring:message	code="admin.ban" /></a>
						</display:column>
					</c:when>
					<c:otherwise>
						<display:column  titleKey="ban?">						
						<a href="administrator/banBrotherhood.do?actorId=${row.id}"><spring:message	code="admin.Unban" /></a>
						</display:column>
					</c:otherwise>
					</c:choose>
		</display:table>
					
				<br />
	
	<acme:cancel url="administrator/actorList.do" code="refresh" />		
	<acme:cancel url=" " code="Back" />
				
			</div>
			<%-- <div>
			<p><h1><spring:message code="wordList"></spring:message></h1></p>
			<div>
			<p><h2><spring:message code="positives"></spring:message></h2></p>
				<display:table name="wordList" id="row" requestURI="${requestURI}" 
					pagesize="5" class="displaytag">
					<display:column property="posWords" titleKey="enWords"></display:column>
					<display:column property="posWordsEs" titleKey="esWords"></display:column>
					<display:column titleKey="edit">
						<a href="administrator/editWords.do"><spring:message code="edit"/></a>
					</display:column>
				</display:table>
			</div>
			<div>
			<p><h2><spring:message code="negatives"></spring:message></h2></p>
				<display:table name="wordList" id="row" requestURI="${requestURI}" 
					pagesize="5" class="displaytag">
					<display:column property="negWords" titleKey="enWords"></display:column>
					<display:column property="negWordsEs" titleKey="esWords"></display:column>
				</display:table>
			</div>
			</div>
			<div>
				<acme:cancel url=" " code="Back" />
			</div> --%>
		</security:authorize>
	</div>
</body>