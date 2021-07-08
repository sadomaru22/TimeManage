<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="model.WorkTimeDTO"
    import="model.EmployeeDTO"%>
<%
	EmployeeDTO employeeCode = (EmployeeDTO)session.getAttribute("employeeCode");
	WorkTimeDTO wd = (WorkTimeDTO)session.getAttribute("workdate");
	//String thisMonthDate = (String)session.getAttribute("thisMonthDate");
%>    
			<% System.out.println(employeeCode.getEmployee_code()); %>
			<% System.out.println(wd); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>時間修正画面</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />

	<div class="menu">
		<div class="main_frame">
			<p>労働時間の修正</p>
		</div>
	</div>
	
	<div class="main_wrapper">
		<div class="main_admin">
			<div>修正が完了したら下の「完了」ボタンを押してください。</div>
			<form action="ModifyCompWorktime" method="post">
			<input type="hidden" name="employeeCode" value="<%= employeeCode.getEmployee_code() %>">
			<input type="hidden" name="day" value="<%= session.getAttribute("thisMonthDate") %>">
				<div class="show_all_table">
					<table class="border_table">
						<tr class="top_table">
							<td>出勤時間</td>
							<td>退勤時間</td>
						</tr>
						
						<tr class="main_table">
							<td>
							<input type="text" name="startTime" value="<%= wd.getStartTime() %>">
							</td>
							<td>
							<input type="text" name="finishTime" value="<%= wd.getFinishTime() %>">
							</td>
						</tr>
					</table>
					
					<input type="submit" class="edit_button" name="submit">
				
				</div>
			</form>

		</div>

		<div class="a_logout_button">
			<a href="/attendance_login.jsp">
				<button class="display_button">メニューに戻る</button>
			</a>
		</div>
	</div>
<jsp:include page="footer.jsp" />		
</body>
</html>