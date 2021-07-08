package control;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeDAO;
import model.EmployeeDTO;
import model.WorkTimeDAO;
import model.WorkTimeDTO;

/**
 * Servlet implementation class AttendanceSelectTimesheet
 * @author Kazuma Watanabe
 * 月を受け取って対応する出退勤時刻情報を渡すクラス。
 */
@WebServlet("/AttendanceSelectTimesheet")
public class AttendanceSelectTimesheet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * 直接アクセスに対して従業員が既にログインしていたらメニュー画面にリダイレクトさせる。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("employeeCode") == null) {
			response.sendRedirect("attendance_login.jsp");
		} else {
			response.sendRedirect("attendance_menu.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が POST リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException POST に相当するリクエストが処理できない場合。
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。<br>
	 * データベースに接続して月に対応する出退勤時刻情報のリストを取得する。<br>
	 * セッション情報に出退勤時刻情報のリスト,また別のものに今月の値をセットする。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		EmployeeDTO employeeCode = (EmployeeDTO) session.getAttribute("employeeCode");
		String thisMonth = request.getParameter("thisMonth");
		Calendar thisMonthCalneder = Calendar.getInstance();
		thisMonthCalneder.set(Calendar.YEAR, Integer.parseInt(thisMonth.substring(0, 4)));
		thisMonthCalneder.set(Calendar.MONTH, Integer.parseInt(thisMonth.substring(5)));

		WorkTimeDAO workTimeDao = new WorkTimeDAO();
		EmployeeDAO empdao = new EmployeeDAO();
		try {
			WorkTimeDAO.getConnection();
			List<WorkTimeDTO> workTimeThisMonthList =
					workTimeDao.selectWorkTimeThisMonthList(employeeCode, thisMonth);
			session.setAttribute("workTimeThisMonthList", workTimeThisMonthList);
			
			EmployeeDAO.getConnection();
			EmployeeDTO employee = empdao.selectEmployee(employeeCode);
			
			String employeeName = employee.getName(); 
			session.setAttribute("employeeName", employeeName);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error_message.jsp");
		}

		session.setAttribute("thisMonth", thisMonthCalneder);
	
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/attendance_view_timesheet.jsp");
		rd.forward(request, response);
	}

}
