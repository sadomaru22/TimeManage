import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
static Connection con = null;	

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		String dbName = "time_manage";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			con = DriverManager.getConnection(
					"jdbc:mariadb://localhost:3306/" + dbName, "root", null);
			
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO worktime (startTime, finishTime) VALUES (?, ?)");
			ps.setString(1, now.format(timeFormat));
			ps.setString(2, "110930");
			
			//ps.setString(1, "090003");
			
			ResultSet rs = ps.executeQuery();
			
			System.out.println(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
