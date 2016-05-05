<%-- 
    Document   : grades
    Created on : Mar 14, 2016, 8:34:31 PM
    Author     : Dokuru
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html lang="en-US">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="http://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script>  
    
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    
    <link rel="stylesheet" type="text/css" href = https://cdn.datatables.net/1.10.11/css/dataTables.bootstrap.min.css>
    <script type="text/javascript" src="/js/jquery.dataTables.js"></script>
    
    
    <script type="text/javascript">
       <%-- $(document).ready(function () {
            $("#grades").dataTable({
                "sPaginationType": "full_numbers",
                "aLengthMenu": [[7, 10, 15, -1], [7, 10, 15, "All"]],
                "iDisplayLength": 7
            });
        });
        "bJQueryUI": true --%>
        $(document).ready(function(){
            $('#grades').DataTable({
                "sPaginationType": "full_numbers",
                "aLengthMenu": [[7, 10, 15, -1], [7, 10, 15, "All"]],
                "iDisplayLength": 7
            });
            $('#grades1').DataTable({
                "sPaginationType": "full_numbers",
                "aLengthMenu": [[7, 10, 15, -1], [7, 10, 15, "All"]],
                "iDisplayLength": 7
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
        
    </style>
    <title>Courses</title>
    
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
        <img src="${pageContext.request.contextPath}/resources/assets/img/background-city.png"  alt="background">
    </div>
</div>
<!-- end Header -->

<!-- Breadcrumb -->
<div class="container">
    <ol class="breadcrumb">
        <li class="active"><a href="${pageContext.request.contextPath}/coursesTest.htm">Back To Courses</a></li>
        <li class="active"><a href="<c:url value="/courses/${courseDetails.get('courseID')}.htm" /> ">${courseDetails.get("courseLables")}</a></li>
        <li>Grades</li>
    </ol>
</div>
<!-- end Breadcrumb -->

<!-- Page Content -->
<div id="page-content">
    <div class="container">
        <header><h1>${courseDetails.get("courseLables")}: ${courseDetails.get("courseName")}</h1></header>
        <div class="row">
            <div class="col-md-8">                
                <br />
                <section id="my-courses">
                    <ul class="nav nav-tabs" id="tabs">
                        <li><a href="<c:url value="/courses/${courseDetails.get('courseID')}.htm" /> ">${courseDetails.get("courseName")}</a></li>
                        <li><a href="<c:url value="attendance.htm"/>">Attendance</a></li>
                        <li class="active"><a href="<c:url value="grades.htm"/>">Grades</a></li>
                        <c:if test="${courseRequestedUser == 'admin'}">
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Appointments <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="<c:url value="/adminAppointmentSchedule.htm"/> ">Make/ Change Schedule</a></li>
                                    <li><a href="<c:url value="/viewAppointments.htm"/>">View Appointments</a></li>   
                                </ul>
                            </li>
                        </c:if>
                        <c:if test="${courseRequestedUser == 'student'}">
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Appointments <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="<c:url value="/makeAppointment.htm"/> ">Make Appointments</a></li>
                                    <li><a href="<c:url value="/viewAppointments.htm"/>">View Appointments</a></li>   
                                </ul>
                            </li>
                        </c:if>
                    </ul><!-- /#my-profile-tabs -->
                    <div class="tab-pane active" id="tab-my-courses">
                        <aside class="news-small" id="news-small">
                            <header><h2>Grades</h2></header>
                            <p class="errors">${errormessage}</p>
                            <div class="section-content">
                            
                            <c:set var="user" value="${gradesRequestedBy}"></c:set>
                            <c:choose>
                                <c:when test= "${user == 'student'}">
                                    <table class="table" id="grades">
                                        <thead>
                                            <tr>
                                                <th>Exam</th>
                                                <th>Exam Name</th>
                                                <th>Score</th>
                                                <th>Max Score</th>
                                                <th>Weightage</th>
                                            </tr>
                                        </thead>
                                        
                                        <tbody>
                                            <c:forEach items="${gradeList.gradesList}" var="entry" varStatus="loop">
                                            <c:set var="score" value="${entry.score}"></c:set> 
                                            <c:set var="maxScore" value="${entry.maxScore}"></c:set>
                                            <tr class="<c:choose>
                                                       <c:when test="${(score/(maxScore * 1.0))*100 >= 80}">
                                                            success
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:choose>
                                                                <c:when test="${(score/(maxScore * 1.0))*100 <= 60}">
                                                                    danger
                                                                </c:when>
                                                                <c:otherwise>
                                                                    info
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:otherwise>
                                                        </c:choose>"
                                            >
                                                <td>
                                                    Exam - ${loop.count}
                                                </td>
                                                <td>
                                                    ${entry.testName}
                                                </td>
                                                <td>
                                                    ${score}
                                                </td>
                                                <td>
                                                    ${maxScore}
                                                </td>
                                                <td>
                                                    ${entry.weightage}
                                                </td>
                                            </tr>
                                            </c:forEach> 
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <table class="table" id="grades1">
                                        <thead>
                                            <tr>
                                                <th>Exam</th>
                                                <th>Exam Name</th>
                                            </tr>
                                        </thead>
                                        
                                        <tbody>
                                        <c:forEach items="${gradeListAdmin}" var="element" varStatus="loop">
                                            <tr>
                                                <td>
                                                    Exam - ${loop.count}
                                                </td>
                                                <td>
                                                    <a href="<c:url value="grades/${element}.htm" />">${element}  <span style="padding-left: 30px;" class="glyphicon glyphicon-pencil"></span></a> 
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </c:otherwise>
                            </c:choose>
                            </div><!-- /.section-content -->
                        </aside><!-- /.news-small -->
                    </div><!-- /.tab-pane -->
                </section>
                <c:if test= "${user == 'admin'}">
                    <hr />
                    <table>
                        <tr>
                            <td>
                                <a class="myLink" style="font-size: 13px; font-weight: 200; color: #024283;" href="<c:url value="addGrades.htm" /> "><p class="btn-link"><span class="glyphicon glyphicon-plus" style="padding-right:10px;"></span>Add Grades</p></a>
                            </td>
                            <td style="padding-left: 425px;">
                                <a class="myLink" style="-webkit-transition: 0.5s ease; font-size: 13px; font-weight: 200; color: #024283;" href="<c:url value="finalGrades.htm" />"><p class="btn-success">Evaluate Final Grade</p></a>
                            </td>
                        </tr>
                    </table>    
                    <hr />
                    
                </c:if>
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