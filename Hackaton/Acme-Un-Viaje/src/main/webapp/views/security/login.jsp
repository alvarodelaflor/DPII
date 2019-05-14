 <%--
 * login.jsp
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
	

<html lang="en"><head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="https://static.codepen.io/assets/favicon/favicon-aec34940fbc1a6e787974dcd360f2c6b63348d4b1f4e06c77743096d55480f33.ico">
<link rel="mask-icon" type="" href="https://static.codepen.io/assets/favicon/logo-pin-8f3771b1072e3c38bd662872f6b673a722f4b3ca2421637d5596661b4e2132cc.svg" color="#111">
<title>CodePen - Sign-Up/Login Form</title>
<link href="https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
<link rel="stylesheet" href="styles/bootstrap.css" type="text/css">
<style type="text/css">
<!--
body {
    background-image: url(images/hello.jpg);
   	background-size: 100% 103%;
}
-->
</style>
<script>
  window.console = window.console || function(t) {};
</script>
<script>
  if (document.location.search.match(/type=embed/gi)) {
    window.parent.postMessage("resize", "*");
  }
</script>
</head>
<body translate="no">
	<div class="form">
		<ul class="tab-group">
			<li class="tab "><a href="#signup"><spring:message code="master.page.registre" /></a></li>
			<li class="tab active"><a href="#login"><spring:message code="master.page.login" /></a></li>
		</ul>
		<div class="tab-content">
		<div id="login" style="display: block;">
			<form:form action="j_spring_security_check" modelAttribute="credentials">
				<div class="field-wrap">
					<label>
						<spring:message code="security.username" /><span class="req">*</span>
					</label>
					<form:input path="username" />	
					<form:errors class="error" path="username" />
				</div>
				<div class="field-wrap">
					<label>	
						<spring:message code="security.password" /><span class="req">*</span>
					</label>
					<form:password path="password" />	
					<form:errors class="error" path="password" />
				</div>
				<div class="field-wrap">
					<jstl:if test="${showError == true}">
						<div class="error">
							<p style="color:#FF0000"><spring:message code="security.login.failed" /></p>
						</div>
					</jstl:if>
				</div>
				<input type="submit" class="button button-block" value="<spring:message code="security.login" />" />
			</form:form>
	</div>
	<div id="signup" style="display: none;">
		<div class="field-wrap">
			<a href="#" class="btn btn-secondary"><spring:message code="register.user" /></a>
		</div>
		<div class="field-wrap">
			<a href="cleaner/create.do" class="btn btn-secondary"><spring:message code="register.cleaner" /></a>
		</div>
		<div class="field-wrap">
			<a href="transporter/create.do" class="btn btn-secondary"><spring:message code="register.trasporter" /></a>
		</div>
	</div>
</div>
</div> 
<script src="https://static.codepen.io/assets/common/stopExecutionOnTimeout-de7e2ef6bfefd24b79a3f68b414b87b8db5b08439cac3f1012092b2290c719cd.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script id="rendered-js">
      $('.form').find('input, textarea').on('keyup blur focus', function (e) {

  var $this = $(this),
  label = $this.prev('label');

  if (e.type === 'keyup') {
    if ($this.val() === '') {
      label.removeClass('active highlight');
    } else {
      label.addClass('active highlight');
    }
  } else if (e.type === 'blur') {
    if ($this.val() === '') {
      label.removeClass('active highlight');
    } else {
      label.removeClass('highlight');
    }
  } else if (e.type === 'focus') {

    if ($this.val() === '') {
      label.removeClass('highlight');
    } else
    if ($this.val() !== '') {
      label.addClass('highlight');
    }
  }

});

$('.tab a').on('click', function (e) {

  e.preventDefault();

  $(this).parent().addClass('active');
  $(this).parent().siblings().removeClass('active');

  target = $(this).attr('href');

  $('.tab-content > div').not(target).hide();

  $(target).fadeIn(600);

});
      //# sourceURL=pen.js
    </script>
<script src="https://static.codepen.io/assets/editor/live/css_reload-5619dc0905a68b2e6298901de54f73cefe4e079f65a75406858d92924b4938bf.js"></script>