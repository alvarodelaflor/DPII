<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('HACKER')">
	<style>
		.linea
		{
		    float: left;
		}
	</style>

	<div>
		<jstl:if test="${hackerLogin==true}">
			<form method="get" action="/Acme-Hacker-Rank/curricula/hacker/delete.do">
				<button class="linea" name="curriculaId" value="${curricula.id}"><spring:message code="curricula.delete"/></button>
			</form>
			<form method="get" action="/Acme-Hacker-Rank/curricula/hacker/edit.do">
				<button name="curriculaId" value="${curricula.id}"><spring:message code="curricula.edit"/></button>
			</form>
			<br>	
		</jstl:if>
	</div>
</security:authorize> 
<div class="content">
	<fieldset>
		<legend>
			<spring:message code="curricula.data" />
		</legend>
		<c:choose>
    		<c:when test="${curricula.hacker.photo=='' or curricula.hacker.photo==null}">
				<img width="95" src="./images/hacker.png" alt="ERROR"/>
    		</c:when>    
    		<c:otherwise>
				<img width="95" src="${curricula.hacker.photo}" alt="ERROR"/>
    		</c:otherwise>
		</c:choose>
		<p><strong><spring:message code="curricula.name" /></strong><jstl:out value="${curricula.name}"></jstl:out></p>
		<p><strong><spring:message code="curricula.statement" /></strong><jstl:out value="${curricula.statement}"></jstl:out></p>
		<fieldset>
			<legend>
				<i><spring:message code="curricula.miscellaneous" /></i><img width="35" height="35" src="./images/att.png" alt="${row1.id}" />
			</legend>
			<c:choose>
					<c:when test="${hackerLogin==true}">
			<p><strong><spring:message code="curricula.miscellaneous" /> </strong><jstl:out value="${curricula.miscellaneous}"></jstl:out></p>
			<security:authorize access="hasRole('HACKER')">
				<form:form class="formularioEdicion" method="POST" modelAttribute="miscellaneousAttachment" action="miscellaneousAttachment/hacker/edit.do">
	          		<form:hidden path="id"/>
	          		<form:hidden path="version"/>
	          		<form:hidden path="curriculaM"/>
	          		<form:hidden path="isCopy"/>
	          		<acme:textbox path="attachment" code="curricula.attachment"/>
	          		<acme:submit name="save" code="save2"/>
				</form:form>
			</security:authorize>
					</c:when>
				</c:choose>
			<display:table name="miscellaneousAttachments" id="row0" requestURI="${requestURI}" pagesize="5" class="displaytag">
				<c:choose>
					<c:when test="${hackerLogin==true}">
						<display:column titleKey="curricula.delete">
							<a href="miscellaneousAttachment/hacker/delete.do?miscellaneousAttachmentId=${row0.id}"><img width="35" height="35" src="./images/delete.png" alt="${row0.id}" /></a>	
						</display:column>
					</c:when>
				</c:choose>
				<display:column property="attachment" titleKey="curricula.attachment"></display:column>
			</display:table>
		</fieldset>
		<fieldset>
			<legend>
				<i><spring:message code="curricula.contact" /></i><img width="35" height="35" src="./images/phone.png" alt="${row1.id}" />	
			</legend>
			<p><strong>Hacker <spring:message code="hacker.name" /></strong><jstl:out value="${curricula.hacker}"></jstl:out></p>
			<p><strong><spring:message code="curricula.phone" /></strong><jstl:out value="${curricula.phone}"></jstl:out></p>
			<p><strong><spring:message code="curricula.linkGitHub" /> </strong><a href =<jstl:out value="${curricula.linkGitHub}"></jstl:out>><jstl:out value="${curricula.linkGitHub}"></jstl:out></a></p>
			<p><strong><spring:message code="curricula.linkLinkedin" /></strong><a href =<jstl:out value="${curricula.linkLinkedin}"></jstl:out>><jstl:out value="${curricula.linkLinkedin}"></jstl:out></a></p>
		</fieldset>
	</fieldset>
	<fieldset>
		<legend>
			<spring:message code="curricula.attachments" />
		</legend>
		<strong><spring:message code="curricula.positionData" /></strong>
		<c:choose>
			<c:when test="${hackerLogin==true and validPositionData==true}">
				<form method="get" action="/Acme-Hacker-Rank/positionData/hacker/create.do">
					<button  name="curriculaId" value="${curricula.id}"><spring:message code="positionData.create"/></button>
				</form>			
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${hackerLogin==true and validPositionData == false}">
						<br>
						<strong><spring:message code="curricula.positionData.emptyPosition" /></strong>			
					</c:when>
					<c:otherwise>
					
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		<br>
		<display:table name="positionDatas" id="row1" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<c:choose>
				<c:when test="${hackerLogin==true}">
					<display:column titleKey="curricula.edit">
						<a href="positionData/hacker/edit.do?positionDataId=${row1.id}"><img width="35" height="35" src="./images/edit.png" alt="${row1.id}" /></a>	
					</display:column>
					<display:column titleKey="curricula.delete">
						<a href="positionData/hacker/delete.do?positionDataId=${row1.id}"><img width="35" height="35" src="./images/delete.png" alt="${row1.id}" /></a>	
					</display:column>
				</c:when>
			</c:choose>
			<display:column titleKey="curricula.show"> 
				<a href="positionData/show.do?positionDataId=${row1.id}"><img width="35" height="35" src="./images/show.png" alt="${row1.id}" /></a>
			</display:column>
			<display:column property="title" titleKey="curricula.positonData.title"></display:column>
			<display:column property="description" titleKey="curricula.positinoData.description"></display:column>
		</display:table>
		<jstl:if test="${empty positionDatas}">
			<br>
			<br>
		</jstl:if>
		<strong><spring:message code="curricula.educationalData" /></strong>
		<jstl:if test="${hackerLogin==true}">
			<form method="get" action="/Acme-Hacker-Rank/educationalData/hacker/create.do">
				<button name="curriculaId" value="${curricula.id}"><spring:message code="educationalData.create"/></button>
			</form>
		</jstl:if>
		<br>
		<display:table name="educationalDatas" id="row2" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<c:choose>
				<c:when test="${hackerLogin==true}">
					<display:column titleKey="curricula.edit">
						<a href="educationalData/hacker/edit.do?educationalDataId=${row2.id}"><img width="35" height="35" src="./images/edit.png" alt="${row2.id}" /></a>	
					</display:column>
					<display:column titleKey="curricula.delete">
						<a href="educationalData/hacker/delete.do?educationalDataId=${row2.id}"><img width="35" height="35" src="./images/delete.png" alt="${row2.id}" /></a>	
					</display:column>
				</c:when>
			</c:choose>
			<display:column titleKey="curricula.show"> 
				<a href="educationalData/show.do?educationalDataId=${row2.id}"><img width="35" height="35" src="./images/show.png" alt="${row2.id}" /></a>
			</display:column>
			<display:column property="degree" titleKey="curricula.educationalData.degree"></display:column>
			<display:column property="institution" titleKey="curricula.educationalData.institution"></display:column>
		</display:table>
		<br>
	</fieldset>
</div>

<br>

		<c:choose>
    		<c:when test="${hackerLogin==true}">
    			<acme:cancel url="curricula/list.do?hackerId=${curricula.hacker.id}" code="back"/>  		
    		</c:when>   
    		<c:otherwise>
				<input type="button" value="back" name="back" onclick="history.back()" />
    		</c:otherwise>
        </c:choose>	
    			