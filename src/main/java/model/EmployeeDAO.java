package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Kazuma Watanabe
 * 従業員データベースと繋ぐDAOクラス。
 */
public class EmployeeDAO {
	static Connection con = null;  //一度呼び出したら消えないようにするため、static
	String tableName = "employee";
	String tableName2 = "worktime";
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

	/**
	 * @param employee_code 従業員コード。
	 * @param name 従業員の氏名。
	 * @param post 部署。
	 * @param password パスワード。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * 従業員情報を新規追加する。
	 */
	public void insertEmployee(EmployeeDTO edt) {
		try {
			String sql = "INSERT INTO employee (employee_code, name, post, password) VALUES (?, ?, ?, ?)";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, edt.getEmployee_code());
			ps.setString(2, edt.getName());
			ps.setString(3, edt.getPost());
			ps.setString(4, edt.getPassword());
			
            int r = ps.executeUpdate();
            
            if(r != 0) {
           	 System.out.println("新規登録成功！");
            } else {
           	 System.out.println("新規登録失敗...");
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param employee - 従業員クラス。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * 従業員の情報をアップデートする。（従業員情報編集機能）
	 */
	public EmployeeDTO updateEmployee(EmployeeDTO employee) {
		//con.setAutoCommit(false);
		try {
		String sql = "UPDATE employee SET name = '" + employee.getName()
		+ "', post = '" + employee.getPost()
		+ "', password = '"+ employee.getPassword()
		+ "' WHERE employee_code = '" + employee.getEmployee_code() + "';";

		//int count = 
		stmt.executeUpdate(sql);

//		if (count > 0) {
//			con.commit();
//		}
		return employee;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param employeeCode - 従業員コード。
	 * @return 対応する従業員、存在しない場合null。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * 指定されたemployeeCodeから従業員の情報を取得して、Employee型で返す。
	 */
	public EmployeeDTO selectEmployee(EmployeeDTO employeeCode) {
		try {
		String sql = "SELECT * FROM employee WHERE employee_code = '"
				+ employeeCode.getEmployee_code() + "';";

		ResultSet rs = stmt.executeQuery(sql);

		EmployeeDTO employee = null;

		if(rs.next() && rs.getString(1).equals(employeeCode.getEmployee_code())){
			employee = new EmployeeDTO();
			employee.setEmployee_code(rs.getString(1));
			employee.setName(rs.getString(2));
			employee.setPost(rs.getString(3));
		}
		return employee;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<EmployeeDTO> selectEmployeeAll() {
		ArrayList<EmployeeDTO> list = new ArrayList<>();
		
		String sql = "SELECT * FROM " + tableName;
		
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				EmployeeDTO p = new EmployeeDTO(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3)
						);
				list.add(p);
			}	
		} catch (Exception e) {
			System.out.println("SELECT文の実行に失敗しました");
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * @param employeeCode - 従業員コード。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * 指定されたemployeeCodeの従業員情報を削除する。
	 */
	public void deleteEmployee(EmployeeDTO employeeCode) {
		try {
		//con.setAutoCommit(false);
		String sql = "DELETE FROM employee WHERE employee_code = '"
				+ employeeCode.getEmployee_code() + "';";
		stmt.executeUpdate(sql);

//		if (count > 0) {
//			con.commit();
//		}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
