package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeDTO;

/**
 * Servlet implementation class CheckEditDelete
 * @author Kazuma Watanabe
 * 削除処理か編集処理か判断するクラス。
 */
@WebServlet("/CheckEditDelete")
public class CheckEditDelete extends HttpServlet {
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
		if(session.getAttribute("loginUserId") == null) {
			response.sendRedirect("/TimeManage/JSPad/login.jsp");
		} else {
			response.sendRedirect("/TimeManage/JSPad/menu.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が POST リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException POST に相当するリクエストが処理できない場合。
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。<br>
	 * 削除処理か編集処理か、もしくは時間の修正かを押されたボタンのvalueで判断する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		
		//checkboxでチェックされた特定の従業員コード
		String employeeCode = request.getParameter("employeeCode");
		
		//AttendanceLoginCheckと同じく、StringのままではEmployeeDTO型の引数として後々利用できないため、下の二行で変換する
		EmployeeDTO edt = new EmployeeDTO();  
		edt.setEmployee_code(employeeCode);

		session.setAttribute("employeeCode", edt);

		if (request.getParameter("submit").equals("従業員を編集する")) {
			response.sendRedirect("EditCheckEmployee");   //指定された従業員コードの従業員情報をとるため、必要
		
		} else if (request.getParameter("submit").equals("労働時間の修正")) {
			response.sendRedirect("/TimeManage/JSPad/modify_radio_worktime.jsp");

		}  else if (request.getParameter("submit").equals("従業員を削除する")) {
			response.sendRedirect("DeleteEmployee");

		} else {
			response.sendRedirect("/TimeManage/JSPad/show_all_employee.jsp");

		}

	}

}
