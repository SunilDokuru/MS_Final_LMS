<%-- 
    Document   : attendanceSheet
    Created on : Mar 28, 2016, 9:18:12 PM
    Author     : Dokuru
--%>

<%@page import="java.util.HashMap"%>
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
        <li class="active"><a href="${pageContext.request.contextPath}/attendance.htm">Back To Scheduled Days</a></li>
        <li>Attendance</li>
    </ol>
</div>
<!-- end Breadcrumb -->

<!-- Page Content -->
<div id="page-content">
    <div class="container">
        <header><h1>${courseDetails.get("courseLables")}: ${courseDetails.get("courseName")}</h1></header>
        <div class="row">
            <div class="col-md-10">
                <section id="my-courses">
                    <ul class="nav nav-tabs" id="tabs">
                        <li><a href="<c:url value="/courses/${courseDetails.get('courseID')}.htm" /> ">${courseDetails.get("courseName")}</a></li>
                        <li class="active"><a href="<c:url value='/attendance.htm' />">Attendance</a></li>
                        <li><a href="<c:url value='/grades.htm' />">Grades</a></li>
                        
                    </ul><!-- /#my-profile-tabs -->
                    <div class="tab-pane active" id="tab-my-courses">
                        <aside class="news-small" id="news-small">
                            <header><h2>Attendance for ${requestedDate}</h2></header>
                            <p class="errors">${errormessage}</p>
                            <div class="section-content">
                                <form:form method = "post" commandName="attendanceData">
                                    <table class="table table-fixed">
                                        <thead>
                                            <tr>
                                                <th class="col-xs-2">Date</th>
                                                <th class="col-xs-2">Student ID</th>
                                                <th class="col-xs-3">Student Name</th>
                                                <th class="col-xs-2">Status</th>
                                                <th class="col-xs-3">Note</th>
                                            </tr>
                                        </thead>
                                    
                                        <tbody>
                                            
                                        <c:forEach items="${attendanceData.userResponse}" var="entry" varStatus="loop">
                                            <c:set var="studentID" value="${entry.studentID}"></c:set>
                                            <tr>
                                                <td class="col-xs-2">
                                                    ${entry.date}
                                                </td>
                                                <td class="col-xs-2">
                                                    ${studentID}
                                                </td>
                                                <td class="col-xs-3">
                                                    <%
                                                        HashMap<Integer, String> studentList = (HashMap)request.getSession().getAttribute("studentNames");
                                                        out.print(studentList.get(Integer.parseInt((String)pageContext.getAttribute("studentID"))));
                                                    %>
                                                </td>
                                                <td class="col-xs-2">
                                                    <c:set var="index" value="${loop.count}"></c:set>
                                                    <input name="userResponse[${loop.index}].studentID" value="${studentID}" hidden />
                                                    <input name="userResponse[${loop.index}].facultyID" value="${entry.facultyID}" hidden />
                                                    <input name="userResponse[${loop.index}].date" value="${entry.date}" hidden />
                                                    <input name="userResponse[${loop.index}].day" value="${entry.day}" hidden />
                                                   
                                                    <c:choose>
                                                        <c:when test="${entry.attendance == 'none'}">
                                                            <select name="userResponse[${loop.index}].attendance" required>
                                                                <option value = "" selected="selected">!--Select--!</option>
                                                                <option value = "absent">Absent</option>
                                                                <option value = "present">Present</option>
                                                            </select>
                                                            <form:errors cssClass="errors" path="userResponse[${loop.index}].attendance"></form:errors>
                                                        </c:when>

                                                        <c:otherwise>
                                                            <c:choose>

                                                                <c:when test="${entry.attendance == 'absent'}">
                                                                    <select name="userResponse[${loop.index}].attendance">
                                                                        <option value = "none">!--Select--!</option>
                                                                        <option value = "absent" selected="selected">Absent</option>
                                                                        <option value = "present">Present</option>
                                                                    </select>
                                                                    <form:errors cssClass="errors" path="userResponse[${loop.index}].attendance"></form:errors>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <select name="userResponse[${loop.index}].attendance">
                                                                        <option value = "none">!--Select--!</option>
                                                                        <option value = "absent">Absent</option>
                                                                        <option value = "present" selected="selected">Present</option>
                                                                    </select>
                                                                    <form:errors cssClass="errors" path="userResponse[${loop.index}].attendance"></form:errors>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="col-xs-3"><textarea name="userResponse[${loop.index}].notes" readonly style="background: transparent; height: 15px;">${entry.notes}</textarea></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table> <br />
                                    <input type="submit" value="Save" class="btn btn-primary btn-sm">
                                </form:form>
                            </div><!-- /.section-content -->
                        </aside><!-- /.news-small -->
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