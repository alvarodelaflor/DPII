<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<p><spring:message code="administrator.editWarranty" /></p>
<body>
	<form:form action="warranty/administrator/edit.do" method="POST"	modelAttribute="warranty">
		
		<form:hidden path="id" />
		<form:hidden path="version" />
				
		<form:label path="title">
			<spring:message code="warranty.title" />:
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		
		<br />
		
		<form:label path="terms">
			<spring:message code="warranty.terms" />:
		</form:label>
		<form:input path="terms" />
		<form:errors cssClass="error" path="terms" />
		
		<br />
		
		
		<form:label path="laws">
			<spring:message code="warranty.laws" />:
		</form:label>
		<form:input path="laws" />
		<form:errors cssClass="error" path="laws" />
		
		<br />
		
		
		<form:label path="isFinal">
			<spring:message code="warranty.isFinal" />:
		</form:label>
		<form:select path="isFinal" >
			<form:option value="false"><spring:message code="warranty.show.isFinal.NO"/></form:option>
			<form:option value="true"><spring:message code="warranty.show.isFinal.SI"/></form:option>
		</form:select>
		<form:errors cssClass="error" path="isFinal" />
		<br />
		
	
		<input type="submit" name="save" value="<spring:message code="administrator.editWarranty" />" />
		
		</form:form>
		
		<input type="button" name="cancel" value="<spring:message code="warranty.cancel"/>" onclick="javascript:relativeRedir('warranty/administrator/list.do');"/>
</body>