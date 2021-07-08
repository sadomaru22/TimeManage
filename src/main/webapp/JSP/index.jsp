<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	session.removeAttribute("loginUserId");
	session.removeAttribute("employeeCode");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>株式会社SCC</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />

	<div class= "menu">
		<div class= "main_frame">
			<p>TOPページ</p>
		</div>
 	</div>
	<div class="i_main_wrapper">
		<div class="employee_button">
			<input type="button" class="regist_button" onclick="location.href='/TimeManage/JSPad/login.jsp'" value="管理者用メニュー">
			<input type="button" onclick="location.href='/TimeManage/JSP/attendance_login.jsp'" value="従業員用メニュー">
		</div>
	</div>
<jsp:include page="footer.jsp" />	
</body>
</html>