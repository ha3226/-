<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="Header.jsp"/>
</head>
<body>
<%
	int num =Integer.parseInt(request.getParameter("num"));
	int ref =Integer.parseInt(request.getParameter("ref"));
 	int re_step =Integer.parseInt(request.getParameter("re_step"));
 	int re_level =Integer.parseInt(request.getParameter("re_level"));

%>


<div class="row">
	<div class="col-md-2"></div>
	<div class="col-md-8">
		<h2 class="text-center">답변글 입력하기</h2><p></p>
		<form action="BoardReWriteProc.jsp" method="post">
			<div class="table table-responsive">
					  <table class="table table-striped">
		 	<tr>
		 		<td>작성자</td>
		 		<td><input type="text"  class="form-control" name="writer"></td>
		 	</tr>
		 	<tr>
		 		<td>제목</td>
		 		<td><input type="text"  class="form-control" name="subject" value="[답변]"></td>
		 	</tr>
		 	<tr>
		 		<td>이메일</td>
		 		<td><input type="email"  class="form-control" name="email"></td>
		 	</tr>
		 	
		 	<tr>
		 		<td>비밀번호</td>
		 		<td><input type="password"  class="form-control" name="password"></td>
		 	</tr>
		 	<!--  넘어가는 파라메타는 빈으로 들어감  -->
		 	<tr>
		 		<td>글내용</td>
		 		<td><textarea rows="10" cols="50" name="content" class="form-control"></textarea></td>
		 	</tr>
		  	<tr>	
		 		<td colspan="2"  class="text-center">
		 		
		 			<input type="hidden" name="num"  value="<%= num %>">
			 		<input type="hidden" name="ref"  value="<%= ref %>">
			 		<input type="hidden" name="re_step"  value="<%= re_step %>">
			 		<input type="hidden" name="re_level"  value="<%= re_level %>">
		 		
		 			<input type="submit" value="답글쓰기완료" class="btn btn-success">
		 			<input type="reset"  class="btn btn-warning" onclick="history.go(-1);" value="취소">
		 			<input type="button"  class="btn btn-primary" onclick="location.href='BoardList.jsp'" value="전체글보기">
		 		</td>
		 	</tr>
		 	
		  </table>
		
	
			</div>
		</form>	
	</div>
</div>


<jsp:include page="Bottom.jsp"/>
<script>
CKEDITOR.replace('content', {
		
	width:'100%',
	height:'350'
		
});
</script>
</body>
</html>
