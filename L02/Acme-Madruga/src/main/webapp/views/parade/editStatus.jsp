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
	<div>
    	<form:form class="formularioEdicion" method="POST" modelAttribute="parade" action="parade/chapter/edit.do">
          	<form:hidden path="id"/>
          	
          	<div>
				<form:label path="status">
					<spring:message code="parade.update.status" />:
				</form:label>
				<form:select path="status" onchange="content(this.value);">
					<form:option value="REJECTED"><spring:message code="parade.update.false"/></form:option>
					<form:option value="ACCEPTED"><spring:message code="parade.update.true"/></form:option>
				</form:select>
				<form:errors cssClass="error" path="status" />
				<br/>
			</div>
			<div id=rejection>
				<acme:textarea code="parade.status.rejection" path="rejectionReason"/>
			</div>
			
          	
          	<acme:cancel url="parade/chapter/list.do" code="cancel"/>
          	<acme:submit name="save" code="send"/>
		</form:form>
	</div>
</body>

<script type="text/javascript">
	function content(value) {
		if(value=="REJECTED"){
			document.getElementById("rejection").style.display="block";
		}else{
			document.getElementById("rejection").style.display="none";
		}
		
	}
</script>
