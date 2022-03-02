<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<link rel="apple-touch-icon" sizes="76x76" href="resources/assets/img/apple-icon.png">
	<link rel="icon" type="image/png" href="resources/assets/img/favicon.png">
	
	<title>Material Kit 2 by Creative Tim</title>
	
	<jsp:include page="../views/include/header.jsp"></jsp:include>

	<!-- Font Awesome Icons -->
	<script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
	<!-- CSS Files -->
	<link id="pagestyle" href="resources/assets/css/material-kit.css?v=3.0.0" rel="stylesheet" />	

</head>

<body class="index-page bg-gray-200">

	<header class="header-2">
		<div class="page-header min-vh-75 relative" style="background-image: url('resources/assets/img/mainBG.jpg')">
			<span class="mask bg-gradient-primary opacity-0"></span>
			<div class="container">
				<div class="row">
					<div class="col-lg-7 text-center mx-auto">
						<!-- 배경화면에 글 입력 -->
					</div>
				</div>
			</div>
		</div>
	</header>

	<div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6">

		<section class="pt-3 pb-4" id="count-stats">
			<div class="container">
				<div class="row">
					<div class="col-lg-9 mx-auto py-3">
						<div class="row text-center py-2 mt-3">
						  <div class="col-4 mx-auto">
						    <div class="input-group input-group-dynamic mb-4">
							    <form action="/results" method="GET" enctype="multipart/form-data">
						      		<span class="input-group-text"><i class="fas fa-search" aria-hidden="true"></i></span>
						      		<input class="form-control" name="search" placeholder="Search" type="text">
						      	</form>
						    </div>
						  </div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<jsp:include page="../views/category.jsp"></jsp:include>
	</div>

	<jsp:include page="../views/include/footer.jsp"></jsp:include>
</body>

</html>
