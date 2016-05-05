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
    
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/selectize.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/owl.carousel.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/vanillabox/vanillabox.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/style.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/login.css">
    
    <title>Northeastern Learning Management</title>

</head>

<body class="page-sub-page page-register-sign-in">
<!-- Wrapper -->
<div class="wrapper">
<!-- Header -->
<div class="navigation-wrapper">
    <div class="secondary-navigation-wrapper">
        <div class="container">
            <div class="navigation-contact pull-left">Call Us:  <span class="opacity-70">(773) 583-4050</span></div>
            <ul class="secondary-navigation list-unstyled pull-right">
                <li><a href="http://www.neiu.edu/about/news/northeastern-ranked-among-most-diverse-universities-lowest-student-debt-us-news-world-report" target="_blank">About Us</a></li>
            </ul>
        </div>
    </div><!-- /.secondary-navigation -->
    <div class="primary-navigation-wrapper">
        <header class="navbar" id="top" role="banner">
            <div class="container">
                <div class="navbar-header">
                    <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <nav class="collapse navbar-collapse bs-navbar-collapse navbar-right" role="navigation">
                    <ul class="nav navbar-nav">
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/loginPage.htm" class="has-child no-link">Home</a>
                            <ul class="list-unstyled child-navigation">
                                <li><a href="http://neiu.edu" target="_blank">Northeastern Home</a></li>
                                <li><a href="http://www.neiu.edu/academics/" target="_blank">Academics</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#" class=" has-child no-link">Courses</a>
                            <ul class="list-unstyled child-navigation">
                                <li><a href="http://www.neiu.edu/future-students/staff/admissions-office" target="_blank">Admissions</a></li>
                                <li><a href="http://www.neiu.edu/academics/all-departments" target="_blank">Departments</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="http://www.neiu.edu/academics/2015-2016-academic-calendar" target="_blank">Academic Calendar</a>
                        </li>
                        
                        <li>
                            <a href="http://www.neiu.edu/future-students/contact-us" target="_blank">Contact Us</a>
                        </li>
                    </ul>
                </nav><!-- /.navbar collapse-->
            </div><!-- /.container -->
        </header><!-- /.navbar -->
    </div><!-- /.primary-navigation -->
    <div class="background">
        <img src="resources/assets/img/background-city.png"  alt="background">
    </div>
</div>
<!-- end Header -->

<!-- Breadcrumb -->
<div class="container">
    <ol class="breadcrumb">
        <li><a href="${pageContext.request.contextPath}/loginPage.htm"><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
        <li class="active">Sign in</li>
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
                        <div class="col-md-6">
                        </div><!-- /.col-md-6 -->
                        <div class="col-md-6">
                            <section id="account-sign-in" class="account-block">
                                <header><h2>Have an Account?</h2></header>
                                <font color="red">${errormessage}</font>
                                <form:form class="clearfix" commandName="loginData" method="post" >
                                    <div class="form-group inner-addon left-addon">
                                        <spring:bind path="loginData.netID">
                                            <i class="glyphicon glyphicon-user"></i>
                                            <input type="text" class="form-control" placeholder="Net ID" name="netID" value="${param.netID}">
                                        </spring:bind>
                                        <form:errors path="netID" cssClass="errors"></form:errors>
                                    </div>
                                    <div class="form-group inner-addon left-addon">
                                        <spring:bind path="loginData.password">
                                            <i class="glyphicon glyphicon-lock"></i>
                                            <input type="password" class="form-control" placeholder="NEIU Port Password" name="password">
                                        </spring:bind>
                                    <form:errors path="password" cssClass="errors"></form:errors>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-6" style="padding-top:1em;">
                                            <a href="<c:url value="register.htm"/>">New Registrations</a>
                                        </div>
                                        <div class="form-group col-md-4">
                                            <input type="submit" class="btn pull-right" value="Sign In">
                                        </div>
                                    </div>
                                </form:form>
                                <hr>
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
    <section id="footer-top">
        <div class="container">
            <div class="footer-inner">
                <div class="footer-social">
                    <figure>Follow us:</figure>
                    <div class="icons">
                        <a href="#"><i class="fa fa-twitter"></i></a>
                        <a href="#"><i class="fa fa-facebook"></i></a>
                        <a href="#"><i class="fa fa-pinterest"></i></a>
                        <a href="#"><i class="fa fa-youtube-play"></i></a>
                    </div><!-- /.icons -->
                </div><!-- /.social -->
                <div class="search pull-right">
                </div><!-- /.pull-right -->
            </div><!-- /.footer-inner -->
        </div><!-- /.container -->
    </section><!-- /#footer-top -->

    <section id="footer-content">
        <div class="container">
            <div class="row">
                <div class="col-md-3 col-sm-4">
                    <aside>
                        <header><h4>Contact Us</h4></header>
                        <address>
                            <strong>Northeastern Illinois University</strong>
                            <br>
                            <span>5500 N.Saint Louis Avenue</span>
                            <br><br>
                            <span>Chicago, IL  60625</span>
                            <br>
                            <abbr title="Telephone">Telephone:</abbr> +1 (773) 583-4050
                            <br>
                            <abbr title="Email">Email:</abbr> <a href="#">neiuenquires@neiu.edu</a>
                        </address>
                    </aside>
                </div><!-- /.col-md-3 -->
                
                <div class="col-md-2 col-sm-3">
                </div><!-- /.col-md-3 -->
                
                <div class="col-md-7 col-sm-5">
                    <aside>
                        <header><h4>About University</h4></header>
                        <p>Northeastern Illinois University (NEIU) is a public state 
                            university located in Chicago, Illinois. The main campus is 
                            located in the community area of North Park with three 
                            additional campuses in the metropolitan area. 
                            Tracing its founding to 1867, it was first established as a 
                            separate branch of a public college in 1949. 
                            NEIU serves 12,000 students in the region and is a federally 
                            designated Hispanic Serving Institution.
                        </p>
                        
                    </aside>
                </div><!-- /.col-md-3 -->
            </div><!-- /.row -->
        </div><!-- /.container -->
        <div class="background"><img src="resources/assets/img/background-city.png" class="" alt=""></div>
    </section><!-- /#footer-content -->

    <section id="footer-bottom">
        <div class="container">
            <div class="footer-inner">
                <div class="copyright">&copy; Northeastern Illinois University, All rights reserved</div><!-- /.copyright -->
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