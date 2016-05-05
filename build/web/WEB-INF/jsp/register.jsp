<%-- 
    Document   : studentDashBoard
    Created on : Mar 2, 2016, 2:24:27 AM
    Author     : Dokuru
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>

<html lang="en-US">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
      
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Lato" />
    
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/selectize.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/owl.carousel.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/vanillabox/vanillabox.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/style.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/glyphicon.css">
    
    <style type="text/css">
        .errors {
            color:red;
            font-style:italic;
        }
   </style>
    <title>User Registration</title>
</head>

<body class="page-sub-page page-register-sign-in">
<!-- Wrapper -->
<div class="wrapper">
<!-- Header -->
<div class="navigation-wrapper">
    <div class="secondary-navigation-wrapper">
        <div class="container">
            <div class="navigation-contact pull-left">Call Us:  <span class="opacity-70">+1(773) 583-4050</span></div>
            <ul class="secondary-navigation list-unstyled pull-right">
                <li><a href="http://www.neiu.edu/about/news/northeastern-ranked-among-most-diverse-universities-lowest-student-debt-us-news-world-report">About Us</a></li>
            </ul>
        </div>
    </div><!-- /.secondary-navigation -->
    <div class="background">
        <img src="resources/assets/img/background-city.png"  alt="background">
    </div>
</div>
<!-- end Header -->

<!-- Breadcrumb -->
<div class="container">
    <ol class="breadcrumb">
        <li><a href="loginPage.htm"><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
        <li class="active">Register</li>
    </ol>
</div>
<!-- end Breadcrumb -->

<!-- Page Content -->
<div id="page-content">
    <div class="container">
        <div class="row">
            <!--MAIN Content-->
            <div id="page-main">
                <div class="col-md-10 col-sm-10 col-sm-offset-1 col-md-offset-1">
                    <div class="row">
                        <div class="col-md-8">
                            <section id="account-register" class="account-block" style="padding:0px;">
                                <p style="color: #3071a9; font-size: medium;">${errormessage}</p>
                                <header><h2>Become a Member of NEIU Family</h2></header>
                                <hr>
                                <form:form method="post" class="clearfix" commandName="registrationData">
                                    <div class="form-group inner-addon left-addon">
                                        <!--<label for="netID">Net ID</label>-->
                                        <spring:bind path="registrationData.netID">
                                            <i class="glyphicon glyphicon-user"></i>
                                            <input type="text" id="netID" class="form-control" name="netID" placeholder="Net ID">
                                        </spring:bind>
                                        <form:errors path="netID" cssClass="errors"></form:errors>
                                    </div>
                                
                                    <div class="form-group inner-addon left-addon">
                                        <!--<label for="userID">User ID</label>-->
                                        <spring:bind path="registrationData.userID">
                                            <i class="glyphicon glyphicon-user"></i>
                                            <input type="text" id="userID" class="form-control" name="userID" placeholder="User ID">
                                        </spring:bind>
                                        <form:errors path="userID" cssClass="errors"></form:errors>
                                    </div>
                                
                                    <div class="form-group inner-addon left-addon">
                                        <!--<label for="email">Email</label>-->
                                        <spring:bind path="registrationData.email">
                                            <i class="glyphicon glyphicon-envelope"></i>
                                            <input type="text" id="email" class="form-control" name="email" placeholder="Northeastern Email" value="${param.email}">
                                        </spring:bind>
                                        <form:errors path="email" cssClass="errors"></form:errors>
                                    </div>
                                
                                    <div class="form-group inner-addon left-addon">
                                        <!--<label for="pwd">Password</label>-->
                                        <spring:bind path="registrationData.password">
                                            <i class="glyphicon glyphicon-lock"></i>
                                            <input type="password" id="pwd" class="form-control" name="password" placeholder="Password">
                                        </spring:bind>
                                        <form:errors path="password" cssClass="errors"></form:errors>
                                    </div>
                                
                                    <div class="form-group inner-addon left-addon">
                                        <!--label for="cpwd">Confirm Password</label>-->
                                        <spring:bind path="registrationData.cPassword">
                                            <i class="glyphicon glyphicon-lock"></i>
                                            <input type="password" id="cpwd" class="form-control" name="cPassword" placeholder="Confirm Password">
                                        </spring:bind>
                                        <form:errors path="cPassword" cssClass="errors"></form:errors>
                                        <p style="color:red;">${noMatch}</p>
                                    </div>
                                
                                    <div class="form-group inner-addon left-addon">
                                        <!--<label for="phone">Phone</label>-->
                                        <spring:bind path="registrationData.phone">
                                            <i class="glyphicon glyphicon-phone"></i>
                                            <input type="text" class="form-control" id="phone" name="phone" placeholder="Phone Number       +##(###) ##-####" value="${param.phone}">
                                        </spring:bind>
                                        <form:errors path="phone" cssClass="errors"></form:errors>
                                    </div>
                                
                                    <div class="form-group inner-addon left-addon">
                                        <!--<label for="social">Social Security</label>-->
                                        <spring:bind path="registrationData.social">
                                            <i class="glyphicon glyphicon-lock"></i>
                                            <input type="text" class="form-control" id="social" name="social" placeholder="Social Security      ###-##-####">
                                        </spring:bind>
                                        <form:errors path="social" cssClass="errors"></form:errors>
                                    </div>
                                    <input type="submit" class="btn pull-right" value="Sign Up">
                                </form:form>
                            </section><!-- /#account-block -->
                        </div><!-- /.col-md-6 -->
                    </div><!-- /.row -->
                </div><!-- /.col-md-10 -->
            </div><!-- /#page-main -->

            <!-- end SIDEBAR Content-->
        </div><!-- /.row -->
    </div><!-- /.container -->
</div>
<!-- end Page Content -->

<!-- Footer -->
<footer id="page-footer">
    <section id="footer-bottom">
        <div class="container">
            <div class="footer-inner">
                <div class="copyright">&copy; Northeastern, All rights reserved</div><!-- /.copyright -->
            </div><!-- /.footer-inner -->
        </div><!-- /.container -->
    </section><!-- /#footer-bottom -->

</footer>
<!-- end Footer -->

</div>
<!-- end Wrapper -->
<!-- Loading Java Script's-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.tablesorter.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/icheck.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.vanillabox-0.1.5.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/retina-1.1.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/selectize.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/owl.carousel.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/custom.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.placeholder.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jQuery.equalHeights.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/countdown.js"></script>

</body>
</html>