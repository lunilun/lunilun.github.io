<%@page import="com.aki.board.dao.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% System.out.println("/board/view.jsp"); //경로 확인용%>   
<%
//자바 코드
//데이터 :no 데이터 받기
String strNo = request.getParameter("no");
int no = Integer.parseInt(strNo);
BoardDAO dao = new BoardDAO();
dao.increase(no);
request.setAttribute("dto", dao.view(no));
%>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글보기</title>

<!-- 라이브러리 등록 -jQuery, Bootstrap :CDN 방식(인터넷에서 가져온 소스로 등록)-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<h1>게시판 글보기</h1>
	<table class=table>
		<tr>
			<th>번호</th>
			<td>${dto.no }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${dto.title }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${dto.writer }</td>
		</tr>
		<tr>
			<th>사용 프로그램</th>
			<td>${dto.use }</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${dto.writeDate }</td>
		</tr>
		<tr>
			<th>내용</th>
			<td><pre style="background: none; border: none;">${dto.content }</pre></td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${dto.hit }</td>
		</tr>
		<tr>
			<td colspan="2">
				<button onclick="admin2()" class="btn btn-default">수정</button>
				<script type="text/javascript">
				function admin2(){
					var admin=prompt("관리자 코드를 입력해주세요");
					if(admin=="886412") location.href="updateForm.jsp?no=${dto.no }";
				}
				</script>
				
				<button onclick="admin()" class="btn btn-default">삭제</button>
				<script type="text/javascript">
				function admin(){
					var admin=prompt("관리자 코드를 입력해주세요");
					if(admin=="886412") location.href="delete.jsp?no=${dto.no }";
				}
				</script>
				<a href="list.jsp" class="btn btn-default">리스트</a>
			</td>
		</tr>
	</table>
	
</div>
</body>
</html>