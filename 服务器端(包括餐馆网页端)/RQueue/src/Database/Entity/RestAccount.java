package Database.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RestAccount {
	private int restaccountid;
	private String phone;
	private String password;
	private int restid;
	
	@Id
	public int getRestaccountid() {
		return restaccountid;
	}
	public void setRestaccountid(int restaccountid) {
		this.restaccountid = restaccountid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRestid() {
		return restid;
	}
	public void setRestid(int restid) {
		this.restid = restid;
	}
	
}
