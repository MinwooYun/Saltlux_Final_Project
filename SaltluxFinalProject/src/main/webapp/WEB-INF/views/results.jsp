<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	
	<!-- Font Awesome Icons -->
	<script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
	<!-- CSS Files -->
	<link id="pagestyle" href="resources/assets/css/material-kit.css?v=3.0.0" rel="stylesheet" />	
	
<style type="text/css">
	.leftContents {
		height:auto; 
		width:80%; 
		border:1px solid orange; 
		float:left; 
		padding:15px 80px;
	}
	.newsContents {
		float : center;
		padding : 10px;
		margin : auto;
		border : 1px solid blue;
		height : 100%;
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
</style>
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
						  <div class="col-4 mx-auto">
						    <div class="input-group input-group-dynamic mb-4">
							    <form action="/results" method="GET" enctype="multipart/form-data">
						      		<span class="input-group-text"><i class="fas fa-search" aria-hidden="true"></i></span>
						      		<input class="form-control" id="search" name="search" value="${search}" placeholder="Search" type="text">
						      	</form>
						    </div>
						  </div>
						</div>
					</div>
				</div>
			</div>
			<div class="leftContents">
				<div class="newsContents">
					<c:forEach var="news" items="${newsList}">
						<div class="newsContentsList" style="border:1px solid red;">
							<img class="thumbnail" src="${news.thumbnailURL}">
							<br>
							<p class="contentTitle">${news.title}</p>
							<br>
							<p>${news.category}</p>
							<br>
						</div>
					</c:forEach>
				</div>
				
				<!-- 페이징 -->
				<div class="row justify-space-between py-2" >
			        <div class="col-lg-4 mx-auto">
			        	<ul class="pagination pagination-primary m-4">
			        		<!-- 이전페이지 버튼 -->
			        		<c:if test="${pageMaker.prev}">
			                    <li class="page-item"><a class="material-icons" href="${pageMaker.startPage-1}">Previous</a></li>
			                </c:if>
			                
			                 <!-- 각 번호 페이지 버튼 -->
			                <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
			                    <li class="page-item ${pageMaker.cri.pageNum == num ? "active":"" }"><a class="page-link" href="localhost:8080/result?search=${search}&page=${num}">${num}</a></li>
			                </c:forEach>
			                
			                <!-- 다음페이지 버튼 -->
			                <c:if test="${pageMaker.next}">
			                    <li class="page-item"><a class="material-icons" href="${pageMaker.endPage + 1 }">Next</a></li>
			                </c:if> 
		                </ul>
			        </div>
			    </div>

				<form id="moveForm" method="get">	
					<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
					<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
					<input type="hidden" name="keyword" value="${pageMaker.cri.keyword }">	
					<input type="hidden" name="type" value="${pageMaker.cri.type }">	
				</form>
				<!-- /페이징 -->
				
				
			</div>
			<div style="height:500px; width:20%; border:1px solid green; float:right;">
			</div>
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