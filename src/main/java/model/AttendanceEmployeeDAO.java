package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Kazuma Watanabe
 * 出退勤時刻管理データベースと繋ぐDAOクラス。
 */
public class AttendanceEmployeeDAO {
	static Connection con = null;  //一度呼び出したら消えないようにするため、static
	String tableName = "employee";
	String tableName2 = "worktime";
	static Statement stmt = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;

	/**
	 * 日付/時間オブジェクトの出力および解析のためのフォーマッタ。<br>
	 * "HH:mm:ss"のフォーマットで表記。
	 */
	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
	/**
	 * 日付/時間オブジェクトの出力および解析のためのフォーマッタ。<br>
	 * "HH:mm:ss"のフォーマットで表記。
	 */

	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

	/**
	 * @param employeeCode 従業員コード。
	 * @param password 対応するパスワード。
	 * @return データベースと一致していたら従業員コード、一致していなかったらnull。
	 * @throws SQLException データベース処理に問題があった場合。
	 * 指定されたemployeeCodeとpasswordから従業員がログインできるかどうかチェックする。
	 */
	public EmployeeDTO loginEmployee(String id) {
		EmployeeDTO returnEd = null;
		
		try {
		String sql = "SELECT * FROM employee WHERE employee_code=?";
	
		ps = con.prepareStatement(sql);  //
		
		ps.setString(1, id);
		
		rs = ps.executeQuery();  //
		
		if (rs.next()) {
			returnEd = new EmployeeDTO(
					rs.getString("employee_code"),
					rs.getString("password")
					);
			} else {
				//アカウントがなければnullを返す
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnEd;
	}

	/**
	 * @param employeeCode 従業員コード。
	 * @return データベースに出勤情報を挿入出来たらtrue、出来なかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * タイムカード出勤時間をテーブルに記録する。
	 */
	public boolean setStartTime(EmployeeDTO employeeCode) throws SQLException {
		//con.setAutoCommit(false);
	
		LocalDateTime now = LocalDateTime.now();
		//既にその日のデータが追加されていたらfalseを返す
		//WHERE句でemployeeCodeだけでは参照型変数としか認識されないので、必ずget()をつける
		String sql = "SELECT * from worktime WHERE employee_code = '" + employeeCode.getEmployee_code()
				+ "' and day = '" + now.format(dateFormat) + "';";
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next()) {
			return false;
		} else {
			System.out.println("-----");
			System.out.println(employeeCode.getEmployee_code());
			sql = "INSERT INTO worktime (employee_code, day, startTime) VALUES ('"
			+ employeeCode.getEmployee_code() + "', '" + now.format(dateFormat) + "', '"
			+ now.format(timeFormat) + "' );";
			stmt.executeUpdate(sql);
			//con.commit();  //トランザクション
			return true;
		}
	}

	/**
	 * @param employeeCode 従業員コード。
	 * @return データベースに退勤情報を更新出来たらtrue、出来なかったらfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * タイムカード退勤時間をテーブルに記録する。
	 */
	public boolean setFinishTime(EmployeeDTO employeeCode) throws SQLException {
		//con.setAutoCommit(false);
		LocalDateTime now = LocalDateTime.now();
		//出勤が押されていなかったらfalseを返す
		String sql = "SELECT * from worktime WHERE employee_code = '" + employeeCode.getEmployee_code()
				+ "' and day = '" + now.format(dateFormat) + "';";
		ResultSet rs = stmt.executeQuery(sql);
		if(!rs.next()) {
		
		System.out.println("退勤時間は入っています");
			return false;
		} else {
			sql = "UPDATE worktime SET finishTime='" + now.format(timeFormat)
					+ "' WHERE employee_code='" + employeeCode.getEmployee_code() + 
					"' AND day='" + now.format(dateFormat) + "'";
			
			System.out.println("UPDATEのSQL= " + sql);

			int result = stmt.executeUpdate(sql);

			System.out.println("result = " + result);

			//con.commit();
			return true;
		}
	}

}