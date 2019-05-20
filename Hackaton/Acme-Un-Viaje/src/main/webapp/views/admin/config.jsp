<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<br />
<body>
	<div class="container-fluid">
		<security:authorize access="hasRole('ADMIN')">
			<div class="spamList">
				<h3>
					<i><spring:message code="spamList" /></i>
				</h3>
				<c:out value="${config.spamList}"></c:out>
				<br /> <br />
				<form:form class="formularioEdicion" method="GET"
					action="admin/newsw.do?newsw='${newsw}'.do">
					<spring:message code="newSpamWord" />
					<input type=text name="newsw" required="required" />
					<acme:submit name="save" code="save" />
				</form:form>
				<form:form class="formularioEdicion" method="GET"
					action="admin/delsw.do?delsw='${delsw}'.do">
					<spring:message code="deleteSpamWord" />
					<input type=text name="delsw" required="required" />
					<acme:submit name="save" code="delete" />
				</form:form>
				<br />
			</div>
			<div class="scoreList">
				<h3>
					<i><spring:message code="scoreList" /></i>
				</h3>
				<c:out value="${config.scoreList}"></c:out>
				<br /> <br />
				<form:form class="formularioEdicion" method="GET"
					action="admin/newscw.do?newscw='${newscw}'.do">
					<spring:message code="newScoreWord" />
					<input type=text name="newscw" required="required" />
					<acme:submit name="save" code="save" />
				</form:form>
				<form:form class="formularioEdicion" method="GET"
					action="admin/delscw.do?delscw='${delscw}'.do">
					<spring:message code="deleteScoreWord" />
					<input type=text name="delscw" required="required" />
					<acme:submit name="save" code="delete" />
				</form:form>
				<br />
			</div>
			<div class="creditCardMakeList">
				<h3>
					<i><spring:message code="creditCardMakeList" /></i>
				</h3>
				<c:out value="${config.creditCardMakeList}"></c:out>
				<br /> <br />
				<form:form class="formularioEdicion" method="GET"
					action="admin/newccm.do?newccm='${newccm}'.do">
					<spring:message code="newCreditCardMake" />
					<input type=text name="nnewccm" required="required" />
					<acme:submit name="save" code="save" />
				</form:form>
				<form:form class="formularioEdicion" method="GET"
					action="admin/delccm.do?delccm='${delccm}'.do">
					<spring:message code="deleteCreditCardMake" />
					<input type=text name="delccm" required="required" />
					<acme:submit name="save" code="delete" />
				</form:form>
				<br />
			</div>
			<div class="transporterBanRatio">
				<h3>
					<i><spring:message code="transporterBanRatio" /></i>
				</h3>
				<c:out value="${config.transporterBanRatio} %"></c:out>
				<br /> <br />
				<form:form class="formularioEdicion" method="GET"
					action="admin/traratio.do?traratio='${traratio}'.do">
					<spring:message code="newTransporterBanRatio" />
					<input type=number name="traratio" required="required" />
					<acme:submit name="save" code="save" />
				</form:form>
				<br />
			</div>
		</security:authorize>
	</div>
</body>