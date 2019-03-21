<!-- /*
 * CONTROL DE CAMBIOS show.jsp
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
				<display:table name="area" id="row" requestURI="${requestURI}"
					pagesize="5" class="displaytag">
					<display:column property="name" titleKey="area.name"></display:column>
					<display:column titleKey="currentlyUsed">
						<jstl:out value="${currentlyUsed}"></jstl:out>
					</display:column>
					<jstl:if test="${currentlyUsed == 0}">
					</jstl:if>
				</display:table>
			</div>
			<div>
				<display:table pagesize="5" name="${pictures}" id="picture"
					requestURI="${requestURI}">
					<display:column titleKey="pictures">
						<img width="300" src="${picture.trim()}" alt="<spring:message code='nopictures' />" />
					</display:column>
				</display:table>
			</div>
			<div>
				<acme:cancel url="area/chapter/list.do" code="Back" />
			</div>
		</security:authorize>
	</div>
</body>
