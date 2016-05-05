<%-- 
    Document   : AdminAllSchedule
    Created on : Apr 10, 2016, 2:22:21 PM
    Author     : Dokuru
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
    
    <script type="text/javascript">   
        $(document).ready(function () {
            $("#appointments").dataTable({
                "sPaginationType": "full_numbers",
                "aLengthMenu": [[7, 10, 15, -1], [7, 10, 15, "All"]],
                "iDisplayLength": 7
            });
            $("#accordion").accordion();
            
            $("#schedule").dataTable({
                "sPaginationType": "full_numbers",
                "aLengthMenu": [[7, 10, 15, -1], [7, 10, 15, "All"]],
                "iDisplayLength": 7
//              "bJQueryUI": true
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
        
        input[type="search"] {
            width: 70%;
            padding-top: 10px;
        }
        
        #accordion .ui-accordion-content {
            max-height: 250px;
        }
        
    </style>
    <title>Appointments</title>
    
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
        <li class="active">My Schedule Info</li>
    </ol>
</div>
<!-- end Breadcrumb -->

<!-- Page Content -->
<div id="page-content">
    <div class="container">
        <header><h1>Schedule/ Appointments</h1></header>
        <div class="row">
            <div class="col-md-10">
                <section id="my-courses">
                    <ul class="nav nav-tabs" id="tabs">
                        <li><a href="<c:url value="/${user.get('user_type')}DashBoard.htm" /> ">Profile</a></li>
                        <li><a href="<c:url value="/coursesTest.htm"/>">My Courses</a></li>
                        <li><a href="<c:url value="/resetPassword.htm"/>">Change Password</a></li>
                        <li class="active"><a href="<c:url value="/adminAllSchedule.htm"/> ">Schedule/ Appointments</a></li>
                    </ul><!-- /#my-profile-tabs -->
                        <div class="tab-pane active" id="tab-my-courses">
                            <p style="color:red;">${message}</p>
                            <section id="course-list">
                                <div id="accordion">
                                    <h3>Appointments</h3>
                                    <div>
                                        <c:choose>
                                            <c:when test="${fn:length(appointmentsList) gt 0}">
                                                <table class="table-striped" id="appointments" style="height: 225px;">
                                                    <thead>
                                                        <tr>
                                                            <th>Course ID</th>
                                                            <th>Course Name</th>
                                                            <th>Date</th>
                                                            <th>Day</th>
                                                            <th>Start time</th>
                                                            <th>End Time</th>
                                                            <th></th>
                                                            <th>Student ID</th>
                                                            <th>Student name</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${appointmentsList}" var="entry" varStatus="loop">
                                                            <tr>
                                                                <td class="filterable-cell">${entry.courseID}</td>
                                                                <td class="filterable-cell">${entry.courseName}</td>
                                                                <td class="filterable-cell">${entry.date}</td>
                                                                <td class="filterable-cell">${entry.dayOfWeek}</td>
                                                                <td class="filterable-cell">${entry.startTime}</td>
                                                                <td class="filterable-cell"><div style="float: left;">${entry.endTime}</div></td>
                                                                <td><c:if test="${not empty entry.date}"><a style="color: #024283;" class="myLink" href="<c:url value="#" />" onclick="return myFunction()">Booked<span class="glyphicon glyphicon-book" style="padding-left: 10px;"></span></a></c:if></td>
                                                                <td class="filterable-cell">${entry.studentID}</td>
                                                                <td class="filterable-cell">${entry.firstName} ${entry.lastName}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:when>
                                            <c:otherwise>
                                                <p style="font-size: 15px;">No appointments found on the system</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <h3 style="background: #c9e2b3;">Open Slots</h3>
                                    <div>
                                        <c:choose>
                                            <c:when test="${fn:length(scheduleList) gt 0}">
                                                <table class="table-striped" id="schedule" style="height: 325px; width: 100%;">
                                                    <thead>
                                                        <tr>
                                                            <th class="col-sm-2">Course ID</th>
                                                            <th class="col-sm-2">Course Name</th>
                                                            <th class="col-sm-2">Date</th>
                                                            <th class="col-sm-2">Day</th>
                                                            <th class="col-sm-2">Start time</th>
                                                            <th class="col-sm-2">End Time</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${scheduleList}" var="entry" varStatus="loop">
                                                            <tr>
                                                                <td class="filterable-cell">${entry.courseID}</td>
                                                                <td class="filterable-cell">${entry.courseName}</td>
                                                                <td class="filterable-cell">${entry.date}</td>
                                                                <td class="filterable-cell">${entry.dayOfWeek}</td>
                                                                <td class="filterable-cell">${entry.startTime}</td>
                                                                <td class="filterable-cell"><div style="float: left;">${entry.endTime}</div></td>
                                                                <td><c:if test="${not empty entry.date}"><a style="color: #024283;" class="myLink" href="<c:url value="#" />" onclick="return myFunction()">Open Slot<span class="glyphicon glyphicon-folder-open" style="padding-left: 10px;"></span></a></c:if></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </c:when>
                                            <c:otherwise>
                                                <p style="font-size: 15px;">No Schedules found on the system. Please upload your schedule</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
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

