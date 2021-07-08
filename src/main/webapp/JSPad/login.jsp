<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String message = (String)session.getAttribute("message");

	if (session.getAttribute("loginUserId") != null) {
		response.sendRedirect("menu.jsp");
	} else {
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者用ログイン</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />

	<div class="menu">
		<div class="main_frame">
			<p>管理者用ログイン画面</p>
		</div>
	</div>
	<div class="main_wrapper">
<%
	if (message != null) {
%>	
<p><%= message %></p>
<% } %>
		<form action="/TimeManage/LoginCheck" method="post">
			<div class="regist_table">
				<table>
					<tr>
						<td>ユーザID</td>
						<td>：</td>
						<td><input type="text" name="userId" required></td>
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
	</div>
		<div class="admin_user_button">
			<a class="display_button" href="/TimeManage/SendIndex"><button>Topページへ</button></a>
		</div>
<jsp:include page="footer.jsp" />	
</body>
</html>
<%
	}
%>