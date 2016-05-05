
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

        <!-- Loading Cascading Style sheets-->
        <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/selectize.css">
        <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/owl.carousel.css">
        <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/vanillabox/vanillabox.css">
        <link rel = "stylesheet" href ="${pageContext.request.contextPath}/resources/assets/css/style.css">


        <link rel="stylesheet" type="text/css" media="screen" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" />
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
        <link href="./css/prettify-1.0.css" rel="stylesheet">
        <link href="./css/base.css" rel="stylesheet">
        <link href="//cdn.rawgit.com/Eonasdan/bootstrap-datetimepicker/e8bddc60e73c1ec2475f827be36e1957af72e2ea/build/css/bootstrap-datetimepicker.css" rel="stylesheet">

        <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
        <script type="text/javascript" src="//code.jquery.com/jquery-2.1.1.min.js"></script>

        <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script>
        <script src="//cdn.rawgit.com/Eonasdan/bootstrap-datetimepicker/e8bddc60e73c1ec2475f827be36e1957af72e2ea/src/js/bootstrap-datetimepicker.js"></script>

        <script type="text/javascript">
            function confirmForChanges() {
                var start = document.getElementById("startT").value;
                var end = document.getElementById("endT").value;
                var dayOfWeek = new Date(start).getDay();
                
                end = (new Date(start).getMonth() + 1) + "/" + new Date(start).getDate() + "/" + new Date(start).getFullYear() + " " + end;
                
                var currentDate = new Date();

                if ((end === "" || end === null) || (start === "" || start === null)) {
                    document.getElementById("errorMessage").innerHTML = "start time and end time required";
                    alert("start time and end time required");
                    return false;
                }
                else {
                    if (new Date(start).getMinutes() % 30 !== 0 || new Date(end).getMinutes() % 30 !== 0) {
                        document.getElementById("errorMessage").innerHTML = "Please Choose Start Time minutes and\n\nEnd Time minutes in multiples of 30";
                        alert("Please Choose Start Time minutes and\n\nEnd Time minutes in multiples of 30");
                        return false;
                    }
                    if (currentDate.getTime() + 60 * 60 * 1000 < new Date(start).getTime()) { 
                        var diff = new Date(end).getTime() - new Date(start).getTime();
                        var twoHour = 2*60*60*1000;
                        var halfHour = 30*60*60;
                        
                        if(diff > twoHour || diff < halfHour) {
                            document.getElementById("errorMessage").innerHTML = "Minimum length of appointment is half-hour and max is 2 hours";
                            alert("Minimum length of appointment is half-hour and max is 2 hours");
                            return false;
                        }
                    } else {

                        currentDate = new Date().getHours() + 1;
                        document.getElementById("errorMessage").innerHTML = "Schedules can be created for time after " + currentDate + " : " + new Date().getMinutes()+" 'O Clock for current Date" + new Date().getDate();
                        alert("Schedules can be created for time after " + currentDate + " : " + new Date().getMinutes()+" 'O Clock for current Date " + new Date().getDate());
                        return false;
                    }
                }
                
                var splitStart = start.split(" ");
                var splitEnd = end.split(" ");
                
                if(dayOfWeek === 0)    dayOfWeek = "Sunday";
                else if(dayOfWeek === 1)     dayOfWeek = "Monday";
                else if(dayOfWeek === 2)     dayOfWeek = "Tuesday";
                else if(dayOfWeek === 3)     dayOfWeek = "Wednesday";
                else if(dayOfWeek === 4)     dayOfWeek = "Thursday";
                else if(dayOfWeek === 5)     dayOfWeek = "Friday";
                else     dayOfWeek = "Saturday";
                

                document.getElementById("setDate").value = new Date(start).getFullYear() + "/" + (new Date(start).getMonth() + 1) + "/" + new Date(start).getDate();
                document.getElementById("setStartTime").value = new Date(start).getHours() + ":" + new Date(start).getMinutes();
                document.getElementById("setEndTime").value = new Date(end).getHours() + ":" + new Date(end).getMinutes();
                document.getElementById("setStartTimeOfDay").value = splitStart[2];
                document.getElementById("setEndTimeOfDay").value = splitEnd[2];
                document.getElementById("dayOfWeek").value = dayOfWeek;
            }

            function myFunction() {
                var checkDate = document.getElementById("setDate").value;
                if(checkDate === "" || checkDate === null) {
                    document.getElementById("errorMessage").innerHTML = "Please check the appointment time is not greater than 2 hours and less than half hour";
                    alert("Please check the appointment time is not greater than 2 hours and less than half hour");
                    return false;
                }
                var r = confirm("Do you want to make changes?");
                if (!r)
                   alert("Your changes will be lost");

                return r;
            }
            
            function deleteFunction() {
                var r = confirm("Do you want to make changes?");
                if (!r)
                   alert("Your changes will be lost");

                return r;
            }
            
            $('.selectpicker').selectpicker({
                style: 'btn-info',
                size: 2
            });
        </script>

        <style type="text/css">
            .myLink:hover {
                text-decoration: underline;
            }
            
            .table-fixed thead {
                width: 97%;
            }
            .table-fixed tbody {
                height: 230px;
                overflow-y: auto;
                width: 100%;
            }
            .table-fixed thead, .table-fixed tbody, .table-fixed tr, .table-fixed td, .table-fixed th {
                display: block;
            }
            .table-fixed tbody td, .table-fixed thead > tr> th {
                float: left;
                border-bottom-width: 0;
            }
        </style>
        <script type="text/javascript" src="jquery.timepicker.js"></script>
        <link rel="stylesheet" type="text/css" href="jquery.timepicker.css">
        <script type="text/javascript" src="lib/bootstrap-datepicker.js"></script>
        <link rel="stylesheet" type="text/css" href="lib/bootstrap-datepicker.css">


        <!-- Select Picker-->
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.2/css/bootstrap-select.min.css" />
        <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.2/js/bootstrap-select.min.js"></script>

        <!--Time Picker-->
        <script type="text/javascript" src="http://wvega.github.io/timepicker/resources/jquery-timepicker/jquery.timepicker.min.js"></script>
        <link rel="stylesheet" href="http://wvega.github.io/timepicker/resources/jquery-timepicker/jquery.timepicker.min.css" />
        
        <title>Appointment Scheduling</title>
    </head>

    <body class="page-sub-page page-my-account" style="font-family: 'Montserrat'; font-size: 13px;">
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
                <ol class="breadcrumb" style="background-color: transparent; font-size: 11px; margin-top: 20px; padding: 0; font-family: 'Montserrat'; ">
                    <li><a href="${pageContext.request.contextPath}/${user.get("user_type")}DashBoard.htm"><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
                    <li class="active"><a href="${pageContext.request.contextPath}/coursesTest.htm">Back To Courses</a></li>
                    <li class="active"><a href="<c:url value="/courses/${courseDetails.get('courseID')}.htm" /> ">${courseDetails.get("courseLables")}</a></li>
                    <li>Make - Change Schedule</li>
                </ol>
            </div>
            <!-- end Breadcrumb -->

            <div class="container">
                <h2>Make/ Change Schedule</h2>
                <hr />
                <div class="row">
                    
                    <div class="col-md-7" role="main">
                        <section id="admin-schedule" style="padding-top: 5px;">
                            <ul class="nav nav-tabs" id="tabs">
                                <li><a href="<c:url value="/courses/${courseDetails.get('courseID')}.htm" /> ">${courseDetails.get("courseName")}</a></li>
                                <li><a href="<c:url value="/attendance.htm"/>">Attendance</a></li>
                                <li><a href="<c:url value="/grades.htm"/>">Grades</a></li>
                                <li class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Appointments <span class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="<c:url value="/adminAppointmentSchedule.htm"/> ">Make/ Change Schedule</a></li>
                                        <li><a href="<c:url value="/viewAppointments.htm"/>">View Appointments</a></li>   
                                    </ul>
                                </li>
                                <li class="active"><a href="<c:url value="#tab-schedule"/>">Create Schedule</a></li>
                            </ul><!-- /#my-profile-tabs -->
                            <p id="errorMessage" style="color:red; font-style: italic;"></p>
                            <p class="errors">${appointMessage12}</p>
                            <form:form method="post" commandName="scheduledDates" class="clearfix">
                                <!--                        <input type="hidden" name="submitted" value="true">-->
                                <spring:bind path="scheduledDates.date">
                                    <input type="text" name="date" id="setDate" hidden>
                                </spring:bind>
                                <%--<form:errors path="date" cssClass="errors" />--%>

                                <spring:bind path="scheduledDates.startTime">
                                    <input type="text" name="startTime" id="setStartTime" hidden>
                                <%--<form:errors path="startTime" cssClass="errors"></form:errors>--%>
                                </spring:bind>
                                
                                
                                <spring:bind path="scheduledDates.endTime">
                                    <input type="text" name="endTime" id="setEndTime" hidden>
                                </spring:bind>

                                <spring:bind path="scheduledDates.startTimeOfDay">
                                    <input type="text" name="startTimeOfDay" id="setStartTimeOfDay" hidden>
                                </spring:bind>

                                <spring:bind path="scheduledDates.endTimeOfDay">
                                    <input type="text" name="endTimeOfDay" id="setEndTimeOfDay" hidden>
                                </spring:bind>
                                
                                <spring:bind path="scheduledDates.dayOfWeek">
                                    <input type="text" name="dayOfWeek" id="dayOfWeek" hidden>
                                </spring:bind>
                                    
                                <div class="container">
                                    <div class="row">
                                        <div class='col-sm-6' style="padding-top: 40px;">
                                            <div class="form-group">
                                                <div class='input-group date' id='datetimepicker1'>
                                                    <input type='text' class="form-control" name="starttime" placeholder="Start Time" style="background-color: transparent;" id="startT" value="<c:out value="${param.startTime}" />"/>
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                        <script type="text/javascript">
                    $(function () {
                        $('#datetimepicker1').datetimepicker();
                    });
                                        </script>
                                    </div>
                                </div>
                                <div style="color:red;">
                                    <c:if test="${noStart}">
                                        Note: You Must Select A Start Time
                                    </c:if>
                                </div>
                                <div class="container">
                                    <div class="row">
                                        <div class='col-sm-6' style="padding-top: 40px;">
                                            <div class="form-group">
                                                <div class='input-group date' id='datetimepicker2'>
                                                    <input type='text' class="form-control endTime" name="endtime" placeholder="End Time" style="background-color: transparent" id="endT" value="<c:out value="${param.endTime}" />"/>
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-time"></span>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                        <script type="text/javascript">
                                            (function($) {
                                                $(function() {
                                                    $('input.endTime').timepicker();
                                                });
                                            })(jQuery);
                                            
                                        </script>
<!--                                        <script type="text/javascript">
                                            $(function () {
                                                $('#datetimepicker2').datetimepicker();
                                            });
                                        </script>-->
                                    </div>
                                </div>
                                <div style="color:red;">
                                    <c:if test="${noEnd}">
                                        Note: You Must Select A End Time
                                    </c:if>
                                </div>

                                <table style="padding-top: 30px;">
                                    <tr>
                                        <td style="padding-left: 15px; padding-right: 20px;">
                                            <spring:bind path="scheduledDates.endTimeOfDay">
                                                <select name="saveCurrent" style="width:100%; border-color: gray" class="form-control" onclick="return confirmForChanges();" required>
                                                    <option value = "" selected="selected">!--Select--!</option>
                                                    <option value = "Yes">Make it Recurring Event</option>
                                                    <option value = "No">Not Recurring Event</option>
                                                </select>
                                            </spring:bind>
                                        </td>

                                        <td>
                                            <input type="Submit" style="width: 100%;" class="btn btn-primary active" value="Make Changes" onclick="return myFunction()">
                                        </td>
                                    </tr>
                                </table>
                            </form:form>
<!--                                    <input type="text" class="timepicker">
                                    <script type="text/javascript">
                                        (function($) {
                                            $(function() {
                                                $('input.timepicker').timepicker();
                                            });
                                        })(jQuery);
                                    </script>-->
                        </section>
                    </div>
                    <!--SIDEBAR Content-->
                    <div class="col-md-5">
                        <div id="page-sidebar" class="sidebar">
                            <aside class="news-small" id="news-small">
                                <header>
                                    <h3>Current Available Slots</h3>
                                    <span class="glyphicon glyphicon-time">
                                        <jsp:useBean id="now" class="java.util.Date"/>
                                        Current Date: <fmt:formatDate value="${now}" dateStyle="long"/>
                                    </span>
                                </header>
                                <div class="section-content">
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-sm-6" style="padding-top:40px;">
                                                <div class="row">
                                                    <div class="col-md-10"><p style="color: red;">${appointMessage2}</p></div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="input-group" id="display-schedule">

                                                            <div id="start"></div>  <div id="end"></div>
                                                            <table class="table table-fixed">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="col-xs-2">Date</th>
                                                                        <th class="col-xs-3">Day</th>
                                                                        <th class="col-xs-3">startTime</th>
                                                                        <th class="col-xs-3">endTime</th>

                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <c:if test="${listSize > 0}">
                                                                        <c:forEach items="${scheduleList}" var="entry" varStatus="loop">
                                                                            <tr>
                                                                                <td class="col-xs-2">${entry.date}</td>
                                                                                <td class="col-xs-3">${entry.dayOfWeek}</td>
                                                                                <td class="col-xs-3">${entry.startTime}</td>
                                                                                <td class="col-xs-3"><div style="float: left;">${entry.endTime}</div><c:if test="${not empty entry.date}"><a class="myLink" href="<c:url value="/delete/${entry.date}/start/${entry.startTime}.htm" />" onclick="return deleteFunction()"><span class="glyphicon glyphicon-trash" style="padding-left: 10px;"></span></a></c:if></td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </c:if>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div><!-- /.section-content -->
                            </aside><!-- /.news-small -->
                        </div><!-- /#sidebar -->
                    </div><!-- /.col-md-4 -->
                    <!-- end SIDEBAR Content-->
                </div>
            </div>
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

        <script src="./js/prettify-1.0.min.js"></script>
        <script src="./js/base.js"></script>

    </body>
</html>