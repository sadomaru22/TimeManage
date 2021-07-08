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
<title>管理者用メニュー</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />

	<div class="menu">
		<div class="main_frame">
			<p>管理者用メニュー画面</p>
		</div>
	</div>

	<div class="main_wrapper">
		<div class="employee_button">

			<a href="/TimeManage/JSPad/regist_employee.jsp" class="regist_button">
				<button class="display_button">従業員登録</button>
			</a> <a href="/TimeManage/DisplayEmployeeList">
				<button class="display_button">従業員情報一覧</button>
			</a>
		</div>
		<div class="logout_button">
			<a href="/TimeManage/JSPad/regist_adminuser.jsp">
				<button class="display_button">管理者権限を付与する</button>
			</a>
		</div>
		<div class="link_main_button">
			<form action="/TimeManage/LogoutCheck" method="post">
				<button class="display_button">ログアウト</button>
			</form>
		</div>
		<div class=link_main_button>
			<input type="button" class="display_button"
				onclick="location.href='/TimeManage/SendIndex'" value="Topページ">
		</div>
	</div>
<jsp:include page="footer.jsp" />	
</body>
</html>
<%
	}
%>