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

/**
 * Servlet implementation class RegistAdminuser
 * @author watanabekazuma
 * 新規管理者のuserIdとpasswordをデータベースに登録するクラス。
 */
@WebServlet("/RegistAdminuser")
public class RegistAdminuser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistAdminuser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		UserDAO.getConnection();
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		UserDAO udao = new UserDAO();
		
		udao.insertUser(userId, password);
		
		HttpSession session = request.getSession();
		session.setAttribute("loginUserId", userId);
		
        RequestDispatcher rd = request.getRequestDispatcher("/JSPad/completion.jsp");
        rd.forward(request, response);	
	}

}
