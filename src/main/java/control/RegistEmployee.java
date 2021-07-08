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
 * Servlet implementation class RegistEmployee
 * @author watanabekazuma
 * 新規従業員情報をデータベースに登録するクラス。
 */
@WebServlet("/RegistEmployee")
public class RegistEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistEmployee() {
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
		
		EmployeeDAO.getConnection();
		
		String employee_code = request.getParameter("employee_code");
		String name = request.getParameter("name");
		String post = request.getParameter("post");
		String password = request.getParameter("password");
		
		//regist_employee.jspから受け取った値をDTOにセット
		EmployeeDTO edt = new EmployeeDTO();
		edt.setEmployee_code(employee_code);
		edt.setName(name);
		edt.setPost(post);
		edt.setPassword(password);
		
		EmployeeDAO eda = new EmployeeDAO();
		
		eda.insertEmployee(edt);
		
		HttpSession session = request.getSession();
		session.setAttribute("account", eda);
		
        RequestDispatcher rd = request.getRequestDispatcher("/JSPad/completion.jsp");
        rd.forward(request, response);		
	}

}
