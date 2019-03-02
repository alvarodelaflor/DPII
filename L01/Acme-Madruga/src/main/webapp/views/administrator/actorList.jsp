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

<body>
	<div>
		<security:authorize access="hasRole('ADMIN')">
			<div>
			<p><h2><spring:message code="members"></spring:message></h2></p>
				<display:table name="members" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag">
					<display:column property="name" titleKey="member.name"></display:column>
					<display:column property="userAccount.spammerFlag" titleKey="spammer"></display:column>
					<display:column property="userAccount.banned" titleKey="banned"></display:column>
					<display:column titleKey="ban?">
						<a href="administrator/banMember.do?actorId=${row.id}">${row.userAccount.banned}</a>
					</display:column>
				</display:table>
			</div>
			<div>
			<p><h2><spring:message code="brotherhoods"></spring:message></h2></p>
				<display:table name="brotherhoods" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag">
					<display:column property="name" titleKey="brotherhood.name"></display:column>
					<display:column property="userAccount.spammerFlag" titleKey="spammer"></display:column>
					<display:column property="userAccount.banned" titleKey="banned"></display:column>
					<display:column titleKey="ban?">
						<a href="administrator/banBrotherhood.do?actorId=${row.id}">${row.userAccount.banned}</a>
					</display:column>
				</display:table>
				<br />
			</div>
			<div>
			<p><h1><spring:message code="wordList"></spring:message></h1></p>
			<div>
			<p><h2><spring:message code="positives"></spring:message></h2></p>
			</div>
			<div>
			<p><h2><spring:message code="negatives"></spring:message></h2></p>
			</div>
			</div>
			<div>
				<acme:cancel url=" " code="Back" />
			</div>
		</security:authorize>
	</div>
</body>