package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeDAO;
import model.EmployeeDTO;

/**
 * Servlet implementation class EditCheckEmployee
 * @author Kazuma Watanabe
 * データベースに接続して従業員コードに対応する従業員情報を送る。
 */
@WebServlet("/EditCheckEmployee")
public class EditCheckEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * データベースに接続して従業員コードに対応する従業員情報をセッションにセットする。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginUserId") == null) {
			response.sendRedirect("login.jsp");
		} else {
			request.setCharacterEncoding("UTF-8");
			EmployeeDTO employeeCode = (EmployeeDTO)session.getAttribute("employeeCode");
			EmployeeDAO empdao = new EmployeeDAO();
			EmployeeDTO employee = null;

//			if (employeeCode != null) {
				EmployeeDAO.getConnection();
				employee = empdao.selectEmployee(employeeCode);

				session.setAttribute("employee", employee); //employee_codeで指定された従業員の情報
				
//				if (employee == null) {
//					response.sendRedirect("/TimeManage/JSPad/menu.jsp");
//				} else {
			        RequestDispatcher rd = request.getRequestDispatcher("/JSPad/edit_employee.jsp");
			        rd.forward(request, response);		
//				}

//			} else {
//				response.sendRedirect("/TimeManage/JSPad/show_all_employee.jsp");
//			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
