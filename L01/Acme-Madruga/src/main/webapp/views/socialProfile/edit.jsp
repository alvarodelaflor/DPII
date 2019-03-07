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

  <section id="main-content">
  
    <article>
      <header>
        <h2>
        	<spring:message code="actor.edit.socialProfile" />
        </h2>
      </header>
      
      <div class="content">
    		
    	<form:form class="formularioEdicion" method="POST" modelAttribute="socialProfile" action="socialProfile/edit.do">
          	<form:hidden path="id"/>
          	<form:hidden path="version"/>
          	
          	<form:label path="name"><spring:message code="actor.name" /></form:label>
			<form:input path="name" required="required"/>
			<form:errors cssClass="error" path="name"/><br>
			
			<form:label path="link"><spring:message code="actor.link" /></form:label>
			<form:input path="link" required="required"/>
			<form:errors cssClass="error" path="link"/><br>
			
			<form:label path="nick"><spring:message code="actor.nick" /></form:label>
			<form:input path="nick" required="required"/>
			<form:errors cssClass="error" path="nick"/><br>
					
			<input type="submit" name="save" value=<spring:message code="send" />/>
		</form:form>
      </div>
      
 	</article>

  
  </section>
