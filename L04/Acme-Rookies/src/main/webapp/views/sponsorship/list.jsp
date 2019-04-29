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
			<display:table name="sponsorships" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
				<display:column property="provider.name" titleKey="provider.name" />
				<display:column property="position.title" titleKey="position.title" />
				<display:column>
					<a href="provider/sponsorship/show.do?sponsorshipId=${row.id}">
						<spring:message	code="show" />
					</a>
				</display:column>
			</display:table>
			<br />
			<acme:cancel url="provider/sponsorship/create.do" code="create"/>
		</security:authorize>
	</div>
</body>