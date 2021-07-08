import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;

import model.EmployeeDTO;
import model.WorkTimeDTO;

public class testtry {
	static Connection con = null;  //一度呼び出したら消えないようにするため、static
	String tableName = "worktime";
	static Statement stmt = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	public static Connection getConnection() {
		String dbName = "time_manage";
		if(con == null) {
			try {
				Class.forName("org.mariadb.jdbc.Driver");
					
				con = DriverManager.getConnection(
						"jdbc:mariadb://localhost:3306/" + dbName, "root", null);
				stmt = con.createStatement();
				System.out.println("データベースと接続しました...");
			} catch(Exception e) {
				System.out.println("データベース接続に失敗しました。");
				e.printStackTrace();
			}
		}
		return con;
	}
	
	public WorkTimeDTO selectWorktime(EmployeeDTO employeeCode, String thisMonthDate) {  //いっこだけとってくる
		WorkTimeDTO wd = null;
		System.out.println(employeeCode.getEmployee_code());
		System.out.println(thisMonthDate);
		try {
			String sql = "SELECT startTime, finishTime FROM " + tableName + " WHERE employee_code = '" + employeeCode.getEmployee_code() +
					"' AND day LIKE '%" + thisMonthDate + "';";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				wd = new WorkTimeDTO();
				wd.setStartTime(LocalTime.parse(rs.getString(1)));
				wd.setFinishTime(LocalTime.parse(rs.getString(2)));
			}
		} catch (Exception e) {
			System.out.println("SELECT失敗");
			e.printStackTrace();
		}
		System.out.println("----");
		System.out.println(wd);
		return wd;
	}
}
