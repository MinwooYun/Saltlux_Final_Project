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
						      		<input class="form-control" id="search" name="search" placeholder="Search" type="text">
						      	</form>
						    </div>
						  </div>
						</div>
					</div>
				</div>
			</div>
			<div class="leftContents">
				<div class="newsContents">
					<c:forEach var="test" items="${testList}">
						<div class="newsContentsList" style="border:1px solid red;">
							<img class="thumbnail" src="https://img1.daumcdn.net/thumb/S95x77ht.u/?fname=https%3A%2F%2Ft1.daumcdn.net%2Fnews%2F202103%2F01%2Fsportalkr%2F20210301234842723khxl.jpg&scode=media">
							<br>
							<p>${test.productName}</p>
							<br>
							<p>${test.productCategoryName}</p>
							<br>
						</div>
					</c:forEach>
				</div>
			</div>
			<div style="height:500px; width:20%; border:1px solid green; float:right;">
			</div>
		</section>
		
	</div>
	<jsp:include page="../views/include/footer.jsp"></jsp:include>
</body>
</html>