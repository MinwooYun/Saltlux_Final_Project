<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<link rel="apple-touch-icon" sizes="76x76" href="resources/assets/img/apple-icon.png">
	<link rel="icon" type="image/png" href="resources/assets/img/favicon.png">
	
	<title>Material Kit 2 by Creative Tim</title>
	
	<jsp:include page="../views/include/header.jsp"></jsp:include>
	
	<!--     Fonts and icons     -->
	<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700" />
	<!-- Nucleo Icons -->
	<link href="resources/assets/css/nucleo-icons.css" rel="stylesheet" />
	<link href="resources/assets/css/nucleo-svg.css" rel="stylesheet" />
	<!-- Font Awesome Icons -->
	<script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
	<!-- Material Icons -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">
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
						      <span class="input-group-text"><i class="fas fa-search" aria-hidden="true"></i></span>
						      <input class="form-control" placeholder="Search" type="text" >
						    </div>
						  </div>
						</div>
					</div>
				</div>
			</div>
		</section>


		<!--   Core JS Files   -->
		<script src="resources/assets/js/plugins/perfect-scrollbar.min.js"></script>

		<!--  Plugin for TypedJS, full documentation here: https://github.com/inorganik/CountUp.js -->
		<script src="resources/assets/js/plugins/countup.min.js"></script>

		<script src="resources/assets/js/plugins/choices.min.js"></script>

		<script src="resources/assets/js/plugins/prism.min.js"></script>
		<script src="resources/assets/js/plugins/highlight.min.js"></script>

		<!--  Plugin for Parallax, full documentation here: https://github.com/dixonandmoe/rellax -->
		<script src="resources/assets/js/plugins/rellax.min.js"></script>
		<!--  Plugin for TiltJS, full documentation here: https://gijsroge.github.io/tilt.js/ -->
		<script src="resources/assets/js/plugins/tilt.min.js"></script>
		<!--  Plugin for Selectpicker - ChoicesJS, full documentation here: https://github.com/jshjohnson/Choices -->
		<script src="resources/assets/js/plugins/choices.min.js"></script>


		<!--  Plugin for Parallax, full documentation here: https://github.com/wagerfield/parallax  -->
		<script src="resources/assets/js/plugins/parallax.min.js"></script>


		<!-- Control Center for Material UI Kit: parallax effects, scripts for the example pages etc -->
		<!--  Google Maps Plugin    -->

		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDTTfWur0PDbZWPr7Pmq8K3jiDp0_xUziI"></script>
		<script src="resources/assets/js/material-kit.min.js?v=3.0.0" type="text/javascript"></script>
		<script type="text/javascript">

  if (document.getElementById('state1')) {
    const countUp = new CountUp('state1', document.getElementById("state1").getAttribute("countTo"));
    if (!countUp.error) {
      countUp.start();
    } else {
      console.error(countUp.error);
    }
  }
  if (document.getElementById('state2')) {
    const countUp1 = new CountUp('state2', document.getElementById("state2").getAttribute("countTo"));
    if (!countUp1.error) {
      countUp1.start();
    } else {
      console.error(countUp1.error);
    }
  }
  if (document.getElementById('state3')) {
    const countUp2 = new CountUp('state3', document.getElementById("state3").getAttribute("countTo"));
    if (!countUp2.error) {
      countUp2.start();
    } else {
      console.error(countUp2.error);
    };
  }
</script>

<jsp:include page="../views/include/footer.jsp"></jsp:include>
</body>

</html>
