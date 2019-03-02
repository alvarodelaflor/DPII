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
				<display:table name="position" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag">
					<display:column property="nameEn" titleKey="position.nameEn">
					</display:column>
					<display:column property="nameEs" titleKey="position.nameEs">
					</display:column>
					<display:column titleKey="currentlyUsed">
						<jstl:out value="${currentlyUsed}"></jstl:out>
					</display:column>
					<jstl:if test="${currentlyUsed == 0}">
					<display:column titleKey="delete">
						<a href="position/administrator/delete.do?positionId=${row.id}"> <spring:message
								code='delete' />
						</a>
					</display:column>
					</jstl:if>
				</display:table>
			</div>
			<div>
				<acme:cancel url="position/administrator/list.do" code="Back" />
			</div>
		</security:authorize>
	</div>
</body>