package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * 画面表示のために出退勤時刻管理データベースと繋ぐDAOクラス。
 */
public class WorkTimeDAO {
	static Connection con = null;  //一度呼び出したら消えないようにするため、static
	String tableName = "worktime";
	static Statement stmt = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;

	/**
	 * @throws SQLException データベース処理に問題があった場合。
	 * 特定のデータベースとの接続(セッション)を生成する。
	 */
	public static Connection getConnection() {
		String dbName = "time_manage";
		if(con == null) {
			try {
				Class.forName("org.mariadb.jdbc.Driver");
					
				con = DriverManager.getConnection(
						"jdbc:mariadb://localhost:3306/" + dbName, "root", "scc1449scc");
				stmt = con.createStatement();
				System.out.println("データベースと接続しました...");
			} catch(Exception e) {
				System.out.println("データベース接続に失敗しました。");
				e.printStackTrace();
			}
		}
		return con;
	}	

	/**
	 * 特定のデータベースとの接続(セッション)を切断する。
	 */
	public void dbDiscon() {
		try {
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public String selectStartTime(EmployeeDTO employeeCode) { //throws SQLException
		try {
			
		String sql = "SELECT * FROM " + tableName + " WHERE employee_code = '" + employeeCode.getEmployee_code() +
				"' AND day = '" + LocalDate.now() + "';";
		rs = stmt.executeQuery(sql);
		if(rs.next()) {
			return "disabled";  
		} else {
			return null;
		}
		} catch (Exception e) {
			e.printStackTrace();
			return null;   //null=データがない=書き込める
		}
	}

	/**
	 * @param employeeCode 従業員コード。
	 * @return 退勤情報が既に存在していたら文字列"disable"、存在しなかったらnull。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * 退勤情報が既に存在しているかチェックする。
	 */
	public String selectFinishTime(EmployeeDTO employeeCode) {
		try {
		String sql = "SELECT * FROM " + tableName + " WHERE employee_code = '" + employeeCode.getEmployee_code() +
				"' AND day = '" + LocalDate.now() + "';";
		rs = stmt.executeQuery(sql);
		System.out.println(LocalDate.now());
		if(rs.next() && rs.getString(4) == null) {
		//if(rs.next()) {
			System.out.println(rs.getString(1));
			return "disablef";
		} else {
			return null;
		}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param employeeCode 従業員コード。
	 * @param thisMonth 月。
	 * @return 出退勤時刻管理用モデルクラスのリスト。
	 * @throws SQLException。 データベース処理に問題があった場合。
	 * 従業員コードと月から勤務記録を抽出する。
	 */
	public List<WorkTimeDTO> selectWorkTimeThisMonthList(EmployeeDTO employeeCode,String thisMonth)
			throws SQLException {
		List<WorkTimeDTO> workTimeThisMonthList = new LinkedList<WorkTimeDTO>();
		String sql = "SELECT * FROM " + tableName + " WHERE employee_code = '" + employeeCode.getEmployee_code() +
		"' AND day LIKE '" + thisMonth + "%';";
		ResultSet rs = stmt.executeQuery(sql);

		while(rs.next()){
			WorkTimeDTO workTime = new WorkTimeDTO();
			workTime.setWorkDate(LocalDate.parse(rs.getString(2),
					DateTimeFormatter.ofPattern("yyyy-MM-dd")) );
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
			if(rs.getString(3) != null) {
				LocalTime startTime = LocalTime.parse(rs.getString(3), dtf);
				workTime.setStartTime(startTime);
			}
			if(rs.getString(4) != null) {
				LocalTime finishTime = LocalTime.parse(rs.getString(4), dtf);
				workTime.setFinishTime(finishTime);
			}

			
			if(rs.getString(3) != null && rs.getString(4) != null) {
				//自動計算セットするメソッド
				workTime.calcWorkingHours();
			} else {
				System.out.println("実働時間が計算できません。");			}
			workTimeThisMonthList.add(workTime);
		}
		return workTimeThisMonthList;
	}
	
	public WorkTimeDTO selectWorktime(EmployeeDTO employeeCode, String thisMonthDate) {  //いっこだけとってくる
		WorkTimeDTO wd = null;
		try {
			String sql = "SELECT startTime, finishTime FROM " + tableName + " WHERE employee_code = '" + employeeCode.getEmployee_code() +
					"' AND day LIKE '%" + thisMonthDate + "%';";
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
		return wd;
	}
	
	public WorkTimeDTO updateWorktime(WorkTimeDTO wdto, String day) {
		try {
		String sql = "UPDATE " + tableName + " SET startTime = '" + wdto.getStartTime()
		+ "', finishTime = '" + wdto.getFinishTime()
		+ "' WHERE employee_code = '" + wdto.getEmployee_code() + "' AND day LIKE '%" + day + "%';";

		stmt.executeUpdate(sql);

		return wdto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}