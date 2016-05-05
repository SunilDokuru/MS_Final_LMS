<%-- 
    Document   : addGrades
    Created on : Mar 27, 2016, 11:41:58 PM
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
        function myFunction() {
            
            var name = document.getElementById("tName").value;
            var percent = document.getElementById("weight").value;
            var maxScore = document.getElementById("max-score").value;
            
            var listNames = document.getElementsByClassName("list");
            var totalWeightage = ${totalWeightage};
            
            
            if((name === null || name === '') || (percent === null || percent === '') || (maxScore === null || maxScore === '')) {
                alert("Testname, Weightage, and Max Score are required");   
            }
            else if(isNaN(Number(percent, 10)) || isNaN(Number(maxScore, 10))) {
                alert("Weightage and max Score for the Test Results. should be in decimal Format");
            } else if(+totalWeightage + +percent > 100) {
                alert("Total weightage of previous tests is: " + totalWeightage+" %\nYou can add a score whose weightage is " + (100 - totalWeightage)+" %");
            }
            else {
                document.getElementById("check").value=maxScore;
                
                    for(var i = 0; i < listNames.length; i++) {
                        var local = listNames[i].value;
                
                        if(local === name) {
                            alert(name + " A test with the same name exists.\nIf you want you use the same name delete the test or override the values for the same");
                            return false;
                        }
                    
                    }
                    var size = ${listSize};
                    for(i=0;i<size;i++) {
                        var names = document.getElementsByClassName("name");
                        var percents = document.getElementsByClassName("percent");
                        var scores = document.getElementsByClassName("score");
                        var maxScores = document.getElementsByClassName("maxScore");
                        
                        names[i].value=name;
                        percents[i].value=percent;
                        scores[i].readOnly=false;
                        maxScores[i].value=maxScore;
                }
                
                document.getElementById("tName").readOnly=true;
                document.getElementById("weight").readOnly=true;
                document.getElementById("max-score").readOnly=true;
            }
        }
        
        function saveFunction() {
            var test = document.getElementById("check").value;
            test = +test;
            
            if((test === null || test === '')) {
                alert("Testname, Weightage, and Max Score are required.\nEnter the values and save them");   
                return false;
            } else {
                
                var scores = document.getElementsByClassName("score");
                
                for(var i = 0; i < scores.length; i++) {
                    
                    if(scores[i].value === "" || scores[i].value === null) {
                        alert("Enter Scores, as they cannot be empty\nAccepts only real values");
                        return false;
                    } else if(isNaN(Number(+scores[i].value, 10))) {
                        alert("Accepts only double values");
                        return false;
                    }
                    else if(+scores[i].value < 0 || +scores[i].value > test) {
                        alert("Scores cannot be negative or greater than maxScore");
                        return false;
                    }
                }
            }
            return true;
        }
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
            height: 45px;

            /*text-align: left;*/
        }

        tbody {
            height: 120px;
            overflow-y: auto;
        }

        thead {
            /* fallback */
        }


        tbody td, thead th {
            width: 20.0%;
            float: left;
        }
        
        td {
            height: 50px;
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
        <li class="active"><a href="${pageContext.request.contextPath}/grades.htm">Grades</a></li>
        <li>Add Test Scores</li>
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
                        <li><a href="<c:url value="/attendance.htm"/>">Attendance</a></li>
                        <li class="active"><a href="#">Grades</a></li>
                        <%--                        <li><a href="<c:url value="/courseContent.htm"/>">Course Material</a></li>--%>
                    </ul><!-- /#my-profile-tabs -->
                    <div class="tab-pane active" id="tab-my-courses">
                        <aside class="news-small" id="news-small">
                            <header><h2>Add Grades</h2></header>
                            <p class="errors">${flashMessage}</p>
                            <div class="section-content table-responsive">
                                <p id="display"></p>
                                <c:forEach items="${gradeListAdmin}" var="entry">
                                    <input type="text" value="${entry}" class="list" hidden>
                                </c:forEach>
                                    <input type="text" id="check" hidden>
                                    <p id="demo"></p>
                                <table>
                                    <tr>
                                        <td style="padding-right 20px;">
                                            <input type="text" id="tName" maxlength="15" size="15" style="width:150px; border: 1px solid #c5c5c5; background-color: #ffffff" placeholder="TestName">
                                        </td>
                                        <td>
                                            <input type="number" id="weight" min="1" max="100" style="width:110px; border: 1px solid #c5c5c5; background-color: #ffffff" placeholder="Weightage">
                                            <br /><span style="font-size: 10px; color: black;" class="btn-warning">In Percentage (ex: 10, 20)</span>
                                        </td>
                                        <td>
                                            <input type="number" id="max-score" min="1" max="100" style="width:110px; border: 1px solid #c5c5c5; background-color: #ffffff" placeholder="Max Score">
                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-primary btn-sm" onclick="myFunction()" id="opener">Save</button>
                                        </td>
                                    </tr>
                                </table>
                       
                                <form:form commandName="addGrades" method="post">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Test Name</th>
                                                <th>Student ID</th>
                                                <th>Student Name</th>
                                                <th>Score</th>
                                                <th>Max-Score</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${addGrades.gradesList}" var="entry" varStatus="loop">
                                                <c:set value="${entry.studentID}" var="studentID"></c:set>    
                                                <tr>
                                                        <td class="filterable-cell">
                                                            <input name="gradesList[${loop.index}].testName" type="text" class="name" readonly style="border: 0px; border-color: #ffffff; background-color: #ffffff"> 
                                                            <input name="gradesList[${loop.index}].weightage" type="text" class="percent" style="border: 0px; border-color: #ffffff; background-color: #ffffff" hidden> 
                                                        </td>
                                                        <td class="filterable-cell">
                                                            ${studentID}
                                                        </td>
                                                        <td class="filterable-cell">
                                                            ${studentNamesList[studentID]}
                                                        </td>
                                                        <td class="filterable-cell" style="padding-bottom: 2px;">
                                                            <%--<input name="gradesList[${loop.index}].testName" value="${entry.testName}" hidden />--%>
                                                            <input name="gradesList[${loop.index}].facultyID" value="${entry.facultyID}" hidden />
                                                            <input name="gradesList[${loop.index}].studentID" value="${entry.studentID}" hidden />
                                                            <input name="gradesList[${loop.index}].courseID" value="${entry.courseID}" hidden />
                                                            <input name="gradesList[${loop.index}].score" min="0" max="100" type="number" class="score" placeholder="score" readonly style="border: 1px solid #c5c5c5; background-color: #ffffff">
                                                        </td>
                                                        <form:errors path="gradesList[${loop.index}].score" cssClass="errors"></form:errors>
                                                        <td>
                                                            <input type="number" class="maxScore" id="maxScore" name="gradesList[${loop.index}].maxScore" style="border: 1px solid #c5c5c5; background-color: #ffffff" readonly>
                                                        </td>
                                                </tr>
                                           </c:forEach>
                                        </tbody>
                                    </table>
                                    <br/>
                                    <input type="Submit" value="Add Record" class="btn btn-primary btn-sm" id="submit" onClick="return saveFunction()">
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