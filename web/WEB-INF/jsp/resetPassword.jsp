<%-- 
    Document   : resetPassword
    Created on : Mar 2, 2016, 2:24:27 AM
    Author     : Dokuru
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>

<html lang="en-US">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
      
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Lato" />

    <!-- Loading Style Sheets-->
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/selectize.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/owl.carousel.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/vanillabox/vanillabox.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/style.css">
        
    <style type="text/css">
        .errors {
            color:red;
            font-style:italic;
        }
    </style>
    <title>User DashBoard</title>
    
</head>

<body class="page-sub-page page-my-account">
<!-- Wrapper -->
<div class="wrapper">
<!-- Header -->
<div class="navigation-wrapper">
    <div class="secondary-navigation-wrapper">
        <div class="container">
            <div class="navigation-contact pull-left">Call Us:  <span class="opacity-70">+1(773) 583-4050</span></div>
            <ul class="secondary-navigation list-unstyled pull-right">
                <li>Welcome, ${user.get("firstName")}</li>
                <li><a href="<c:url value="/logout.htm" />">Logout</a></li>
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
        <li><a href="${pageContext.request.contextPath}/${user.get("user_type")}DashBoard.htm"><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
        <li class="active">Password Reset</li>
    </ol>
</div>
<!-- end Breadcrumb -->

<!-- Page Content -->
<div id="page-content">
    <div class="container">
        <header><h1>Change Password</h1></header>
        <div class="row">
            <div class="col-md-8">
                <section id="my-account">
                    <ul class="nav nav-tabs" id="tabs">
                        <li><a href="<c:url value="/${user.get('user_type')}DashBoard.htm" /> ">Profile</a></li>
                        <li><a href="<c:url value="coursesTest.htm"/>">My Courses</a></li>
                        <li class="active"><a href="#tab-reset">Change Password</a></li>
                        <c:choose>
                            <c:when test="${requested == 'admin'}">
                                <li><a href="<c:url value="/adminAllSchedule.htm"/> ">Schedule/ Appointments</a></li>
                            </c:when>
                            <c:otherwise>
                            <li><a class="myLink" style="-webkit-transition: 0.5s ease; font-size: 13px; font-weight: 200; color: #024283;" href="<c:url value="studentFinalGrades.htm" />">Final Grades</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul><!-- /#my-profile-tabs -->
                        <div class="tab-pane active" id="tab-change-password">
                            <section id="password">
                                <header><h3>Change Password</h3></header>
                                <font style="color:red;">${errormessage}</font>
                                <div class="row">
                                    <div class="col-md-5 col-md-offset-4">
                                        <form:form class="clearfix" commandName="resetPassword" method="post">
                                            <div class="form-group">
                                                <spring:bind path="resetPassword.cPWD">
                                                    <span class="glyphicon glyphicon-lock"></span><input type="password" class="form-control" placeholder="Current Northeastern Password" name="cPWD">
                                                </spring:bind>
                                                <form:errors path="cPWD" cssClass="errors"></form:errors>
                                                
                                                
                                            </div>
                                            <div class="form-group">
                                                <spring:bind path="resetPassword.nPWD">
                                                    <span class="glyphicon glyphicon-lock"></span><input type="password" class="form-control" placeholder="New Password" name="nPWD">
                                                </spring:bind>
                                                <form:errors path="nPWD" cssClass="errors"></form:errors>
                                                
                                                
                                            </div>
                                            <div class="form-group">
                                                <spring:bind path="resetPassword.rPWD">    
                                                    <span class="glyphicon glyphicon-lock"></span><input type="password" class="form-control" placeholder="Confirm New Password" name="rPWD">
                                                </spring:bind>
                                                <form:errors path="rPWD" cssClass="errors"></form:errors>
                                                
                                                
                                            </div>
                                            <input type="submit" class="btn pull-right" value="Change Password">
                                        </form:form>
                                    </div>
                                </div>
                            </section>
                        </div>
                </section>
            </div>
            
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/icheck.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.vanillabox-0.1.5.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/retina-1.1.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/selectize.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/owl.carousel.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/custom.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.placeholder.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jQuery.equalHeights.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/countdown.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/assets/js/jquery.tablesorter.min.js"></script>
    
</body>
</html>