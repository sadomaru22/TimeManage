package control;

import java.io.IOException;
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

/**
 * Servlet implementation class EmployeeListDisplay
 * @author Kazuma Watanabe
 * データベースに接続して従業員情報全件一覧を取得するクラス。
 */
@WebServlet("/DisplayEmployeeList")
public class DisplayEmployeeList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * データベースに接続して従業員情報全件一覧を取得してセッションにセットする。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		
		//セキュリティチェック
		if(session.getAttribute("loginUserId") == null) {
			response.sendRedirect("login.jsp");
		} else {
			EmployeeDAO.getConnection();
			EmployeeDAO dao = new EmployeeDAO();
			 
			List<EmployeeDTO> viewlist = dao.selectEmployeeAll();
			session.setAttribute("viewlist", viewlist);

	        RequestDispatcher rd = request.getRequestDispatcher("/JSPad/show_all_employee.jsp");
	        rd.forward(request, response);	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が POST リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException POST に相当するリクエストが処理できない場合。
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
