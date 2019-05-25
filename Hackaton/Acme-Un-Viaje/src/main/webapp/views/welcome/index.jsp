<%--
 * index.jsp
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
<hr>
<div class="container-fluid">
	<div class="col-md-12">
		<div id="myCarousel" class="carousel slide bg-inverse w-90 ml-auto mr-auto" data-ride="carousel">
		
		  <!-- Indicators -->
		  <ol class="carousel-indicators">
		    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		    <li data-target="#myCarousel" data-slide-to="1"></li>
		    <li data-target="#myCarousel" data-slide-to="2"></li>
		  </ol>
		
		  <!-- The slideshow -->
		  <div class="carousel-inner" role="listbox">
		    <div class="carousel-item active">
		      	<img class="card-img-top" src="https://cdn.civitatis.com/estados-unidos/nueva-york/guia/nueva-york.jpg" alt="Los Angeles">
				<div class="carousel-caption d-none d-md-block">
			    	<h3 style="text-shadow: 2px 0 0 #000, -2px 0 0 #000, 0 2px 0 #000, 0 -2px 0 #000, 1px 1px #000, -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000;"><b><spring:message code="viaja1" /></b></h3>
			    	<p style="text-shadow: 2px 0 0 #000, -2px 0 0 #000, 0 2px 0 #000, 0 -2px 0 #000, 1px 1px #000, -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000;"><spring:message code="viajaSub1" /></p>
			  </div>	
		    </div>
		    <div class="carousel-item">
		      	<img class="card-img-top" src="https://www.puzzlestumecompletas.com/wp-content/uploads/2016/09/3175-1000-LONDRES-PANORAMICO.jpg" alt="Los Angeles">
				<div class="carousel-caption d-none d-md-block">
			    	<h3 style="text-shadow: 2px 0 0 #000, -2px 0 0 #000, 0 2px 0 #000, 0 -2px 0 #000, 1px 1px #000, -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000;"><b><spring:message code="viaja2" /></b></h3>
			    	<p style="text-shadow: 2px 0 0 #000, -2px 0 0 #000, 0 2px 0 #000, 0 -2px 0 #000, 1px 1px #000, -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000;"><spring:message code="viajaSub2" /></p>
			  </div>	
		    </div>
		    <div class="carousel-item">
		      	<img class="card-img-top" src="https://www.achesonlaw.ca/wp-content/uploads/2018/07/distracted-driving-personal-injury-lawyer.jpg" alt="Los Angeles">
				<div class="carousel-caption d-none d-md-block">
			    	<h3 style="text-shadow: 2px 0 0 #000, -2px 0 0 #000, 0 2px 0 #000, 0 -2px 0 #000, 1px 1px #000, -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000;"><b><b><spring:message code="viaja3" /></b></b></h3>
			    	<p style="text-shadow: 2px 0 0 #000, -2px 0 0 #000, 0 2px 0 #000, 0 -2px 0 #000, 1px 1px #000, -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000;"><spring:message code="viajaSub3" /></p>
			  </div>	
		    </div>
		  </div>
		
		  <!-- Left and right controls -->
			<a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
			  	<span class="carousel-control-prev-icon" aria-hidden="true"></span>
			  	<span class="sr-only">Previous</span>
			</a>
			<a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
			  <span class="carousel-control-next-icon" aria-hidden="true"></span>
			  <span class="sr-only">Next</span>
			</a>
		</div>
								<script>
						// Initialize tooltip component
						$(function () {
						  $('[data-toggle="tooltip"]').tooltip()
						})
						
						// Initialize popover component
						$(function () {
						  $('[data-toggle="popover"]').popover()
						})
						</script>
	</div>
</div>