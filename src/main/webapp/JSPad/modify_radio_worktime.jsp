<%@page
	import="model.EmployeeDTO, java.time.LocalDateTime, java.time.format.DateTimeFormatter"%>
<%@page import="model.LastDayofMonth" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	LocalDateTime now = LocalDateTime.now();
	int year = now.getYear();
	int month = now.getMonthValue();
	EmployeeDTO employeeCode = (EmployeeDTO) session.getAttribute("employeeCode");
	if (employeeCode == null) {
		response.sendRedirect("login.jsp");
	} else {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>労働時間の修正</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />

	<div class="menu">
		<div class="main_frame">
			<p>修正する日時の選択</p>
		</div>
	</div>

	<div class="main_wrapper">
		<div class="main_admin">
			<div>修正したい日時を選択してください</div>
			<%= year %>年
					<%
								if (month < 10) {
					%>
					0<%=month%>

					<%
						} else {
					%>
					<%=month%>
				<% } %>
				月
			<form action="/TimeManage/ModifyCheckWorktime" method="post">
				<select name="thisDate" required>
					<%
					int result = LastDayofMonth.lastDay();
						for (int d = 1; d <= result; d++) {
							if (d < 10) {
					%>
					<option>0<%= d %></option>
					<% } else { %>
					<option><%= d %></option>	
					<% }}%>
				</select>
				日 
				<input type="submit" value="日時を確定する" class="attendance_select_timesheet">
			</form>
			</div>
		</div>

		<div class="a_logout_button">
			<a href="/TimeManage/JSPad/login.jsp">
				<button class="display_button">メニューに戻る</button>
			</a>
		</div>
<jsp:include page="footer.jsp" />	
</body>
</html>
<%
	}
%>