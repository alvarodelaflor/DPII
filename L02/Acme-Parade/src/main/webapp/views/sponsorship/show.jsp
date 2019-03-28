<%--
 * action-1.jsp
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

<body>

<div class="content">
				<table>    						
    				<tr><td><spring:message code="sponsorship.show.banner" /></td></tr>
    				
    				<tr><td><img src="${sponsorship.banner}" border="1" alt="Error" width="400" height="300"></td></tr>
    							
    				<tr><td><spring:message code="sponsorship.show.target" /><a href =<jstl:out value="${sponsorship.target}"></jstl:out>><jstl:out value="${sponsorship.target}"></jstl:out></a></td></tr>
    				  		
      				<tr><td><spring:message code="sponsorship.show.sponsor" /><jstl:out value="${sponsorship.sponsor.name}"></jstl:out></td></tr>
  																          			
          		    <tr><td><spring:message code="sponsorship.show.parade" /><jstl:out value="${sponsorship.parade.title}"></jstl:out></td></tr>
 
               		<tr><td><spring:message code="sponsorship.show.active" />
               		
               		<c:choose>
    					<c:when test="${language=='English'}">
							
							<c:choose>
    						<c:when test="${sponsorship.active == true}">
								<spring:message code="SI" />
    						</c:when>    
    						<c:otherwise>
								<spring:message code="NO" />
    						</c:otherwise>
							</c:choose>
							
    				</c:when>    
    				<c:otherwise>
						
							<c:choose>
    						<c:when test="${sponsorship.active == true}">
								<spring:message code="SI" />
    						</c:when>    
    						<c:otherwise>
								<spring:message code="NO" />
    						</c:otherwise>
							</c:choose>

    				</c:otherwise>
					</c:choose>
               		
               		
               		</td></tr>
     
           		    <tr><td><spring:message code="sponsorship.show.creditCard.number" /><jstl:out value="${sponsorship.creditCard.number}"></jstl:out></td></tr>
 
           		    <tr><td><spring:message code="sponsorship.show.creditCard.make" /><jstl:out value="${sponsorship.creditCard.make}"></jstl:out></td></tr>
          			</table>
          			
          			
      			</div>
      			<div>      
	<form>
		<br>
		<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
	</form>
</div>
      			