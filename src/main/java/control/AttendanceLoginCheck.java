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

/**
 * Servlet implementation class AttendenceLoginChk
 * @author Kazuma Watanabe
 * 従業員のログインをチェックするクラス。
 */
@WebServlet("/AttendanceLoginCheck")
public class AttendanceLoginCheck extends HttpServlet {
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String employeeCode = (String) session.getAttribute("employeeCode");
		if (employeeCode == null) {
			response.sendRedirect("attendance_login.jsp");  //RequestDispatcherと違い下のコードが実行されるかされないかでのバグが起こりにくい
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
	 * データベースに接続して従業員のログインをチェックする。<br>
	 * ログインに成功したらセッション情報に従業員コードをセットする。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = null;
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		AttendanceEmployeeDAO.getConnection();

		String inputId = request.getParameter("employeeCode");
		String inputPass = request.getParameter("password");
		
		//セキュリティチェック
		if (inputId == null || inputPass == null) {
			String message = "不正アクセスの可能性があります。";
			session.setAttribute("message", message);
			System.out.println("動作確認、ここまで");		

        	url = "/JSP/attendance_login.jsp";			
		}
		
		
		//既存のアカウント情報を取得
		AttendanceEmployeeDAO attendEmpDao = new AttendanceEmployeeDAO();
		EmployeeDTO returnAed = attendEmpDao.loginEmployee(inputId);
		
		//セキュリティチェック
        if (returnAed == null) {  
        	String message = "失敗しました。IDまたはパスワードが違います。";
        	session.setAttribute("message", message);
        	
        	url = "/JSP/attendance_login.jsp";
        } else {
        	
	        String correctId = returnAed.getEmployee_code();
	        String correctPass = returnAed.getPassword();	

		if (inputId.equals(correctId) && inputPass.equals(correctPass)) {
        	EmployeeDTO edto = new EmployeeDTO();  //インスタンス化したときに、データベースのEmployeeCode(correctId)
        	//を記憶させないとworktimeのテーブルに同じものを書きこめない
        	edto.setEmployee_code(correctId);
        	
        	session.setAttribute("employeeCode", edto);
        	
        
        	url = "/JSP/attendance_menu.jsp";
        	        	
        } else {
        	String message = "ログインに失敗しました。IDまたはパスワードが違います。";
        	session.setAttribute("message", message);
        	
        	url = "/JSP/attendance_login.jsp";
		}

	}
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
   }

}
