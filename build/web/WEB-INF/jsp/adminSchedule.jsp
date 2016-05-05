
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="http://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script>  
    
    <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Lato" />
    <link rel="stylesheet" type="text/css" href = "http://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css">
    
    

    <link rel="stylesheet" type="text/css" media="screen" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" />
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link href="./css/prettify-1.0.css" rel="stylesheet">
    <link href="./css/base.css" rel="stylesheet">
    <link href="//cdn.rawgit.com/Eonasdan/bootstrap-datetimepicker/e8bddc60e73c1ec2475f827be36e1957af72e2ea/build/css/bootstrap-datetimepicker.css" rel="stylesheet">

        
    <script type="text/javascript" src="//code.jquery.com/jquery-2.1.1.min.js"></script>
    
    <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script>
    <script src="//cdn.rawgit.com/Eonasdan/bootstrap-datetimepicker/e8bddc60e73c1ec2475f827be36e1957af72e2ea/src/js/bootstrap-datetimepicker.js"></script>
    
    
    <!-- Loading Cascading Style sheets-->
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/selectize.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/owl.carousel.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/vanillabox/vanillabox.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/style.css">
    
    <title>Appointment Scheduling</title>
</head>

<body class="page-sub-page page-my-account">
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
            <img src="${pageContext.request.contextPath}/resources/assets/img/background-city.png"  alt="background">
        </div>
    </div>
    <!-- end Header -->
        
    <!-- Breadcrumb -->
    <div class="container">
        <ol class="breadcrumb" style="background-color: white;">
            <li><a href="${pageContext.request.contextPath}/${user.get("user_type")}DashBoard.htm"><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
            <li class="active">Courses</li>
        </ol>
    </div>
    <!-- end Breadcrumb -->
    
    <div class="container">
        <div class="row">
            <div class="col-md-7" role="main">
                <section id="admin-schedule">
                <h2>Appointment Scheduling</h2>
                <hr />
                <div style="overflow:hidden; padding-left: 45px; padding-top: 40px;">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-8">
                                <div id="datetimepicker12"></div>
                            </div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        $(function () {
                            $('#datetimepicker12').datetimepicker({
                                inline: true,
                                sideBySide: true
                            });
                        });
                    </script>
                </div>
                </section>
            </div>
        </div>
    </div>
</div>

    <script src="./js/prettify-1.0.min.js"></script>
    <script src="./js/base.js"></script>

    </body>
</html>