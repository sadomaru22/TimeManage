<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.ArrayList" 
    import="model.EmployeeDTO"%>
<%
	if (session.getAttribute("loginUserId") == null) {
		response.sendRedirect("login.jsp");
	} else {
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>従業員一覧</title>
<link rel="stylesheet" href="common/css/style.css">
<script type="text/javascript" src="common/JS/function.js"></script>

</head>
<body>
<%
	ArrayList<EmployeeDTO> viewlist = (ArrayList<EmployeeDTO>)session.getAttribute("viewlist");
	if (viewlist != null) {
%>
<jsp:include page="header.jsp" />

	<div class="menu">
		<div class="main_frame">
			<p>従業員一覧</p>
		</div>
	</div>
	<div class="main_wrapper">
		<form action="CheckEditDelete" method="post"
			onsubmit="return chkShowAll(this.name)">
			<div class="show_all_table">
				<table class="border_table">
					<tr class="top_table">
						<td></td>
						<td>従業員コード</td>
						<td>氏名</td>
						<td>所属部署</td>
					</tr>

					<%
						for (EmployeeDTO vl : viewlist) {
					%>
					<tr class="main_table">
						<td><input type="radio" name="employeeCode"
							value="<%=vl.getEmployee_code()%>"></td>
						<td><%=vl.getEmployee_code()%></td>
						<td><%=vl.getName()%></td>
						<td><%=vl.getPost()%></td>
					</tr>

					<%
						}
							} else {
								response.sendRedirect("/TimeManage/DisplayEmployeeList");
							}
					%>
				</table>
			</div>

			<div class="comment_show_all" id="comment_show_all">編集・削除したい従業員にチェックを入れてください</div>
			<div class="employee_button">
				<input type="submit" class="edit_button" name="submit"
					value="従業員を編集する" onclick="setValue('edit_submit')">
				
				<input type="submit" class="edit_button" name="submit"
					value="労働時間の修正" onclick="setValue('edit_submit')">
					 
				<input type="submit" name="submit" value="従業員を削除する"
					onclick="setValue('delete_submit')"> <input type="hidden"
					name="chkBtn">
			</div>
		</form>
		<div class="link_main_button">
			<a href="/TimeManage/JSPad/menu.jsp">
				<button class="display_button">メニュー画面に戻る</button>
			</a>
		</div>
	</div>
<jsp:include page="footer.jsp" />		
</body>
</html>
<%
	}
%>
