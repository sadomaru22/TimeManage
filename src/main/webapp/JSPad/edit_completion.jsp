<%@page import="model.EmployeeDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	if (session.getAttribute("loginUserId") == null) {
		response.sendRedirect("/TimeManage/JSPad/login.jsp");
	} else {
		EmployeeDTO employee = (EmployeeDTO) session.getAttribute("employee");
		if (employee != null) {
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>編集完了</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />

	<div class="menu">
		<div class="main_frame">
			<p>
				従業員コード：<%=employee.getEmployee_code()%>の編集が完了しました
			</p>
		</div>
	</div>

	<div class="main_admin">
		<p>引き続き入力する場合は、メニューから入力をお願いいたします。</p>
		<div class="a_logout_button">
			<a href="/TimeManage/JSPad/menu.jsp"><button class="display_button">メニューに戻る</button></a>
		</div>
	</div>
</body>
</html>
<%
	} else {
			response.sendRedirect("/TimeManage/JSPad/menu.jsp");
		}
	}
%>