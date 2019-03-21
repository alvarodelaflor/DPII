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
		<security:authorize access="hasRole('CHAPTER')">
			<div>
				<display:table name="areas" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag">
					<display:column titleKey="area.name">
						<a href="area/chapter/show.do?areaId=${row.id}"><jstl:out
								value="${row.name}"></jstl:out> </a>
					</display:column>
					<display:column titleKey="edit">
						<a href="area/chapter/edit.do?areaId=${row.id}">
						<spring:message code="area.assign"></spring:message> </a>
					</display:column>
				</display:table>
			</div>
			<div>
				<acme:cancel url=" " code="Back" />
			</div>
		</security:authorize>
	</div>
</body>

