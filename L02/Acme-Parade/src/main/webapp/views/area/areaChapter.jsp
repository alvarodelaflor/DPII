<!-- /*
 * CONTROL DE CAMBIOS list.jsp
 * FRAN 19/02/2019 13:16 CREACIÓN DE LA VISTA
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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<body>

		<c:choose>
    		<c:when test="${status==false}">
				<h3><spring:message code="chapter.no.area"></spring:message></h3>
    		</c:when>    
    		<c:otherwise>
				<tr><td><spring:message code="area.name" /> <jstl:out	value="${area.name}"></jstl:out></td></tr>
				
				<br>
				<br>
				
				<h3><spring:message code="brotherhoodList"></spring:message></h3>
				
				<display:table name="brotherhood" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
				<display:column titleKey="brotherhood.title"><a href="brotherhood/showBrotherhood.do?id=${row.id}"><jstl:out value="${row.title}"/></a></display:column>
				<display:column property="surname" titleKey="brotherhood.surname"></display:column>
				<display:column property="name" titleKey="brotherhood.name"></display:column>

				<display:column titleKey="showParades" ><a href="parade/listParades.do?id=${row.id}"><spring:message code="parade" /></a></display:column>
				</display:table>
    		</c:otherwise>
		</c:choose>
</body>

<input type="button" value="back" name="back" onclick="history.back()" />
