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

<style type="text/css">
	.oculta{
	display:none;
	}
	.oculta2{
	display:none;
	}
</style>

<script type= "text/javascript"> 
	function mostrar(value_elemento){
	var elemento;
	elemento = document.getElementById('capa0');
	elemento2 = document.getElementById('capaRechazo');
	if (true == value_elemento || "true" == value_elemento){
		elemento.style.display="block";
		elemento2.style.display="none";
	}else{
		elemento.style.display="none";
		elemento2.style.display="block";
		}
	}
</script>

<script type= "text/javascript"> 
	function mostrar2(value_elemento){
	var elemento;
	elemento = document.getElementById('capa1');
	if (true == value_elemento || "true" == value_elemento){
		elemento.style.display="block";
	}else{
		elemento.style.display="none";
		}
	}
</script>

<body onload="mostrar(${request.status});" onload="mostrar2(${request.status});">

	<div>
    	<form:form class="formularioEdicion" method="POST" modelAttribute="request" action="request/brotherhood/edit.do">
          	<form:hidden path="id"/>
          	<acme:selectTrueFalse code="request" path="status" onchange="mostrar(this.value);"/>
          	
          	<div class="oculta" id="capa0" >
    			<fieldset>
        			<legend><p><spring:message code="request.defaultPositionAux"></spring:message>${positionsAux[0].row} ${positionsAux[0].colum}</p></legend>
        			<label>
            			<input type="radio" name="change" value="true" onchange="mostrar2(this.value);"> <spring:message code="request.rechazar"></spring:message>
        			</label>
        			<label>
            			<input checked="checked" type="radio" name="change" value="false" onchange="mostrar2(this.value);"> <spring:message code="request.aceptar"></spring:message>
        			</label>
        			<label class="oculta" id="capa1">
        				<br>
        				<acme:select2 items="${positionsAux}" code="request.positionAux"  path="positionAux"/>
        			</label>
    				</fieldset>
	        </div>
	      	<div class="oculta2" id="capaRechazo" >
    			<fieldset>
        			<legend><p><spring:message code="request.reasonReject"></spring:message></p></legend>
        			<label>
            			<acme:textarea code="request.comment" path="comment"/>
        			</label>
    				</fieldset>
	        </div>
          	<acme:cancel url="request/brotherhood/list.do" code="cancel"/>
          	<acme:submit name="save" code="send"/>
		</form:form>
	</div>
</body>