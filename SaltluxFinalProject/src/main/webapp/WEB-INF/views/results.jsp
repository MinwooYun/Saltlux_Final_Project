<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<link rel="apple-touch-icon" sizes="76x76" href="resources/assets/img/apple-icon.png">
	<link rel="icon" type="image/png" href="resources/assets/img/favicon.png">
	<!--     Fonts and icons     -->
	<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700" />
	
	<!-- Nucleo Icons -->
	<link href="resources/assets/css/nucleo-icons.css" rel="stylesheet" />
	<link href="resources/assets/css/nucleo-svg.css" rel="stylesheet" />

	<!-- Material Icons -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">	
	<!-- Font Awesome Icons -->
	<script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
	<!-- CSS Files -->
	<link id="pagestyle" href="resources/assets/css/material-kit.css?v=3.0.0" rel="stylesheet" />	
	
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
		border:1px solid orange; 
		float:left; 
		padding:100px 80px;
	}
	.newsContents {
		float : center;
		padding : 30px 10px;
		margin : auto;
		border : 1px solid blue;
		height : 500px;
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
$(function() {
	$("#searchBox").autocomplete({
		source : function(request, response) {
			$.ajax({
				url : "autocomplete",
				dataType : "jsonp",
				data : {
					term : request.term
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
	<div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6"
		style="resize: vertical;">

		<section class="pt-3 pb-4" id="count-stats">
			<div class="container">
				<div class="row">
					<div class="col-lg-9 mx-auto py-3">
						<div class="row text-center py-2 mt-3">
							<form action="/results" method="GET"
								enctype="multipart/form-data">
								<input class="textbox" id="searchBox" name="search"
									placeholder="Search" type="text"> <input title="Search"
									value="" type="submit" class="button">
							</form>
						</div>
					</div>
				</div>
			</div>

			<div class="leftContents">
				<div class="newsContents">
					<div class="card-group">
						<div class="card" data-animation="true">
							<div
								class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
								<a class="d-block blur-shadow-image"> <img
									src="https://demos.creative-tim.com/test/material-dashboard-pro/assets/img/products/product-1-min.jpg"
									alt="img-blur-shadow" class="img-fluid shadow border-radius-lg">
								</a>
								<div class="colored-shadow"
									style="background-image: url(&quot;https://demos.creative-tim.com/test/material-dashboard-pro/assets/img/products/product-1-min.jpg&quot;);"></div>
							</div>
							<div class="card-body text-center">
								<div class="d-flex mt-n6 mx-auto">
									<a class="btn btn-link text-primary ms-auto border-0"
										data-bs-toggle="tooltip" data-bs-placement="bottom"
										title="Refresh"> <i class="material-icons text-lg">refresh</i>
									</a>
									<button class="btn btn-link text-info me-auto border-0"
										data-bs-toggle="tooltip" data-bs-placement="bottom"
										title="Edit">
										<i class="material-icons text-lg">edit</i>
									</button>
								</div>
								<h5 class="font-weight-normal mt-3">
									<a href="javascript:;">Cozy 5 Stars Apartment</a>
								</h5>
								<p class="mb-0">The place is close to Barceloneta Beach and
									bus stop just 2 min by walk and near to "Naviglio" where you
									can enjoy the main night life in Barcelona.</p>
							</div>
						</div>
						<div class="card" data-animation="true">
							<div
								class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
								<a class="d-block blur-shadow-image"> <img
									src="https://demos.creative-tim.com/test/material-dashboard-pro/assets/img/products/product-1-min.jpg"
									alt="img-blur-shadow" class="img-fluid shadow border-radius-lg">
								</a>
								<div class="colored-shadow"
									style="background-image: url(&quot;https://demos.creative-tim.com/test/material-dashboard-pro/assets/img/products/product-1-min.jpg&quot;);"></div>
							</div>
							<div class="card-body text-center">
								<div class="d-flex mt-n6 mx-auto">
									<a class="btn btn-link text-primary ms-auto border-0"
										data-bs-toggle="tooltip" data-bs-placement="bottom"
										title="Refresh"> <i class="material-icons text-lg">refresh</i>
									</a>
									<button class="btn btn-link text-info me-auto border-0"
										data-bs-toggle="tooltip" data-bs-placement="bottom"
										title="Edit">
										<i class="material-icons text-lg">edit</i>
									</button>
								</div>
								<h5 class="font-weight-normal mt-3">
									<a href="javascript:;">Cozy 5 Stars Apartment</a>
								</h5>
								<p class="mb-0">The place is close to Barceloneta Beach and
									bus stop just 2 min by walk and near to "Naviglio" where you
									can enjoy the main night life in Barcelona.</p>
							</div>
						</div>
						<div class="card" data-animation="true">
							<div
								class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
								<a class="d-block blur-shadow-image"> <img
									src="https://demos.creative-tim.com/test/material-dashboard-pro/assets/img/products/product-1-min.jpg"
									alt="img-blur-shadow" class="img-fluid shadow border-radius-lg">
								</a>
								<div class="colored-shadow"
									style="background-image: url(&quot;https://demos.creative-tim.com/test/material-dashboard-pro/assets/img/products/product-1-min.jpg&quot;);"></div>
							</div>
							<div class="card-body text-center">
								<div class="d-flex mt-n6 mx-auto">
									<a class="btn btn-link text-primary ms-auto border-0"
										data-bs-toggle="tooltip" data-bs-placement="bottom"
										title="Refresh"> <i class="material-icons text-lg">refresh</i>
									</a>
									<button class="btn btn-link text-info me-auto border-0"
										data-bs-toggle="tooltip" data-bs-placement="bottom"
										title="Edit">
										<i class="material-icons text-lg">edit</i>
									</button>
								</div>
								<h5 class="font-weight-normal mt-3">
									<a href="javascript:;">Cozy 5 Stars Apartment</a>
								</h5>
								<p class="mb-0">The place is close to Barceloneta Beach and
									bus stop just 2 min by walk and near to "Naviglio" where you
									can enjoy the main night life in Barcelona.</p>
							</div>
						</div>
					</div>
				</div>
				<br>
				
				<!-- 페이징 -->
				<div class="row justify-space-between py-2">
					<div class="col-lg-4 mx-auto">
						<ul class="pagination pagination-primary m-4">
							<!-- 이전페이지 버튼 -->
							<c:if test="${pageMaker.prev}">
								<li class="page-item"><a class="material-icons"
									href="${pageMaker.startPage-1}">Previous</a></li>
							</c:if>
	
							<!-- 각 번호 페이지 버튼 -->
							<c:forEach var="num" begin="${pageMaker.startPage}"
								end="${pageMaker.endPage}">
								<li class="page-item ${pageMaker.cri.pageNum == num ? "active":"" }"><a
									class="page-link"
									href="localhost:8080/result?search=${search}&page=${num}">${num}</a></li>
							</c:forEach>
	
							<!-- 다음페이지 버튼 -->
							<c:if test="${pageMaker.next}">
								<li class="page-item"><a class="material-icons"
									href="${pageMaker.endPage + 1 }">Next</a></li>
							</c:if>
						</ul>
					</div>
				</div>
				
			</div>

			<div style="height: 500px; width: 20%; border: 1px solid green; float: right;">
			</div>

			<form id="moveForm" method="get">
				<input type="hidden" name="pageNum"
					value="${pageMaker.cri.pageNum }"> <input type="hidden"
					name="amount" value="${pageMaker.cri.amount }"> <input
					type="hidden" name="keyword" value="${pageMaker.cri.keyword }">
				<input type="hidden" name="type" value="${pageMaker.cri.type }">
			</form>
			<!-- /페이징 -->
		</section>
	</div>
	<jsp:include page="../views/include/footer.jsp"></jsp:include>
	
<script>
$(document).ready(function(){
	$(".pagination a").on("click", function(e){
		e.preventDefault();
		moveForm.find("input[name='pageNum']").val($(this).attr("href"));
		moveForm.attr("action", "/results");
		moveForm.submit();
		
	});	
});
</script>

</body>
</html>