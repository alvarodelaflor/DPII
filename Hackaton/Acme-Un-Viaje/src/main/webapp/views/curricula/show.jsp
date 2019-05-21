<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css" type="text/css">

<script>
$(document).ready(function() {
    $('#example').DataTable();
} );
</script>

<div class="jumbotron" style="width: 100%">
	<div class="row">
		<div class="col-md-3">
			<c:choose>
				<c:when test="${curricula.cleaner.photo == null or curricula.cleaner.photo=='' }">
					<div class="card">
						<img class="card-img-top" src="images/registerPhoto.png" alt="ERROR">
						<div class="card-body">
							<h4 class="card-title">${curricula.cleaner.name} <br>${curricula.cleaner.surname}</h4>
							<jstl:if test="${cleanerLogin==true}">
								<div class="row">
									<div class="col-md-8">
											<acme:create name = "" url="curricula/cleaner/edit.do?curriculaId=${curricula.id}" code="curricula.edit"/>
									</div>
									<div class="col-md-4">
											<acme:delete name = "" url="curricula/cleaner/delete.do?curriculaId=${curricula.id}" code="curricula.delete"/>
									</div>
								</div>
							</jstl:if>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="card">
						<img class="card-img-top" src="${curricula.cleaner.photo}"
							alt="ERROR">
						<div class="card-body">
							<h4 class="card-title">${curricula.cleaner.name} <br>${curricula.cleaner.surname}</h4>
							<jstl:if test="${cleanerLogin==true}">
								<div class="row">
									<div class="col-md-8">
											<acme:create name = "" url="curricula/cleaner/edit.do?curriculaId=${curricula.id}" code="curricula.edit"/>
									</div>
									<div class="col-md-4">
											<acme:delete name = "" url="curricula/cleaner/delete.do?curriculaId=${curricula.id}" code="curricula.delete"/>
									</div>
								</div>
							</jstl:if>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			
			<table id="example" class="table table-striped table-bordered" style="width:100%">
  				<tfoot>
    				<tr>
      					<th class="th-sm">
      						<c:choose>
								<c:when test="${cleanerLogin==true}">
									<form:form class="formularioEdicion" method="POST" modelAttribute="miscellaneousAttachment" action="miscellaneousAttachment/cleaner/edit.do">
          								<div class="row">
											<div class="col-md-7">
												<div class="form-group">
													<spring:message htmlEscape="false" code="curricula.attachment" var="placeholder1" />
													<form:input class="form-control" path="attachment" placeholder="${placeholder1}"/>
													<form:errors path="attachment" cssClass="error" />
												</div>
											</div>
											<div class="col-md-5">
												<div class="form-group">
													<acme:submit name="save" code="save2"/>
												</div>
											</div>
          								</div>
          								<form:hidden path="id"/>
          								<form:hidden path="version"/>
          								<form:hidden path="curriculaM"/>
          								<form:hidden path="isCopy"/>
									</form:form>
								</c:when>
							</c:choose>
      					</th>
      				</tr>
  				</tfoot>
  				<tbody>
			      <c:forEach var = "i" items="${miscellaneousAttachments}">
  						<tr>
  							<td>
	  							<div class="row">	
	  								<c:choose>
	  									<c:when test="${cleanerLogin==true}">
	  										<div class="col-md-8">
	  											<c:out value = "${i.attachment}"/>
	  										</div>
	  										<div class="col-md-4">
	  											<acme:delete name = "" url="miscellaneousAttachment/cleaner/delete.do?miscellaneousAttachmentId=${row0.id}${i.id}" code="curricula.delete"/>
	  										</div>
	  									</c:when>
	  									<c:otherwise>
	  										<div class="col-md-12">
	  											<c:out value = "${i.attachment}"/>
	  										</div>	  									
	  									</c:otherwise>
	  								</c:choose>
		  						</div>
  							</td>
  						</tr>
			      </c:forEach>
      			</tbody>
      		</table>
		</div>
		<div class="col-md-9">
			<h1 class="display-3">
				<jstl:out value="${curricula.name}"></jstl:out>
			</h1>
			<p>
				<jstl:out value="${curricula.statement}"></jstl:out>
			</p>
			<p>
				<spring:message code="numberMoreInfo" /><strong><jstl:out value="${curricula.phone}"></jstl:out></strong>
			</p>			
			<p>
				<strong><spring:message code="cotilla" /></strong>
			</p>
			<div style="padding-left: 2.5em">
				<p>
					<spring:message code="linkLinkedinMoreInfo" /><a href="<jstl:out value="${curricula.linkLinkedin}"></jstl:out>"><jstl:out value="${curricula.linkLinkedin}"></jstl:out></a>
				</p>
				<p>
					<spring:message code="profileMoreInfo" /><a href="socialProfile/list.do?cleanerId=<jstl:out value="${curricula.cleaner.id}"></jstl:out>"><spring:message code="link" /></a>
				</p>
			</div>
			<img class="card-img-top" src="<jstl:out value="${curricula.bannerLogo}"></jstl:out>" alt="ERROR">
		</div>
	</div>
</div>

