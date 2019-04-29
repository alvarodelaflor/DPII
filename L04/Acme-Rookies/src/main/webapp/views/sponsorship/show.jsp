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
			<div>
				<h2><spring:message code="sgi" /></h2>
				<display:table name="sponsorship" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
					<display:column property="provider.name" titleKey="provider.name" />
					<display:column property="position.title" titleKey="position.title" />
					<display:column property="banner" titleKey="sponsorship.banner" />
					<display:column property="target" titleKey="sponsorship.target" />
				</display:table>
				<br />
			</div>
			<div>
				<h2><spring:message code="cci" /></h2>
				<display:table name="sponsorship" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
					<display:column property="creditCard.holder" titleKey="creditCard.holder" />
					<display:column property="creditCard.make" titleKey="creditCard.make" />
					<display:column property="creditCard.number" titleKey="creditCard.number" />
					<display:column property="creditCard.CVV" titleKey="creditCard.CVV" />
					<display:column property="creditCard.expiration" titleKey="creditCard.expiration" />
				</display:table>
				<br />
			</div>
			<div>
				<acme:cancel url="sponsorship/provider/edit.do?sponsorshipId=${row.id}" code="edit"/>
				<acme:cancel url="sponsorship/provider/delete.do?sponsorshipId=${row.id}" code="delete"/>
				<acme:cancel url="sponsorship/provider/list.do" code="back"/>
			</div>
		</security:authorize>
	</div>
</body>