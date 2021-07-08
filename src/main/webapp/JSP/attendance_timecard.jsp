<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@ page
	import="model.EmployeeDTO, java.text.SimpleDateFormat, java.util.Date, java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String message2 = (String)session.getAttribute("message2");
	String startWork = (String) session.getAttribute("startWork");
	String finishWork = (String) session.getAttribute("finishWork");
	
	//セキュリティチェック
	EmployeeDTO employeeCode = (EmployeeDTO) session.getAttribute("employeeCode");
	if (employeeCode == null) {
		response.sendRedirect("attendance_login.jsp");
	} else {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タイムカード</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/common/css/style.css">
<script type="text/javascript" src="<%= request.getContextPath() %>/common/JS/function.js"></script>
</head>

<body>
<jsp:include page="header.jsp" />
	<div class="menu">
		<div class="main_frame">
			<p>タイムカード</p>
		</div>
	</div>

	<%
		//現在日時の表示
		LocalDateTime now = LocalDateTime.now();
	%>

	<div class="display_top">
		<% if (message2 != null) { %>
					<p class="commit_message"><%= message2 %></p>
		<% } %>
		<p id="today_area"><%=now.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))%></p>
		<p id="RealtimeClockArea"></p> <%--jsからSetIntervalくる --%>
	</div>
	
	<%  System.out.println(startWork);
		System.out.println(finishWork);
		//if (startWork == null) {   //startWork == nullに条件式だと、このページに帰ってくる前にデータベースを読み込みなおして!=nullになっていることを確認する必要があるため、大変
		if (startWork == null) {	 
			%>
	<form action="AttendanceTimeCard" method="POST" class="attendance_form">
		<input type="hidden" name="attendance" value="出勤処理"> <input
			type="submit" name="submit" value="出勤" id="disableStartWorkButton"
			class="attendance_timecard">
	</form>
	<%
		} else { //disableで無効化   //二回目以降、もしくは出勤押していったんページを離れて、再ログインした場合非表示
	%>
	<form action="AttendanceTimeCard" method="POST" class="attendance_form">
		<input type="hidden" name="attendance" value="出勤処理"> <input
			type="submit" name="submit" value="出勤" id="disableStartWorkButton"
			disabled="disabled" class="attendance_timecard2">
	</form>

	<%
		}   //
			if (finishWork != null && startWork != null) { //!=null すなわち"disablef"
	%>
	<form action="AttendanceTimeCard" method="POST" class="attendance_form">
		<input type="hidden" name="attendance" value="退勤処理"> <input
			type="submit" name="submit" value="退勤" id="disableFinishWorkButton"
			class="attendance_timecard">
	</form>

<%
	} else {
%>		
	<form action="AttendanceTimeCard" method="POST" class="attendance_form">
		<input type="hidden" name="attendance" value="退勤処理"> <input
			type="submit" name="submit" value="退勤" id="disableFinishWorkButton"
			disabled="disabled" class="attendance_timecard2">
	</form>
<% 	}
}
%>	
<jsp:include page="footer.jsp" />			
</body>
</html>