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
	<script type= "text/javascript">
		function preguntaES(){
    		if (confirm('Una vez creada, no podrá borrar ni editar, ¿está usted seguro de que todo sea correcto?')){
    			return true;
    		} else {
    			return false;
    		}
		}
	</script>
	<script type= "text/javascript">
		function preguntaEN(){
    		if (confirm('Once created, you can not delete or edit, are you sure everything is correct?')){
    			return true;
    		} else {
    			return false;
    		}
		}
	</script>
	<div>
    	<form:form class="formularioEdicion" method="POST" modelAttribute="proclaim" action="proclaim/chapter/create.do">
          	<form:hidden path="id"/>
          	<form:hidden path="version"/>
          	<fieldset>
          		<legend>
          			<spring:message code="proclaim.dates" />
          		</legend>
               	<acme:textbox code="proclaim.text" path="text"/>
               	<acme:textboxMoment code="proclaim.moment" path="moment"/>
          	</fieldset>
          	<acme:cancel url="chapter/show.do?id=${chapterId}" code="cancel"/>
	       	<c:choose>
	       		<c:when test="${language == true}">
					<button type="submit" name="save" class="btn btn-primary" onclick="return preguntaEN()">
						<spring:message code="send" />
					</button>
				</c:when>
				<c:otherwise>
					<button type="submit" name="save" class="btn btn-primary" onclick="return preguntaES()">
						<spring:message code="send" />
					</button>
				</c:otherwise>
			</c:choose>
		</form:form>
	</div>
</body>
