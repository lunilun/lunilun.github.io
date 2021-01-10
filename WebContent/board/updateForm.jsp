<%@page import="com.aki.board.dao.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% System.out.println("/board/updateForm.jsp");%>
<%
String strNo = request.getParameter("no");
int no = Integer.parseInt(strNo);
BoardDAO dao = new BoardDAO();
request.setAttribute("dto", dao.view(no));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글 수정</title>

<!-- 라이브러리 등록 -jQuery, Bootstrap :CDN 방식(인터넷에서 가져온 소스로 등록)-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script type="text/javascript">
$(function(){
	$("#cancelBtn").click(function(){
		history.back();
	});
});

</script>

</head>
<body>
<div class="container">
	<h1>게시판 글 수정</h1>
	<form action="update.jsp" method="post">
		<div class="form-group">
  			<label for="no">번호:</label>
  			<input type="text" class="form-control" id="no" name="no"
  			readonly="readonly" value="${dto.no }">
		</div>
		<div class="form-group">
  			<label for="title">제목:</label>
  			<input type="text" class="form-control" id="title" name="title"
  			value="${dto.title }">
		</div>
		<div class="form-group">
  			<label for="writer">작성자:</label>
  			<input type="text" class="form-control" id="writer" name="writer"
  			readonly="readonly" value="${dto.writer }">
		</div>
		<div class="form-group">
  			<label for="use">사용 프로그램:</label>
  			<input type="text" class="form-control" id="use" name="use"
  			value="${dto.use }">
		</div>
		<div class="form-group">
  			<label for="content">내용:</label>
  			<textarea class="form-control" rows="5" id="content" name="content">${dto.content }</textarea>
		</div>
		
		<button class="btn btn-default">저장</button>\
		<%-- <a href="update.jsp?no=${dto.no }" class="btn btn-default">저장</a> --%>
		<button class="btn btn-default" type="reset">다시 입력</button>
		<button class="btn btn-default" type="button" id="cancelBtn">취소</button>
	</form>
</div>
</body>
</html>