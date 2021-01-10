<%@page import="com.aki.board.dao.BoardDAO"%>
<%@page import="com.aki.board.dto.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% System.out.println("/board/update.jsp"); //경로 확인용%>

<%
request.setCharacterEncoding("utf-8");
//자바 부분
BoardDTO dto = new BoardDTO();
dto.setTitle(request.getParameter("title"));
dto.setContent(request.getParameter("content"));
dto.setUse(request.getParameter("use"));
String strNo = request.getParameter("no");
int no = Integer.parseInt(strNo);
System.out.println(no);
//BoardDAO에 write(dto) 매서드를 이용해서 DB에 저장하는 처리
BoardDAO dao = new BoardDAO();
dao.update(dto, no);
%>         
<% 
	response.sendRedirect("list.jsp");//특정 페이지로 이동하기 위한 자바구문
%>