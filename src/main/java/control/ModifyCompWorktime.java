package control;

import java.io.IOException;
import java.time.LocalTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.WorkTimeDAO;
import model.WorkTimeDTO;

/**
 * Servlet implementation class ModifyCompWorktime
 * @author watanabekazuma
 * 既存のデータベースの時間情報を入力したものに更新するクラス。
 */
@WebServlet("/ModifyCompWorktime")
public class ModifyCompWorktime extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyCompWorktime() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		String day = request.getParameter("day");
		
		String employee_code = request.getParameter("employeeCode");
		String startTime = request.getParameter("startTime");
		String finishTime = request.getParameter("finishTime");
		
		//String型からLocalTime型にキャスト(WorkTimeDTOに入れるため)
		LocalTime st = LocalTime.parse(startTime);
		LocalTime ft = LocalTime.parse(finishTime);
		
		WorkTimeDTO wdto = new WorkTimeDTO();
		wdto.setEmployee_code(employee_code);
		wdto.setStartTime(st);
		wdto.setFinishTime(ft);
		
		WorkTimeDAO wdao = new WorkTimeDAO();
		WorkTimeDAO.getConnection();
		
		wdto = wdao.updateWorktime(wdto, day);
		
		session.setAttribute("employeeCode", wdto.getEmployee_code());
		
		RequestDispatcher dispatch = request.getRequestDispatcher("/JSPad/modify_completion.jsp");
		dispatch.forward(request, response);
		
		
		System.out.println(day);
		System.out.println(startTime);
	}

}
