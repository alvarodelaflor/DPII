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
		<security:authorize access="hasRole('PROVIDER')">
			<form:form class="formularioEdicion" method="POST" modelAttribute="sponsorship" action="provider/sponsorship/edit.do">
			
			<form:hidden path="id" />
			
			<acme:textbox code="sponsorship.banner" path="banner"/>
			<acme:textbox code="sponsorship.target" path="target"/>
			<acme:select items="${positions}" itemLabel="positions" code="positions" path="position"/>
			<br />
			
			<acme:submit name="save" code="submit"/>
			<acme:cancel url="provider/sponsorship/list.do" code="cancel"/>
			</form:form>
			
		</security:authorize>
	</div>
</body>