<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<!--
	CONTROL DE CAMBIOS header.jsp
  
	ALVARO 17/02/2019 12:38 A�adido valores para brotherhood procession
	ALVARO 17/02/2019 15:06 Cambiado logo
	ALVARO 17/02/2019 19:32 A�adido floatBro
	ALVARO 17/02/2019 21:43 A�adido enrolled
	HIPONA 19/02/2019 11:55 A�adido acceso a request/member/list
-->

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img style="width:20%;" src="images/logo.jpg" alt="Acme-Madrug� Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>																												
					<li><a href="brotherhood/list.do"><spring:message code="master.page.brotherhood1" /></a></li>
					<li><a href="area/administrator/list.do"><spring:message code="master.page.administrator.areaList" /></a></li>
					<li><a href="position/administrator/list.do"><spring:message code="master.page.administrator.adminList" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>		
					<li><a href="administrator/actorList.do"><spring:message code="master.page.administrator.actorList" /></a></li>		
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv"><spring:message	code="master.page.brotherhood" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="floatBro/brotherhood/list.do"><spring:message code="master.page.brotherhood.floatBro.list" /></a></li>
					<li><a href="procession/brotherhood/list.do"><spring:message code="master.page.brotherhood.procession.list" /></a></li>
					<li><a href="enrolled/brotherhood/list.do"><spring:message code="master.page.brotherhood.enrolled.list" /></a></li>
					<li><a href="request/brotherhood/list.do"><spring:message code="master.page.brotherhood.request.list" /></a></li>
					<li><a href="area/brotherhood/edit.do"><spring:message code="master.page.brotherhood.area.edit" /></a></li>
					<li><a href="brotherhood/list.do"><spring:message code="master.page.brotherhood1" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('MEMBER')">
			<li><a class="fNiv"><spring:message	code="master.page.member" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="request/member/list.do"><spring:message code="master.page.request.list" /></a></li>
					<li><a href="brotherhood/member/list.do"><spring:message code="master.page.myBrotherhoods" /></a></li>
					<li><a href="brotherhood/list.do"><spring:message code="master.page.brotherhood1" /></a></li>									
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.createUser" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="member/create.do"><spring:message code="master.page.member" /></a></li>
					<li><a href="brotherhood/create.do"><spring:message code="master.page.brotherhood" /></a></li>	
							
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.brotherhood" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="brotherhood/list.do"><spring:message code="master.page.brotherhood1" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
					<security:authorize access="hasRole('BROTHERHOOD')">(<spring:message code="master.page.brotherhood" />)</security:authorize>
					<security:authorize access="hasRole('MEMBER')">(<spring:message code="master.page.member" />)</security:authorize>
				</a>
				<ul>
					<li class="arrow"></li>				
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
					<li><a href="messageBox/list.do"><spring:message code="master.page.messageBox.list" /></a></li>	
					<li><a href="socialProfile/list.do"><spring:message code="master.page.socialProfile.list" /></a></li>					
					<security:authorize access="hasRole('BROTHERHOOD')">
						<li><a href="brotherhood/edit.do"><spring:message code="master.page.brotherhood.edit" /></a></li>
						<li><a href="brotherhood/show.do"><spring:message code="master.page.brotherhood.show" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('MEMBER')">
						<li><a href="member/edit.do"><spring:message code="master.page.member.edit" /></a></li>					
						<li><a href="member/show.do"><spring:message code="master.page.member.show" /></a></li>	
					</security:authorize>
					<security:authorize access="hasRole('ADMIN')">	
						<li><a href="administrator/show.do"><spring:message code="master.page.administrator.show" /></a></li>
						<li><a href="administrator/edit.do"><spring:message code="master.page.administrator.edit" /></a></li>
						<li><a href="administrator/create.do"><spring:message code="master.page.administrator.create" /></a></li>			
					</security:authorize>
				</ul>
			</li>
</security:authorize>
		
		
		
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

