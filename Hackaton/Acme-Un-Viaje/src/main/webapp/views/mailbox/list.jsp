<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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


<hr>
<div class="container-fluid" style="padding-left: 2.5em">
	<display:table name="mailboxes" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag table table-hover">
		<div class="row" style="padding-left: 2.5em">
			<div class="col-md-6">
				<fieldset>
					<display:column titleKey="administrator.showMailbox">
						<a href="message/list.do?mailboxId=${row.id}"><spring:message
								code="seeMessages" /></a>
					</display:column>

					<c:choose>
						<c:when test="${row.isDefault != true}">
							<display:column titleKey="administrator.editMailbox">
								<a href="mailbox/edit.do?mailboxId=${row.id}"><spring:message
										code="editMailbox" /></a>
							</display:column>
						</c:when>
						<c:otherwise>
							<display:column titleKey="administrator.editMailbox" />
						</c:otherwise>
					</c:choose>

					<display:column property="name" titleKey="mailbox.name" />

					<c:choose>
						<c:when test="${row.isDefault != true}">
							<display:column titleKey="administrator.deleteMailbox">
								<a href="mailbox/delete.do?mailboxId=${row.id}"><spring:message
										code="deleteMailbox" /></a>
							</display:column>
						</c:when>
						<c:otherwise>
							<display:column titleKey="administrator.deleteMailbox" />
						</c:otherwise>
					</c:choose>

				</fieldset>
			</div>
		</div>
	</display:table>


	<div class="row">
		<div class="col-md-3">
			<span> <acme:create url="mailbox/create.do"
					name="buttonMailbox" code="createMailbox" />
			</span> 
			<br>
			<span> <a href="message/create.do"><spring:message
				code="send.msg" /></a>
			</span>
		</div>
	</div>

	<br>

	
	<div class="row">
		<div class="col-md-3">
			<span> <security:authorize access="hasRole('ADMIN')">
		<a href="message/createBroadcast.do"><spring:message
				code="send.msg.broadcast" /></a>
	</security:authorize>

			</span>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-3">
			 <span> <acme:historyBack />
			</span>
		</div>
	</div>

</div>