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
			<form:form class="formularioEdicion" method="POST" modelAttribute="sponsorship" action="sponsorship/provider/edit.do">
			
			<form:hidden path="id" />
			
			<acme:textbox code="sponsorship.banner" path="banner"/>
			<br />
			<acme:textbox code="sponsorship.target" path="target"/>
			<br />
			<%-- <acme:select items="${positions}" itemLabel="position" code="positions" path="position"/> --%>
			
			<form:label path="position">
			<spring:message code="positions" />:
			</form:label>
			<form:select multiple="false" path="position">
   	 		<form:options items="${positions}" required="required"/>
			</form:select>
			<form:errors cssClass="error" path="position" />
			<br />	
			<br />
			
			<acme:submit name="save" code="submit"/>
			<acme:cancel url="sponsorship/provider/list.do" code="cancel"/>
			</form:form>
			
		</security:authorize>
	</div>
</body>