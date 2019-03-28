<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">
	<body>
		<div>
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="position" action="position/administrator/edit.do">

				<form:hidden path="id"/>
				<form:hidden path="version"/>
				<div>
				<acme:textbox code="position.nameEs" path="nameEs" />
				<acme:textbox code="position.nameEn" path="nameEn" />
				<br/>
				</div>
				<div>
				<acme:cancel url=" " code="cancel" />
				<acme:submit name="save" code="send" />
				</div>
			</form:form>
		</div>
	</body>
</security:authorize>
