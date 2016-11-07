package Database.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Like implements Serializable {
	private int userid;
	private int restid;
	
	@Id
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getRestid() {
		return restid;
	}
	public void setRestid(int restid) {
		this.restid = restid;
	}
}
