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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


  <section id="main-content">
  
    <article>
      
      <div class="content">
    		
    	<form:form class="formularioEdicion" method="POST" modelAttribute="sponsorship" action="sponsorship/edit.do">
          	<form:hidden path="id"/>
          	<form:hidden path="version"/>
          	
          		<acme:textbox code="sponsorship.banner" path="banner" />
          		<acme:textbox code="sponsorship.target" path="target" />
          		<acme:textbox code="sponsorship.creditCard.holder" path="creditCard.holder" />
          		<form:label path="creditCard.make">
					<spring:message code="creditCardMakes" />:
				</form:label>
          		<form:select required ="required" path="creditCard.make">
   		 			<form:options items="${makes}"/>
				</form:select>
				<form:errors cssClass="error" path="parade" />		
          		<acme:textbox code="sponsorship.creditCard.number" path="creditCard.number" />
          		<acme:textbox code="sponsorship.creditCard.cvv" path="creditCard.CVV" />
				<acme:textboxMoment code="sponsorship.creditCard.expiration" path="creditCard.expiration"/>
				<form:label path="parade">
				<spring:message code="sponsorship.parade" />:
				</form:label>
				<form:select required ="required" path="parade">
   		 			<form:options items="${parades}" itemLabel="title"/>
				</form:select>
				<form:errors cssClass="error" path="parade" />					
			<br></br>
			<acme:submit name="save" code="send"/>
			<acme:cancel url="sponsorship/list.do" code="sponsorship.cancel"/>
		</form:form>
      </div>
      
 	</article>
 	 	
	
  
  </section>
