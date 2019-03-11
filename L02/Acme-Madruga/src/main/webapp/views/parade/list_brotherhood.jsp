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
	CONTROL DE CAMBIOS list_customer.jsp PARADE
  
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
		<security:authorize access="hasRole('BROTHERHOOD')"> 
			<c:choose>
				<c:when test="${checkValid==false and checkArea==false}">
					<p class="create"><input type="button" value=<spring:message code="brotherhood.createParade" /> id="buttonCreateParade" name="buttonCreateParade"  onclick="location.href='parade/brotherhood/create.do';"/></p>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${checkValid==true and checkArea==false}">
							<p><spring:message code="parade.float.empty"/></p>
						</c:when>
						<c:otherwise>
							<p><spring:message code="parade.area.empty"/></p>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<display:table name="parades" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
				<c:choose>
					<c:when test="${row.isFinal==true}">
						<display:column titleKey="parade.edit" style="background-color: #58FA58" > 
							
						</display:column>
						<display:column titleKey="parade.delete" style="background-color: #58FA58" > 
							<a href="parade/brotherhood/delete.do?id=${row.id}"><spring:message code="parade.delete"></spring:message></a>
						</display:column>
						<display:column titleKey="parade.ticker" style="background-color: #58FA58" > 
							<a href="parade/brotherhood/show.do?paradeId=${row.id}">${row.ticker}</a>
						</display:column>
						<display:column property="title" titleKey="parade.title" style="background-color: #58FA58"></display:column>
						<display:column titleKey="parade.isFinal" style="background-color: #58FA58">
							<spring:message code="parade.${row.isFinal}"/>
						</display:column>
						<display:column property="maxRow" titleKey="parade.maxRow" style="background-color: #58FA58"></display:column>
						<display:column property="maxColum" titleKey="parade.maxColum" style="background-color: #58FA58"></display:column>
						<display:column property="floatt.title" titleKey="parade.float" style="background-color: #58FA58"></display:column>
					</c:when>
					<c:otherwise>
						<display:column titleKey="parade.ticker" style="background-color: #FA5858" > 
							<a href="parade/brotherhood/edit.do?id=${row.id}"><spring:message code="parade.edit"></spring:message></a>
						</display:column>
						<display:column titleKey="parade.delete" style="background-color: #FA5858" > 
							<a href="parade/brotherhood/delete.do?id=${row.id}"><spring:message code="parade.delete"></spring:message></a>
						</display:column>
						<display:column titleKey="parade.ticker" style="background-color: #FA5858" > 
							<a href="parade/brotherhood/show.do?paradeId=${row.id}">${row.ticker}</a>
						</display:column>
						<display:column property="title" titleKey="parade.title" style="background-color: #FA5858"></display:column>
						<display:column titleKey="parade.isFinal" style="background-color: #FA5858">
							<spring:message code="parade.${row.isFinal}"/>
						</display:column>
						<display:column property="maxRow" titleKey="parade.maxRow" style="background-color: #FA5858"></display:column>
						<display:column property="maxColum" titleKey="parade.maxColum" style="background-color: #FA5858"></display:column>
						<display:column property="floatt.title" titleKey="parade.float" style="background-color: #FA5858"></display:column>					
					</c:otherwise>
				</c:choose>
			</display:table>
		</security:authorize>
	</div>
	<div>
		<form method="get" action="#">
			<button type="submit">
				<spring:message code="back" />
			</button>
		</form>
	</div>
</body>