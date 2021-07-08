<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	if (session.getAttribute("loginUserId") == null) {
		response.sendRedirect("login.jsp");
	} else {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録完了</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />

	<div class="menu">
		<div class="main_frame">
			<p>登録完了いたしました!</p>
		</div>
	</div>

	<div class="main_wrapper">
		<div class="main_admin">
			<p>引き続き入力する場合は、メニューから入力をお願いいたします。</p>
		</div>

		<div class="logout_button ">
			<a href="/TimeManage/JSPad/menu.jsp">
				<button class="display_button">メニューに戻る</button>
			</a>
		</div>
	</div>
</body>
</html>
<%
	}
%>