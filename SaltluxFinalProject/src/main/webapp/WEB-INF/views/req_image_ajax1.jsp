<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="../views/include/header.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("input[type=button]").click(function() {
				console.log()
				var form = $("#fileUploadForm")[0];
				var form_data = new FormData(form);
				$("input[type=button]").prop("disabled", true);
				$.ajax({
					url : "http://127.0.0.1:5000/test_img",
					async : true,
					type : "POST",
					data : form_data,
					processData : false,
					contentType : false,
					success : function(data) {
						console.log(data)
						$("#result").text(data.param)
						img_src = "data:image/png;base64," + data.file
						$("#result").append("<img src='" + img_src + "'>");
						$("input[type=button]").prop("disabled", false);
					},
					error : function(e) {
						console.log("ERROR:", e);
						alert("fail");
					}
				});
			})
		});
	</script>
</head>

<body>
	<!-- enctype="multipart/form-data" 사진을 넘겨줄때 사용 -->
	<form id="fileUploadForm" method="post"	enctype="multipart/form-data">
		데이터<input type="text" name="data"><p>
		file<input type="file" name="file"><p>
		<!--<input type="submit" value="요청">-->
		<input type="button" value="비동기 요청">
		<input type="reset" value="초기화">
	</form>
	<div id="result">결과가 표시될 곳</div>
	
	<jsp:include page="../views/include/footer.jsp"></jsp:include>
</body>
</html>