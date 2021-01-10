<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% System.out.println("/board/writeForm.jsp");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>

<!-- 라이브러리 등록 -jQuery, Bootstrap :CDN 방식(인터넷에서 가져온 소스로 등록)-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<h1>게시판 글쓰기</h1>
	<form action="write.jsp" method="post">
		<div class="form-group">
  			<label for="title">제목:</label>
  			<input type="text" class="form-control" id="title" name="title" required="required"
  			placeholder="제목을 5글자 이상으로 입력하세요">
		</div>
		<div class="form-group">
  			<label for="writer">작성자:</label>
  			<input type="text" class="form-control" id="writer" name="writer">
		</div>
		<div class="form-group">
  			<label for="use">사용 프로그램:</label>
  			<input type="text" class="form-control" id="use" name="use">
		</div>
		<div class="form-group">
  			<label for="content">내용:</label>
  			<textarea class="form-control" rows="5" id="content" name="content" required="required"></textarea>
		</div>
		<button class="btn btn-default">등록</button>
	</form>
</div>
</body>
</html>