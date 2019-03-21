<%--
 * list_customer.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<!--
	CONTROL DE CAMBIOS list_customer.jsp PROCESSION
  
	ALVARO 17/02/2019 12:54 CREACIÓN
	ALVARO 17/02/2019 12:54 AÑADIDO IS FINAL
-->

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
	<div>
		<h2><spring:message code="submittedParades"/></h2>
		<security:authorize access="hasRole('CHAPTER')"> 
			<display:table name="submittedParades" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
						<display:column titleKey="parade.edit" style="background-color: #ee7600" > 
								<a href="parade/chapter/edit.do?id=${row.id}"><spring:message code="parade.edit"></spring:message></a>
						</display:column>
						<display:column titleKey="parade.ticker" style="background-color: #ee7600" > 
							<a href="parade/chapter/show.do?paradeId=${row.id}">${row.ticker}</a>
						</display:column>
						<display:column property="title" titleKey="parade.title" style="background-color: #ee7600"></display:column>
						<display:column titleKey="parade.isFinal" style="background-color: #ee7600">
							<spring:message code="parade.${row.isFinal}"/>
						</display:column>
						<display:column property="maxRow" titleKey="parade.maxRow" style="background-color: #ee7600"></display:column>
						<display:column property="maxColum" titleKey="parade.maxColum" style="background-color: #ee7600"></display:column>
			
			</display:table>
		</security:authorize>
	</div>
	
	<div>
	<h2><spring:message code="acceptedParades"/></h2>
		<security:authorize access="hasRole('CHAPTER')"> 
			<display:table name="acceptedParades" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
						<display:column titleKey="parade.ticker" style="background-color: #58FA58" > 
							<a href="parade/chapter/show.do?paradeId=${row.id}">${row.ticker}</a>
						</display:column>
						<display:column property="title" titleKey="parade.title" style="background-color: #58FA58"></display:column>
						<display:column titleKey="parade.isFinal" style="background-color: #58FA58">
							<spring:message code="parade.${row.isFinal}"/>
						</display:column>
						<display:column property="maxRow" titleKey="parade.maxRow" style="background-color: #58FA58"></display:column>
						<display:column property="maxColum" titleKey="parade.maxColum" style="background-color: #58FA58"></display:column>
			</display:table>
		</security:authorize>
	</div>
	
	<h2><spring:message code="rejectedParades"/></h2>
		<security:authorize access="hasRole('CHAPTER')"> 
			<display:table name="rejectedParades" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
					
						<display:column titleKey="parade.ticker" style="background-color: #8b0000" > 
							<a href="parade/chapter/show.do?paradeId=${row.id}">${row.ticker}</a>
						</display:column>
						<display:column property="title" titleKey="parade.title" style="background-color: #8b0000"></display:column>
						<display:column titleKey="parade.isFinal" style="background-color: #8b0000">
							<spring:message code="parade.${row.isFinal}"/>
						</display:column>
						<display:column property="maxRow" titleKey="parade.maxRow" style="background-color: #8b0000"></display:column>
						<display:column property="maxColum" titleKey="parade.maxColum" style="background-color: #8b0000"></display:column>
			
			</display:table>
		</security:authorize>
		
	<div>
		<form method="get" action="#">
			<button type="submit">
				<spring:message code="back" />
			</button>
		</form>
	</div>
</body>