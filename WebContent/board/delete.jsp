<%@page import="com.aki.board.dao.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% System.out.println("/board/delete.jsp"); //경로 확인용%>             
<%
//자바 코드
//데이터 :no 데이터 받기
String strNo = request.getParameter("no");
int no = Integer.parseInt(strNo);
BoardDAO dao = new BoardDAO();
dao.delete(no);
%>             
<% 
	response.sendRedirect("list.jsp");//특정 페이지로 이동하기 위한 자바구문
%>