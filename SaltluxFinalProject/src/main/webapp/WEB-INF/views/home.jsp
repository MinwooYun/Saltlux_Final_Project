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
	
	<!-- WordCloud BarChart -->
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/modules/wordcloud.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>
	<script src="https://code.highcharts.com/modules/export-data.js"></script>
	<script src="https://code.highcharts.com/modules/accessibility.js"></script>
	
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
<script type="text/javascript">
$(function() {
	$("input[name='cateogryName']").click(function() {
		console.log()
		var categoryName = $("input[name='cateogryName']:checked").val();
		var categortID = $("input[name='cateogryName']:checked").attr('id');
		var categoryKR = $("label[for='"+categortID+"']").attr('id');
		var selectRadio = categoryName + '-radio-wordcloud';
		
		$('input:radio[name=select-chart]:input[value=' + selectRadio + ']').attr("checked", true);
		$.ajax({
			url : "/api/v1/main-board/dash-board/wordcloud/" + categoryKR,
			async : true,
			type : "POST",
			data : categoryName,
			processData : false,
			contentType : false,
			success : function(data) {
				OnSuccessWordcloud(data, categoryName);
			},
			error : function(e) {
				console.log("ERROR:", e);
				alert("fail");
			}
		});
		
		$.ajax({
			url : "/api/v1/main-board/dash-board/barchart/" + categoryKR,
			async : true,
			type : "POST",
			data : categoryName,
			processData : false,
			contentType : false,
			success : function(data) {
				alert("aaaaa");
				OnSuccessBarchart(data, categoryName);
			},
			error : function(e) {
				console.log("ERROR:", e);
				alert("fail");
			}
		});
	});
	
	//ajax = Success 워드클라우드 함수
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
		
		//Barchart
		function OnSuccessBarchart(data, categoryName) {
			//BarChart
			Highcharts.chart(categoryName + '-barchart', {
			    chart: {
			        type: 'column'
			    },
			    title: {
			        text: 'World\'s largest cities per 2017'
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
			            text: 'Population (millions)'
			        }
			    },
			    legend: {
			        enabled: false
			    },
			    tooltip: {
			        pointFormat: 'Population in 2017: <b>{point.y:.1f} millions</b>'
			    },
			    series: [{
			        name: 'Population',
			        data: [
			            ['Shanghai', 24.2],
			            ['Beijing', 20.8],
			            ['Karachi', 14.9],
			            ['Shenzhen', 13.7],
			            ['Guangzhou', 13.1],
			            ['Istanbul', 12.7],
			            ['Mumbai', 12.4],
			            ['Moscow', 12.2],
			            ['São Paulo', 12.0],
			            ['Delhi', 11.7],
			            ['Kinshasa', 11.5],
			            ['Tianjin', 11.2],
			            ['Lahore', 11.1],
			            ['Jakarta', 10.6],
			            ['Dongguan', 10.6],
			            ['Lagos', 10.6],
			            ['Bengaluru', 10.3],
			            ['Seoul', 9.8],
			            ['Foshan', 9.3],
			            ['Tokyo', 9.3]
			        ],
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
	}
	//end function()
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
				    <div id="all-wordcloud"></div>
				    <div id="all-barchart"></div>
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
				    <div id="society-wordcloud"></div>
				    <div id="society-barchart"></div>
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
				    <div id="entertainment-wordcloud"></div>
				    <div id="entertainment-barchart"></div>
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
				    <div id="economy-wordcloud"></div>
				    <div id="economy-barchart"></div>
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
				    <div id="politics-wordcloud"></div>
				    <div id="politics-barchart"></div>
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
				    <div id="sport-wordcloud"></div>
				    <div id="sport-barchart"></div>
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
				    <div id="culture-wordcloud"></div>
				    <div id="culture-barchart"></div>
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
				    <div id="global-wordcloud"></div>
				    <div id="global-barchart"></div>
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
					<div id="it-wordcloud"></div>
				    <div id="it-barchart"></div>
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
