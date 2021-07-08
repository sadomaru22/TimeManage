package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 管理者データベースと繋ぐDAOクラス。
 */
public class UserDAO {
	static Connection con = null;  //一度呼び出したら消えないようにするため、static
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
	 * @param userId - ユーザーID。
	 * @param password - パスワード。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * 指定されたemployeeCodeとpasswordから管理者ユーザーがログインできるかどうかチェックする。
	 */
//	public boolean loginUser(String userId, String password) throws SQLException, NoSuchAlgorithmException {
//
//		boolean loginUserChkFlag = false;
//
//		//パスワードをハッシュ化
//		MessageDigest digest = MessageDigest.getInstance("SHA-1");
//		byte[] passwordDigest = digest.digest(password.getBytes());
//		String sha2 = String.format("%040x", new BigInteger(1, passwordDigest));
//
//		// user_idとpasswordがマッチしたユーザレコードを取得する
//		String sql = "SELECT * FROM userinfo WHERE user_id='"
//				+ userId + "' AND password='" + sha2.substring(8) + "';";
//		ResultSet rs = stmt.executeQuery(sql);
//
//		// マッチしたデータがあればtrueを代入する
//		if (rs.next()) {
//			if (userId.equals(rs.getString(1))) {
//				if (sha2.substring(8).equals(rs.getString(2))) {
//					loginUserChkFlag = true;
//				}
//			}
//		}
//		return loginUserChkFlag;
//	}
	
	public UserDTO loginUser(String userId) {
		UserDTO userDto = null;
		
		try {
			String sql = "SELECT * FROM userinfo WHERE userId=?";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, userId);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				userDto = new UserDTO(
						rs.getString("userId"),
						rs.getString("password")
						);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDto;
	}

	/**
	 * @param userId - ユーザーID。
	 * @param password - パスワード。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * 管理者ユーザーの情報を新規追加する。
	 */
	public void insertUser(String userId, String password) {
		try {
		String sql = "INSERT INTO userInfo (userId, password) VALUES (?, ?)";
		ps = con.prepareStatement(sql);
		
		ps.setString(1, userId);
		ps.setString(2, password);
		
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
}
