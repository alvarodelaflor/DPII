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
				<display:table name="positions" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag">
					<display:column property="nameEn" titleKey="position.nameEn">
					</display:column>
					<display:column property="nameEs" titleKey="position.nameEs">
					</display:column>
					<display:column titleKey="show">
						<a href="position/administrator/show.do?positionId=${row.id}">
						<spring:message code='show' /></a>
					</display:column>
					<display:column titleKey="edit">
						<a href="position/administrator/edit.do?positionId=${row.id}">
						<spring:message code='edit' /></a>
					</display:column>
				</display:table>
			</div>
			<div>
				<acme:cancel url="position/administrator/create.do" code="create" />
				<acme:cancel url=" " code="Back" />
			</div>
		</security:authorize>
	</div>
</body>