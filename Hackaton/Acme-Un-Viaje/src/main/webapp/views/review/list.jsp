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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<hr>   	
  	<div class="container-fluid" style="padding-left: 2.5em">
	<display:table name="reviews" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag table table-hover">
		<div class="row" style="padding-left: 2.5em">
			<div class="col-md-6">
				<fieldset>
						<display:column titleKey="review.showReview"> 
		<a href="review/show.do?reviewId=${row.id}"><spring:message code="showReview" /></a>
	</display:column>
  	<display:column property="referee.name" titleKey="referee.name" />
  	<display:column property="description" titleKey="description"/>
				</fieldset>
			</div>
		</div>
	</display:table>
	<div class="row">
		<div class="col-md-3">
			<span style="padding-left: 0.5em"> 
				<jstl:if test="${complaintsEmpty==true}">
					<p class="lead">
						<acme:create url="review/create.do" name="buttonReview"	code="review.create" />
					</p>
				</jstl:if>
				
				
			</span>
			 <span><acme:historyBack />
			</span>
		</div>
	</div>
	
</div>