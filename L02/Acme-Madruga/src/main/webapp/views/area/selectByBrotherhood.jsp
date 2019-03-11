<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles"	uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<body>
	<c:choose>
		<%-- 
			* Posibles casos:
			* validBrotherhood==true -> Existen areas en la base de datos y el brotherhood NO tiene ninguna asignada
			* validBrotherhood==false -> Existen areas en la base de datos y el brotherhood SI tiene ninguna asignada
			* validBrotherhood==null -> NO existen areas en la base de datos	
		--%>
		<c:when test="${validBrotherhood==true}">
			<div>
				<form:form class="formularioEdicion" method="POST" modelAttribute="brotherhoodInit" action="area/brotherhood/edit.do">
					<acme:select3 items="${areas}" code="area.select" path="area"/>
  		        	<acme:cancel url="parade/brotherhood/list.do" code="cancel"/>
        		  	<acme:submit name="save" code="send"/>
				</form:form>
			</div>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${validBrotherhood==false}">
					<div>
						<p><spring:message code="area.alreadyInAnArea" /><jstl:out value="${areaSelected}"></jstl:out></p>
					</div>
				</c:when>
				<c:otherwise>
					<div>
						<p><spring:message code="area.notAreaAvailable" /></p>
					</div>			
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</body>