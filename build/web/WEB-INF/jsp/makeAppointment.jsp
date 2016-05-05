<%-- 
    Document   : makeAppointment
    Created on : Apr 5, 2016, 11:12:12 PM
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
    <script src="http://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script>  
    
    <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Lato" />
    <link rel="stylesheet" type="text/css" href = "http://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css">
    
    <script type="text/javascript">
        $(document).ready(function () {
            $("#courses").dataTable({
                "sPaginationType": "full_numbers",
                "bJQueryUI": true
            });
        });
    </script>
    
    <!-- Loading Cascading Style sheets-->
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/selectize.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/owl.carousel.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/vanillabox/vanillabox.css">
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/style.css">
    
    <style type="text/css">
        .errors {
            color:red;
            font-style:italic;
        }
        .myLink:hover {
            text-decoration: underline;
        }
    </style>
    <title>Appointment Scheduling</title>
    
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
    <ol class="breadcrumb" style="background-color: transparent; font-size: 11px; margin-top: 20px; padding: 0; font-family: 'Montserrat';">
        <li><a href="${pageContext.request.contextPath}/${user.get("user_type")}DashBoard.htm"><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
        <li class="active"><a href="${pageContext.request.contextPath}/coursesTest.htm">Back To Courses</a></li>
        <li class="active"><a href="<c:url value="/courses/${courseDetails.get('courseID')}.htm" /> ">${courseDetails.get("courseLables")}</a></li>
        <li>Make Appointments</li>
    </ol>
</div>
<!-- end Breadcrumb -->

<!-- Page Content -->
<div id="page-content">
    <div class="container">
        <header><h1>Schedule</h1></header>
        <div class="row">
            <div class="col-md-10">
                <section id="my-courses">
                    <ul class="nav nav-tabs" id="tabs">
                        <li><a href="<c:url value="/courses/${courseDetails.get('courseID')}.htm" /> ">${courseDetails.get("courseName")}</a></li>
                                <li><a href="<c:url value="/attendance.htm"/>">Attendance</a></li>
                                <li><a href="<c:url value="/grades.htm"/>">Grades</a></li>
                                <li class="dropdown active">
                                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Appointments <span class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <li class="active"><a href="<c:url value="/makeAppointment.htm"/> ">Make Appointments</a></li>
                                        <li><a href="<c:url value="/viewAppointments.htm"/>">View Appointments</a></li>   
                                    </ul>
                                </li>
<!--                                <li class="active"><a href="<c:url value="#tab-schedule"/>">Make Appointment</a></li>-->
                    </ul><!-- /#my-profile-tabs -->
                        <div class="tab-pane active" id="tab-my-courses">
                            <section id="course-list">
                                <header><h3>Available Slots</h3></header>
                                <p class="errors">${appointMessage}</p>
                                <table id="courses">
                                    <thead>
                                    <tr>
                                        <th>Date</th>
                                        <th>Day</th>
                                        <th>Start time</th>
                                        <th>End Time</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${listSize > 0}">
                                            <c:forEach items="${scheduleList}" var="entry" varStatus="loop">
                                                <tr>
                                                    <td class="filterable-cell">${entry.date}</td>
                                                    <td class="filterable-cell">${entry.dayOfWeek}</td>
                                                    <td class="filterable-cell">${entry.startTime}</td>
                                                    <td class="filterable-cell"><div style="float: left;">${entry.endTime}</div></td>
                                                    <td><c:if test="${not empty entry.date}"><a class="myLink" href="<c:url value="/book/${entry.date}/start/${entry.startTime}/end/${entry.endTime}.htm" />" onclick="return myFunction()">Book<span class="glyphicon glyphicon-book" style="padding-left: 10px;"></span></a></c:if></td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                    </tbody>
                                </table>
                            </section><!-- /#course-list -->
                        </div><!-- /.tab-pane -->
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
