<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Result Page</title>
	<link rel="apple-touch-icon" sizes="76x76" href="resources/assets/img/apple-icon.png">
	<link rel="icon" type="image/png" href="resources/assets/img/favicon.png">
	<!--     Fonts and icons     -->
	<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700" />
	
	<!-- Nucleo Icons -->
	<link href="resources/assets/css/nucleo-icons.css" rel="stylesheet" />
	<link href="resources/assets/css/nucleo-svg.css" rel="stylesheet" />
	<link rel="stylesheet" href="resources/assets/css/master.css">

	<!-- Material Icons -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">	
	<!-- Font Awesome Icons -->
	<script src="https://kit.fontawesome.com/c1ecb15d05.js" crossorigin="anonymous"></script>
	<!-- CSS Files -->
	<link id="pagestyle" href="resources/assets/css/material-kit.css?v=3.0.0" rel="stylesheet" />	
	<link rel="stylesheet" href="resources/assets/css/material-kit.css">
	
	<!-- WordCloud BarChart -->
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/modules/wordcloud.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>
	<script src="https://code.highcharts.com/modules/export-data.js"></script>
	<script src="https://code.highcharts.com/modules/accessibility.js"></script>
	
	<link href="https://code.jquery.com/ui/1.13.0/themes/smoothness/jquery-ui.css" rel="stylesheet" />
	
<style type="text/css">
	.leftContents {
		height:auto; 
		width:80%; 
		float:left; 
		padding:100px 20px;
	}
	.newsContents {
		float : center;
		padding : 30px 10px;
		margin : auto;
	}
	.newsContentsList {
		padding-left : 50px;
		margin : 50px;
	}
	.thumbnail {
		width : 150px;
		height : 150px;
		margin-right : 20px;
		float : left;
		align : absbottom;
	}
	.contentTitle {
		font-style: italic ; 
		font-weight: bold; 
		font-size: 1.5em;
		line-height: 1.0em; 
		color: navy;
		font-family: arial;
	}
	.pageInfo{
	  	list-style : none;
	  	display: inline-block;
	    margin: 50px 0 0 100px;  	
  	}
	.pageInfo li{
		float: left;
		font-size: 20px;
		margin-left: 18px;
		padding: 7px;
		font-weight: 500;
	}
	.textbox {
	  outline: 0;
	  height: 100%;
	  width: 90%;
	  font-size: 30px;
	  line-height: 80px;
	  padding: 0 16px;
	  background-color: rgba(255, 255, 255, 0.8);
	  color: #212121;
	  border: 0;
	  float: left;
	  -webkit-border-radius: 4px 0 0 4px;
	  border-radius: 4px 0 0 4px;
	  outline: 0;
	  background-color: #FFF;
	}
	
	.button {
	  outline: 0;
	  background: none;
	  background-color: rgba(38, 50, 56, 0.8);
	  float: left;
	  height: 80px;
	  width: 80px;
	  text-align: center;
	  line-height: 42px;
	  border: 0;
	  color: #FFF;
	  font: normal normal normal 14px/1 FontAwesome;
	  font-size: 16px;
	  text-rendering: auto;
	  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
	  -webkit-transition: background-color .4s ease;
	  transition: background-color .4s ease;
	  -webkit-border-radius: 0 4px 4px 0;
	  border-radius: 0 4px 4px 0;
	  background-color: rgba(0, 150, 136, 0.8);
	}

</style>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script	src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		url : "/risings",
		type : "POST",
		data:{
			
		},
		success: function(data){
			for(i=0; i<data.length; i++){
                    $("#rightKeyword").append("<p><h4>" + (i+1) + ". " + data[i].keyword + "</h4></p>");
               }
		},
		error : function(e) {
			console.log("ERROR:", e);
			alert("fail");
		}
	})
});
$(function() {
	data = <%= request.getAttribute("jsonArray")%>;
	
	Highcharts.chart('Highcharts', {
	    accessibility: {
	        screenReaderSection: {
	            beforeChartFormat: '<h5>{chartTitle}</h5>' +
	                '<div>{chartSubtitle}</div>' +
	                '<div>{chartLongdesc}</div>' +
	                '<div>{viewTableButton}</div>'
	        }
	    },
	    series: [{
	        type: 'wordcloud',
	        data,
	        name: 'Occurrences'
	    }],
	    title: {
	        text: ''
	    }
	});
	
	
	$("#searchBox").autocomplete({
		source : function(request, response) {
			$.ajax({
				url : "/api/v1/autocomplete",
				dataType : "jsonp",
				data: {
					term: request.term
				},
				success : function(data) {
					response(data.words);
				}
			});
		}
	});
});
</script>

</head>
<body>
<div style="height:100px">
<jsp:include page="../views/include/header.jsp"></jsp:include>
</div>
	<div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6" style="resize: vertical;">

		<section class="pt-3 pb-4" id="count-stats">
			<div class="container">
				<div class="row">
					<div class="col-lg-9 mx-auto py-3">
						<div class="row text-center py-2 mt-3">
						<img src="resources/assets/img/logo1.jpg" style="display: block; margin: 0px auto; width:400px;">
							<form action="/news" method="GET" enctype="multipart/form-data">
								<input class="textbox" value="${question}" style="border: 1px solid #32AAA0;" id="searchBox" name="question" placeholder="Search" type="text"> 
								<input type=hidden name="pageNum" value=1 >
								<input title="Search" value="" type="submit" class="button">
							</form>
						</div>
					</div>
				</div>
			</div>

			<div class="leftContents" style="border-radius: 20px; background-color: #F7F7F7;">
				<!-- contents -->
				<div class="newsContents">
					<c:forEach items="${newsList}" var="news" varStatus="status">
						<c:if test="${status.index%3==0 }">
							<div class="card-group">
						</c:if>
						<div class="card" data-animation="true"
							style="margin: 10px; margin-bottom: 50px;">
							<div
								class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
								<a class="d-block blur-shadow-image" style="text-align: center;">
									<img src="${news.thumbnailUrl}" alt="img-blur-shadow"
									class="img-fluid shadow border-radius-lg"
									style="height: 200px;">
								</a>
								<div class="colored-shadow"
									style="background-image: url(&quot;https://demos.creative-tim.com/test/material-dashboard-pro/assets/img/products/product-1-min.jpg&quot;);"></div>
							</div>
							<div class="card-body text-center">
								<div class="d-flex mt-n6 mx-auto">
									<div style="width: 50%">
										<h6 style="text-align: center;">${news.press}</h6>
									</div>
									<div style="width: 50%">
										<h6 style="text-align: center;">${news.newsDate}</h6>
									</div>
								</div>
								<h5 class="font-weight-normal mt-3">
									<a class="myBtn_multi" href="javascript:;">${news.title}</a>
								</h5>
								<p style="font-size: 11px">${news.fragments} ...</p>

								<!-- Modal -->


								<!-- The Modal -->
								<div class="modal modal_multi">
									<!-- Modal content -->
									<div class="modal-content" style="width: 900px;">
										<div class="modal-header">
											<h3>${news.title}</h3>
											<span class="close close_multi">×</span>
										</div>
										<div class="modal-body">
											<img src="${news.imageUrl}"
												style="width: 600px; height: 600px;">
											<% pageContext.setAttribute("replaceChar", "\n"); %>
											<p style="text-align: left; line-height: 250%; margin: 80px;">${fn:replace(news.contents, replaceChar, "<br/>")}</p>
										</div>
										<div class="modal-footer">
											<h3>${news.press}</h3>
										</div>
									</div>
								</div>
								<!-- end Modal -->
							</div>
						</div>
						<c:if test="${status.index%3==2 || status.last }">
				</div>
				</c:if>
				</c:forEach>
				<div class="row justify-space-between py-2">
					<div class="col-lg-4 mx-auto">
						<ul class="pagination pagination-primary m-4">
							<c:if test="${pageNum > 1}">
								<li class="page-item"><a class="page-link" href="/news?question=${question}&pageNum=${pageNum - 1}" aria-label="Previous"> 
									<span aria-hidden="true"><i class="material-icons" aria-hidden="true">chevron_left</i></span></a>
								</li>
							</c:if>

							<c:forEach begin="${pageStart}" end="${pageEnd}"
								varStatus="status">
								<c:choose>
									<c:when test="${status.index > pageTotal}">
										<c:set var="doneLoop" value="true" />
									</c:when>
									<c:when test="${pageNum == status.index}">
										<li class="page-item active"><a class="page-link" href="/news?question=${question}&pageNum=${status.index}">${status.index}</a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link" href="/news?question=${question}&pageNum=${status.index}">${status.index}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<c:if test="${pageNum < pageTotal}">
								<li class="page-item"><a class="page-link"
									href="/news?question=${question}&pageNum=${pageNum + 1}"
									aria-label="Next"> <span aria-hidden="true"><i
											class="material-icons" aria-hidden="true">chevron_right</i></span>
								</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
			</div>
			<!-- /end contents -->
			<div id="rightContents" style="width: 20%; float: right; text-align:center; margin-top:100px;">
				<h4>-키워드 랭킹-</h4>
				<div id="rightKeyword" style="text-align: center;"></div>
			</div>			
		</section>
		<figure class="highcharts-figure">
		<h3 style="text-align:center; margin-top:50px;">"${question} 과(와) 관련된 키워드</h3>
			    <div id="Highcharts" style="height:600px;"></div>
			</figure>
	</div>
	<jsp:include page="../views/include/footer.jsp"></jsp:include>
<script>
$(document).ready(function(){
	// Get the modal

    var modalparent = document.getElementsByClassName("modal_multi");

    // Get the button that opens the modal

    var modal_btn_multi = document.getElementsByClassName("myBtn_multi");

    // Get the <span> element that closes the modal
    var span_close_multi = document.getElementsByClassName("close_multi");

    // When the user clicks the button, open the modal
    function setDataIndex() {

        for (i = 0; i < modal_btn_multi.length; i++)
        {
            modal_btn_multi[i].setAttribute('data-index', i);
            modalparent[i].setAttribute('data-index', i);
            span_close_multi[i].setAttribute('data-index', i);
        }
    }



    for (i = 0; i < modal_btn_multi.length; i++)
    {
        modal_btn_multi[i].onclick = function() {
            var ElementIndex = this.getAttribute('data-index');
            modalparent[ElementIndex].style.display = "block";
        };

        // When the user clicks on <span> (x), close the modal
        span_close_multi[i].onclick = function() {
            var ElementIndex = this.getAttribute('data-index');
            modalparent[ElementIndex].style.display = "none";
        };

    }

    window.onload = function() {

        setDataIndex();
    };

    window.onclick = function(event) {
        if (event.target === modalparent[event.target.getAttribute('data-index')]) {
            modalparent[event.target.getAttribute('data-index')].style.display = "none";
        }

        // OLD CODE
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };

//Get the modal

    var modal = document.getElementById('myModal');

//Get the button that opens the modal
    var btn = document.getElementById("myBtn");

//Get the <span> element that closes the modal
    var span = modal.getElementsByClassName("close")[0]; // Modified by dsones uk

//When the user clicks on the button, open the modal

    btn.onclick = function() {

        modal.style.display = "block";
    }

//When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }
});
</script>

</body>
</html>
