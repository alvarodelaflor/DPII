<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>
<div class="container-fluid">
	<display:table name="travelPacks" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag table table-hover">
		<div class="row">
			<div class="col-md-12">
				<fieldset>
					<display:column titleKey="travelPack.name">
						<a href="travelPack/travelAgency/show.do?travelPackId=${row.id}">
							<jstl:out value="${row.name}"></jstl:out>
						</a>
					</display:column>
					<display:column titleKey="travelPack.customer">
						<jstl:out value= "${row.customer.name}"/>
					</display:column>
					<display:column titleKey="travelPack.draft">
						<jstl:choose>
							<jstl:when test="${row.draft}"><spring:message code="travelPack.draft.true" /></jstl:when>
							<jstl:otherwise>
								<spring:message code="travelPack.draft.false" />
							</jstl:otherwise>
						</jstl:choose>
					</display:column>
					
					<display:column titleKey="curricula.edit">
						<jstl:if test="${row.draft}">
						<a href="travelPack/travelAgency/edit.do?travelPackId=${row.id}">
							<spring:message code="curricula.edit" />
						</a>
						</jstl:if>
					</display:column>
				</fieldset>
			</div>
		</div>
	</display:table>
	<br>

	<div class="row">
		<div class="col-md-3">
			<span> <acme:cancel url="" code="actor.back" />
			</span>
		</div>
	</div>
</div>

