<%@page import="model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <%
 	request.setCharacterEncoding("UTF-8");
 %>
<jsp:useBean id="boardbean" class="model.BoardBean">
	<jsp:setProperty  name="boardbean"  property="*"/>
</jsp:useBean>
<%
	BoardDAO bdao =new BoardDAO();
	bdao.reWriteBoard(boardbean);
	response.sendRedirect("BoardList.jsp");
%>