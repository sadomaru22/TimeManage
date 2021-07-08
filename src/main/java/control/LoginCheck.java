package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDAO;
import model.UserDTO;

/**
 * Servlet implementation class LoginChk
 * @author Kazuma Watanabe
 * 管理者ユーザーのログインをチェックするクラス。
 */
@WebServlet("/LoginCheck")
public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException Servlet が GET リクエストを処理している間に入出力エラーが発生した場合。
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * 直接アクセスに対して管理者ユーザーが既にログインしていたらメニュー画面にリダイレクトさせる。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUserId") == null) {
			response.sendRedirect("login.jsp");
		} else {
			response.sendRedirect("menu.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @param request クライアントが Servlet へ要求したリクエスト内容を含む HttpServletRequest オブジェクト。
	 * @param response Servlet がクライアントに返すレスポンス内容を含む HttpServletResponse オブジェクト。
	 * @throws ServletException Servlet が POST リクエストを処理している間に入出力エラーが発生した場合。
	 * @throws IOException POST に相当するリクエストが処理できない場合。
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。<br>
	 * データベースに接続して管理者ユーザーのログインをチェックする。<br>
	 * ログインに成功したらセッション情報にユーザーIDをセットする。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = null;
		
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		UserDAO.getConnection();

		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		//セキュリティチェック
		if (userId == null || password == null) {
			String message = "不正アクセスの可能性があります。";
			session.setAttribute("message", message);
			System.out.println("動作確認、ここまで");		

        	url = "/JSPad/login.jsp";			
		}

		UserDAO userDao = new UserDAO();
		UserDTO returnUdt = userDao.loginUser(userId);
		
		//セキュリティチェック
        if (returnUdt == null) {  
        	String message = "失敗しました。IDまたはパスワードが違います。";
        	session.setAttribute("message", message);
        	
        	url = "/JSP/attendance_login.jsp";
        } else {
        
        String correctId = returnUdt.getUserId();
        String correctPass = returnUdt.getPassword();


		if (userId.equals(correctId) && password.equals(correctPass)) {
					
			UserDTO udt = new UserDTO(); //多分いらん
			
			session.setAttribute("udt", udt);
			session.setAttribute("loginUserId", userId);
			
			url = "/JSPad/menu.jsp";
		} else {
			String message = "ログインに失敗しました。IDまたはパスワードに誤りがあります。";
			session.setAttribute("message", message);
			url = "/JSPad/login.jsp";
		}
        }
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
}
