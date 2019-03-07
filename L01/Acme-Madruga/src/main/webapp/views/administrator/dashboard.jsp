<%--
 * action-1.jsp
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

<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Lato">

<style>
    canvas{
       display:inline-block;}
</style>

<security:authorize access="hasRole('ADMIN')"> 

		<p><spring:message code="admin.TheLargestBrotherhoods" /> <jstl:out	value="${largestBrotherhood}"></jstl:out></p>
		<p><spring:message code="admin.TheSmallestBrotherhoods" /> <jstl:out value="${smallestBrotherhood}"></jstl:out></p>		
		<p><spring:message code="admin.TheRatioRequestsTrue" /> <jstl:out value="${getRatioRequestStatusTrue}"></jstl:out></p>
		<p><spring:message code="admin.TheRatioRequestsFalse" /> <jstl:out value="${getRatioRequestStatusFalse}"></jstl:out></p>
		<p><spring:message code="admin.TheRatioRequestsNull" /> <jstl:out value="${getRatioRequestStatusNull}"></jstl:out></p>
		<p><spring:message code="admin.getRatioRequestProcessionStatusTrue" /> <jstl:out value="${getRatioRequestProcessionStatusTrue}"></jstl:out></p>
		<p><spring:message code="admin.getRatioRequestProcessionStatusFalse" /> <jstl:out value="${getRatioRequestProcessionStatusFalse}"></jstl:out></p>
		<p><spring:message code="admin.getRatioRequestProcessionStatusNull" /> <jstl:out value="${getRatioRequestProcessionStatusNull}"></jstl:out></p>
		<p><spring:message code="admin.maxNumberOfMemberPerBrotherhood" /> <jstl:out value="${maxNumberOfMemberPerBrotherhood}"></jstl:out></p>
		<p><spring:message code="admin.minNumberOfMemberPerBrotherhood" /> <jstl:out value="${minNumberOfMemberPerBrotherhood}"></jstl:out></p>
		<p><spring:message code="admin.avgNumberOfMemberPerBrotherhood" /> <jstl:out value="${avgNumberOfMemberPerBrotherhood}"></jstl:out></p>
		<p><spring:message code="admin.desviationOfNumberOfMemberPerBrotherhood" /> <jstl:out value="${stddevNumberOfMemberPerBrotherhood}"></jstl:out></p>
		<p><spring:message code="admin.avgBrotherhoodPerArea" /> <jstl:out value="${avgBrotherhoodPerArea}"></jstl:out></p>		
		<p><spring:message code="admin.minBrotherhoodPerArea" /> <jstl:out value="${minBrotherhoodPerArea}"></jstl:out></p>		
		<p><spring:message code="admin.maxBrotherhoodPerArea" /> <jstl:out value="${maxBrotherhoodPerArea}"></jstl:out></p>		
		<p><spring:message code="admin.stddevBrotherhoodPerArea" /> <jstl:out value="${stddevBrotherhoodPerArea}"></jstl:out></p>		
		<p><spring:message code="admin.countBrotherhoodPerArea" /> <jstl:out value="${map}"></jstl:out></p>		
		<p><spring:message code="admin.minNumberOfResult" /> <jstl:out value="${minNumberOfResult}"></jstl:out></p>		
		<p><spring:message code="admin.maxNumberOfResult" /> <jstl:out value="${maxNumberOfResult}"></jstl:out></p>		
		<p><spring:message code="admin.avgNumberOfResult" /> <jstl:out value="${avgNumberOfResult}"></jstl:out></p>		
		<p><spring:message code="admin.stddevNumberOfResult" /> <jstl:out value="${stddevNumberOfResult}"></jstl:out></p>	
		
		
				
<p><spring:message code="admin.lisMemberAccept" /> </p>	
<display:table pagesize="5" name="${lisMemberAccept}" id="lisMemberAccept"
	requestURI="${requestURI}">
		<display:column property="name" titleKey="admin.member.name"></display:column>
		<display:column property="middleName" titleKey="admin.member.middleName"></display:column>
		<display:column property="surname" titleKey="admin.member.surname"></display:column>
</display:table>

<p><spring:message code="admin.processionOrganised" /> </p>	
<display:table pagesize="5" name="${processionOrganised}" id="processionOrganised"
	requestURI="${requestURI}">
		<display:column property="title" titleKey="procession.title"></display:column>
	<display:column property="moment" titleKey="procession.moment"></display:column>
	<display:column property="description" titleKey="procession.description"></display:column>
</display:table>

<br>
<br>
 
<p><spring:message code="admin.histogram" /></p>
 
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js" ></script> 
<div style="width: 50%;">	
	<canvas id="myChart" height="60" width="400"></canvas>myChartB
	<br>
	<br>
	<p><spring:message code="admin.spammer" /></p>
	<canvas id="oilChart" width="600" height="400"></canvas>
	<br>
	<br>
	<p><spring:message code="admin.brotherhoodArea" /></p>
	<canvas id="myChartB" height="200" width="400"></canvas>
	<br>
	<br>
	<p><spring:message code="admin.MemberBro" /></p>
	<canvas id="myChartBM" height="200" width="400"></canvas>
</div>

<script>
var ctx = document.getElementById("myChart").getContext('2d');
var myChart = new Chart(ctx, {
    type: 'bar',
    
    data: {
        labels: ["${maxProcession}","${minProcession}"],
        datasets: [{
            label: ["<spring:message code="admin.positions"/>"],
            data: ["${maxProcessionN}","${minProcessionN}"],
            backgroundColor: [
                'rgba(255, 40, 132, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,50,1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }},
        type: 'bar',
});
</script> 


<script>


var oilCanvas = document.getElementById("oilChart");

Chart.defaults.global.defaultFontFamily = "Lato";
Chart.defaults.global.defaultFontSize = 18;

var oilData = {
    labels: [
        "<spring:message code="admin.noSpammersRation"/>",
        "<spring:message code="admin.spammersRation"/>"
    ],
    datasets: [
        {
            data: ["${noSpammersRation}","${spammersRation}"],
            backgroundColor: [
                "#FF6384",
                "#63FF84"
            ]
        }]
};

var pieChart = new Chart(oilCanvas, {
  type: 'pie',
  data: oilData
});
	
</script>


<script>
var ctx = document.getElementById("myChartB").getContext('2d');
var myChart = new Chart(ctx, {
    type: 'bar',
    
    data: {
        labels: [
		"<spring:message code="admin.avgBrotherhoodPerAreaH"/>",
		"<spring:message code="admin.minBrotherhoodPerAreaH"/>",
		"<spring:message code="admin.maxBrotherhoodPerAreaH"/>",
        "<spring:message code="admin.stddevBrotherhoodPerAreaH"/>"
                 ],
        datasets: [{
            label: ["<spring:message code="admin.brotherhoodArea"/>"],
            data: ["${avgBrotherhoodPerArea}","${minBrotherhoodPerArea}","${maxBrotherhoodPerArea}","${stddevBrotherhoodPerArea}"],
            backgroundColor: [
                'rgba(255, 40, 132, 0.2)',
                'rgba(255, 255, 132, 0.2)',
                'rgba(255, 40, 13, 0.2)'
            ],
            borderColor: [
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }},
        type: 'bar',
});
</script> 

<script>
var ctx = document.getElementById("myChartBM").getContext('2d');
var myChart = new Chart(ctx, {
    type: 'bar',
    
    data: {
        labels: [
		"<spring:message code="admin.avgNumberOfMemberPerBrotherhoodH"/>",
		"<spring:message code="admin.minNumberOfMemberPerBrotherhoodH"/>",
		"<spring:message code="admin.maxNumberOfMemberPerBrotherhoodH"/>",
        "<spring:message code="admin.desviationOfNumberOfMemberPerBrotherhoodH"/>"
                 ],
        datasets: [{
            label: ["<spring:message code="admin.MemberBrotherhood"/>"],
            data: ["${avgNumberOfMemberPerBrotherhood}","${minNumberOfMemberPerBrotherhood}","${maxNumberOfMemberPerBrotherhood}","${stddevNumberOfMemberPerBrotherhood}"],
            backgroundColor: [
                'rgba(255, 40, 132, 0.2)',
                'rgba(255, 255, 132, 0.2)',
                'rgba(255, 40, 13, 0.2)'
            ],
            borderColor: [
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }},
        type: 'bar',
});
</script> 


</security:authorize>
<acme:cancel url=" " code="back"/> 