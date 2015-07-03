package so.pickme.response;

import java.io.Serializable;

public class SignupDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4261275338964400645L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String repassword;
	private String email;
	private String reemail;
	private String role;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReemail() {
		return reemail;
	}
	public void setReemail(String reemail) {
		this.reemail = reemail;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	

}
