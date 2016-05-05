<%-- 
    Document   : finalGrades
    Created on : Apr 12, 2016, 6:58:31 PM
    Author     : Dokuru
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
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
    
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Lato" />
    <link rel="stylesheet" type="text/css" href = "http://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css">
    
    <script type="text/javascript">
        $(document).ready(function () {
            $("#grades").dataTable({
                "sPaginationType": "full_numbers",
                "aLengthMenu": [[7, 10, 15, -1], [7, 10, 15, "All"]],
                "iDisplayLength": 7
            });
        });
        <%--"bJQueryUI": true --%>
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
        
        table {
            width: 100%;
        }

        thead, tbody, tr, td, th { display: block; }

        tr:after {
            content: ' ';
            display: block;
            visibility: hidden;
            clear: both;
        }

        thead th {
            height: 50px;

            /*text-align: left;*/
        }

        tbody {
            height: 180px;
            overflow-y: auto;
        }

        thead {
            /* fallback */
        }


        tbody td, thead th {
            width: 16.0%;
            float: left;
        }
        
        td {
            height: 45px;
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
        <li class="active"><a href="${pageContext.request.contextPath}/grades.htm">Grades List</a></li>
        <li>Final Grades Evaluation</li>
    </ol>
</div>
<!-- end Breadcrumb -->

<!-- Page Content -->
<div id="page-content">
    <div class="container">
        <header><h1>${courseDetails.get("courseLables")}: ${courseDetails.get("courseName")}</h1></header>
        <div class="row">
            <p style="color:red;">${message10}</p>
            <div class="col-md-12 col-sm-12 col-xs-9">
                <section id="my-courses">
                    <ul class="nav nav-tabs" id="tabs">
                        <li><a href="<c:url value="/courses/${courseDetails.get('courseID')}.htm" /> ">${courseDetails.get("courseName")}</a></li>
                        <li><a href="<c:url value="/attendance.htm"/>">Attendance</a></li>
                        <li class="active"><a href="<c:url value="/grades.htm"/>">Grades</a></li>
                        <%--                        <li><a href="<c:url value="/courseContent.htm"/>">Course Material</a></li>--%>
                    </ul><!-- /#my-profile-tabs -->
                    <div class="tab-pane active" id="tab-my-courses">
                        <aside class="news-small" id="news-small">
                            <header><h2>Final Grades</h2></header>
                            <div class="section-content table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Course Name</th>
                                            <th>Student ID</th>
                                            <th>Student Name</th>
                                            <th>Final Score</th>
                                            <th>Grade</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach items="${FinalGrades}" var="entry" varStatus="loop">
                                            <c:set var="grade" value="${entry.grade}"></c:set>
                                            <tr class="<c:choose>
                                                    <c:when test="${grade == 'A'}">
                                                            success
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${grade == 'B'}">
                                                                    success
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:choose>
                                                                    <c:when test="${grade == 'C'}">info</c:when>
                                                                    <c:otherwise>
                                                                        <c:choose>
                                                                            <c:when test="${grade == 'D'}">danger</c:when>
                                                                            <c:otherwise>danger</c:otherwise>
                                                                        </c:choose>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>"
                                                >
                                                <td class="filterable-cell">
                                                    ${entry.courseName}
                                                </td>
                                                <td class="filterable-cell">
                                                    ${entry.studentID}
                                                </td>
                                                <td class="filterable-cell">
                                                    ${entry.studentName}
                                                </td>
                                                <td class="filterable-cell">
                                                    ${entry.finalScore}
                                                </td>
                                                <td class="filterable-cell">
                                                    ${grade}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <br/>
                            </div><!-- /.section-content -->
                        </aside><!-- /.news-small -->
                    </div><!-- /.tab-pane -->
                </section>
            </div>
        </div><!-- /.row -->
        <div class="row">
            <h2>Final Grades Evaluation Method Explained</h2>
            <div class="col-md-10">
                <p>Final Grades are calculated based on all the test scores. Once grades evaluated cannot be changed directly.
                                <br />To change the grades you have to edit the test scores and commit those changes. Once the new scores are committed
                                <br />you can re-evaluate the grades. These new grades would override the previous calculated scores.
                                <br />
                                <br />Method used to evaluate the final scores:</p>
                                <br /><p style="padding-left: 20px;">
                                    Calculate the total weights of all the individual tests.
                                    <br />Calculate the percent of score attained in each test, using the formula (weight*score*100)/(maximum Score) for each test
                                    <br />Sum up all the test scores for each user.
                                    <br />Multiply the resulting final score with 100 and divide with the total Weight.
                                    <br />The above step is performed to ensure all final grades are evaluated to 100%.
                                </p>
            </div>
        </div>
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