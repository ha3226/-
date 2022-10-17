<%@page import="model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!-- 사용자데이터를 읽어드리는 빈클래스설정  -->
<jsp:useBean id="boardbean" class="model.BoardBean">
  <jsp:setProperty name="boardbean" property="*" />
</jsp:useBean>
<%
 	BoardDAO bdao =new BoardDAO();
	String pass=bdao.getPass(boardbean.getNum());

	if(pass.equals(boardbean.getPassword())){
		bdao.updateBoard(boardbean);
		response.sendRedirect("BoardList.jsp");
	}else{ 
%>
<script>
	alert("패스워드가 일치 하지 않습니다. 다시 확인 후 수정해주세요.");
	history.go(-1);
</script>
<%
	}
%>