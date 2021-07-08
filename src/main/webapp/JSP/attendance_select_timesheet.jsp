<%@page
	import="model.EmployeeDTO, java.time.LocalDateTime, java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	LocalDateTime now = LocalDateTime.now();
	int year = now.getYear();
	int month = now.getMonthValue();
	//String employeeCode = (String) session.getAttribute("employeeCode");
	//if //(employeeCode == null) {
	//	response.sendRedirect("attendance_login.jsp");
	//} else {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>月次報告画面</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />
	<div class="menu">
		<div class="main_frame">
			<p>月次報告画面</p>
		</div>
	</div>

	<div class="main_wrapper">
		<div class="main_admin">
			<div>表示させたい年月を選択してください</div>
			<form action="/TimeManage/AttendanceSelectTimesheet" method="post">
				<select name="thisMonth" class="margin_r" required>
					<%
						for (int i = month; i > 0; i--) {
								if (i < 10) {
					%>
					<option><%=year%>-0<%=i%></option>

					<%
						} else {
					%>
					<option><%=year%>-<%=i%></option>

					<%
						}
							}
							for (int i = 12; i > month; i--) {
								if (i < 10) {
					%>
					<option><%=year - 1%>-0<%=i%></option>

					<%
						} else {
					%>
					<option><%=year - 1%>-<%=i%></option>

					<%
						}
							}
					%>
				</select> <input type="submit" value="タイムシートを表示する" class="attendance_select_timesheet">
			</form>
		</div>

		<div class="a_logout_button">
			<a href="/TimeManage/JSP/attendance_menu.jsp">
				<button class="display_button">メニューに戻る</button>
			</a>
		</div>
	</div>
<jsp:include page="footer.jsp" />	
</body>
</html>
<%
	//}
%>