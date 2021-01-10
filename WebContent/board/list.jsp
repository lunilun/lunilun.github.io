<%@page import="java.util.ArrayList"%>
<%@page import="com.aki.board.dto.BoardDTO"%>
<%@page import="com.aki.board.dao.BoardDAO"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- jstl등록 --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% System.out.println("/board/list.jsp");%>

<!--  DB에서 데이터를 가져오는 자바 프로그램 -->
<%
//여기가 자바프로그램
//DAO를 생성, 호출해서 사용
BoardDAO dao = new BoardDAO();
String strPg = request.getParameter("no");//list.jsp?pg=?
String strSh = request.getParameter("search");
int rowSize = 10; //한페이지에 보여줄 글의 수
int no=1; //페이지 , list.jsp로 넘어온 경우 , 초기값 =1
if(strPg != null){ //list.jsp?pg=2
    no = Integer.parseInt(strPg); //.저장
}
int from = (no * rowSize) - (rowSize-1); //(1*10)-(10-1)=10-9=1 //from
int to=(no * rowSize); //(1*10) = 10 //to
List<BoardDTO> list = dao.list(from,to,strSh);
// java와jsp에서 공통으로 사용하는 데이터 영역에 해당되는 객체 -requset를 주로 사용
request.setAttribute("list", list);
int total = dao.getTotal(); //총 게시물 수
int allPage = (int) Math.ceil(total/(double)rowSize); //페이지수
//int totalPage = total/rowSize + (total%rowSize==0?0:1);
int block = 10; //한페이지에 보여줄  범위 << [1] [2] [3] [4] [5] [6] [7] [8] [9] [10] >>
int fromPage = ((no-1)/block*block)+1;  //보여줄 페이지의 시작
int toPage = ((no-1)/block*block)+block; //보여줄 페이지의 끝
if(toPage> allPage){ // 예) 20>17
    toPage = allPage;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Study Process</title>

<!-- 라이브러리 등록 -jQuery, Bootstrap :CDN 방식(인터넷에서 가져온 소스로 등록)-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<style type="text/css">
html,body{
	height: 100%;
}
body{
	margin: 0;
}
#body{
	min-height: 100%;
}
.container{
	min-height : 80%;
	padding-bottom :1.9em; 
}
.jumbotron{
	margin-top:-1.9em;
	align-content: flex-end;
	text-align:center;
	background-color: #ffffff;
}
.dataRow:hover{
	/* rgb 3자리(각 0~15) 색상을 16단계로 나눔. 16*16*16가지 색 */
	/* rgb 6자리(각(두자리씩 차지함) 0~255) 색상을 256단계로 나눔. 256*256*256가지 색 */
	background: #ddd;
	cursor: pointer;
}
</style>

<script type="text/javascript">
$(function() { //onready와 동일(html의 body부분이 다 로딩되면 동작되도록)
	//데이터 한줄 클릭하면 글보기로 이동되는 이벤트 처리
	$(".dataRow").click(function() {
		var no = $(this).find(".no").text();
		//location = "view.jsp";
		location.href = "view.jsp?no="+no;
	});
});
</script>
</head>
<body>
<div class="container">
	<div style="float: left;">
		<a href="index.jsp"><h1>My Study Process</h1></a> 
	</div>
	<div style="float: right;">
		<form class="navbar-form navbar-left" action="list.jsp">
			<div class="form-group">
				<!-- <select name="keyField">
					<option value="title">제목</option>
					<option value="use">프로그램</option>
				</select> -->
		    	<input type="text" class="form-control" placeholder="Search" name="search">
		    </div>
		   	<button type="submit" class="btn btn-default">검색</button>
	    </form>	
		<!-- <img alt="" src="..\images\cat.png" align="right" width="100px" height="100px"> -->
	</div>
	 <%-- ${list  } --%>
	<table class="table">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>사용 프로그램</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
	    <%-- page(list) request(dto) ==> jsp에서만 동작 --%>
		<c:forEach items="${list }" var="dto">
		<!--  반복문의 시작  -->
		<tr class="dataRow">
			<td class="no">${dto.no }</td>
			<td>${dto.title }</td>
			<td>${dto.writer }</td>
			<td>${dto.use }</td>
			<td>${dto.writeDate }</td>
			<td>${dto.hit }</td>
		</tr>
		</c:forEach>
		
		<tr>
			<td colspan="6">
				<!-- <a href="writeForm.jsp" class="btn btn-default">글쓰기</a> -->
				<button onclick="admin()" class="btn btn-default">글쓰기</button>
				<script type="text/javascript">
				function admin(){
					var admin=prompt("관리자 코드를 입력해주세요");
					if(admin=="886412") location.href="writeForm.jsp";
				}
				</script>
			</td>
		</tr>
		<tr>
	    <td colspan="6" align="right">
	        <%
	            if(no>block){ //처음, 이전 링크
	        %>
	                [<a href="list.jsp?no=1">◀◀</a>]
	                [<a href="list.jsp?no=<%=fromPage-1%>">◀</a>]    
	        <%     
	            }else{
	        %>             
	                [<span style="color:black">◀◀</span>]   
	                [<span style="color:black">◀</span>]
	        <%
	            }
	        %>
	        <%
	            for(int i=fromPage; i<= toPage; i++){
	                if(i==no){
	        %>         
	                    [<%=i%>]
	        <%     
	                }else{
	        %>
	                    [<a href="list.jsp?no=<%=i%>"><%=i%></a>]
	        <%
	                }
	            }
	        %>
	        <%
	            if(toPage<allPage){ //다음, 이후 링크
	        %>
	                [<a href="list.jsp?no=<%=toPage+1%>">▶</a>]
	                [<a href="list.jsp?no=<%=allPage%>">▶▶</a>]
	        <%     
	            }else{
	        %>             
	                [<span style="color:black">▶</span>]
	                [<span style="color:black">▶▶</span>]
	        <%
	            }
	        %>
	    	</td>
		</tr>
	</table>
</div>
	<div class="jumbotron">
	    <h2>My Study Process</h2>
	    <h4><p>This is my HomePage to record my study process of Android, DataBase, JAVA and Python etc.</p></h4>
	    <h5><p>present by Obtuse programmer</p></h5>
  	</div>
</body>
</html>