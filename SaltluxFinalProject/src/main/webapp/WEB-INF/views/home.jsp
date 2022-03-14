<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<link rel="stylesheet" href="resources/assets/css/material-kit.css">

	<!-- Material Icons -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">	
	<!-- Font Awesome Icons -->
	<script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
	<!-- CSS Files -->
	<link id="pagestyle" href="resources/assets/css/material-kit.css?v=3.0.0" rel="stylesheet" />	
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">
	<link rel="stylesheet" href="resources/assets/css/master.css">
	<!-- WordCloud BarChart -->
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/modules/wordcloud.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>
	<script src="https://code.highcharts.com/modules/export-data.js"></script>
	<script src="https://code.highcharts.com/modules/accessibility.js"></script>
	
	<link href="https://code.jquery.com/ui/1.13.0/themes/smoothness/jquery-ui.css" rel="stylesheet" />
	
	<!-- Core -->
	<script src="resources/assets/js/core/popper.min.js"></script>
	
	
<style>
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

/*WordCloud CSS*/
.highcharts-figure,
.highcharts-data-table table {
    min-width: 80%;
    max-width: 90%;
    margin: 1em auto;
}

.highcharts-data-table table {
    font-family: Verdana, sans-serif;
    border-collapse: collapse;
    border: 1px solid #ebebeb;
    margin: 10px auto;
    text-align: center;
    width: 100%;
    max-width: 500px;
}

.highcharts-data-table caption {
    padding: 1em 0;
    font-size: 1.2em;
    color: #555;
}

.highcharts-data-table th {
    font-weight: 600;
    padding: 0.5em;
}

.highcharts-data-table td,
.highcharts-data-table th,
.highcharts-data-table caption {
    padding: 0.5em;
}

.highcharts-data-table thead tr,
.highcharts-data-table tr:nth-child(even) {
    background: #f8f8f8;
}

.highcharts-data-table tr:hover {
    background: #f1f7ff;
}
/*end WordCloud*/
</style>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script	src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
<script type="text/javascript">
$(function() {
	 $( "#searchBox" ).autocomplete({
	    	source: function( request, response ) {
				$.ajax( {
					url: "api/v1/autocomplete",
					dataType: "jsonp",
					data: {
						term: request.term
					},
					success: function( data ) {
						response( data.words );
					}
				} );
			}
	    });
	$("input[name='cateogryName']").click(function() {
		console.log()
		var categoryName = $("input[name='cateogryName']:checked").val();
		var categortID = $("input[name='cateogryName']:checked").attr('id');
		var categoryKR = $("label[for='"+categortID+"']").attr('id');
		var selectRadio = categoryName + '-radio-wordcloud';
		
		$('input:radio[name=select-chart]:input[value=' + selectRadio + ']').attr("checked", true);
		$.ajax({
			url : "/api/v1/main-board/dash-board/chart/" + categoryKR,
			async : false,
			type : "POST",
			data : categoryName,
			processData : false,
			contentType : false,
			success : function(data) {
				OnSuccessWordcloud(data, categoryName);
				OnSuccessBarchart(data, categoryName);
			},
			error : function(e) {
				console.log("ERROR:", e);
				alert("fail");
			}
		});
	});
	
	//ajax = Success Wordcloud
	function OnSuccessWordcloud(data, categoryName) {
		data = JSON.parse(data);
		//WordCloud
		Highcharts.chart(categoryName + '-wordcloud', {
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
		        name: '빈도수'
		    }],
		    title: {
		        text: categoryName + ' 카테고리 워드 클라우드'
		    }
		});
		//end WordCloud
	}
	
	//ajax = Success Barchart
	function OnSuccessBarchart(data, categoryName) {
		data = JSON.parse(data);
		var result = [];
		for(var i = 0; i < 20; i++){
		    console.log(data[i].name);
		    result.push([data[i].name, parseInt(data[i].weight / 10)]);
		}
		data = result;
		//BarChart
		Highcharts.chart(categoryName + '-barchart', {
		    chart: {
		        type: 'column'
		    },
		    title: {
		        text: '키워드별 BTF 순위'
		    },
		    subtitle: {
		        text: 'Source: <a href="http://en.wikipedia.org/wiki/List_of_cities_proper_by_population">Wikipedia</a>'
		    },
		    xAxis: {
		        type: 'category',
		        labels: {
		            rotation: -45,
		            style: {
		                fontSize: '13px',
		                fontFamily: 'Verdana, sans-serif'
		            }
		        }
		    },
		    yAxis: {
		        min: 0,
		        title: {
		            text: '빈도수'
		        }
		    },
		    legend: {
		        enabled: false
		    },
		    tooltip: {
		        pointFormat: '단어가 사용된 수: <b>{point.y:.1f}건</b>'
		    },
		    series: [{
		        name: 'Population',
		        data,
		        dataLabels: {
		            enabled: true,
		            rotation: -90,
		            color: '#FFFFFF',
		            align: 'right',
		            format: '{point.y:.1f}', // one decimal
		            y: 10, // 10 pixels down from the top
		            style: {
		                fontSize: '13px',
		                fontFamily: 'Verdana, sans-serif'
		            }
		        }
		    }]
		});
	}
	//end BarChart 
});
$(function() {
	$("input[name='select-chart']").click(function() {
		var categoryName = $("input[name='cateogryName']:checked").val();
		var chart = $("input[name='select-chart']:checked").val();
		var chartRadio = categoryName + "-radio-" + chart;
		if($('input:radio[name=select-chart]:input[value=' + chartRadio + ']').is(':checked')){
			alert("a");
	        //$('#divId').hide();
	    }else{
	    	alert("b");
	        //$('#divId').show();
	    }
	});
});	

$(function setupTabs() {
	document.querySelectorAll(".tabs__button").forEach(button => {
		button.addEventListener("click", () => {
			const sideBar = button.parentElement;
			const tabsContainer = sideBar.parentElement;
			const tabNumber = button.dataset.forTab;
			const tabToActivate = tabsContainer.querySelector('.tabs__content[data-tab="' + tabNumber + '"]');
			
			sideBar.querySelectorAll(".tabs__button").forEach(button => {
				button.classList.remove("tabs__button--active");
			});
				
			tabsContainer.querySelectorAll(".tabs__content").forEach(button => {
				button.classList.remove("tabs__content--active");
			});
			
			button.classList.add("tabs__button--active");
            tabToActivate.classList.add("tabs__content--active");		
		});
	});
});
document.addEventListener("DOMContentLoadded", () => {
	setupTabs();
	document.querySelectorAll(".tabs").forEach(tabsContainer =>{
		tabsContainer.querySelector(".tabs__sidebar .tabs__button").click();
	});
});
</script>

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
						    <form action="/api/v1/news" method="GET" enctype="multipart/form-data">
					      		<input class="textbox" id="searchBox" name="question" placeholder="Search" type="text">
					      		<input type=hidden name="pageNum" value=1 >
					      		<input title="Search" value="" type="submit" class="button">
					      	</form>
						</div>
					</div>
				</div>
			</div>
		</section>

		<div>
			<h1>오늘의 이슈</h1>
			<div id="issueTabs" class="tabs">
				<div class="tabs__sidebar">
					<button class="tabs__button tabs__button--active" data-for-tab="1">Tab #1</button>
					<button class="tabs__button" data-for-tab="2">Tab #2</button>
					<button class="tabs__button" data-for-tab="3">Tab #3</button>
					<button class="tabs__button" data-for-tab="4">Tab #4</button>
					<button class="tabs__button" data-for-tab="5">Tab #5</button>
				</div>
				
				<div id="tabs__content1" class="tabs__content tabs__content--active" data-tab="1">
					<c:forEach items="${newsList1}" var="list" varStatus="status">
						<p><h3>${list.title}</h3></p>
					</c:forEach>
				</div>
				
				<div id="tabs__content2" class="tabs__content" data-tab="2">
					<c:forEach items="${newsList2}" var="list" varStatus="status">
						<p><h3>${list.title}</h3></p>
					</c:forEach>
				</div>
				<div id="tabs__content3" class="tabs__content" data-tab="3">
					<c:forEach items="${newsList3}" var="list" varStatus="status">
						<p><h3>${list.title}</h3></p>
					</c:forEach>
				</div>
				<div id="tabs__content4" class="tabs__content" data-tab="4">
					<c:forEach items="${newsList4}" var="list" varStatus="status">
						<p><h3>${list.title}</h3></p>
					</c:forEach>
				</div>
				<div id="tabs__content5" class="tabs__content" data-tab="5">
					<c:forEach items="${newsList5}" var="list" varStatus="status">
						<p><h3>${list.title}</h3></p>
					</c:forEach>
				</div>
			</div>
		</div>

		<div class="tab_container">
			<input id="tab1" type="radio" name="cateogryName" class="input_style" value="all">
			<label for="tab1" id="전체"><i class="fa fa-circle-o-notch"></i><br><span>전체</span></label>
		
			<input id="tab2" type="radio" name="cateogryName" class="input_style" value="society">
			<label for="tab2" id="사회"><i class="fa fa-pencil-square-o"></i><br><span>사회</span></label>
		
			<input id="tab3" type="radio" name="cateogryName" class="input_style" value="entertainment">
			<label for="tab3" id="연예"><i class="fa fa-bar-chart-o"></i><br><span>연예</span></label>
		
			<input id="tab4" type="radio" name="cateogryName" class="input_style" value="economy">
			<label for="tab4" id="경제"><i class="fa fa-folder-open-o"></i><br><span>경제</span></label>
		
			<input id="tab5" type="radio" name="cateogryName" class="input_style" value="politics">
			<label for="tab5" id="정치"><i class="fa fa-envelope-o"></i><br><span>정치</span></label>
			
			<input id="tab6" type="radio" name="cateogryName" class="input_style" value="sport">
			<label for="tab6" id="스포츠"><i class="fa fa-envelope-o"></i><br><span>스포츠</span></label>
			
			<input id="tab7" type="radio" name="cateogryName" class="input_style" value="culture">
			<label for="tab7" id="문화"><i class="fa fa-envelope-o"></i><br><span>문화</span></label>
			
			<input id="tab8" type="radio" name="cateogryName" class="input_style" value="global">
			<label for="tab8" id="국제"><i class="fa fa-envelope-o"></i><br><span>국제</span></label>
			
			<input id="tab9" type="radio" name="cateogryName" class="input_style" value="it">
			<label for="tab9" id="it"><i class="fa fa-envelope-o"></i><br><span>IT</span></label>
			
			<section id="content1" class="tab-content input_style">
			<input type="radio" id="all-radio-wordcloud" name="select-chart" value="all-radio-wordcloud">워드클라우드
			<input type="radio" id="all-radio-barchart" name="select-chart" value="all-radio-barchart">막대그래프
				<h3>전체 카테고리</h3>
		      	<figure class="highcharts-figure">
				    <div id="all-wordcloud" style="height:600px;"></div>
				    <div id="all-barchart" style="height:700px;"></div>
				    <p class="highcharts-description">
			        	카테고리의 워드클라우드 설명글
				    </p>
				</figure>
			</section>
		
			<section id="content2" class="tab-content input_style">
			<input type="radio" id="society-radio-wordcloud" name="select-chart" value="society-radio-wordcloud">워드클라우드
			<input type="radio" id="society-radio-barchart" name="select-chart" value="society-radio-barchart">막대그래프
				<h3>사회 카테고리</h3>
		      	<figure class="highcharts-figure">
				    <div id="society-wordcloud" style="height:600px;"></div>
				    <div id="society-barchart" style="height:700px;"></div>
				    <p class="highcharts-description">
			        	카테고리의 워드클라우드 설명글
				    </p>
				</figure>
			</section>
		
			<section id="content3" class="tab-content input_style">
			<input type="radio" id="entertainment-radio-wordcloud" name="select-chart" value="entertainment-radio-wordcloud">워드클라우드
			<input type="radio" id="entertainment-radio-barchart" name="select-chart" value="entertainment-radio-barchart">막대그래프
				<h3>연예 카테고리</h3>
		      	<figure class="highcharts-figure">
				    <div id="entertainment-wordcloud" style="height:600px;"></div>
				    <div id="entertainment-barchart" style="height:700px;"></div>
				    <p class="highcharts-description">
			        	카테고리의 워드클라우드 설명글
				    </p>
				</figure>
			</section>
		
			<section id="content4" class="tab-content input_style">
			<input type="radio" id="economy-radio-wordcloud" name="select-chart" value="economy-radio-wordcloud">워드클라우드
			<input type="radio" id="economy-radio-barchart" name="select-chart" value="economy-radio-barchart">막대그래프
				<h3>경제 카테고리</h3>
		      	<figure class="highcharts-figure">
				    <div id="economy-wordcloud" style="height:600px;"></div>
				    <div id="economy-barchart" style="height:700px;"></div>
				    <p class="highcharts-description">
			        	카테고리의 워드클라우드 설명글
				    </p>
				</figure>
			</section>
		
			<section id="content5" class="tab-content input_style">
			<input type="radio" id="politics-radio-wordcloud" name="select-chart" value="politics-radio-wordcloud">워드클라우드
			<input type="radio" id="politics-radio-barchart" name="select-chart" value="politics-radio-barchart">막대그래프
				<h3>정치 카테고리</h3>
		      	<figure class="highcharts-figure">
				    <div id="politics-wordcloud" style="height:600px;"></div>
				    <div id="politics-barchart" style="height:700px;"></div>
				    <p class="highcharts-description">
			        	카테고리의 워드클라우드 설명글
				    </p>
				</figure>
			</section>
			
			<section id="content6" class="tab-content input_style">
			<input type="radio" id="sport-radio-wordcloud" name="select-chart" value="sport-radio-wordcloud">워드클라우드
			<input type="radio" id="sport-radio-barchart" name="select-chart" value="sport-radio-barchart">막대그래프
				<h3>스포츠 카테고리</h3>
		      	<figure class="highcharts-figure">
				    <div id="sport-wordcloud" style="height:600px;"></div>
				    <div id="sport-barchart" style="height:700px;"></div>
				    <p class="highcharts-description">
			        	카테고리의 워드클라우드 설명글
				    </p>
				</figure>
			</section>
			
			<section id="content7" class="tab-content input_style">
			<input type="radio" id="culture-radio-wordcloud" name="select-chart" value="culture-radio-wordcloud">워드클라우드
			<input type="radio" id="culture-radio-barchart" name="select-chart" value="culture-radio-barchart">막대그래프
				<h3>문화 카테고리</h3>
		      	<figure class="highcharts-figure">
				    <div id="culture-wordcloud" style="height:600px;"></div>
				    <div id="culture-barchart" style="height:700px;"></div>
				    <p class="highcharts-description">
			        	카테고리의 워드클라우드 설명글
				    </p>
				</figure>
			</section>
			
			<section id="content8" class="tab-content input_style">
			<input type="radio" id="global-radio-wordcloud" name="select-chart" value="global-radio-wordcloud">워드클라우드
			<input type="radio" id="global-radio-barchart" name="select-chart" value="global-radio-barchart">막대그래프
				<h3>국제 카테고리</h3>
		      	<figure class="highcharts-figure">
				    <div id="global-wordcloud" style="height:600px;"></div>
				    <div id="global-barchart" style="height:700px;"></div>
				    <p class="highcharts-description">
			        	카테고리의 워드클라우드 설명글
				    </p>
				</figure>
			</section>
			
			<section id="content9" class="tab-content input_style">
			<input type="radio" id="it-radio-wordcloud" name="select-chart" value="it-radio-wordcloud">워드클라우드
			<input type="radio" id="it-radio-barchart" name="select-chart" value="it-radio-barchart">막대그래프
				<h3>IT 카테고리</h3>
		      	<figure class="highcharts-figure">
					<div id="it-wordcloud" style="height:600px;"></div>
				    <div id="it-barchart" style="height:700px;"></div>
				    <p class="highcharts-description">
			        	카테고리의 워드클라우드 설명글
				    </p>
				</figure>
			</section>
		</div>
	</div>
	<jsp:include page="../views/include/footer.jsp"></jsp:include>
</body>

</html>
