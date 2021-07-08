package control;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeDTO;
import model.WorkTimeDAO;
import model.WorkTimeDTO;

/**
 * Servlet implementation class CheckModifyWorktime
 * @author watanabekazuma
 * 指定された日時の時間を変更するために、データベースからその日時の時間情報をとってくるクラス。
 */
@WebServlet("/ModifyCheckWorktime")
public class ModifyCheckWorktime extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyCheckWorktime() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		    	doPost(request, response);
		}	
	//}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		EmployeeDTO employeeCode = (EmployeeDTO)session.getAttribute("employeeCode");
		
		String thisDate = request.getParameter("thisDate");
		
		LocalDateTime now = LocalDateTime.now();
		int mon = now.getMonthValue();
		String month = String.valueOf(mon);
		
		String thisMonthDate = month + "-" + thisDate;
		session.setAttribute("thisMonthDate", thisMonthDate);
		
		WorkTimeDAO.getConnection();
		WorkTimeDAO wdao = new WorkTimeDAO();
		WorkTimeDTO wd = wdao.selectWorktime(employeeCode, thisMonthDate);
		
		//System.out.println(wd);
		
		session.setAttribute("workdate", wd);
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSPad/modify_show_worktime.jsp");
		rd.forward(request, response);
	}

}
