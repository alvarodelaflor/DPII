<!-- /*
 * CONTROL DE CAMBIOS adminEdit.jsp
 * FRAN 19/02/2019 15:47 CREACIÓN DE LA VISTA
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

<script type="text/javascript">
	function passwordVal {
		var password = document.getElementById("password").value;
		var cpassword = document.getElementById("cpassword").value;
					
		if (password != cpassword) {
			return alert("<spring:message code='passVal'/>");
		} 

	}
</script>

<security:authorize access="hasRole('ADMIN')">
	<body>
		<div>
			<form:form class="formularioEdicion" method="POST"
				modelAttribute="administratorForm" action="administrator/create.do">

				<acme:textbox code="administrator.photo" path="photo" />

				<acme:textbox code="administrator.name" path="name" />
				<acme:textbox code="administrator.middleName" path="middleName" />
				<acme:textbox code="administrator.surname" path="surname" />

				<acme:textbox code="administrator.email" path="email" />
				<acme:textbox code="administrator.phone" path="phone" />
				<acme:textbox code="administrator.address" path="address" />

				<acme:textbox code="administrator.username" path="username" />
				<acme:password code="administrator.password" path="password" />
				<acme:cpassword code="administrator.confirmPass" path="password" />

				<acme:cancel url=" " code="cancel" />
				<acme:submit name="save" code="send" />

			</form:form>
		</div>
	</body>
</security:authorize>