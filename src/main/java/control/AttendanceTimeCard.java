package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AttendanceEmployeeDAO;
import model.EmployeeDTO;
import model.WorkTimeDAO;

/**
 * Servlet implementation class AttendanceTimeCard
 * @author Kazuma Watanabe
 *  打刻情報を受け取ってデータベースに反映させるクラス。
 */
@WebServlet("/AttendanceTimeCard")
public class AttendanceTimeCard extends HttpServlet {
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
	 * 押されたボタンに応じた打刻処理を行う。<br>
	 * データベースに接続して対応する打刻処理を反映させる。<br>
	 * 処理が成功したら完了画面にフォワードする。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String message2 = null;
		
		request.setCharacterEncoding("UTF-8");
		
		EmployeeDTO employeeCode = (EmployeeDTO) session.getAttribute("employeeCode");	
		
		//どのボタンが押されたかをチェック
		String attendance = request.getParameter("attendance");
		
		AttendanceEmployeeDAO attendEmpDao = new AttendanceEmployeeDAO();

		boolean Flag = false;
		try {
			if (attendance.equals("出勤処理")) {
				message2 = "出勤しました。";
				Flag = attendEmpDao.setStartTime(employeeCode);
				
//			} else if (attendance.equals("退勤処理")) {
			} else if (attendance.equals("退勤処理")) {
				System.out.println("attendance = " + attendance);
				message2 = "退勤しました。";
				Flag = attendEmpDao.setFinishTime(employeeCode);
			} else {}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		//ViewTimecard.servと同じ処理をまず行う(null or "disabled"のチェック)
		WorkTimeDAO.getConnection();
		
		WorkTimeDAO workTimeDao = new WorkTimeDAO();
		
			String startCheck = workTimeDao.selectStartTime(employeeCode);
			session.setAttribute("startWork", startCheck);
			
			//出勤ボタンが押されてたら退勤押せる
			if(startCheck != null) {
				String finishCheck = workTimeDao.selectFinishTime(employeeCode);
				session.setAttribute("finishWork", finishCheck);
			}


		if (Flag) {
			session.setAttribute("message2", message2);
			session.setAttribute("attendance", attendance);
			RequestDispatcher rd = request.getRequestDispatcher("/JSP/attendance_timecard.jsp");
			rd.forward(request, response);
		} else {
			session.setAttribute("message2", message2);
			//session.setAttribute("attendance", attendance);
			RequestDispatcher rd = request.getRequestDispatcher("/JSP/attendance_timecard.jsp");
			rd.forward(request, response);
		}
	}

}
