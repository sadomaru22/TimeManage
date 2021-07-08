package model;
import java.io.Serializable;

public class EmployeeDTO implements Serializable {
	private String employee_code;
	private String name;
	private String post;
	private String password;
	
	public EmployeeDTO() {}
	
	public EmployeeDTO(String employee_code, String name, String post, String password) {
		this.employee_code = employee_code;
		this.name = name;
		this.post = post;
		this.password = password;
	}
	
	//★コンストラクタは引数が違っていれば同じ名前でも複数作れる（↓従業員ログイン用）
	public EmployeeDTO(String employee_code, String password) {
		this.employee_code = employee_code;
		this.password = password;
	}
	
	//管理者側で一覧表示するときに使う
	public EmployeeDTO(String employee_code, String name, String post) {
		this.employee_code = employee_code;
		this.name = name;
		this.post = post;
	}

	public String getEmployee_code() {
		return employee_code;
	}

	public void setEmployee_code(String employee_code) {
		this.employee_code = employee_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
