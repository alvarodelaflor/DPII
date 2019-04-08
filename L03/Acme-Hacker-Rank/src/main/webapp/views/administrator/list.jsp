<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="acme"%>


<br>

<!--FAIR-->
<!-- 
<h3>
	<i><spring:message code="admin.fair" /></i>
</h3>
<p>${fair}</p>
<security:authorize access="hasRole('ADMIN')">
	<form:form class="formularioEdicion" method="GET"
		action="administrator/newFair.do?newFair='${newFair}'.do">
		<spring:message code="newFair" />
		<input type=number name="newFair" required="required" />
		<acme:submit name="save" code="saveNewFair" />
	</form:form>
</security:authorize>

<p>${VAT}</p>
<security:authorize access="hasRole('ADMIN')">
	<form:form class="formularioEdicion" method="GET"
		action="administrator/newVAT.do?newVAT='${newVAT}'.do">
		<spring:message code="newVAT" />
		<input type=number name="newVAT" required="required" />
		<form:errors path="VAT"></form:errors>
		<acme:submit name="save" code="saveNewVAT" />
	</form:form>
</security:authorize>
 -->
<!--FAIR-->
<!-- FINDER -->
<security:authorize access="hasRole('ADMIN')">
	<div>
		<display:table name="configuration" id="row" requestURI="${requestURI}"
			pagesize="5" class="displaytag">
			<display:column property="cacheHours" titleKey="configuration.cacheHours" />
			<display:column property="cacheAmount" titleKey="configuration.cacheAmount" />
			<display:column titleKey="edit">
				<a href="configuration/administrator/edit.do?configurationId=${row.id}"><spring:message code="edit"/></a>
			</display:column>
		</display:table>
	</div>
</security:authorize>
<!-- FINDER -->

<!-- CREDITCARDSMAKES -->
<!-- 
<h3>
	<i><spring:message code="creditCardMakes" /></i>
</h3>
<p>${creditCardMakes}</p>
<security:authorize access="hasRole('ADMIN')">
 -->
	<!-- Add CCMake -->
	<!-- 
	<form:form class="formularioEdicion" method="GET"
		action="administrator/newCreditCardMake.do?newCreditCardMake='${newCreditCardMake}'.do">
		<spring:message code="newCreditCardMake" />
		<input type=text name="newCreditCardMake" required="required"/>
		<acme:submit name="save" code="newCreditCardMake" />
	</form:form>
	-->
	<!-- Remove CCMake -->
	<!-- 
	<form:form class="formularioEdicion" method="GET"
		action="administrator/deleteCreditCardMake.do?deleteCreditCardMake='${deleteCreditCardMake}'.do">
		<spring:message code="deleteCreditCardMake" />
		<input type=text name="deleteCreditCardMake" required="required" />
		<acme:submit name="save" code="deleteCreditCardMake" />
	</form:form>
</security:authorize>
-->
<!-- CREDITCARDSMAKES -->

<!-- PRIORITIES -->
<h3>
	<i><spring:message code="priorities" /></i>
</h3>
<p>${priorities}</p>

<security:authorize access="hasRole('ADMIN')">
	<form:form class="formularioEdicion" method="GET"
		action="administrator/newPriority.do?newPriority='${newPriority}'.do">
		<spring:message code="newPriority" />
		<input type=text name="newPriority" required="required" />
		<acme:submit name="save" code="saveNewPriority" />
	</form:form>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<form:form class="formularioEdicion" method="GET"
		action="administrator/deletePriority.do?deletePriority='${deletePriority}'.do">
		<spring:message code="deletePriority" />
		<input type=text name="deletePriority" required="required" />
		<acme:submit name="save" code="deletePriority" />
	</form:form>
</security:authorize>
<!-- PRIORITIES -->

<!--SPAM WORDS-->
<h3>
	<i><spring:message code="spamWords" /></i>
</h3>
<p>${spamWords}</p>

<security:authorize access="hasRole('ADMIN')">
	<form:form class="formularioEdicion" method="GET"
		action="administrator/newSpamWord.do?newSpamWord='${newSpamWord}'.do">
		<spring:message code="newSpamWord" />
		<input type=text name="newSpamWord" required="required" />
		<acme:submit name="save" code="saveNewSpamWord" />
	</form:form>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<form:form class="formularioEdicion" method="GET"
		action="administrator/deleteSpamWord.do?deleteSpamWord='${deleteSpamWord}'.do">
		<spring:message code="deleteSpamWord" />
		<input type=text name="deleteSpamWord" required="required" />
		<acme:submit name="save" code="saveNewSpamWord" />
	</form:form>
</security:authorize>
<!--SPAM WORDS-->

<!--SCORE WORDS-->
<!-- 
<h3>
	<i><spring:message code="scoreWordsPos" /></i>
</h3>
<p>${scoreWordsPos}</p>

<security:authorize access="hasRole('ADMIN')">
	<form:form class="formularioEdicion" method="GET"
		action="administrator/newScoreWordPos.do?newScoreWord='${newScoreWord}'.do">
		<spring:message code="newScoreWord" />
		<input type=text name="newScoreWord" required="required" />
		<acme:submit name="save" code="saveNewSpamWord" />
	</form:form>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<form:form class="formularioEdicion" method="GET"
		action="administrator/deleteScoreWordPos.do?deleteScoreWord='${deleteScoreWord}'.do">
		<spring:message code="deleteScoreWord" />
		<input type=text name="deleteScoreWord" required="required" />
		<acme:submit name="save" code="saveNewSpamWord" />
	</form:form>
</security:authorize>

<h3>
	<i><spring:message code="scoreWordsNeg" /></i>
</h3>
<p>${scoreWordsNeg}</p>

<security:authorize access="hasRole('ADMIN')">
	<form:form class="formularioEdicion" method="GET"
		action="administrator/newScoreWordNeg.do?newScoreWord='${newScoreWord}'.do">
		<spring:message code="newScoreWord" />
		<input type=text name="newScoreWord" required="required" />
		<acme:submit name="save" code="saveNewSpamWord" />
	</form:form>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<form:form class="formularioEdicion" method="GET"
		action="administrator/deleteScoreWordNeg.do?deleteScoreWord='${deleteScoreWord}'.do">
		<spring:message code="deleteScoreWord" />
		<input type=text name="deleteScoreWord" required="required" />
		<acme:submit name="save" code="saveNewSpamWord" />
	</form:form>
</security:authorize>

<!--SCORE WORDS-->

<br>

<!--Welcome page-->
<h3>
	<i><spring:message code="welcomePage" /></i>
</h3>
<spring:message code="welcomePageS" />
<p>${spanish}</p>
<spring:message code="welcomePageE" />
<p>${ingles}</p>

<form:form class="formularioEdicion" method="GET"
	action="administrator/newWelcome.do?newIngles='${newIngles}'&&newSpanish='${newSpanish}'.do">
	<spring:message code="ingles" />
	<input type=text name="newIngles" required="required" />
	<br>
	<spring:message code="spanish" />
	<input type=text name="newSpanish" required="required" />
	<br>
	<acme:submit name="save" code="saveNewSpamWord" />
</form:form>
<!--Welcome page-->

<br>


<br>

<!-- Logo -->
<h3>
	<i><spring:message code="nameLogo" /></i>${logo}
</h3>

<form:form class="formularioEdicion" method="GET"
	action="administrator/newLogo.do?newLogo='${newLogo}'.do">
	<spring:message code="logoM" />
	<input type="url" name="newLogo" required="required" />
	<form:errors cssClass="error" path="${newLogo}" />
	<br>
	<acme:submit name="save" code="saveNewSpamWord" />
</form:form>
<!-- Logo -->

<br>

<!-- Name of system -->
<h3>
	<i><spring:message code="nameSistem" /></i>${system}
</h3>

<form:form class="formularioEdicion" method="GET"
	action="administrator/newSystem.do?newSystem='${newSystem}'.do">
	<spring:message code="systemM" />
	<input type="text" name="newSystem" required="required" />
	<acme:submit name="save" code="saveNewSpamWord" />
</form:form>
<!-- Name of system -->

<br>

<!-- PHONE -->
<h3>
	<i><spring:message code="phone" /></i>${phone}
</h3>
<form:form class="formularioEdicion" method="GET"
	action="administrator/newPhone.do?newPhone='${newPhone}'.do">
	<spring:message code="phoneM" />
	<input type="number" name="newPhone" required="required" />
	<acme:submit name="save" code="saveNewSpamWord" />
</form:form>
<!-- PHONE -->

<!-- 		<a href="welcome/edit.do"><spring:message code="ad.welcom" /></a>
					<br>
					<a href="administrator/editIVA.do"><spring:message code="ad.welcom" /></a>  -->
<!-- 					
<form:form action="welcome/edit?es=$request->get('esp').do" >					
<spring:message code="ad.welcom" /> 
<input type="text" name="esp" />
<input type="submit" name="save" />
</form:form>



<c:choose>
    		<c:when test="${language=='English'}">
        		<form>
      				<input type="button" value="Back" name="volver atr�s2" onclick="history.back()" />
	  			</form> 
    		</c:when>    
    		<c:otherwise>
		 		<form>
      				<input type="button" value="Volver" name="volver atr�s2" onclick="history.back()" />
	  			</form>        		
    		</c:otherwise>
		</c:choose>
 -->

<!-- PHONE -->
<!-- 
<h3>
	<i><spring:message code="phoneCountry" /></i>${phoneCountry}
</h3>
<form:form class="formularioEdicion" method="GET"
	action="administrator/newPhoneCountry.do?newPhoneCountry='${newPhoneCountry}'.do">
	<spring:message code="phoneCountryM" />
	<input type="text" name="newPhoneCountry" required="required" />
	<acme:submit name="save" code="saveNewSpamWord" />
</form:form>
-->
<!-- PHONE -->
