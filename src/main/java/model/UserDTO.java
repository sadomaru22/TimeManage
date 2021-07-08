package model;
import java.io.Serializable;

public class UserDTO implements Serializable {
	private String userId;
	private String password;
	
	public UserDTO() {}
	
	public UserDTO(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String user_id) {
		this.userId = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
