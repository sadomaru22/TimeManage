<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("loginUserId") != null) {
		response.sendRedirect("menu.jsp");
	} else {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト画面</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />

	<div class="menu">
		<div class="main_frame">
			<p>ログアウトしました。</p>
		</div>
	</div>

	<div class="main_wrapper">
		<div class="main_admin">本日もお疲れ様でした！！！</div>

		<div class="logout_button">
			<input type="button" class="display_button" value="ログイン画面に戻る"
				onclick="location.href='login.jsp';">
		</div>
	</div>
<jsp:include page="footer.jsp" />		
</body>
</html>

<%
	}
%>