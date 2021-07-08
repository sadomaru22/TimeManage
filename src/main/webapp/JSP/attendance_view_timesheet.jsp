<%@page import="java.time.LocalTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
<%@page import="model.WorkTimeDTO"%>
<%@page import="java.util.Calendar"%>
<%@page import="model.EmployeeDTO"%>
<%@page import="model.LastDayofMonth" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	EmployeeDTO employeeCode = (EmployeeDTO) session.getAttribute("employeeCode");
	if(employeeCode == null) {
		response.sendRedirect("attendance_login.jsp");
	} else {
		Calendar thisMonthCalendar = (Calendar) session.getAttribute("thisMonth");
		if(thisMonthCalendar == null) {
			response.sendRedirect("attendance_menu.jsp");
		} else {
			List<WorkTimeDTO> workTimeThisMonthList =
					(List<WorkTimeDTO>) session.getAttribute("workTimeThisMonthList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タイムシート</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />

	<div class= "menu">
	<div class= "main_frame">
		<p>タイムシート</p>
	</div>
	</div>
		<div class="name_format">
			名前：<%= session.getAttribute("employeeName") %>
		</div>
		<div class="name_format">
			<%= thisMonthCalendar.get(Calendar.YEAR) + "年"
		+ thisMonthCalendar.get(Calendar.MONTH) + "月" %>分
		</div>

		<div class="edit_top">
		<div class="show_all_table"><table>
		<tr class="top_table"><th>日にち</th><th>出勤</th><th>退勤</th><th>実働時間</th></tr>

		<%
			thisMonthCalendar.add(Calendar.MONTH, -1);
			int dayOfMonth = thisMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			thisMonthCalendar.add(Calendar.MONTH, 1);
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH時mm分");
			DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern("HH時間mm分");
			for(int i = 1; i <= dayOfMonth; i++) {
				Boolean chkDateFlag = false;
		%>
				<tr class="main_table">
				<td><%= thisMonthCalendar.get(Calendar.MONTH) + "月" + i + "日" %></td>

			<%
				for(WorkTimeDTO workTime: workTimeThisMonthList) {
				String workDate =
						workTime.getWorkDate().format(DateTimeFormatter.ofPattern("dd")); //出勤日
					if(Integer.parseInt(workDate) == i) {
			%>
						<td><% if(workTime.getStartTime() != null) { %>
						<%= workTime.getStartTime().format(timeFormat) %><% } %></td>
						<td><% if(workTime.getFinishTime() != null) { %>
						<%= workTime.getFinishTime().format(timeFormat) %><% } %></td>
						
						<td><% if(workTime.getWorkingHours() != 0) { %>
						<%--= hourFormat.format(LocalTime.MIDNIGHT.plus(workTime.getWorkingHours())) --%>
						<%= workTime.getWorkingHours() %>時間<%= workTime.getWorkingMins() %>分
						<% } %></td></tr>
			<%
						chkDateFlag = true;
						break;
					}
				}
				if(!chkDateFlag) {
			%>
			<td></td><td></td><td>休</td></tr>
					<%-- <tr><td></td><td></td><td></td><td></td></tr>--%>
		<%
				}
			}
		%>


		</table>
		
		<%  //時間を分に直し、WorkingMinsと足してから60で割った商を求める(1からその月の末日回ループ)→実動時間合計
			WorkTimeDTO workTime = workTimeThisMonthList.get(0);
			int wtHour = workTime.getWorkingHours() * 60;
			int wtMins = workTime.getWorkingMins();    
			int wtPlus = wtHour + wtMins;
			
			int sum = 0;
			int result = LastDayofMonth.lastDay();
	        for (int i = 1; i <= result; i++) {
	        	sum = sum + wtPlus;
	        }
	        
	        sum = sum / 60;
	        session.setAttribute("sum", sum);
		%>
		<div class="sum_right">
		<span style="border-bottom: solid 2px black;">実働時間合計： <%= sum %>時間  &ensp;</span>
		<a href="/TimeManage/CSVOutputServlet"><button>時間を出力する</button></a>
		</div>
		
		</div></div>
		
		
		<div class="signature_form"><table border="1">
		<tr><th>本人印</th><th>承認印</th></tr>
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>

		</table></div>
		<div class="link_button">
			<a href="/TimeManage/JSP/attendance_menu.jsp">
				<button class="display_button">メニュー画面に戻る</button>
			</a>
		</div>
<jsp:include page="footer.jsp" />		
</body>
</html>
<%
	  }
	}
%>	