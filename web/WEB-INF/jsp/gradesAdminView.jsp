<%-- 
    Document   : gradesAdminView
    Created on : Mar 26, 2016, 4:47:46 PM
    Author     : Dokuru

    This Displays the Student Individual Test Scores and ability to edit them
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
        
        function myFunction() {
            var percent = document.getElementById("weight").value;
            
            if(isNaN(Number(percent, 10))) {
                alert("Expected weightage for the Test Results. \nValue should be in percentage (ex: 10, 5)");
            }
            else {
               
                    var size = ${studentSize};
                    
                    for(var i = 0; i < size; i++) {
                        var percents = document.getElementsByClassName("percent");
                        percents[i].value = percent;
                    }
               
            } 
        }
        
        function saveFunction() {
            var maxScores = document.getElementsByClassName("bet");
            var scores = document.getElementsByClassName("score");
            
            for(var i = 0; i < scores.length; i++) {
                if(+scores[i].value > maxScores[0].innerHTML || +scores[i].value < 0) {
                    alert("You cannot exceed max score or go below 0");
                    return false;
                }
            }
            return true;
        }
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
        <li>${testName} - Scores</li>
    </ol>
</div>
<!-- end Breadcrumb -->

<!-- Page Content -->
<div id="page-content">
    <div class="container">
        <header><h1>${courseDetails.get("courseLables")}: ${courseDetails.get("courseName")}</h1></header>
        <div class="row">
            <p style="color:red;">${message10}</p>
            <div class="col-md-11">
                <section id="my-courses">
                    <ul class="nav nav-tabs" id="tabs">
                        <li><a href="<c:url value="/courses/${courseDetails.get('courseID')}.htm" /> ">${courseDetails.get("courseName")}</a></li>
                        <li><a href="<c:url value="/attendance.htm"/>">Attendance</a></li>
                        <li class="active"><a href="<c:url value="/grades.htm"/>">Grades</a></li>
                        <%--                        <li><a href="<c:url value="/courseContent.htm"/>">Course Material</a></li>--%>
                    </ul><!-- /#my-profile-tabs -->
                    <div class="tab-pane active" id="tab-my-courses">
                        <aside class="news-small" id="news-small">
                            <header><h2>Grades</h2></header>
                            <div class="section-content table-responsive">
                            
                                <input type="text" id="weight" maxlength="4" size="4" style="width:110px; border: 1px solid #c5c5c5; background-color: #ffffff" placeholder="Weightage">

                                <button type="button" class="btn btn-primary btn-sm" onclick="myFunction()" id="opener">Change</button>

                                <form:form commandName="gradeUpdates" method="post">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Exam Name</th>
                                                <th>Student ID</th>
                                                <th>Student Name</th>
                                                <th>Weightage</th>
                                                <th>Score</th>
                                                <th>Max Score</th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach items="${gradeUpdates.gradesList}" var="entry" varStatus="loop">
                                                <c:set var="testID" value="${entry.testName}"></c:set>
                                                <c:set var="studentID" value="${entry.studentID}"></c:set>

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
                                                    <td class="filterable-cell">
                                                        ${entry.testName}
                                                    </td>
                                                    <td class="filterable-cell">
                                                        ${studentID}
                                                    </td>
                                                    <td class="filterable-cell">
                                                        ${studentNames[studentID]}
                                                    </td>
                                                    <td class="filterable-cell" style="padding-bottom: 2px;" >
                                                        <input class="percent" name="gradesList[${loop.index}].weightage" value="${entry.weightage}" readonly/>
                                                    </td>
                                                    <td class="filterable-cell" style="padding-bottom: 2px;">
                                                        <input name="gradesList[${loop.index}].testName" value="${entry.testName}" hidden />
                                                        <input name="gradesList[${loop.index}].facultyID" value="${entry.facultyID}" hidden />
                                                        <input name="gradesList[${loop.index}].studentID" value="${entry.studentID}" hidden />
                                                        <input name="gradesList[${loop.index}].courseID" value="${entry.courseID}" hidden />
                                                        <input class="score" name="gradesList[${loop.index}].score" value="${entry.score}"/>
                                                    </td>
                                                    <td class="filterable-cell">
                                                        <p class="bet" style="padding-left: 15px;">${entry.maxScore}</p>
                                                        <c:if test="${loop.index} == 0"><input type="text" id="maxsc" value="${entry.maxScore}" hidden></c:if>
                                                    </td>
                                                </tr>

                                           </c:forEach>
                                        </tbody>
                                    </table>
                                    <br/>
                                    <input type="Submit" value="Make Changes" class="btn btn-primary btn-sm" id="submit" onClick="return saveFunction()">
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