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
<title>総務部登録</title>
<link rel="stylesheet" href="common/css/style.css">
<script type="text/javascript" src="common/JS/func.js"></script>

</head>
<body>
<jsp:include page="header.jsp" />

	<div class="menu">
		<div class="main_frame">
			<p>総務部登録</p>
		</div>
	</div>

	<div class="main_wrapper">
		<div class="main_admin">
			総務部として登録をします。<br> ユーザーID(4文字以上)、パスワード(8文字以上)を半角英数で入力してください。
		</div>

		<form action="/TimeManage/RegistAdminuser" method="POST"
			onsubmit="return chkUser()">
			<div class="regist_table">
				<table>
					<tr>
						<td>ユーザーID</td>
						<td>：</td>
						<td><input type="text" name="userId" id="user_id"></td>
						<td>
							<div id="comment_user_id">半角英数4文字以上24文字以下で入力してください</div>
						</td>
					</tr>
					<tr>
						<td>パスワード</td>
						<td>：</td>
						<td><input type="password" name="password" id="password"></td>
						<td><div id="comment_password">半角英数8文字以上32文字以下で入力してください</div></td>
					</tr>
					<tr>
						<td>もう一度</td>
						<td>：</td>
						<td><input type="password" name="confirmation"
							id="confirmation"></td>
						<td>
							<div id="comment_confirmation">パスワードと違います</div>
						</td>
					</tr>
				</table>
				<div class="tomenu_button">
					<div class="admin_user_button">
						<input type="submit" class="admin_user_submit" value="登録する">

						<a href="menu.jsp"> <input type="button" class="clear_button"
							value="キャンセル">
						</a>
					</div>
				</div>
			</div>
		</form>
	</div>
<jsp:include page="footer.jsp" />	
</body>
</html>
<%
	}
%>