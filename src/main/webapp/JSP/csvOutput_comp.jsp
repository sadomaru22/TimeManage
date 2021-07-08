<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CSV出力完了</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />

	<div class="menu">
		<div class="main_frame">
			<p>CSV出力完了画面</p>
		</div>
	</div>
	<div class="main_wrapper">
		<div class="main_admin">
			<p>
				ファイル出力が完了しました。ファイルをご確認ください。
			</p>
		</div>

		<div class="logout_button">
			<input type="button" class="display_button" value="メニュー画面に戻る"
				onclick="location.href='/TimeManage/JSP/attendance_menu.jsp';">
		</div>

	</div>
</body>
</html>