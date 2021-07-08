<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.EmployeeDTO"%>
<%
	if (session.getAttribute("loginUserId") == null) {
		response.sendRedirect("login.jsp");
	} else if (session.getAttribute("employeeCode") == null) {
		response.sendRedirect("menu.jsp");
	} else {
		EmployeeDTO employee = (EmployeeDTO)session.getAttribute("employee");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>従業員情報編集画面</title>
<link rel="stylesheet" href="common/css/style.css">
<script type="text/javascript" src="common/JS/func.js"></script>

</head>
<body>
<jsp:include page="header.jsp" />

	<div class="main_wrapper">

		<div class="menu">
			<div class="main_frame">
				<p>従業員編集</p>
			</div>
		</div>

		<form action="/TimeManage/EditEmployee" method="post" onsubmit="return chkEdit()">
			<div class="comment_show_all" id="comment_show_all">変更箇所を入力してください</div>
			<div class="comment_error" id="comment_error">{&lt;&gt;&amp;.,/}の入力はできません</div>
			<div class="regist_table">
				<table>
					<tr>
						<td>従業員コード</td>
						<td>：</td>
						<td><input type="hidden" name="employee_code" value="<%= employee.getEmployee_code() %>">
						<%=employee.getEmployee_code()%></td>
					</tr>
					<tr>
						<td>氏名</td>
						<td>：</td>
						<td><input type="text" name="name"
							value="<%=employee.getName()%>" id="last_name"></td>
					</tr>
					<tr>
						<td>部署名</td>
						<td>：</td>
						<td><input type="text" name="post"
							id="last_kana_name" value="<%=employee.getPost()%>"></td>
					</tr>
					<tr>
						<td>パスワード</td>
						<td>：</td>
						<td><input type="text" name="password"
							id="last_kana_name" value="<%=employee.getPassword()%>"></td>
					</tr>
				</table>

				<div class="tomenu_button">
					<div class="admin_user_button">
						<input type="submit" class="admin_user_submit" value="編集する">

						<a href="/TimeManage/JSPad/menu.jsp"> <input type="button" class="clear_button"
							value="キャンセル">
						</a>
					</div>
				</div>
			</div>
		</form>

	</div>
</body>
</html>
<%
	}
%>