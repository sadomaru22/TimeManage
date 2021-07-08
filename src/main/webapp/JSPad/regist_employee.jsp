<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("loginUserId") == null) {
		response.sendRedirect("/TimeManage/JSPad/login.jsp");
	} else {
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp" />

<div class="main_wrapper">

		<div class="menu">
			<div class="main_frame">
				<p>従業員登録</p>
			</div>
		</div>

	<form action="/TimeManage/RegistEmployee" method="post" onsubmit="return chk()">
			<div class="comment_show_all" id="comment_show_all">
			<p>すべて入力してください↓</p>			
			</div>
			<div class="comment_error" id="comment_error">{&lt;&gt;&amp;.,/}の入力はできません</div>
			<div class="regist_table">
				<table>
					<tr>
						<td>従業員コード</td>
						<td>：</td>
						<td><input type="text" name="employee_code" id="last_name">
						</td>
					</tr>
					<tr>
						<td>名前</td>
						<td>：</td>
						<td><input type="text" name="name" id="first_name"></td>
					</tr>
					<tr>
						<td>部署</td>
						<td>：</td>
						<td><input type="text" name="post" id="last_kana_name"></td>
					</tr>
					<tr><td></td></tr>
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