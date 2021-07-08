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

import model.CsvOutput2;
import model.WorkTimeDTO;

/**
 * Servlet implementation class CSVOutputServlet
 */
@WebServlet("/CSVOutputServlet")
public class CSVOutputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String filename = "sum_worktime.csv";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CSVOutputServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
//		response.setHeader("Content-Type", "text/csv; charset=UTF-8");
		
		HttpSession session = request.getSession();
		int sum = (int)session.getAttribute("sum");
		
		List<WorkTimeDTO> workTimeThisMonthList =
				(List<WorkTimeDTO>) session.getAttribute("workTimeThisMonthList");
		
		CsvOutput2.csvWrite(request, response, workTimeThisMonthList, sum);
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/csvOutput_comp.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
