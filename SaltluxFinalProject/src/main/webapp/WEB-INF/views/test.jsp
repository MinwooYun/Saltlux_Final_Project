<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
<c:forEach var="item" items="${newsList}">
	제목 : ${item.title}<p>
	본문 : ${item.contents}<p>
	날짜 : ${item.newsDate}<p>
	이미지 URL : ${item.imageUrl}<p>
	하이라이트 : ${item.fragments } ...<p>
	
	<p>
	<p>
	<p>
	
</c:forEach>
</body>
</html>