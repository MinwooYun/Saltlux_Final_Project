<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8" />
<style type="text/css">
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
		width: 90%;
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
	  width: 9.7%;
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
	#tab9:checked ~ #content9,
	#tab10:checked ~ #content10 {
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
<body>
<div class="tab_container">
	<input id="tab1" type="radio" name="tabs" class="input_style" checked>
	<label for="tab1"><i class="fa fa-code"></i><br><span>tab1</span></label>

	<input id="tab2" type="radio" name="tabs" class="input_style">
	<label for="tab2"><i class="fa fa-pencil-square-o"></i><br><span>tab2</span></label>

	<input id="tab3" type="radio" name="tabs" class="input_style">
	<label for="tab3"><i class="fa fa-bar-chart-o"></i><br><span>tab3</span></label>

	<input id="tab4" type="radio" name="tabs" class="input_style">
	<label for="tab4"><i class="fa fa-folder-open-o"></i><br><span>tab4</span></label>

	<input id="tab5" type="radio" name="tabs" class="input_style">
	<label for="tab5"><i class="fa fa-envelope-o"></i><br><span>tab5</span></label>
	
	<input id="tab6" type="radio" name="tabs" class="input_style">
	<label for="tab6"><i class="fa fa-envelope-o"></i><br><span>tab6</span></label>
	
	<input id="tab7" type="radio" name="tabs" class="input_style">
	<label for="tab7"><i class="fa fa-envelope-o"></i><br><span>tab7</span></label>
	
	<input id="tab8" type="radio" name="tabs" class="input_style">
	<label for="tab8"><i class="fa fa-envelope-o"></i><br><span>tab8</span></label>
	
	<input id="tab9" type="radio" name="tabs" class="input_style">
	<label for="tab9"><i class="fa fa-envelope-o"></i><br><span>tab9</span></label>
	
	<input id="tab10" type="radio" name="tabs" class="input_style">
	<label for="tab10"><i class="fa fa-envelope-o"></i><br><span>tab10</span></label>

	<section id="content1" class="tab-content input_style">
		<h3>Headline 1</h3>
      	<p>https://fontawesome.com/v4/icons/</p>
	</section>

	<section id="content2" class="tab-content input_style">
		<h3>Headline 2</h3>
      	<p>text2</p>
	</section>

	<section id="content3" class="tab-content input_style">
		<h3>Headline 3</h3>
      	<p>text3</p>
	</section>

	<section id="content4" class="tab-content input_style">
		<h3>Headline 4</h3>
      	<p>text4</p>
	</section>

	<section id="content5" class="tab-content input_style">
		<h3>Headline 5</h3>
      	<p>text 5</p>
	</section>
	
	<section id="content6" class="tab-content input_style">
		<h3>Headline 6</h3>
      	<p>text 6</p>
	</section>
	
	<section id="content7" class="tab-content input_style">
		<h3>Headline 7</h3>
      	<p>text 7</p>
	</section>
	
	<section id="content8" class="tab-content input_style">
		<h3>Headline 8</h3>
      	<p>text 8</p>
	</section>
	
	<section id="content9" class="tab-content input_style">
		<h3>Headline 9</h3>
      	<p>text 9</p>
	</section>
	
	<section id="content10" class="tab-content input_style">
		<h3>Headline 10</h3>
      	<p>text 10</p>
	</section>
</div>
</body>