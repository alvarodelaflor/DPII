<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<hr>
<body>
	<div class="container-fluid">
		<security:authorize access="hasRole('ADMIN')">
			<div>
				<h2>
					<spring:message code="bannedActors" />
				</h2>
				<display:table name="bannedActors" id="row"
					requestURI="${requestURI}" pagesize="10"
					class="displaytag table table-hover">
					<div class="row" style="padding-left: 2.5em">
						<div class="col-md-6">
							<fieldset>
								<display:column property="userAccount.authorities"
									titleKey="role" sortable="true" />
								<display:column property="name" titleKey="name" />
								<display:column property="surname" titleKey="surname" />
								<display:column property="email" titleKey="email" />
								<display:column property="phone" titleKey="phone" />
								<display:column>
									<a href="admin/ban.do?id=${row.id}"><spring:message
											code="unban" /></a>
								</display:column>
							</fieldset>
						</div>
					</div>
				</display:table>
				<br /> <br />
			</div>
			<div>
				<h2>
					<spring:message code="nonBannedActors" />
				</h2>
				<display:table name="NonBannedActors" id="row"
					requestURI="${requestURI}" pagesize="10"
					class="displaytag table table-hover">
					<div class="row" style="padding-left: 2.5em">
						<div class="col-md-6">
							<fieldset>
								<display:column property="userAccount.authorities"
									titleKey="role" sortable="true" />
								<display:column property="name" titleKey="name" />
								<display:column property="surname" titleKey="surname" />
								<display:column property="email" titleKey="email" />
								<display:column property="phone" titleKey="phone" />
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
								<display:column>
									<c:if test="${row.userAccount.spammerFlag}">
									<a href="admin/ban.do?id=${row.id}"><spring:message
											code="ban" /></a>
									</c:if>
								</display:column>
							</fieldset>
						</div>
					</div>
				</display:table>
				<br /> <br />
			</div>
			<div class="row">
				<div class="col-md-3">
					<acme:cancel url="admin/actorList.do" code="Refresh" />
					<acme:cancel url=" " code="Back" />
				</div>
			</div>
		</security:authorize>
	</div>
</body>