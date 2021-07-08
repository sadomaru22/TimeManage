<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.WorkTimeDTO"%>

<%
	if (session.getAttribute("loginUserId") == null) {
		response.sendRedirect("login.jsp");
	} else {
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>労働時間修正完了</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />

	<div class="menu">
		<div class="main_frame">
			<p>労働時間修正完了画面</p>
		</div>
	</div>
	<div class="main_wrapper">
		<div class="main_admin">
			<p>
				従業員コード<%= session.getAttribute("employeeCode") %>の労働時間を一部変更しました
			</p>
		</div>

		<div class="logout_button">
			<input type="button" class="display_button" value="メニュー画面に戻る"
				onclick="location.href='/TimeManage/JSPad/menu.jsp';">
		</div>

		<%
			}
		%>
	</div>
</body>
</html>