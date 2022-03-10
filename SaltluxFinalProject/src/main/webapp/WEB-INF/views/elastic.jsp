<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<body>
	<div>
		<form action="/elastictest" method="POST">
			<input id="word" name="word" value="${word}" type="text">
		</form>
	</div>
	<c:forEach var="ela" items="${elasticList}">
			<br>
			<p>${ela}</p>
			<br>
	</c:forEach>

</body>
<script>
$(function() {
	$("#word").keypress(function(e) {
		var word = $("#word");
		$ajax({
			url : "/elastictest",
			async : true,
			type : "POST",
			data : word,
			processData : false,
			contentType : false
		});
		$("#word").submit();
	});
});
</script>
</html>