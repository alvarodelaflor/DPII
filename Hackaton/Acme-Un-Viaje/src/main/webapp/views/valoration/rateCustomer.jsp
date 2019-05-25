<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form class="formularioEdicion" method="POST"
	modelAttribute="valoration" action="host/rateCustomer.do">

	<div class="row" style="padding-left: 2.5em">
		<div class="col-md-6">
			<fieldset>
				<acme:numberbox code="score" path="score" cssLabel="col-md-3"
					cssInput="col-md-5" cssError="col-md-4" />
				<acme:textbox code="comment" path="comment" cssLabel="col-md-3"
					cssInput="col-md-5" cssError="col-md-4" />
			</fieldset>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="col-md-3">
			<span style="padding-left: 2.5em"> <acme:submit name="save"
					code="save" />
			</span> <span style="padding-left: 0.5em"> <acme:cancel url="accomodation/host/list.do"
					code="cancel" />
			</span>
		</div>
	</div>
</form:form>