<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Employee Directory</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

</head>
<body>

	<div class="container">

		<h1 class="text-center ">직원 리스트</h1>
		<hr />

		<p>${NOTIFICATION}</p>

		<p>
			<button class="btn btn-primary"
				onclick="window.location.href = 'views/employee-form.jsp'">직원
				추가</button>
			<button class="btn btn-primary"
				onclick="window.location.href = './BoardList.jsp'">직원 게시판</button>
		</p>

		<table class="table table-striped table-bordered">

			<tr class="thead-dark">
				<th>Name</th>
				<th>Department</th>
				<th>Date of birth</th>
				<th>Actions</th>
			</tr>

			<c:forEach items="${list}" var="employee">

				<tr>
					<td>${employee.name}</td>
					<td>${employee.department}</td>
					<td>${employee.dob}</td>
					<td><a
						href="${pageContext.request.contextPath}/EmployeeController?action=EDIT&id=${employee.id}">Edit</a>
						| <a
						href="${pageContext.request.contextPath}/EmployeeController?action=DELETE&id=${employee.id}">Delete</a>
					</td>
				</tr>

			</c:forEach>

		</table>

	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>