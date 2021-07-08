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
 * Servlet implementation class EditEmployee
 * @author Kazuma Watanabe
 * 既存のデータベースの情報を入力された情報に更新するクラス。
 */
@WebServlet("/EditEmployee")
public class EditEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditEmployee() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUserId") == null) {
			response.sendRedirect("/TimeManage/JSPad/login.jsp");
		} else {
			response.sendRedirect("/TimeManage/JSPad/menu.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//HttpSession session = request.getSession();
		//EmployeeDTO employee = (EmployeeDTO)session.getAttribute("employee"); hiddenタグ使えばこの2行必要ない
		
		String employee_code = request.getParameter("employee_code");  //hiddenタグ
		String name = request.getParameter("name");
		String post = request.getParameter("post");
		String password = request.getParameter("password");
		
		//edit_employee/jspから受け取った値をDTOにセット
		EmployeeDTO edto = new EmployeeDTO();
		edto.setEmployee_code(employee_code);
		edto.setName(name);
		edto.setPost(post);
		edto.setPassword(password);
		
		EmployeeDAO empdao = new EmployeeDAO();
		EmployeeDAO.getConnection();
		
		edto = empdao.updateEmployee(edto);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("/JSPad/edit_completion.jsp");
		dispatch.forward(request, response);
	}

}
