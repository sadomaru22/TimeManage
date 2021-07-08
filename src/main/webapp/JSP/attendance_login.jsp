<%@page import="model.EmployeeDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<% //ログインしてたらそのままメニューへ
	EmployeeDTO employeeCode = (EmployeeDTO) session.getAttribute("employeeCode");
	if (employeeCode != null) {
		response.sendRedirect("attendance_menu.jsp");
	} else {
%>
<%	String message = (String)session.getAttribute("message"); %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>従業員用ログイン画面</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />
	<div class="menu">
		<div class="main_frame">
<% if (message != null) { %>
			<p><%= message %></p>
<% } %>
			<p>従業員用ログイン画面</p>
		</div>
	</div>

	<div class="main_wrapper">
		<form action="/TimeManage/AttendanceLoginCheck" method="post">
			<div class="regist_table">
				<table>
					<tr>
						<td>従業員コード</td>
						<td>：</td>
						<td><input type="text" name="employeeCode" required></td>
					</tr>
					<tr>
						<td>パスワード</td>
						<td>：</td>
						<td><input type="password" name="password" required></td>
					</tr>
				</table>
			</div>
			<div class="admin_user_button">
				<input type="submit" class="login_button" value="ログイン"> <input
					type="reset" class="clear_button">
			</div>

		</form>
		<div class="admin_user_button">
			<a class="display_button" href="/TimeManage/SendIndex"><button>Topページへ</button></a>
		</div>
	</div>
<jsp:include page="footer.jsp" />	
</body>
</html>
<%
	}
%>