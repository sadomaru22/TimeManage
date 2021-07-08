<%@page import="model.EmployeeDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%  //セキュリティチェック
	EmployeeDTO employeeCode = (EmployeeDTO) session.getAttribute("employeeCode");
	if (employeeCode == null) {
		response.sendRedirect("attendance_login.jsp");
	} else {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>従業員用メニュー画面</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />
	<div class="menu">
		<div class="main_frame">
			<p>従業員用メニュー画面</p>
		</div>
	</div>
	<div class="main_wrapper">
		<div class="employee_button">
			<a href="/TimeManage/AttendanceViewTimecard" class="regist_button">
				<button class="a_display_button">打刻する</button>
			</a> <a href="/TimeManage/JSP/attendance_select_timesheet.jsp" class="regist_button">
				<button class="a_display_button">月次報告する</button>
			</a>
		</div>

		<div class="logout_button">
			<form action="/TimeManage/AttendanceLogoutCheck" method="post">
				<input type="submit" class="a_display_button" value="ログアウト">
			</form>
		</div>
		<div class="display_top">
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