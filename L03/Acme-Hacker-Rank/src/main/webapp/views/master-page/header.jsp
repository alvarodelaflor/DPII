<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="images/logo.png" alt="Acme Hacker-Rank Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="administrator/actorList.do"><spring:message code="master.page.administrator.actorList" /></a></li>
					<li><a href="administrator/create.do"><spring:message code="master.page.administrator.create" /></a></li>		
					<li><a href="administrator/list.do"><spring:message code="master.page.administrator.configuration" /></a></li>				
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.information" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="company/list.do"><spring:message code="master.page.list.company" /></a></li>	
					<li><a href="position/list.do"><spring:message code="master.page.list.position" /></a></li>							
				</ul>
			</li>
			
		</security:authorize>
		
		<security:authorize access="hasRole('HACKER')">
		<li><a class="fNiv"><spring:message	code="master.page.hacker" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/hacker/list.do"><spring:message code="master.page.list.application" /></a></li>	
				</ul>
		</li>
		<li><a class="fNiv"><spring:message	code="master.page.information" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="company/list.do"><spring:message code="master.page.list.company" /></a></li>	
					<li><a href="position/list.do"><spring:message code="master.page.list.position" /></a></li>							
				</ul>
		</li>

		</security:authorize>
		
		<security:authorize access="hasRole('COMPANY')">
		<li><a class="fNiv"><spring:message	code="master.page.company" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="position/company/list.do"><spring:message code="master.page.list.logged_company_positions" /></a></li>
					<li><a href="problem/company/list.do"><spring:message code="master.page.list.logged_company_problems" /></a></li>	
				</ul>
		</li>
		<li><a class="fNiv"><spring:message	code="master.page.information" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="company/list.do"><spring:message code="master.page.list.company" /></a></li>	
					<li><a href="position/list.do"><spring:message code="master.page.list.position" /></a></li>					
				</ul>
		</li>

		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			
			<li><a class="fNiv"><spring:message	code="master.page.rgister" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="hacker/create.do"><spring:message code="master.page.register.hacker" /></a></li>
					<li><a href="company/create.do"><spring:message code="master.page.register.company" /></a></li>				
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.information" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="company/list.do"><spring:message code="master.page.list.company" /></a></li>	
					<li><a href="position/list.do"><spring:message code="master.page.list.position" /></a></li>							
				</ul>
			</li>

			
				
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="socialProfile/list.do"><spring:message code="master.page.socialProfile.list" /></a></li>					
					<li><a href="message/list.do"><spring:message code="master.page.message.list" /></a></li>					
					<security:authorize access="hasRole('HACKER')">
						<li><a href="hacker/show.do"><spring:message code="master.page.hacker.show" /></a></li>
						<li><a href="hacker/edit.do"><spring:message code="master.page.hacker.edit" /></a></li>	
					</security:authorize>	
					<security:authorize access="hasRole('COMPANY')">
						<li><a href="company/show.do"><spring:message code="master.page.company.show" /></a></li>
						<li><a href="company/edit.do"><spring:message code="master.page.company.edit" /></a></li>	
					</security:authorize>			
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

