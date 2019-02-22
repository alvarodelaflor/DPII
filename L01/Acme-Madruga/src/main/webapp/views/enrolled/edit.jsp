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
</style>

<script type= "text/javascript"> 
	function mostrar(value_elemento){
	var elemento;
	elemento = document.getElementById('capa0');
	if (true == value_elemento || "true" == value_elemento){
		elemento.style.display="block";
	}else{
		elemento.style.display="none";
		}
	}
</script>

<body  onload="mostrar(${enrolled.state});">

	<div>
    	<form:form class="formularioEdicion" method="POST" modelAttribute="enrolled" action="enrolled/brotherhood/edit.do">
          	<form:hidden path="id"/>
          	
          	<acme:selectTrueFalse code="enrolled" path="state" onchange="mostrar(this.value);"/>
			<div class="oculta" id="capa0" >
				<acme:selectItemEsEn items="${positions}" code="enrolled" path="state"/>	
          	</div>
          	<acme:cancel url="enrolled/brotherhood/list.do" code="cancel"/>
          	<acme:submit name="save" code="send"/>
		</form:form>
	</div>
</body>