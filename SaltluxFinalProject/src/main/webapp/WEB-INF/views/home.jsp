<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<link rel="apple-touch-icon" sizes="76x76" href="resources/assets/img/apple-icon.png">
	<link rel="icon" type="image/png" href="resources/assets/img/favicon.png">
	
	<title>Material Kit 2 by Creative Tim</title>
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

<style>
.textbox {
  outline: 0;
  height: 100%;
  width: 70%;
  line-height: 42px;
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
  height: 42px;
  width: 42px;
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

@import url('https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css');

*,
*:after,
*:before {
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

h1 {
	color: #ccc;
	text-align: center;
}

a {
  color: #ccc;
  text-decoration: none;
  outline: none;
}

/*Fun begins*/
.tab_container {
	width: 100%;
	padding: 5px;
	margin: 0 auto;
	padding-top: 70px;
	position: relative;
}

.input_style {
  clear: both;
  padding-top: 10px;
  display: none;
}

label {
  font-weight: 700;
  font-size: 18px;
  display: block;
  float: left;
  width: 10.8%;
  padding: 1.0em;
  color: #757575;
  cursor: pointer;
  text-decoration: none;
  text-align: center;
  background: #f0f0f0;
}

#tab1:checked ~ #content1,
#tab2:checked ~ #content2,
#tab3:checked ~ #content3,
#tab4:checked ~ #content4,
#tab5:checked ~ #content5,
#tab6:checked ~ #content6,
#tab7:checked ~ #content7,
#tab8:checked ~ #content8,
#tab9:checked ~ #content9{
  display: block;
  padding: 20px;
  background: #fff;
  color: #999;
  border-bottom: 2px solid #f0f0f0;
}

.tab_container .tab-content p,
.tab_container .tab-content h3 {
  -webkit-animation: fadeInScale 0.7s ease-in-out;
  -moz-animation: fadeInScale 0.7s ease-in-out;
  animation: fadeInScale 0.7s ease-in-out;
}
.tab_container .tab-content h3  {
  text-align: center;
}

.tab_container [id^="tab"]:checked + label {
  background: #fff;
  box-shadow: inset 0 3px #0CE;
}

.tab_container [id^="tab"]:checked + label .fa {
  color: #0CE;
}

label .fa {
  font-size: 1.3em;
  margin: 0 0.4em 0 0;
}

/*Media query*/
@media only screen and (max-width: 900px) {
  label span {
    display: none;
  }
  
  .tab_container {
    width: 100%;
  }
}

/*Content Animation*/
@keyframes fadeInScale {
  0% {
  	transform: scale(0.9);
  	opacity: 0;
  }
  
  100% {
  	transform: scale(1);
  	opacity: 1;
  }
}

.no_wrap {
  text-align:center;
  color: #0ce;
}
.link {
  text-align:center;
}
</style>
</head>
<jsp:include page="../views/include/header.jsp"></jsp:include>
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
						    <form action="/results" method="GET" enctype="multipart/form-data">
					      		<input class="textbox" name="search" placeholder="Search" type="text">
					      		<input title="Search" value="" type="submit" class="button">
					      	</form>
						</div>
					</div>
				</div>
			</div>
		</section>
		<div class="tab_container">
			<input id="tab1" type="radio" name="tabs" class="input_style">
			<label for="tab1"><i class="fa fa-circle-o-notch"></i><br><span><a href="localhost:8080/api/v1/main-board/all">전체</a></span></label>
		
			<input id="tab2" type="radio" name="tabs" class="input_style">
			<label for="tab2"><i class="fa fa-pencil-square-o"></i><br><span>사회</span></label>
		
			<input id="tab3" type="radio" name="tabs" class="input_style">
			<label for="tab3"><i class="fa fa-bar-chart-o"></i><br><span>연예</span></label>
		
			<input id="tab4" type="radio" name="tabs" class="input_style">
			<label for="tab4"><i class="fa fa-folder-open-o"></i><br><span>경제</span></label>
		
			<input id="tab5" type="radio" name="tabs" class="input_style">
			<label for="tab5"><i class="fa fa-envelope-o"></i><br><span>정치</span></label>
			
			<input id="tab6" type="radio" name="tabs" class="input_style">
			<label for="tab6"><i class="fa fa-envelope-o"></i><br><span>스포츠</span></label>
			
			<input id="tab7" type="radio" name="tabs" class="input_style">
			<label for="tab7"><i class="fa fa-envelope-o"></i><br><span>문화</span></label>
			
			<input id="tab8" type="radio" name="tabs" class="input_style">
			<label for="tab8"><i class="fa fa-envelope-o"></i><br><span>국제</span></label>
			
			<input id="tab9" type="radio" name="tabs" class="input_style">
			<label for="tab9"><i class="fa fa-envelope-o"></i><br><span>IT</span></label>
		
			<section id="content1" class="tab-content input_style">
				<h3>전체 카테고리</h3>
				<div>
					<input type="radio" name="all-category" checked>워드클라우드
					<input type="radio" name="all-category">꺽은선 그래프
				</div>
		      	<p>https://fontawesome.com/v4/icons/</p>
		      	
			</section>
		
			<section id="content2" class="tab-content input_style">
				<h3>사회 카테고리</h3>
		      	<p>text2</p>
			</section>
		
			<section id="content3" class="tab-content input_style">
				<h3>연예 카테고리</h3>
		      	<p>text3</p>
			</section>
		
			<section id="content4" class="tab-content input_style">
				<h3>경제 카테고리</h3>
		      	<p>text4</p>
			</section>
		
			<section id="content5" class="tab-content input_style">
				<h3>정치 카테고리</h3>
		      	<p>text 5</p>
			</section>
			
			<section id="content6" class="tab-content input_style">
				<h3>스포츠 카테고리</h3>
		      	<p>text 6</p>
			</section>
			
			<section id="content7" class="tab-content input_style">
				<h3>문화 카테고리</h3>
		      	<p>text 7</p>
			</section>
			
			<section id="content8" class="tab-content input_style">
				<h3>국제 카테고리</h3>
		      	<p>text 8</p>
			</section>
			
			<section id="content9" class="tab-content input_style">
				<h3>IT 카테고리</h3>
		      	<p>text 9</p>
			</section>
		</div>
	</div>
	<jsp:include page="../views/include/footer.jsp"></jsp:include>
</body>

</html>
